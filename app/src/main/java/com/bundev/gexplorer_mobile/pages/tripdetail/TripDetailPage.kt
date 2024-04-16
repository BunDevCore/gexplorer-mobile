package com.bundev.gexplorer_mobile.pages.tripdetail

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Color
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.GoToPreviousPage
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.classes.Trip
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.distanceUnit
import com.bundev.gexplorer_mobile.formatDate
import com.bundev.gexplorer_mobile.formatDistance
import com.bundev.gexplorer_mobile.formatDuration
import com.bundev.gexplorer_mobile.formatLongText
import com.bundev.gexplorer_mobile.formatPace
import com.bundev.gexplorer_mobile.formatSpeed
import com.bundev.gexplorer_mobile.formatTime
import com.bundev.gexplorer_mobile.icons.filled.Bookmark
import com.bundev.gexplorer_mobile.icons.outlined.Bookmark
import com.bundev.gexplorer_mobile.icons.outlined.Speed
import com.bundev.gexplorer_mobile.icons.outlined.Timer
import com.bundev.gexplorer_mobile.icons.simple.AvgPace
import com.bundev.gexplorer_mobile.icons.simple.Path
import com.bundev.gexplorer_mobile.ui.ActionButton
import com.bundev.gexplorer_mobile.ui.ErrorCard
import com.bundev.gexplorer_mobile.ui.LoadingCard
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
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
import java.util.Locale
import kotlin.time.Duration.Companion.hours

@OptIn(MapboxExperimental::class)
@Composable
fun TripDetailPage(
    tripId: String?,
    navController: NavHostController? = null,
    changePage: () -> Unit,
) {
    Log.d("tripdetail", "TRIP ID IN PARAMETER IS $tripId")

    val vm = hiltViewModel<TripDetailViewModel>()
    val state by vm.state.collectAsState()
    LaunchedEffect(vm) {
        Log.d("tripdetail", "tripdetail: getting $tripId")
        vm.fetchTrip(tripId ?: error("no tripId provided to tripdetail page"))
    }
    val configuration = LocalConfiguration.current
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(10.0)
            center(state.data?.point ?: Point.fromLngLat(18.6570989, 54.3542712))
            pitch(0.0)
            bearing(0.0)
        }
    }
    val geoJsonResource = remember {
        mutableStateOf("")
    }
    state.runIfSuccess {
        Log.d("tripdetail", "fetched geojson! rawr")
        Log.d("tripdetail", "geojson trip id: ${state.data!!.detailedTripDto.id}")
        geoJsonResource.value = state.data!!.detailedTripDto.gpsPolygon.toJson()
        mapViewportState.flyTo(
            cameraOptions = CameraOptions.Builder().center(state.data!!.point).pitch(0.0).zoom(10.0)
                .build()
        )
    }
    Log.d("tripdetail", "geojson resource len = ${geoJsonResource.value.length}")
    val tripColor = colorResource(id = R.color.tripArea)
    val style = style(Style.MAPBOX_STREETS) {
        +geoJsonSource(id = "Trip") { data(geoJsonResource.value) }
        +layerAtPosition(lineLayer("line-layer", "Trip") {
            lineColor(tripColor.hashCode())
            lineWidth(3.0)
            lineOpacity(0.8)
        }, at = 100)
        +layerAtPosition(fillLayer("fill-layer", "Trip") {
            fillOpacity(0.4)
            fillColor(Color.GRAY)
        }, at = 100)
    }
    val trip = Trip(
        id = state.data?.detailedTripDto?.id,
        timeEnded = state.data?.detailedTripDto?.endTime ?: Clock.System.now(),
        timeBegun = state.data?.detailedTripDto?.startTime ?: (Clock.System.now() - 1.hours),
        distance = state.data?.detailedTripDto?.length ?: 0.0,
        saved = state.data?.detailedTripDto?.starred ?: false
    )

    if (configuration.orientation == ORIENTATION_PORTRAIT)
        Column(modifier = Modifier.fillMaxSize()) {
            TripTopBar(trip = trip, vm, navController) { changePage() }
            when (state) {
                is ApiResource.Success -> {
                    TripMap(
                        modifier = Modifier.weight(1f),
                        mapViewportState = mapViewportState,
                        style = style
                    )
                    TripContent(trip = trip)
                }

                is ApiResource.Loading -> LoadingCard(text = stringResource(id = R.string.loading))
                is ApiResource.Error -> ErrorCard(error = state.error)
            }
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
                when (state) {
                    is ApiResource.Success -> {
                        TripTopBar(trip = trip, vm, navController) { changePage() }
                        TripContent(trip = trip)
                    }

                    is ApiResource.Loading -> LoadingCard(text = stringResource(id = R.string.loading))
                    is ApiResource.Error -> ErrorCard(error = state.error)
                }
            }
        }
}

@Composable
private fun TripTopBar(
    trip: Trip,
    vm: TripDetailViewModel? = null,
    navController: NavHostController? = null,
    changePage: () -> Unit,
) {
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
                vm,
                GexplorerIcons.Filled.Bookmark,
                GexplorerIcons.Outlined.Bookmark,
                trip
            )
            Spacer(modifier = Modifier.width(16.dp))
            ActionButton(Icons.Default.Close) { GoToPreviousPage(navController) { changePage() } }
        }
    }
}

@Composable
private fun SaveTripButton(
    vm: TripDetailViewModel?,
    imageVectorTrue: ImageVector,
    imageVectorFalse: ImageVector,
    trip: Trip,
) {
    SmallFloatingActionButton(
        onClick = {
            vm?.updateStarred()
        },
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
    ) {
        if (trip.saved)
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
        modifier = Modifier.padding(bottom = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ValueElement(
            imageVector = GexplorerIcons.Simple.Path,
            title = stringResource(id = R.string.distance)
        ) { formatDistance(distanceInMeters = distance, measureUnit = distanceUnit) }
        ValueElement(
            imageVector = GexplorerIcons.Outlined.Timer,
            title = stringResource(id = R.string.total_time)
        ) { formatDuration(duration) }
        if (duration.inWholeSeconds > 0 && distance > 0.0) {
            ValueElement(
                imageVector = GexplorerIcons.Outlined.Speed,
                title = if (AppCompatDelegate.getApplicationLocales()
                        .toLanguageTags() != "de"
                ) stringResource(id = R.string.avg_speed)
                else formatLongText(stringResource(id = R.string.avg_speed))
            ) {
                formatSpeed(
                    distanceInMeters = distance,
                    duration = duration,
                    measureUnit = distanceUnit
                )
            }
            ValueElement(
                imageVector = GexplorerIcons.Simple.AvgPace,
                title = stringResource(id = R.string.avg_pace)
            ) {
                formatPace(
                    duration = duration,
                    distanceInMeters = distance,
                    measureUnit = distanceUnit
                )
            }
        } else {
            ValueElement(
                imageVector = GexplorerIcons.Outlined.Speed,
                title = if (AppCompatDelegate.getApplicationLocales()
                        .toLanguageTags() != "de"
                ) stringResource(id = R.string.avg_speed)
                else formatLongText(stringResource(id = R.string.avg_speed))
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

@Preview(locale = "de")
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