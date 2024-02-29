package com.example.gexplorer_mobile

import android.content.res.Configuration.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gexplorer_mobile.ui.theme.GexplorermobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GexplorermobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainPagePreview()
                    SettingsPagePreview()

                    val navController = rememberNavController()
                    var selectedTab by rememberSaveable {
                        mutableStateOf(Screen.Main.route)
                    }
                    val configuration = LocalConfiguration.current

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    if (configuration.orientation == ORIENTATION_PORTRAIT) {
                        Scaffold(
                            topBar = {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .background(color = colorResource(id = R.color.primary)),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,

                                    ) {
                                    Text(
                                        text = stringResource(id = R.string.app_name),
                                        fontSize = 18.sp,
                                        color = colorResource(id = R.color.black)
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.gexplorer_logo),
                                        contentDescription = null,
                                        modifier = Modifier.padding(all = 2.dp)
                                    )
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
                            NavHost(
                                navController,
                                startDestination = Screen.Main.route,
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                composable(Screen.Main.route) { MainPage() }
                                composable(Screen.Map.route) { MapPage() }
                                composable(Screen.Account.route) { AccountPage() }
                                composable(Screen.Settings.route) { SettingsPage() }
                            }

                        }
                    } else {
                        Row (modifier = Modifier.fillMaxSize()){
                            NavigationRail {
                                Image(
                                    painter = painterResource(id = R.drawable.gexplorer_logo),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(top = 6.dp, bottom = 6.dp)
                                        .height(56.dp)
                                )
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
                            NavHost(
                                navController,
                                startDestination = Screen.Main.route
                            ) {
                                composable(Screen.Main.route) { MainPage() }
                                composable(Screen.Map.route) { MapPage() }
                                composable(Screen.Account.route) { AccountPage() }
                                composable(Screen.Settings.route) { SettingsPage() }
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val iconFilled: ImageVector,
    val iconOutline: ImageVector
) {
    data object Main :
        Screen("main", R.string.main, Icons.Filled.Home, Icons.Outlined.Home)

    data object Map :
        Screen("map", R.string.map, Icons.Filled.LocationOn, Icons.Outlined.LocationOn)

    data object Account :
        Screen("account", R.string.account, Icons.Filled.Person, Icons.Outlined.Person)

    data object Settings :
        Screen("settings", R.string.settings, Icons.Filled.Settings, Icons.Outlined.Settings)
}

val items = listOf(
    Screen.Main,
    Screen.Map,
    Screen.Account,
    Screen.Settings
)

//@Composable
//fun NavigationBarLayout(
//    destination: NavDestination,
//    onMenuItemSelected: (String) -> Unit,
//    content: @Composable (innerPadding: PaddingValues) -> Unit
//) {
//
//
//}

@Composable
fun MainPage() {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Witaj w:", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            shadowElevation = 5.dp,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Gexplorer", fontSize = 50.sp, modifier = Modifier.padding(10.dp))
        }
        Text(text = "achievements")
        Text(text = "(połączenie z API)")
    }
}

@Composable
fun SettingsPage() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Settings page")
        Text(
            text = "Choose 1",
            modifier = Modifier.background(colorResource(R.color.primary))
        )

        Text(text = "Theme, language -> default from android")
    }
}

@Composable
fun AccountPage() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Użytkowniku!")
        Text(text = "Zesrałeś się")
    }
}

@Composable
fun MapPage() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Mapa tu będzie ig")
        Text(text = "Powodzenia Stachu")
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    MainPage()
}

@Preview(showBackground = true)
@Composable
fun SettingsPagePreview() {
    SettingsPage()
}

@Preview(showBackground = true)
@Composable
fun MapPagePreview() {
    MapPage()
}

@Preview(showBackground = true)
@Composable
fun AccountPagePreview() {
    AccountPage()
}