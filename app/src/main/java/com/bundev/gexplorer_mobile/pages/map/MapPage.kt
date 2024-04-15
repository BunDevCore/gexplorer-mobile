package com.bundev.gexplorer_mobile.pages.map

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.ConfirmDialog
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.RequestLocationPermission
import com.bundev.gexplorer_mobile.checkLocationPermission
import com.bundev.gexplorer_mobile.classes.Screen
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.funi
import com.bundev.gexplorer_mobile.icons.filled.Account
import com.bundev.gexplorer_mobile.icons.filled.Location
import com.bundev.gexplorer_mobile.icons.outlined.Account
import com.bundev.gexplorer_mobile.icons.outlined.Location
import com.bundev.gexplorer_mobile.icons.outlined.NoAccount
import com.bundev.gexplorer_mobile.icons.simple.QuestionMark
import com.bundev.gexplorer_mobile.navigateTo
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
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.DefaultSettingsProvider
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation
import com.mapbox.maps.extension.localization.localizeLabels
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.viewport.data.ViewportStatusChangeReason
import java.util.Locale

@OptIn(MapboxExperimental::class)
@Composable
fun MapPage(navController: NavHostController, changePage: () -> Unit) {
    val configuration = LocalConfiguration.current
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(10.0)
            center(Point.fromLngLat(18.6570989, 54.3542712))
            pitch(0.0)
            bearing(0.0)
        }
    }
    val vm = hiltViewModel<MapViewModel>()
    val state by vm.state.collectAsState()
    val context = LocalContext.current
    val (permissionRequestCount, setPermissionRequestCount) = rememberSaveable { mutableIntStateOf(1) }
    val tripStarted = rememberSaveable { mutableStateOf(false) }

    //Location service
    val locationList = rememberSaveable { mutableListOf<Location>() }

    val locationService: LocationService = LocationServiceFactory.getOrCreate()
    var locationProvider: DeviceLocationProvider? = null
    val request = LocationProviderRequest.Builder().interval(
        IntervalSettings.Builder().interval(10000L).minimumInterval(5000L).maximumInterval(20000L)
            .build()
    ).displacement(3F).accuracy(AccuracyLevel.HIGHEST).build()
    val result = locationService.getDeviceLocationProvider(request = request)
    if (result.isValue) {
        locationProvider = result.value!!
    } else {
        Log.e("LOCATION PROVIDER", "Failed to get device location provider")
    }
    val locationObserver = LocationObserver {}
    locationProvider!!.addLocationObserver(locationObserver, looper = Looper.myLooper()!!)

    locationProvider.getLastLocation { lastLocation ->
        lastLocation?.let {
            locationList.add(lastLocation)
        }
    }

    LaunchedEffect(Unit) { vm.fetchSelf() }
    RequestLocationPermission(requestCount = permissionRequestCount, onPermissionDenied = { }) {}

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //Documentation here: https://docs.mapbox.com/android/maps/guides/
        MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = mapViewportState,
            locationComponentSettings = DefaultSettingsProvider.defaultLocationComponentSettings(
                context
            ).toBuilder()
                .setLocationPuck(DefaultSettingsProvider.createDefault2DPuck(withBearing = true))
                .setPuckBearingEnabled(true)
                .setPuckBearing(PuckBearing.HEADING)
                .setEnabled(true)
                .build()
        ) {
            MapEffect { mapView ->
                mapView.mapboxMap.loadStyle(Style.STANDARD) {}
                mapView.mapboxMap.style?.localizeLabels(
                    Locale.forLanguageTag(
                        AppCompatDelegate.getApplicationLocales().toLanguageTags()
                    )
                )
                mapView.compass.marginTop = 180f
            }
            if (funi.getValue() == 20L) CircleAnnotation(
                point = Point.fromLngLat(18.6650564007217, 54.29906183330589),
                circleOpacity = 0.5,
                circleColorInt = R.color.green
            )
            CircleAnnotation(
                point = Point.fromLngLat(18.6545101, 54.3542491),
                circleOpacity = 0.5,
                circleColorInt = R.color.green
            )
            if (locationList.isNotEmpty()) {
                val points = mutableListOf<Point>()
                locationList.forEach { location ->
                    points += Point.fromLngLat(location.longitude, location.latitude)
                }
                PolylineAnnotation(
                    points = points,
                    lineJoin = LineJoin.ROUND,
                    lineBorderWidth = 10.0
                )

            }
        }
    }
    val changedReason = mapViewportState.mapViewportStatusChangedReason
    val (followingUser, setFollowing) = remember {
        mutableStateOf(false)
    }
    if (followingUser) {
        if (changedReason == ViewportStatusChangeReason.USER_INTERACTION) {
            setFollowing(false)
        }
    }
    val openPermissionDialog = rememberSaveable { mutableStateOf(false) }
    when {
        openPermissionDialog.value -> {
            PermissionsDialog { openPermissionDialog.value = false }
        }
    }
    // Location button
    FloatingActionButton(
        onClick = {
            if (context.checkLocationPermission()) {
                setFollowing(true)
                mapViewportState.transitionToFollowPuckState()
            } else {
                setPermissionRequestCount(permissionRequestCount + 1)
                if (permissionRequestCount > 2) {
                    openPermissionDialog.value = true
                }
            }
        }, modifier = Modifier
            .padding(
                top = if (configuration.orientation == ORIENTATION_PORTRAIT)
                    (configuration.screenHeightDp - 208).dp
                else (configuration.screenHeightDp - 48).dp,
                start = if (configuration.orientation == ORIENTATION_PORTRAIT)
                    (configuration.screenWidthDp - 72).dp
                else (configuration.screenWidthDp - 152).dp
            )
            .width(56.dp)
            .height(56.dp)
    ) {
        if (followingUser) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = GexplorerIcons.Filled.Location,
                contentDescription = null
            )
        } else {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = GexplorerIcons.Outlined.Location,
                contentDescription = null
            )
            if (!context.checkLocationPermission())
                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = GexplorerIcons.Simple.QuestionMark,
                    contentDescription = null
                )
        }
    }
    // Account button
    SmallFloatingActionButton(
        modifier = Modifier.padding(
            top = 8.dp,
            start = if (configuration.orientation == ORIENTATION_PORTRAIT) (configuration.screenWidthDp - 52).dp
            else (configuration.screenWidthDp - 136).dp
        ),
        onClick = {
            when (state.userDto) {
                is ApiResource.Success -> navigateTo(
                    navController,
                    Screen.Account.route,
                    true
                ) { changePage() }

                is ApiResource.Error -> navigateTo(
                    navController,
                    Screen.Login.route
                ) { changePage() }

                else -> {}
            }
        }) {
        when (state.userDto) {
            is ApiResource.Success -> Icon(
                modifier = Modifier,
                imageVector = GexplorerIcons.Filled.Account,
                contentDescription = null
            )

            is ApiResource.Loading -> {
                Icon(
                    modifier = Modifier,
                    imageVector = GexplorerIcons.Outlined.Account,
                    contentDescription = null
                )
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 4.dp)
            }

            else -> Icon(
                modifier = Modifier.padding(2.5.dp),
                imageVector = GexplorerIcons.Outlined.NoAccount,
                contentDescription = null
            )
        }
    }
    if (tripStarted.value)
        Card {
            Text(text = "Dane z lokalizacji tutaj")
            val currentLocation = locationList.last()
            Text(text = "LOCATIONS GOT: ${locationList.size}")
            Text(text = "LON: ${currentLocation.longitude} LAT: ${currentLocation.latitude}")
            Text(text = "TIME ${currentLocation.timestamp}")
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (context.checkLocationPermission())
            if (tripStarted.value) {
                val openFinishDialog = rememberSaveable { mutableStateOf(false) }
                Button(onClick = { openFinishDialog.value = true }) {
                    Text(text = stringResource(id = R.string.finish_trip))
                }
                when {
                    openFinishDialog.value -> {
                        ConfirmDialog(
                            onDismissRequest = { openFinishDialog.value = false },
                            confirmRequest = {
                                tripStarted.value = false
                                vm.sendTrip(locationList)
                            },
                            text = stringResource(id = R.string.confirm_finish_trip)
                        )
                    }
                }
            } else
                Button(onClick = { tripStarted.value = true }) {
                    Text(text = stringResource(id = R.string.start_trip))
                }
        else
            Button(
                onClick = {
                    if (permissionRequestCount > 2) context.startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                    )
                    else setPermissionRequestCount(permissionRequestCount + 1)
                }) {
                if (permissionRequestCount > 2)
                    Text(text = stringResource(R.string.onboard_open_settings))
                else
                    Text(text = stringResource(id = R.string.onboard_permissions_button))
            }
    }
}

@Composable
private fun PermissionsDialog(onDismissRequest: () -> Unit) {
    val context = LocalContext.current
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.location_detailed_needed),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { onDismissRequest() }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                TextButton(
                    onClick = {
                        onDismissRequest()
                        context.startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            )
                        )
                    }) {
                    Text(text = stringResource(id = R.string.onboard_open_settings))
                }
            }
        }
    }
}