package com.bundev.gexplorer_mobile

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.text.format.DateUtils
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import java.text.DateFormat
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun navigateTo(
    navController: NavHostController? = null,
    route: String,
    goToTripDetail: () -> Unit
) {
    if (selectedTabSave != route) {
        selectedTabSave = route
        goToTripDetail()
        navController?.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = false
            restoreState = true
        }
    }
}

fun formatDate(instant: Instant, format: Int = DateFormat.DEFAULT): String {
    return DateFormat.getDateInstance(format).format(instant.toEpochMilliseconds())
}

fun formatDate(date: LocalDate, format: Int = DateFormat.DEFAULT): String {
    return DateFormat.getDateInstance(format)
        .format(date.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds())
}

fun formatTime(instant: Instant, format: Int = DateFormat.DEFAULT): String {
    return DateFormat.getTimeInstance(format).format(instant.toEpochMilliseconds())
}

fun formatDuration(duration: Duration): String {
    return DateUtils.formatElapsedTime(duration.inWholeSeconds)
}

@Composable
fun formatDistance(distanceInMeters: Double, measureUnit: MeasureUnit): String {
    val measure = when (measureUnit) {
        MeasureUnit.METER -> {
            if (distanceInMeters < 1000.0)
                Measure(distanceInMeters.roundToInt(), MeasureUnit.METER)
            else
                Measure((distanceInMeters / 1000).roundTo(3), MeasureUnit.KILOMETER)
        }

        MeasureUnit.FOOT -> {
            if (distanceInMeters * 3.2808399 < 5280) {
                Measure((distanceInMeters * 3.2808399).roundToInt(), MeasureUnit.FOOT)
            } else {
                Measure((distanceInMeters * 0.000621371192).roundTo(3), MeasureUnit.MILE)
            }
        }

        else -> Measure(-1, MeasureUnit.METER)
    }

    return "${measure.number} ${stringResource(id = getShortUnitName(measure.unit))}"
}

@Composable
fun formatSpeed(distanceInMeters: Double, duration: Duration, measureUnit: MeasureUnit): String {
    val avgSpeedInKph = (distanceInMeters / 1000.0) / (duration.inWholeSeconds / 3600.0)
    val measure = when (measureUnit) {
        MeasureUnit.METER -> {
            Measure(avgSpeedInKph.roundTo(3), MeasureUnit.KILOMETER_PER_HOUR)
        }

        MeasureUnit.FOOT -> {
            Measure((avgSpeedInKph * 0.621371192).roundTo(3), MeasureUnit.MILE_PER_HOUR)
        }

        else -> Measure(-1, MeasureUnit.KILOMETER_PER_HOUR)
    }

    return "${measure.number} ${stringResource(id = getShortUnitName(measure.unit))}"
}

@Composable
fun formatPace(duration: Duration, distanceInMeters: Double, measureUnit: MeasureUnit): String {
    val avgPace: Duration
    val paceUnit: MeasureUnit
    when (measureUnit) {
        MeasureUnit.METER -> {
            avgPace = ((duration.inWholeSeconds / 60) / distanceInMeters * 1000).toDuration(DurationUnit.MINUTES)
            paceUnit = MeasureUnit.KILOMETER
        }

        MeasureUnit.FOOT -> {
            avgPace = ((duration.inWholeSeconds / 60) / (distanceInMeters * 0.000621371192)).toDuration(DurationUnit.MINUTES)
            paceUnit = MeasureUnit.MILE
        }

        else -> {
            avgPace = (-1).toDuration(DurationUnit.MINUTES)
            paceUnit = MeasureUnit.KILOMETER
        }
    }
    return "${formatDuration(avgPace)}/${stringResource(id = getShortUnitName(paceUnit))}"
}

fun getShortUnitName(unit: MeasureUnit): Int {
    return when (unit) {
        MeasureUnit.METER -> R.string.unit_meter
        MeasureUnit.KILOMETER -> R.string.unit_kilometer
        MeasureUnit.FOOT -> R.string.unit_foot
        MeasureUnit.MILE -> R.string.unit_mile
        MeasureUnit.KILOMETER_PER_HOUR -> R.string.unit_kilometer_per_hour
        MeasureUnit.MILE_PER_HOUR -> R.string.unit_mile_per_hour
        else -> R.string.unit_not_found
    }
}

fun Double.roundTo(n: Int): Double {
    return round(this * 10f.pow(n)) / 10f.pow(n)
}