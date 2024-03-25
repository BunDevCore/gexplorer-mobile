package com.example.gexplorer_mobile

//ORIGINAL ACTIVITY -> import androidx.activity.ComponentActivity
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.util.Log
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Looper
//ORIGINAL ACTIVITY -> import androidx.activity.ComponentActivity
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gexplorer_mobile.icons.filled.Map
import com.example.gexplorer_mobile.icons.outlined.Map
import com.example.gexplorer_mobile.icons.filled.SocialLeaderboard
import com.example.gexplorer_mobile.icons.outlined.SocialLeaderboard
import com.example.gexplorer_mobile.ui.theme.GexplorermobileTheme
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.Location
import com.mapbox.common.location.LocationObserver
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PolygonAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation

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

//val locationPermissions =
//    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
//
//Location permission listener
//var permissionsListener: PermissionsListener = object : PermissionsListener {
//    override fun onExplanationNeeded(permissionsToExplain: List<String>) {
//        Log.e("PERMISSIONS", "explain it to me")
//        ActivityCompat.requestPermissions(MainActivity(), locationPermissions, 1)
//    }
//
//    override fun onPermissionResult(granted: Boolean) {
//        Log.e("PERMISSIONS", "huh idk")
//        TODO("Not yet implemented")
//    }
//}

var locationPermissionState = "None"
var bruh = 0

//var locationResult = arrayOf<android.location.Location>()
val permissions = arrayOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION,
)

