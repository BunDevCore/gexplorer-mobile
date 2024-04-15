package com.bundev.gexplorer_mobile.pages.tripdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.repo.GexplorerRepository
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.thefen.gexplorerapi.dtos.DetailedTripDto
import me.thefen.gexplorerapi.dtos.StarStatusDto
import javax.inject.Inject

data class TripDetailModelState(val detailedTripDto: DetailedTripDto, val point: Point)

@HiltViewModel
class TripDetailViewModel @Inject constructor(
    private val repo: GexplorerRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<ApiResource<TripDetailModelState>>(ApiResource.Loading())
    val state: StateFlow<ApiResource<TripDetailModelState>>
        get() = _state

    fun fetchTrip(tripId: String) {
        Log.d("tripdetailvm", "fetchTrip called")

        viewModelScope.launch {
            Log.d("tripdetailvm", "launching trip fetch... $tripId")
            val trip = repo.getTrip(tripId).mapSuccess { TripDetailModelState(it, getCenter(it)) }
            _state.value = trip
        }
    }

    fun getCenter(detailedTripDto: DetailedTripDto): Point {
        val polygon = detailedTripDto.gpsPolygon as Polygon
        val minLatitude = polygon.coordinates()[0].minBy { it.latitude() }
        val minLongitude = polygon.coordinates()[0].minBy { it.longitude() }
        val maxLatitude = polygon.coordinates()[0].maxBy { it.latitude() }
        val maxLongitude = polygon.coordinates()[0].maxBy { it.longitude() }
        return Point.fromLngLat(
            (maxLongitude.longitude() + minLongitude.longitude()) / 2,
            (maxLatitude.latitude() + minLatitude.latitude()) / 2
        )
    }

    fun updateStarred(){
        Log.d("tripdetailvm", "update saved called")
        viewModelScope.launch {
            val detailedTrip = _state.value.data!!.detailedTripDto
            _state.update {
                val newTrip = detailedTrip.copy(starred = !detailedTrip.starred)
                it.mapSuccess { TripDetailModelState(newTrip, it.point) }
            }
            repo.setTripStar(detailedTrip.id, StarStatusDto(!detailedTrip.starred))
        }
    }
}