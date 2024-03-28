package com.bundev.gexplorer_mobile

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
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
import androidx.compose.material3.NavigationBarDefaults.containerColor
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bundev.gexplorer_mobile.icons.filled.Map
import com.bundev.gexplorer_mobile.icons.filled.SocialLeaderboard
import com.bundev.gexplorer_mobile.icons.outlined.Map
import com.bundev.gexplorer_mobile.icons.outlined.SocialLeaderboard
import com.bundev.gexplorer_mobile.pages.AccountPage
import com.bundev.gexplorer_mobile.pages.MapPage
import com.bundev.gexplorer_mobile.pages.ScoresPage
import com.bundev.gexplorer_mobile.pages.SettingsPage
import com.bundev.gexplorer_mobile.ui.theme.GexplorermobileTheme
import com.bundev.gexplorer_mobile.classes.Funi
import com.bundev.gexplorer_mobile.classes.JustAVariable
import java.util.Locale

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val iconFilled: ImageVector,
    val iconOutline: ImageVector
) {
    data object Map :
        Screen(
            "map",
            R.string.map,
            GexplorerIcons.Filled.Map,
            GexplorerIcons.Outlined.Map
        )

    data object Scores :
        Screen(
            "scores",
            R.string.scores,
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
}

val items = listOf(
    Screen.Map,
    Screen.Scores,
    Screen.Account,
    Screen.Settings
)

val funi = Funi()
val systemOfUnits = JustAVariable("metric")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(Locale.getDefault().language))
            GexplorermobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Enables checking the current configuration of the phone
                    val configuration = LocalConfiguration.current

                    // Everything for the navigation to work while in portrait and landscape orientation
                    val navController = rememberNavController()
                    var selectedTab by rememberSaveable {
                        mutableStateOf(Screen.Map.route)
                    }
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
                                composable(Screen.Map.route) { MapPage(funi) }
                                composable(Screen.Scores.route) { ScoresPage(systemOfUnits) }
                                composable(Screen.Account.route) { AccountPage(funi) }
                                composable(Screen.Settings.route) {
                                    SettingsPage(
                                        systemOfUnits,
                                        funi
                                    )
                                }
                            }
                        }
                    }
                    if (configuration.orientation == ORIENTATION_PORTRAIT) {
                        Scaffold(
                            topBar = {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    color = containerColor,
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
                                                    containerColor
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
            }
        }
    }
}

@Preview(showBackground = true, locale = "pl", name = "pl")
@Composable
fun SettingsPagePreview() {
    SettingsPage()
}

@Preview(showBackground = true)
@Composable
fun AccountPagePreview() {
    AccountPage()
}