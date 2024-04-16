package com.bundev.gexplorer_mobile.pages.savedtrips

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.formatDate
import com.bundev.gexplorer_mobile.navigateTo
import com.bundev.gexplorer_mobile.pages.trips.TripItem
import com.bundev.gexplorer_mobile.ui.GroupingList
import com.bundev.gexplorer_mobile.ui.LoadingCard
import com.bundev.gexplorer_mobile.ui.MiddleCard
import com.bundev.gexplorer_mobile.ui.TitleBar
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun SavedTripsPage(navController: NavHostController, changePage: () -> Unit) {
    val vm = hiltViewModel<SavedTripsViewModel>()
    val state by vm.state.collectAsState()
    LaunchedEffect(Unit) { vm.fetchSavedTrips() }

    Column(Modifier.fillMaxSize()) {
        TitleBar(text = stringResource(id = R.string.saved_trips), navController = navController) {
            changePage()
        }
        if (state is ApiResource.Loading) return LoadingCard(text = stringResource(id = R.string.loading_api))
        if (state.data == null) {
            Log.d("trips", "state trips data is null (${state.kind})")
            return LoadingCard(text = stringResource(id = R.string.loading))
        }
        if (state.data!!.isEmpty())
            return MiddleCard { Text(stringResource(id = R.string.no_saved_trips)) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            GroupingList(
                items = state.data!!,
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
}