class MainActivity : AppCompatActivity() {
    //    private lateinit var locationCallback: LocationCallback
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        locationPermissionState = when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                "Fine"
            }

            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                "Coarse"
            }

            else -> {
                // No location access granted.
                "None"
            }
        }
    }

    //    private lateinit var permissionsManager: PermissionsManager
    override fun onCreate(savedInstanceState: Bundle?) {
//        // Before you perform the actual permission request, check whether your app
//        // already has the permissions, and whether your app needs to show a permission
//        // rationale dialog. For more details, see Request permissions.

//        if (PermissionsManager.areLocationPermissionsGranted(baseContext)) {
//            Log.i("PERMISSIONS", "ALREADY GRANTED")
//        } else {
//            Log.i("PERMISSIONS", "GRANTING PROCESS")
//            permissionsManager = PermissionsManager(permissionsListener)
//            Log.e("PERMISSIONS", "Got out of listener")
//            val locationRequest = permissionsManager.requestLocationPermissions(this)
//            Log.i("PERMISSIONS", "Sent request $locationRequest")
//            val answer = permissionsManager.onRequestPermissionsResult(
//                1,
//                locationPermissions,
//                intArrayOf(PERMISSION_GRANTED, PERMISSION_GRANTED)
//            )
//            Log.i("PERMISSION", "Got an answer: $answer")
//        }
        locationPermissionRequest.launch(permissions)
        if (checkSelfPermission(permissions[1]) == PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, permissions, 1)
        }

        super.onCreate(savedInstanceState)
        setContent {
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
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                composable(Screen.Map.route) { MapPage() }
                                composable(Screen.Scores.route) { ScoresPage() }
                                composable(Screen.Account.route) { AccountPage() }
                                composable(Screen.Settings.route) { SettingsPage() }
                            }
                        }
                    }
                    if (configuration.orientation == ORIENTATION_PORTRAIT) {
                        Scaffold(//Navigation while phone is vertical
                            topBar = {
                                TextButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .background(color = colorResource(id = R.color.primary)),
                                    onClick = {
                                        val route = Screen.Map.route
                                        if (selectedTab != route) {
                                            selectedTab = route
                                            navController.navigate(route)
                                        }
                                    }
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.app_name),
                                            fontSize = 18.sp,
                                            color = colorResource(id = R.color.primaryText)
                                        )
                                        Image(
                                            painter = painterResource(id = R.drawable.gexplorer_logo),
                                            contentDescription = null,
                                            modifier = Modifier.padding(all = 2.dp)
                                        )
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

@OptIn(MapboxExperimental::class)
@Composable
fun MapPage() {
    val context = LocalContext.current

//    val locationPermissionRequest = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        locationPermission = when {
//            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
//                // Precise location access granted.
//                "Fine"
//            }
//
//            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
//                // Only approximate location access granted.
//                "Coarse"
//            }
//
//            else -> {
//                // No location access granted.
//                "None"
//            }
//        }
//    }

//    if (context.checkSelfPermission(permissions[1]) == PERMISSION_DENIED) {
//        locationPermissionRequest.launch(permissions)
//    }
//    val openPermissionDialog = remember {
//        mutableStateOf(false)
//    }
//    if (context.checkSelfPermission(permissions[0]) == PERMISSION_DENIED) {
//        Dialog(onDismissRequest = { openPermissionDialog.value = false }) {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height((56 * 2 + 68 * 2).dp)
//                    .padding(16.dp),
//                shape = RoundedCornerShape(16.dp),
//
//                ) {
//                Text(
//                    text = stringResource(id = R.string.location_dialog_title),
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(all = 20.dp)
//                )
//                Text(
//                    text = stringResource(id = R.string.location_dialog_content),
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.padding(start = 16.dp)
//                )
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp),
//                    horizontalAlignment = Alignment.End
//                ) {Row {
//                    TextButton(
//                        onClick = { openPermissionDialog.value = false }) {
//                        Text(text = stringResource(id = R.string.cancel))
//                    }
//                    TextButton(onClick = { openPermissionDialog.value = false
//                        locationPermissionRequest.launch(permissions)
//                    }) {
//                        Text(text = stringResource(id = R.string.ok))
//                    }
//                }
//                }
//            }
//        }
//    }

    var currentLocation: Location? = null

    val locationService: LocationService = LocationServiceFactory.getOrCreate()
    var locationProvider: DeviceLocationProvider? = null
    val request = LocationProviderRequest.Builder().interval(
        IntervalSettings.Builder().interval(0L).minimumInterval(0L).maximumInterval(0L)
            .build()
    ).displacement(0F).accuracy(AccuracyLevel.HIGHEST).build()
    val result = locationService.getDeviceLocationProvider(request = request)
    if (result.isValue) {
        locationProvider = result.value!!
    } else {
        Log.e("MapBox Location Service", "Failed to get device location provider")
    }

    val locationObserver =
        LocationObserver { locations -> Log.i("Location update", locations.toString()) }
    locationProvider!!.addLocationObserver(locationObserver) //, looper = Looper.myLooper()!!

    val lastLocationCancellable = locationProvider.getLastLocation { lastLocation ->
        lastLocation?.let {
            currentLocation = lastLocation
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(locationPermissionState)
        Text(
            "Fine: ${if (context.checkSelfPermission(permissions[1]) == PERMISSION_GRANTED) "Granted" else "Denied"} " +
                    "Coarse: ${if (context.checkSelfPermission(permissions[0]) == PERMISSION_GRANTED) "Granted" else "Denied"}"
        )
//        Text("GRANTED $PERMISSION_GRANTED | DENIED $PERMISSION_DENIED")
//        Text("FINE $permissionStatus COARSE")
        Button(onClick = {
            Toast.makeText(
                context,
                "Obecna dozwolona lokalizacja: $locationPermissionState",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text(text = "Sprawdź lokalizację tutaj")
        }
        //Documentation here: https://docs.mapbox.com/android/maps/guides/
        MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = MapViewportState().apply {
                setCameraOptions {
                    zoom(10.0)
                    center(Point.fromLngLat(18.6570989, 54.3542712))
                    pitch(0.0)
                    bearing(0.0)
                }
            }
        ) {
            val points = listOf(
                listOf(
                    Point.fromLngLat(18.615274605637016, 54.40211158342004),
                    Point.fromLngLat(18.730974363868317, 54.37152378253998),
                    Point.fromLngLat(18.6650564007217, 54.29906183330589),
                    Point.fromLngLat(18.6547192660595, 54.355547811237834),
                    Point.fromLngLat(18.615274605637016, 54.40211158342004)
                )
            )
//            1111111111if (currentLocation is Location) {
//                CircleAnnotation(
//                    point = Point.fromLngLat(
//                        currentLocation!!.longitude,
//                        currentLocation!!.latitude
//                    )
//                )
//            }
            PolygonAnnotation(
                points = points,
                fillColorString = "#FFEE4E8B",
                fillOpacity = 0.4
            )
            // I want to find a way to outline a polygon and delete the Polyline
            points.forEach { point ->
                PolylineAnnotation(
                    points = point,
                    lineColorString = "#FFBB0B",
                    lineOpacity = 1.0,
                    lineWidth = 5.0
                )
            }
        }
    }
}

@Composable
fun SettingsPage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.settings),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(all = 10.dp)
        )
        val context = LocalContext.current
        val languageOptions = listOf(R.string.en, R.string.pl, R.string.de)
        val languageIndex =
            when (AppCompatDelegate.getApplicationLocales().toLanguageTags()) {
                "en" -> 0
                "pl" -> 1
                "de" -> 2
                else -> 0
            }
        val (selectedLanguage, onLanguageSelected) = remember {
            mutableIntStateOf(
                languageOptions[languageIndex]
            )
        }
        val themeOptions =
            listOf(R.string.light_theme, R.string.dark_theme, R.string.black_amoled_theme)
        val (selectedTheme, onThemeSelected) = remember {
            mutableIntStateOf(themeOptions[1])
        }
        val openLanguageDialog = remember {
            mutableStateOf(false)
        }
        when {
            openLanguageDialog.value -> {
                RadioDialog(
                    onDismissRequest = { openLanguageDialog.value = false },
                    options = languageOptions,
                    selectedOption = selectedLanguage,
                    onOptionSelected = onLanguageSelected
                )
                changeLanguage(context, selectedLanguage)
            }
        }
        DialogButton(
            label = stringResource(id = R.string.language),
            subLabel = stringResource(id = R.string.language_chosen),
            onClick = { openLanguageDialog.value = true })
        val openThemeDialog = remember {
            mutableStateOf(false)
        }
        when {
            openThemeDialog.value -> {
                RadioDialog(
                    onDismissRequest = { openThemeDialog.value = false },
                    options = themeOptions,
                    selectedOption = selectedTheme,
                    onOptionSelected = onThemeSelected
                )
            }
        }
        DialogButton(
            label = stringResource(id = R.string.theme),
            subLabel = stringResource(id = selectedTheme),
            onClick = { openThemeDialog.value = true })
        Text(text = "Theme --(on first load)-> default from android")
        HorizontalDivider(thickness = 1.dp)
        Text(text = "Metric to imperial change")
        Text(text = "About us page (you will figure it out)")
        HorizontalDivider(thickness = 1.dp)
        Text(text = "About us page")
    }
}

