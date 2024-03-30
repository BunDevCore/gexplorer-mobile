package com.bundev.gexplorer_mobile.pages

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.classes.JustAVariable
import com.bundev.gexplorer_mobile.classes.Trip
import com.bundev.gexplorer_mobile.icons.filled.Walk
import com.mapbox.geojson.Point
import java.text.DateFormat
import kotlin.math.pow
import kotlin.math.round
import kotlin.time.Duration.Companion.hours
import kotlin.time.DurationUnit

@Composable
fun ScoresPage(systemOfUnits: JustAVariable) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
//        Text(text = "System jednostek! -> ${systemOfUnits.value}")
//        Text(text = "Tu będzie tablica wyników")
//        Text(text = "Powodzenia Fen")
        val tempTrips = listOf(
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 0.3,
                timeBegun = System.currentTimeMillis() - 3.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 2.9.hours.toLong(DurationUnit.MILLISECONDS)
            ),
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 0.9,
                timeBegun = System.currentTimeMillis() - 7.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 5.hours.toLong(DurationUnit.MILLISECONDS)
            ),
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 0.9,
                timeBegun = System.currentTimeMillis() - 7.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 5.hours.toLong(DurationUnit.MILLISECONDS)
            ),
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 0.9,
                timeBegun = System.currentTimeMillis() - 7.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 5.hours.toLong(DurationUnit.MILLISECONDS)
            ),
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 0.9,
                timeBegun = System.currentTimeMillis() - 7.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 5.hours.toLong(DurationUnit.MILLISECONDS)
            ),
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 34.5,
                timeBegun = System.currentTimeMillis() - 25.6.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 24.9.hours.toLong(DurationUnit.MILLISECONDS)
            ),
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 99.999,
                timeBegun = System.currentTimeMillis() - 27.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 26.1.hours.toLong(DurationUnit.MILLISECONDS)
            ),
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 0.45,
                timeBegun = System.currentTimeMillis() - 48.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 47.7.hours.toLong(DurationUnit.MILLISECONDS)
            ),
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 6.7,
                timeBegun = System.currentTimeMillis() - 56.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 55.hours.toLong(DurationUnit.MILLISECONDS)
            ),
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 42.0,
                timeBegun = System.currentTimeMillis() - 57.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 56.8.hours.toLong(DurationUnit.MILLISECONDS)
            ),
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 20.2,
                timeBegun = System.currentTimeMillis() - 59.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 58.4.hours.toLong(DurationUnit.MILLISECONDS)
            ),
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 0.987,
                timeBegun = System.currentTimeMillis() - 61.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 60.6.hours.toLong(DurationUnit.MILLISECONDS)
            ),
            Trip(
                points = listOf(Point.fromLngLat(18.6650564007217, 54.29906183330589)),
                distance = 0.987,
                timeBegun = System.currentTimeMillis() - 112.hours.toLong(DurationUnit.MILLISECONDS),
                timeEnded = System.currentTimeMillis() - 111.02.hours.toLong(DurationUnit.MILLISECONDS)
            )
        )
        TripList(trips = tempTrips, isMetric = systemOfUnits.value == "metric")
    }
}

@Composable
fun TripList(trips: List<Trip>, isMetric: Boolean) {
    val lastDate = remember { mutableStateOf("") }
    val tripSections = mutableListOf<Int>()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        trips.forEach {
            if (lastDate.value != getDate(it.timeBegun, DateFormat.SHORT)) {
                lastDate.value = getDate(it.timeBegun, DateFormat.SHORT)
                if (trips.indexOf(it) != 0)
                    tripSections += trips.indexOf(it)
            }
        }
        val lastPosition = remember { mutableIntStateOf(0) }

        Log.i("TRIP SECTIONS", tripSections.toString())
        tripSections.forEach {
            Card(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 7.5.dp),
                    text = getDate(trips[lastPosition.intValue].timeBegun, DateFormat.LONG),
                    fontWeight = FontWeight.Bold
                )

                var i = lastPosition.value
                while (i < it) {
                    val trip = trips[it]
                    val openTripDialog = remember { mutableStateOf(false) }
                    TextButton(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(bottom = 7.5.dp),
                        onClick = { openTripDialog.value = true },
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        TripItem(trip = trip, isMetric = isMetric)
                        when {
                            openTripDialog.value -> {
                                TripDialog(trip = trip) { openTripDialog.value = false }
                            }
                        }
                    }
                    i++
                }
                lastPosition.intValue = it
            }
        }
    }
}

@Composable
fun TripItem(trip: Trip, isMetric: Boolean) {
    TripItem(
        timeBegun = trip.timeBegun,
        timeEnded = trip.timeEnded,
        distance = trip.distance,
        isMetric = isMetric
    )
}

@Composable
fun TripItem(timeBegun: Long, timeEnded: Long, distance: Double, isMetric: Boolean) {
    val finalDuration = timeEnded - 1.hours.toLong(DurationUnit.MILLISECONDS)
    val duration = timeEnded - timeBegun
    ListItem(
        overlineContent = { Text(stringResource(id = R.string.trip)) },
        headlineContent = {
            Text(
                if (isMetric) {
                    "${distance.roundTo(3)} km"
                } else {
                    "${(distance * 0.621371).roundTo(3)} mi"
                }
            )
        },
//        supportingContent = { Text(" Secondary text that is long and perhaps goes onto another line") },
        leadingContent = {
            Icon(
                GexplorerIcons.Filled.Walk,
                contentDescription = null,
            )
        },
        trailingContent = {
            Text(
                text = "${getTime(finalDuration)}\n${getTime(duration, DateFormat.SHORT)}",
                textAlign = TextAlign.End
            )
        }
    )
}

@Composable
fun TripDialog(trip: Trip, onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(modifier = Modifier.fillMaxWidth().height(112.dp)){
            Text("Bruh you got me")
            Text(trip.distance.toString())
        }
    }
}

fun Double.roundTo(n: Int): Double {
    return round(this * 10f.pow(n)) / 10f.pow(n)
}

fun getDate(timeInMilliseconds: Long, format: Int = DateFormat.DEFAULT): String {
    return DateFormat.getDateInstance(format).format(timeInMilliseconds)
}

fun getTime(timeInMilliseconds: Long, format: Int = DateFormat.DEFAULT): String {
    return DateFormat.getTimeInstance(format).format(timeInMilliseconds)
}

@Preview(showBackground = true, locale = "pl")
@Composable
fun ScoresPagePreview() {
    ScoresPage(JustAVariable("metric"))
}