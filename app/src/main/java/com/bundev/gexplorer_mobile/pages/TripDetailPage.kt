package com.bundev.gexplorer_mobile.pages

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Color
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.classes.Trip
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
import java.util.Locale
import kotlin.math.pow
import kotlin.time.Duration.Companion.hours
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(MapboxExperimental::class)
@Composable
fun TripDetailPage(trip: Trip, onDismissRequest: () -> Unit) {
    //TODO Fetch points from backend using trip.id

    val configuration = LocalConfiguration.current
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(10.0)
            center(Point.fromLngLat(18.6570989, 54.3542712))
            pitch(0.0)
            bearing(0.0)
        }
    }
    val secondary = colorResource(id = R.color.secondaryTemp)
    val style = style(Style.MAPBOX_STREETS) {
        +geoJsonSource(id = "Trip") { data("asset://gdansk_4326.geojson") }
        +layerAtPosition(lineLayer("line-layer", "Trip") {
            lineColor(secondary.hashCode())
            lineWidth(3.0)
            lineOpacity(0.8)
        }, at = 100)
        +layerAtPosition(fillLayer("fill-layer", "Trip") {
            fillOpacity(0.4)
            fillColor(Color.GRAY)
        }, at = 100) //at = 100
    }

    if (configuration.orientation == ORIENTATION_PORTRAIT)
        Card(modifier = Modifier.fillMaxSize()) {
            TripTopBar(trip = trip) { onDismissRequest() }
            TripMap(
                modifier = Modifier.height((configuration.screenHeightDp / 2).dp),
                mapViewportState = mapViewportState,
                style = style
            )
            TripContent(modifier = Modifier.padding(top = 10.dp), trip = trip)
        } else
        Card(modifier = Modifier.fillMaxSize()) {
            Row {
                TripMap(
                    modifier = Modifier.width((configuration.screenWidthDp / 2).dp),
                    mapViewportState = mapViewportState,
                    style = style
                )
                Column {
                    TripTopBar(trip = trip) { onDismissRequest() }
                    TripContent(trip = trip)
                }
            }
        }
}

@Composable
fun TripTopBar(trip: Trip, onDismissRequest: () -> Unit) {
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
        CloseButton { onDismissRequest() }
    }
}

@Composable
fun CloseButton(onDismissRequest: () -> Unit) {
    SmallFloatingActionButton(
        onClick = { onDismissRequest() },
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Default.Close,
            contentDescription = null
        )
    }
}

