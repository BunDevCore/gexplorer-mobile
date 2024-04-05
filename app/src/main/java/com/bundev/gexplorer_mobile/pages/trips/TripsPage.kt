package com.bundev.gexplorer_mobile.pages.trips

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.IconAndTextButton
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.classes.Screen
import com.bundev.gexplorer_mobile.classes.Trip
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.formatDate
import com.bundev.gexplorer_mobile.formatDistance
import com.bundev.gexplorer_mobile.formatDuration
import com.bundev.gexplorer_mobile.formatTime
import com.bundev.gexplorer_mobile.icons.filled.Bookmark
import com.bundev.gexplorer_mobile.icons.simple.Walk
import com.bundev.gexplorer_mobile.measureUnit
import com.bundev.gexplorer_mobile.selectedTabSave
import com.bundev.gexplorer_mobile.ui.GroupingList
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.thefen.gexplorerapi.dtos.TripDto
import kotlin.time.Duration.Companion.hours

private var DEBUG = true
private val tempTrips = listOf(
    Trip(
        distance = 1000.0,
        timeBegun = Clock.System.now() - 2.hours,
        timeEnded = Clock.System.now() - 1.hours
    ),
    Trip(
        distance = 100.3,
        timeBegun = Clock.System.now() - 3.hours,
        timeEnded = Clock.System.now() - 2.9.hours
    ),
    Trip(
        distance = 500.3,
        timeBegun = Clock.System.now() - 7.hours,
        timeEnded = Clock.System.now() - 5.hours
    ),
    Trip(
        distance = 123455.0,
        timeBegun = Clock.System.now() - 7.hours,
        timeEnded = Clock.System.now() - 5.hours
    ),
    Trip(
        distance = 100.0,
        timeBegun = Clock.System.now() - 7.hours,
        timeEnded = Clock.System.now() - 5.hours
    ),
    Trip(
        distance = 499.0,
        timeBegun = Clock.System.now() - 7.hours,
        timeEnded = Clock.System.now() - 5.hours
    ),
    Trip(
        distance = 34500.0,
        timeBegun = Clock.System.now() - 25.6.hours,
        timeEnded = Clock.System.now() - 24.9.hours
    ),
    Trip(
        distance = 99999.0,
        timeBegun = Clock.System.now() - 27.hours,
        timeEnded = Clock.System.now() - 26.1.hours
    ),
    Trip(
        distance = 450.0,
        timeBegun = Clock.System.now() - 48.hours,
        timeEnded = Clock.System.now() - 47.7.hours
    ),
    Trip(
        distance = 6700.0,
        timeBegun = Clock.System.now() - 56.hours,
        timeEnded = Clock.System.now() - 55.hours
    ),
    Trip(
        distance = 42000.0,
        timeBegun = Clock.System.now() - 57.hours,
        timeEnded = Clock.System.now() - 56.8.hours
    ),
    Trip(
        distance = 20200.0,
        timeBegun = Clock.System.now() - 59.hours,
        timeEnded = Clock.System.now() - 58.4.hours
    ),
    Trip(
        distance = 987.0,
        timeBegun = Clock.System.now() - 61.hours,
        timeEnded = Clock.System.now() - 60.6.hours
    ),
    Trip(
        distance = 987.0,
        timeBegun = Clock.System.now() - 112.hours,
        timeEnded = Clock.System.now() - 111.02.hours
    ),
    Trip(
        distance = 0.0,
        timeBegun = Clock.System.now() - 124.hours,
        timeEnded = Clock.System.now() - 124.hours
    )
)

@Composable
fun TripsPage(navController: NavHostController? = null, changePage: () -> Unit) {
//    val items = tempTrips
//    
//    if (items.isEmpty()) {
//        EmptyTripsPage()
//        return
//    }
    

    val vm = hiltViewModel<TripsViewModel>()
    val state by vm.state.collectAsState()
    LaunchedEffect(Unit) {
        vm.checkLogin()
        Log.d("trips", "launch effect")
    }

    Log.d("trips", "recomposed..., loggedIn=${state.loggedIn}")
    
    when (state.loggedIn) {
        false -> return Text("please log in ffs") 
        true -> {
            Log.d("trips", "got auth success, fetchtrips")
            if (state.trips.data == null)
                vm.fetchTrips()
        }
        null -> return Text("loading...")
    }
    
    if (state.trips.data == null) {
        Log.d("trips", "state trips data is null (${state.trips.kind})")
        return Text("loading...2")
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
            //TODO show saved trips
        }
        GroupingList(
            items = state.trips.data!!,
            groupBy = { it.startTime.toLocalDateTime(TimeZone.currentSystemDefault()).date },
            title = { formatDate(it) },
        ) { trip ->
            TripItem(trip) {
//                val routeScreen = Screen.TripDetail.route + "/${trip.id}"
                val routeScreen = "trip/${trip.id}"
                Log.d("tripdetailentry", "entering $routeScreen")
                if (selectedTabSave != routeScreen) {
                    selectedTabSave = routeScreen
                    changePage()
                    navController?.navigate(routeScreen) {
                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true     // the navigation WILL use stale data if it's uncommented
                        }
                        launchSingleTop = false
//                        restoreState = true      // the navigation WILL use stale data if it's uncommented
                    }
                }
            }
        }
    }
}

@Composable
private fun TripItem(trip: TripDto, onClick: () -> Unit) {
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
                        measureUnit = measureUnit
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

@Composable
private fun EmptyTripsPage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card {
            Text(text = stringResource(id = R.string.no_trips), Modifier.padding(10.dp))
        }
    }
}

@Preview(showBackground = true, locale = "en")
@Composable
private fun TripsPagePreview() {
    DEBUG = true
    TripsPage {}
}