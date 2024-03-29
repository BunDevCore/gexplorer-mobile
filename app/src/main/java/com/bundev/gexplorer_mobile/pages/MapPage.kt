package com.bundev.gexplorer_mobile.pages

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.classes.Funi
import com.bundev.gexplorer_mobile.icons.filled.Location
import com.bundev.gexplorer_mobile.icons.outlined.Location
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolygonAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.viewport.data.ViewportStatusChangeReason

@OptIn(MapboxExperimental::class)
var mapViewportState = MapViewportState()

@OptIn(MapboxExperimental::class)
@Composable
fun MapPage(funi: Funi? = null) {
    val configuration = LocalConfiguration.current
    val followingUser = remember {
        mutableStateOf(false)
    }
    val tempLocation = remember {
        mutableStateOf(Point.fromLngLat(18.6570989, 54.3542712))
    }
    mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(10.0)
            center(Point.fromLngLat(18.6570989, 54.3542712))
            pitch(0.0)
            bearing(0.0)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //Documentation here: https://docs.mapbox.com/android/maps/guides/
        MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = mapViewportState
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
            if (funi?.getValue() == 1L) CircleAnnotation(
                point = Point.fromLngLat(18.6650564007217, 54.29906183330589),
                circleOpacity = 0.5,
                circleColorInt = R.color.green
            )
        }
    }
    val changedReason = mapViewportState.mapViewportStatusChangedReason
    val animationSpeed: MapAnimationOptions
    if (followingUser.value) {
        animationSpeed = MapAnimationOptions.mapAnimationOptions { duration(1000L) }
        if (changedReason == ViewportStatusChangeReason.USER_INTERACTION ||
            changedReason == ViewportStatusChangeReason.TRANSITION_FAILED) {
            followingUser.value = false
        }
    } else {
        animationSpeed = MapAnimationOptions.mapAnimationOptions { duration(500L) }
    }
    Log.i("Map ChangedReason", mapViewportState.mapViewportStatusChangedReason.toString())
    Log.i("Map following", followingUser.value.toString())
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
    FloatingActionButton(
        onClick = {
            mapViewportState.flyTo(
                cameraOptions = cameraOptions {
                    center(
                        tempLocation.value
//                        Point.fromLngLat(location.longitude, location.latitude)
                    )
                    if (followingUser.value)
                        zoom(16.0)
                    pitch(0.0)
                    bearing(0.0)
                },
                animationOptions = animationSpeed
            )
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
        }
    }
}