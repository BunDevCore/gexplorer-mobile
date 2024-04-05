package com.bundev.gexplorer_mobile.pages.trips

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.repo.GexplorerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.thefen.gexplorerapi.dtos.LoginDto
import me.thefen.gexplorerapi.dtos.TripDto
import javax.inject.Inject

data class TripsPageState(var trips: ApiResource<List<TripDto>>, var loggedIn: Boolean?)

@HiltViewModel
class TripsViewModel @Inject constructor(
    private val repo: GexplorerRepository
) : ViewModel() {
    private val _state = MutableStateFlow(TripsPageState(ApiResource.Loading(), repo.username != null))
    private var fetchAttempted: Boolean = false
    val state: StateFlow<TripsPageState>
        get() = _state
    
    fun fetchTrips() {
        Log.d("gexapi", "fetchTrips called")
        
        fetchAttempted = true
        viewModelScope.launch {
            Log.d("gexapi", "launching trip fetch...")
            val trips = repo.getSelf().mapSuccess { it.trips }
            _state.update { 
                TripsPageState(trips, it.loggedIn)
            }
            Log.d("gexapi", "got trips ${trips.kind}")
        }
    }

    fun checkLogin() {
        _state.update { 
            TripsPageState(it.trips, repo.username != null)
        } 
    }
}