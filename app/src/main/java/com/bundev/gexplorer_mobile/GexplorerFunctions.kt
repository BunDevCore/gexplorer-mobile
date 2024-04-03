package com.bundev.gexplorer_mobile

import android.text.format.DateUtils
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import java.text.DateFormat
import kotlin.math.pow
import kotlin.math.round
import kotlin.time.Duration

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

fun Double.roundTo(n: Int): Double {
    return round(this * 10f.pow(n)) / 10f.pow(n)
}