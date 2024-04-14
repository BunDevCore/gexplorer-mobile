package com.bundev.gexplorer_mobile.pages.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.repo.GexplorerRepository
import com.mapbox.common.location.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.thefen.gexplorerapi.dtos.TimedPoint
import me.thefen.gexplorerapi.dtos.TripDto
import me.thefen.gexplorerapi.dtos.UserDto
import javax.inject.Inject

data class MapViewModelState(
    val userDto: ApiResource<UserDto> = ApiResource.Loading(),
    val tripDto: ApiResource<TripDto>? = null,
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repo: GexplorerRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(MapViewModelState())
    val state: StateFlow<MapViewModelState>
        get() = _state

    fun fetchSelf() {
        Log.d("gexapi", "fetchUser called")

        viewModelScope.launch {
            Log.d("gexapi", "launching self fetch...")
            _state.update { MapViewModelState(repo.getSelf(), it.tripDto) }
        }
    }

    fun sendTrip(locationList: List<Location>) {
        Log.d("gexapi", "send trip data called")
        val timedPoints = locationList.map { location ->
            TimedPoint(
                location.longitude,
                location.latitude,
                location.timestamp
            )
        }
        viewModelScope.launch {
            Log.d("gexapi", "sending trip data...")
            _state.update { MapViewModelState(it.userDto, repo.sendTrip(timedPoints)) }
        }
    }
}