package com.bundev.gexplorer_mobile.pages.tripdetail

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Color
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.*
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.classes.Screen
import com.bundev.gexplorer_mobile.classes.Trip
import com.bundev.gexplorer_mobile.icons.filled.Bookmark
import com.bundev.gexplorer_mobile.icons.outlined.Bookmark
import com.bundev.gexplorer_mobile.icons.outlined.Speed
import com.bundev.gexplorer_mobile.icons.outlined.Timer
import com.bundev.gexplorer_mobile.icons.simple.AvgPace
import com.bundev.gexplorer_mobile.icons.simple.Path
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.localization.localizeLabels
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import kotlinx.datetime.Clock
import java.text.DateFormat
import java.util.*
import kotlin.time.Duration.Companion.hours

@OptIn(MapboxExperimental::class)
@Composable
fun TripDetailPage(
    tripId: String?,
    navController: NavHostController? = null,
    changePage: () -> Unit,
) {
    Log.d("tripdetail", "TRIP ID IN PARAMETER IS $tripId")
    
//    val tripId = "72f6a540-ee0e-42d2-a2a4-9da9add529b0"
    val vm = hiltViewModel<TripDetailViewModel>()
    val state by vm.state.collectAsState()
    LaunchedEffect(vm) {
        Log.d("tripdetail", "tripdetail: getting $tripId")
        vm.reset()
        vm.fetchTrip(tripId ?: error("no tripId provided to tripdetail page"))
    }
    val configuration = LocalConfiguration.current
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(10.0)
            center(Point.fromLngLat(18.6570989, 54.3542712))
            pitch(0.0)
            bearing(0.0)
        }
    }
    val geoJsonResource = remember {
        mutableStateOf("")
    }
    state.runIfSuccess {
        Log.d("tripdetail", "fetched geojson! rawr")
        Log.d("tripdetail", "geojson trip id: ${state.data!!.id}")
        geoJsonResource.value = state.data!!.gpsPolygon.toJson()
    }
    Log.d("tripdetail", "geojson resource len = ${geoJsonResource.value.length}")
    val secondary = colorResource(id = R.color.secondaryTemp)
    val style = style(Style.MAPBOX_STREETS) {
        +geoJsonSource(id = "Trip") { data(geoJsonResource.value) }
        +layerAtPosition(lineLayer("line-layer", "Trip") {
            lineColor(secondary.hashCode())
            lineWidth(3.0)
            lineOpacity(0.8)
        }, at = 100)
        +layerAtPosition(fillLayer("fill-layer", "Trip") {
            fillOpacity(0.4)
            fillColor(Color.GRAY)
        }, at = 100)
    }
    val trip = Trip(
        id = state.data?.id,
        timeEnded = Clock.System.now(),
        timeBegun = Clock.System.now() - 1.023.hours,
        distance = state.data?.length ?: 0.0
    )

    if (configuration.orientation == ORIENTATION_PORTRAIT)
        Column(modifier = Modifier.fillMaxSize()) {
            TripTopBar(trip = trip) {
                navigateTo(
                    navController,
                    Screen.Trips.route
                ) { changePage() }
            }
            TripMap(
                modifier = Modifier.weight(1f),
                mapViewportState = mapViewportState,
                style = style
            )
            TripContent(trip = trip)
        }
    else
        Row(modifier = Modifier.fillMaxSize()) {
            TripMap(
                modifier = Modifier.weight(1f),
                mapViewportState = mapViewportState,
                style = style
            )
            Column(
                modifier = Modifier.width(IntrinsicSize.Max)
            ) {
                TripTopBar(trip = trip) {
                    navigateTo(
                        navController,
                        Screen.Trips.route
                    ) { changePage() }
                }
                TripContent(trip = trip)
            }
        }
}

@Composable
private fun TripTopBar(trip: Trip, onCloseClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "${stringResource(id = R.string.trip)} " +
                        formatDate(trip.timeBegun, DateFormat.LONG),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${formatTime(trip.timeBegun, DateFormat.SHORT)} - " +
                        formatTime(trip.timeEnded, DateFormat.SHORT),
                fontSize = 16.sp
            )
        }
        Row {
            SaveTripButton(
                GexplorerIcons.Filled.Bookmark,
                GexplorerIcons.Outlined.Bookmark,
                trip
            )
            Spacer(modifier = Modifier.width(16.dp))
            ActionButton(Icons.Default.Close) { onCloseClick() }
        }
    }
}

