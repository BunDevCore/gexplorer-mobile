package com.bundev.gexplorer_mobile.pages

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.DateFormat
import kotlin.math.pow
import kotlin.math.round
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

var isMetric = false

@Composable
fun TripsPage(systemOfUnits: JustAVariable) {
    isMetric = systemOfUnits.value == "metric"
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val tempTrips = listOf(
            Trip(
                distance = 0.3,
                timeBegun = Clock.System.now() - 3.hours,
                timeEnded = Clock.System.now() - 2.9.hours
            ),
            Trip(
                distance = 0.9,
                timeBegun = Clock.System.now() - 7.hours,
                timeEnded = Clock.System.now() - 5.hours
            ),
            Trip(
                distance = 0.9,
                timeBegun = Clock.System.now() - 7.hours,
                timeEnded = Clock.System.now() - 5.hours
            ),
            Trip(
                distance = 0.9,
                timeBegun = Clock.System.now() - 7.hours,
                timeEnded = Clock.System.now() - 5.hours
            ),
            Trip(
                distance = 0.9,
                timeBegun = Clock.System.now() - 7.hours,
                timeEnded = Clock.System.now() - 5.hours
            ),
            Trip(
                distance = 34.5,
                timeBegun = Clock.System.now() - 25.6.hours,
                timeEnded = Clock.System.now() - 24.9.hours
            ),
            Trip(
                distance = 99.999,
                timeBegun = Clock.System.now() - 27.hours,
                timeEnded = Clock.System.now() - 26.1.hours
            ),
            Trip(
                distance = 0.45,
                timeBegun = Clock.System.now() - 48.hours,
                timeEnded = Clock.System.now() - 47.7.hours
            ),
            Trip(
                distance = 6.7,
                timeBegun = Clock.System.now() - 56.hours,
                timeEnded = Clock.System.now() - 55.hours
            ),
            Trip(
                distance = 42.0,
                timeBegun = Clock.System.now() - 57.hours,
                timeEnded = Clock.System.now() - 56.8.hours
            ),
            Trip(
                distance = 20.2,
                timeBegun = Clock.System.now() - 59.hours,
                timeEnded = Clock.System.now() - 58.4.hours
            ),
            Trip(
                distance = 0.987,
                timeBegun = Clock.System.now() - 61.hours,
                timeEnded = Clock.System.now() - 60.6.hours
            ),
            Trip(
                distance = 0.987,
                timeBegun = Clock.System.now() - 112.hours,
                timeEnded = Clock.System.now() - 111.02.hours
            )
        )
        TripList(trips = tempTrips)
    }
}

@Composable
fun TripList(trips: List<Trip>) {
    val tripSections =
        trips.groupBy { it.timeBegun.toLocalDateTime(TimeZone.currentSystemDefault()).date }
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        tripSections.forEach {
            TripSection(trips = it.value)
        }
    }
}

@Composable
fun TripSection(trips: List<Trip>) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 7.5.dp),
            text = formatDate(trips[0].timeBegun, DateFormat.LONG),
            fontWeight = FontWeight.Bold
        )
        trips.forEach { trip ->
            val openTripDialog = remember { mutableStateOf(false) }
            TripItem(trip = trip) { openTripDialog.value = true }
            when {
                openTripDialog.value -> {
                    TripDialog(trip = trip) { openTripDialog.value = false }
                }
            }
        }
    }
}

@Composable
fun TripItem(trip: Trip, onClick: () -> Unit) {
    val duration = (trip.timeEnded - trip.timeBegun)
    val timeBegun = trip.timeBegun
    val distance = trip.distance

    TextButton(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 7.5.dp),
        onClick = { onClick() },
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
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
            leadingContent = {
                Icon(
                    GexplorerIcons.Filled.Walk,
                    contentDescription = null,
                )
            },
            trailingContent = {
                Text(
                    text = "${formatTime(timeBegun)}\n${formatDuration(duration)}",
                    textAlign = TextAlign.End
                )
            }
        )
    }
}

@OptIn(MapboxExperimental::class)
@Composable
fun TripDialog(trip: Trip, onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(0.dp)
        ) {
            TripDetailPage("")
        }
    }
}

fun Double.roundTo(n: Int): Double {
    return round(this * 10f.pow(n)) / 10f.pow(n)
}

fun formatDate(instant: Instant, format: Int = DateFormat.DEFAULT): String {
    return DateFormat.getDateInstance(format).format(instant.toEpochMilliseconds())
}

fun formatTime(instant: Instant, format: Int = DateFormat.DEFAULT): String {
    return DateFormat.getTimeInstance(format).format(instant.toEpochMilliseconds())
}

fun formatDuration(duration: Duration): String {
    return DateUtils.formatElapsedTime(duration.inWholeSeconds)
}

@Preview(showBackground = true, locale = "pl")
@Composable
fun ScoresPagePreview() {
    TripsPage(JustAVariable("metric"))
}