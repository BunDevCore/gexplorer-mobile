package com.bundev.gexplorer_mobile.pages.map

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.classes.Screen
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.funi
import com.bundev.gexplorer_mobile.icons.filled.Location
import com.bundev.gexplorer_mobile.icons.outlined.Location
import com.bundev.gexplorer_mobile.icons.simple.Account
import com.bundev.gexplorer_mobile.icons.simple.Explore
import com.bundev.gexplorer_mobile.icons.simple.NoAccount
import com.bundev.gexplorer_mobile.icons.simple.QuestionMark
import com.bundev.gexplorer_mobile.navigateTo
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.localization.localizeLabels
import com.mapbox.maps.plugin.animation.MapAnimationOptions
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

    LaunchedEffect(Unit) { vm.fetchSelf() }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //Documentation here: https://docs.mapbox.com/android/maps/guides/
        MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = mapViewportState
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
//            val points = listOf(
//                listOf(
//                    Point.fromLngLat(18.615274605637016, 54.40211158342004),
//                    Point.fromLngLat(18.730974363868317, 54.37152378253998),
//                    Point.fromLngLat(18.6650564007217, 54.29906183330589),
//                    Point.fromLngLat(18.6547192660595, 54.355547811237834),
//                    Point.fromLngLat(18.615274605637016, 54.40211158342004)
//                )
//            )
//            PolygonAnnotation(
//                points = points,
//                fillColorString = "#FFEE4E8B",
//                fillOpacity = 0.4
//            )
            // I want to find a way to outline a polygon and delete the Polyline
//            points.forEach { point ->
//                PolylineAnnotation(
//                    points = point,
//                    lineColorString = "#FFBB0B",
//                    lineOpacity = 1.0,
//                    lineWidth = 5.0
//                )
//            }
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
        }
    }
    val changedReason = mapViewportState.mapViewportStatusChangedReason
    val animationSpeed: MapAnimationOptions
    val tempLocation = Point.fromLngLat(18.6570989, 54.3542712)
    val tempLocationBearing = 17.1234
    val followingUser = remember {
        mutableStateOf(false)
    }
    val exploreMode = remember {
        mutableStateOf(false)
    }
    if (followingUser.value) {
        animationSpeed = MapAnimationOptions.mapAnimationOptions { duration(1000L) }
        if (changedReason == ViewportStatusChangeReason.USER_INTERACTION ||
            changedReason == ViewportStatusChangeReason.TRANSITION_FAILED
        ) {
            followingUser.value = false
            exploreMode.value = false
        }
    } else {
        animationSpeed = MapAnimationOptions.mapAnimationOptions { duration(500L) }
    }
    /* //If I would ever need a small FAB here it is
    SmallFloatingActionButton(
        onClick = { followingUser.value = false },
        modifier = Modifier
            .padding(
                top = if (configuration.orientation == ORIENTATION_PORTRAIT)
                    (configuration.screenHeightDp - 264).dp
                else (configuration.screenHeightDp - 40).dp,
                start = if (configuration.orientation == ORIENTATION_PORTRAIT)
                    (configuration.screenWidthDp - 62).dp
                else (configuration.screenWidthDp - 208).dp
            )
            .width(40.dp)
            .height(40.dp)
    ) {
        Text("Stop")
    }*/
    // Location button
    FloatingActionButton(
        onClick = {//TODO if no permission show dialog which sends user to settings where they can give needed permissions
            if (followingUser.value) {
                exploreMode.value = !exploreMode.value
            }
            if (exploreMode.value) {
                mapViewportState.flyTo(
                    cameraOptions = cameraOptions {
                        center(tempLocation)
                        zoom(16.0)
                        pitch(50.0)
                        bearing(tempLocationBearing)
                    },
                    animationOptions = animationSpeed
                )
            } else {
                mapViewportState.flyTo(
                    cameraOptions = cameraOptions {
                        center(
                            tempLocation
//                        Point.fromLngLat(location.longitude, location.latitude)
                        )
                        zoom(16.0)
                        pitch(0.0)
                        bearing(0.0)
                    },
                    animationOptions = animationSpeed
                )
            }
            followingUser.value = true
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
        if (followingUser.value) {
            if (exploreMode.value) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = GexplorerIcons.Simple.Explore,
                    contentDescription = null
                )
            } else {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = GexplorerIcons.Filled.Location,
                    contentDescription = null
                )
            }
        } else {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = GexplorerIcons.Outlined.Location,
                contentDescription = null
            )
            if (true) //TODO write here check for location permission
                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = GexplorerIcons.Simple.QuestionMark,
                    contentDescription = null
                )
        }
    }
    // Account button
    SmallFloatingActionButton(
        modifier = Modifier.padding(top = 8.dp, start = (configuration.screenWidthDp - 52).dp),
        onClick = {
            if (state is ApiResource.Success)
                navigateTo(navController, Screen.Account.route, true) { changePage() }
            else navigateTo(navController, Screen.LogIn.route) { changePage() }
        }) {
        if (state is ApiResource.Success)
            Icon(
                modifier = Modifier,
                imageVector = GexplorerIcons.Simple.Account,
                contentDescription = null
            )
        else
            Icon(
                modifier = Modifier.padding(2.5.dp),
                imageVector = GexplorerIcons.Simple.NoAccount,
                contentDescription = null
            )
    }
}