@OptIn(MapboxExperimental::class)
@Composable
fun TripMap(
    modifier: Modifier,
    mapViewportState: MapViewportState,
    style: StyleContract.StyleExtension
) {
    MapboxMap(
        modifier = modifier,
        mapViewportState = mapViewportState
    ) {
        MapEffect { mapView ->
            run {
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
fun TripContent(modifier: Modifier = Modifier, trip: Trip) {
    val distance = trip.distance
    val duration = (trip.timeEnded - trip.timeBegun)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ValueElement(title = stringResource(id = R.string.distance)) {
            if (distance > 0.5)
                if (isMetric) {
                    "${distance.roundTo(3)} km"
                } else {
                    "${(distance * 0.621371).roundTo(3)} mi"
                }
            else
                if (isMetric) {
                    "${distance.convert(3).roundTo(3)} m"
                } else {
                    "${(distance * 0.621371 * 5280).roundTo(3)} ft"
                }
        }
        ValueElement(title = stringResource(id = R.string.total_time)) {
            formatDuration(duration)
        }
        if (duration.inWholeSeconds > 0 && distance > 0.0) {
            ValueElement(title = stringResource(id = R.string.avg_speed)) {
                val avgSpeed = distance / (duration.inWholeSeconds / 3600.0)
                if (avgSpeed > 0.5)
                    if (isMetric) {
                        "${avgSpeed.roundTo(3)} km/h"
                    } else {
                        "${(avgSpeed * 0.621371).roundTo(3)} mi/h"
                    }
                else
                    if (isMetric) {
                        "${avgSpeed.convert(3).roundTo(3)} m/h"
                    } else {
                        "${(avgSpeed * 0.621371 * 5280).roundTo(3)} mi/h"
                    }
            }
            ValueElement(title = stringResource(id = R.string.avg_pace)) {
                val avgPace = if (isMetric) {
                    ((duration.inWholeSeconds / 60) / distance)
                        .toDuration(DurationUnit.MINUTES)
                } else {
                    ((duration.inWholeSeconds / 60) / (distance * 0.621371))
                        .toDuration(DurationUnit.MINUTES)
                }
                "${formatDuration(avgPace)}/${if (isMetric) "km" else "mi"}"
            }
        } else {
            ValueElement(title = stringResource(id = R.string.avg_speed)) { "Null" }
            ValueElement(
                modifier = Modifier.padding(bottom = 10.dp),
                title = stringResource(id = R.string.avg_pace)
            ) { "Null" }
        }
        /*
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ValueCard(
        //                modifier = Modifier.width(180.dp),
                        title = stringResource(id = R.string.distance)
                    ) {
                        if (distance > 0.5)
                            if (isMetric) {
                                "${distance.roundTo(3)} km"
                            } else {
                                "${(distance * 0.621371).roundTo(3)} mi"
                            }
                        else
                            if (isMetric) {
                                "${distance.convert(3).roundTo(3)} m"
                            } else {
                                "${(distance * 0.621371 * 5280).roundTo(3)} ft"
                            }
                    }
                    ValueCard(
        //                modifier = Modifier.width(180.dp),
                        modifier = Modifier.padding(end = 10.dp),
                        title = stringResource(id = R.string.total_time)
                    ) {
                        formatDuration(duration)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (duration.inWholeSeconds > 0 && distance > 0.0) {
                        ValueCard(title = stringResource(id = R.string.avg_speed)) {
                            val avgSpeed = distance / (duration.inWholeSeconds / 3600.0)
                            if (avgSpeed > 0.5)
                                if (isMetric) {
                                    "${avgSpeed.roundTo(3)} km/h"
                                } else {
                                    "${(avgSpeed * 0.621371).roundTo(3)} mi/h"
                                }
                            else
                                if (isMetric) {
                                    "${avgSpeed.convert(3).roundTo(3)} m/h"
                                } else {
                                    "${(avgSpeed * 0.621371 * 5280).roundTo(3)} mi/h"
                                }
                        }
                        ValueCard(
                            modifier = Modifier.padding(end = 10.dp),
                            title = stringResource(id = R.string.avg_pace)
                        ) {
                            val avgPace = if (isMetric) {
                                ((duration.inWholeSeconds / 60) / distance)
                                    .toDuration(DurationUnit.MINUTES)
                            } else {
                                ((duration.inWholeSeconds / 60) / (distance * 0.621371))
                                    .toDuration(DurationUnit.MINUTES)
                            }
                            "${formatDuration(avgPace)}/${if (isMetric) "km" else "mi"}"
                        }
                    } else {
                        ValueCard(title = stringResource(id = R.string.avg_speed)) { "Null" }
                        ValueCard(
                            modifier = Modifier.padding(end = 10.dp),
                            title = stringResource(id = R.string.avg_pace)
                        ) { "Null" }
                    }
                }*/
    }
}

@Composable
fun ValueElement(modifier: Modifier = Modifier, title: String, value: () -> String) {
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
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
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

@Composable
fun ValueCard(modifier: Modifier = Modifier, title: String, value: () -> String) {
    ElevatedCard(
        modifier = modifier
            .width(189.dp)
            .aspectRatio(1f)
            .padding(start = 10.dp)
            .padding(vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                minLines = 2
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = value(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    }

}

fun Double.convert(n: Int): Double {
    return this * 10f.pow(n)
}

@Preview(locale = "pl")
@Composable
fun TripContentPreview() {
    isMetric = true
    TripContent(
        trip =
        Trip(
            distance = 0.987,
            timeBegun = Clock.System.now() - 112.hours,
            timeEnded = Clock.System.now() - 111.02.hours
        )
    )
}