@Composable
private fun SaveTripButton(
    imageVectorTrue: ImageVector,
    imageVectorFalse: ImageVector,
    trip: Trip,
) {
    val isTripSaved = remember {
        mutableStateOf(trip.saved)
    }
    SmallFloatingActionButton(
        onClick = {
            isTripSaved.value = !isTripSaved.value
            trip.saved = isTripSaved.value
        },
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
    ) {
        if (isTripSaved.value)
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = imageVectorTrue,
                contentDescription = null
            )
        else
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = imageVectorFalse,
                contentDescription = null
            )
    }
}

@OptIn(MapboxExperimental::class)
@Composable
private fun TripMap(
    modifier: Modifier = Modifier,
    mapViewportState: MapViewportState,
    style: StyleContract.StyleExtension,
) {
    MapboxMap(
        modifier = modifier,
        mapViewportState = mapViewportState
    ) {
        MapEffect(key1 = style) { mapView ->
            run {
                Log.wtf("mapboxeffect", "loading style")
                mapView.mapboxMap.loadStyle(style)
                mapView.mapboxMap.style?.styleLayers
                mapView.mapboxMap.style?.localizeLabels(
                    Locale.forLanguageTag(
                        AppCompatDelegate.getApplicationLocales().toLanguageTags()
                    )
                )
            }
        }
    }
}

@Composable
private fun TripContent(trip: Trip) {
    val distance = trip.distance
    val duration = (trip.timeEnded - trip.timeBegun)
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ValueElement(
            imageVector = GexplorerIcons.Simple.Path,
            title = stringResource(id = R.string.distance)
        ) { formatDistance(distanceInMeters = distance, measureUnit = measureUnit) }
        ValueElement(
            imageVector = GexplorerIcons.Outlined.Timer,
            title = stringResource(id = R.string.total_time)
        ) { formatDuration(duration) }
        if (duration.inWholeSeconds > 0 && distance > 0.0) {
            ValueElement(
                imageVector = GexplorerIcons.Outlined.Speed,
                title = stringResource(id = R.string.avg_speed)
            ) {
                formatSpeed(
                    distanceInMeters = distance,
                    duration = duration,
                    measureUnit = measureUnit
                )
            }
            ValueElement(
                imageVector = GexplorerIcons.Simple.AvgPace,
                title = stringResource(id = R.string.avg_pace)
            ) {
                formatPace(
                    duration = duration,
                    distanceInMeters = distance,
                    measureUnit = measureUnit
                )
            }
        } else {
            ValueElement(
                imageVector = GexplorerIcons.Outlined.Speed,
                title = stringResource(id = R.string.avg_speed)
            ) { "Null" }
            ValueElement(
                imageVector = GexplorerIcons.Simple.AvgPace,
                modifier = Modifier.padding(bottom = 10.dp),
                title = stringResource(id = R.string.avg_pace)
            ) { "Null" }
        }
    }
}

@Composable
private fun ValueElement(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    contentDescription: String? = null,
    title: String,
    value: @Composable () -> String,
) {
    ElevatedCard(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (imageVector is ImageVector)
                Icon(imageVector = imageVector, contentDescription = contentDescription)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
                    text = title,
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = value(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview(locale = "pl")
@Composable
private fun TripTopBarPreview() {
    TripTopBar(
        trip =
        Trip(
            distance = 0.987,
            timeBegun = Clock.System.now() - 112.hours,
            timeEnded = Clock.System.now() - 111.02.hours
        )
    ) {}
}

@Preview(locale = "pl")
@Composable
private fun TripContentPreview() {
    TripContent(
        trip =
        Trip(
            distance = 0.987,
            timeBegun = Clock.System.now() - 112.hours,
            timeEnded = Clock.System.now() - 111.02.hours
        )
    )
}