package com.bundev.gexplorer_mobile.pages.trips

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.ui.IconAndTextButton
import com.bundev.gexplorer_mobile.ui.LoadingCard
import com.bundev.gexplorer_mobile.ui.MiddleCard
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.classes.Screen
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.distanceUnit
import com.bundev.gexplorer_mobile.formatDate
import com.bundev.gexplorer_mobile.formatDistance
import com.bundev.gexplorer_mobile.formatDuration
import com.bundev.gexplorer_mobile.formatTime
import com.bundev.gexplorer_mobile.icons.filled.Bookmark
import com.bundev.gexplorer_mobile.icons.simple.Walk
import com.bundev.gexplorer_mobile.navigateTo
import com.bundev.gexplorer_mobile.ui.GroupingList
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.thefen.gexplorerapi.dtos.TripDto


@Composable
fun TripsPage(navController: NavHostController? = null, changePage: () -> Unit) {
    val vm = hiltViewModel<TripsViewModel>()
    val state by vm.state.collectAsState()
    LaunchedEffect(Unit) {
        vm.checkLogin()
        Log.d("trips", "launch effect")
    }

    Log.d("trips", "recomposed..., loggedIn=${state.loggedIn}")

    when (state.loggedIn) {
        false -> return MiddleCard {
            Text(text = stringResource(id = R.string.trips_not_logged_in))
            Button(onClick = { navigateTo(navController, Screen.Login.route) { changePage() } }) {
                Text(text = stringResource(id = R.string.log_in))
            }
        }

        true -> {
            Log.d("trips", "got auth success, fetchtrips")
            if (state.trips.data == null)
                vm.fetchTrips()
        }

        null -> return LoadingCard(text = stringResource(id = R.string.loading_api))
    }
    if (state.trips is ApiResource.Loading) return LoadingCard(text = stringResource(id = R.string.loading_api))
    if (state.trips.data == null) {
        Log.d("trips", "state trips data is null (${state.trips.kind})")
        return MiddleCard { Text(stringResource(id = R.string.no_trips)) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        IconAndTextButton(
            imageVector = GexplorerIcons.Filled.Bookmark,
            label = stringResource(id = R.string.saved_trips)
        ) {
            navigateTo(navController, Screen.SavedTrips.route) { changePage() }
        }
        GroupingList(
            items = state.trips.data!!,
            groupBy = { it.startTime.toLocalDateTime(TimeZone.currentSystemDefault()).date },
            title = { formatDate(it) },
        ) { trip ->
            TripItem(trip) {
                val routeTripDetail = "trip/${trip.id}"
                Log.d("tripdetailentry", "entering $routeTripDetail")
                navigateTo(navController, routeTripDetail) { changePage() }
            }
        }
    }
}

@Composable
fun TripItem(trip: TripDto, onClick: () -> Unit) {
    val duration = (trip.endTime - trip.startTime)
    val timeBegun = trip.startTime
    val distance = trip.length

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
                    text = formatDistance(
                        distanceInMeters = distance,
                        measureUnit = distanceUnit
                    )
                )
            },
            leadingContent = {
                Icon(
                    GexplorerIcons.Simple.Walk,
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