package com.bundev.gexplorer_mobile

import android.app.Application
import android.content.res.Configuration
import android.icu.util.MeasureUnit
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bundev.gexplorer_mobile.classes.Funi
import com.bundev.gexplorer_mobile.icons.filled.Map
import com.bundev.gexplorer_mobile.icons.filled.SocialLeaderboard
import com.bundev.gexplorer_mobile.icons.outlined.Map
import com.bundev.gexplorer_mobile.icons.outlined.SocialLeaderboard
import com.bundev.gexplorer_mobile.icons.simple.ExploreNearby
import com.bundev.gexplorer_mobile.icons.simple.Walk
import com.bundev.gexplorer_mobile.pages.AchievementsPage
import com.bundev.gexplorer_mobile.pages.MapPage
import com.bundev.gexplorer_mobile.pages.PlacesPage
import com.bundev.gexplorer_mobile.pages.SettingsPage
import com.bundev.gexplorer_mobile.pages.TripsPage
import com.bundev.gexplorer_mobile.pages.account.AccountPage
import com.bundev.gexplorer_mobile.pages.tripdetail.TripDetailPage
import com.bundev.gexplorer_mobile.ui.theme.GexplorerTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class GexplorerApplication : Application()

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val iconFilled: ImageVector,
    val iconOutline: ImageVector = iconFilled,
) {
    data object Map :
        Screen(
            "map",
            R.string.map,
            GexplorerIcons.Filled.Map,
            GexplorerIcons.Outlined.Map
        )

    data object Trips :
        Screen(
            "trips",
            R.string.trips,
            GexplorerIcons.Simple.Walk
        )

    data object Achievements :
        Screen(
            "achievements",
            R.string.achievements,
            GexplorerIcons.Filled.SocialLeaderboard,
            GexplorerIcons.Outlined.SocialLeaderboard
        )

    data object Account :
        Screen(
            "account",
            R.string.account,
            Icons.Filled.Person,
            Icons.Outlined.Person
        )

    data object Settings :
        Screen(
            "settings",
            R.string.settings,
            Icons.Filled.Settings,
            Icons.Outlined.Settings
        )

    data object TripDetail :
        Screen(
            "trip/{tripId}",
            R.string.trip,
            GexplorerIcons.Simple.Walk
        )

    data object Places :
        Screen(
            "places",
            R.string.places,
            GexplorerIcons.Simple.ExploreNearby
        )
}

val items = listOf(
    Screen.Map,
    Screen.Trips,
    Screen.Places,
    Screen.Account
)

val funi = Funi()
var measureUnit: MeasureUnit = MeasureUnit.METER
var selectedTabSave = ""

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(Locale.getDefault().language))
            GexplorerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GexplorerNavigation()
                }
            }
        }
        //Hides the ugly action bar at the top
        actionBar?.hide()
        //Hide the status bars
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            @Suppress("DEPRECATION")
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}

@Composable
private fun GexplorerNavigation() {
    // Enables checking the current configuration of the phone
    val configuration = LocalConfiguration.current
    // Everything for the navigation to work while in portrait and landscape orientation
    val navController = rememberNavController()
    var selectedTab by rememberSaveable {
        mutableStateOf(Screen.Map.route)
    }
    selectedTabSave = selectedTab
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    // Single navigation host for both navigationBar and navigationRail
    val navHost = remember {
        movableContentOf<PaddingValues> { innerPadding ->
            NavHost(
                navController,
                startDestination = Screen.Map.route,
                modifier = Modifier.padding(innerPadding),
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                composable(Screen.Map.route) { MapPage() }
                composable(Screen.Trips.route) {
                    TripsPage(navController) { selectedTab = selectedTabSave }
                }
                composable(Screen.Achievements.route) { AchievementsPage() }
                composable(Screen.Places.route) { PlacesPage() }
                composable(Screen.Account.route) {
                    AccountPage(navController) { selectedTab = selectedTabSave }
                }
                composable(Screen.Settings.route) { SettingsPage() }
                composable(Screen.TripDetail.route) { backStackEntry ->
                    TripDetailPage(
                        tripId = backStackEntry.arguments?.getString("tripId"),
                        navController = navController
                    ) { selectedTab = selectedTabSave }
                }
            }
        }
    }
    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Scaffold(
            topBar = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    color = NavigationBarDefaults.containerColor,
                    tonalElevation = NavigationBarDefaults.Elevation
                ) {
                    TextButton(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                            val route = Screen.Map.route
                            if (selectedTab != route) {
                                selectedTab = route
                                navController.navigate(route)
                            }
                        },
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.app_name),
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.contentColorFor(
                                    NavigationBarDefaults.containerColor
                                )
                            )
                            Image(
                                painter = painterResource(id = R.drawable.gexplorer_logo),
                                contentDescription = null,
                                modifier = Modifier.padding(all = 2.dp)
                            )
                        }
                    }
                }
            },
            bottomBar = {
                NavigationBar {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selectedTab == screen.route) {
                                        screen.iconFilled
                                    } else {
                                        screen.iconOutline
                                    },
                                    contentDescription = null
                                )
                            },
                            label = { Text(stringResource(screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any
                            { navDest -> navDest.route == screen.route } == true,
                            onClick = {
                                selectedTab = screen.route
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = false
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            navHost(innerPadding)
        }
    } else { //Navigation while phone is horizontal
        Row(modifier = Modifier.fillMaxSize()) {
            NavigationRail {
                TextButton(onClick = {
                    val route = Screen.Map.route
                    if (selectedTab != route) {
                        selectedTab = route
                        navController.navigate(route)
                    }
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.gexplorer_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 6.dp, bottom = 6.dp)
                            .height(56.dp)
                    )
                }
                items.forEach { screen ->
                    NavigationRailItem(
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == screen.route) {
                                    screen.iconFilled
                                } else {
                                    screen.iconOutline
                                },
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any
                        { navDest -> navDest.route == screen.route } == true,
                        onClick = {
                            selectedTab = screen.route
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
            navHost(PaddingValues())
        }
    }
}