@Composable
fun DialogButton(
    label: String,
    subLabel: String,
    onClick: () -> Unit
) {
    TextButton(
        shape = RoundedCornerShape(0.dp),
        onClick = { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                fontSize = 18.sp,
                text = label
            )
            Text(
                fontSize = 12.sp,
                text = subLabel
            )
        }
    }
}

@Composable
fun RadioDialog(
    onDismissRequest: () -> Unit,
    options: List<Int>,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height((56 * (options.size) + 68 * 2).dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),

            ) {
            Text(
                text = stringResource(id = R.string.language),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(all = 20.dp)
            )
            Column(modifier = Modifier.selectableGroup()) {
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .selectable(
                                selected = (option == selectedOption),
                                onClick = {
                                    onOptionSelected(option)
                                },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = null
                        )
                        Text(
                            text = stringResource(id = option),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.End
            ) {
                TextButton(
                    onClick = { onDismissRequest() }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        }
    }
}

fun changeLanguage(
    context: Context,
    languageStringResource: Int
) {
    AppCompatDelegate.setApplicationLocales(
        LocaleListCompat.forLanguageTags(
            context.resources.getResourceEntryName(languageStringResource)
        )
    )
}

@Composable
fun AccountPage() {
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
        Text(text = "ta strona będzie dla użytkownika, ale to później")
        Text(text = "achievements")
        Text(text = "(połączenie z API)")
    }
}

@Composable
fun ScoresPage() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Tu będzie tablica wyników")
        Text(text = "Powodzenia Fen")
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