package com.bundev.gexplorer_mobile.pages.savedtrips

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.repo.GexplorerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.thefen.gexplorerapi.dtos.TripDto
import javax.inject.Inject

@HiltViewModel
class SavedTripsViewModel @Inject constructor(
    private val repo: GexplorerRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ApiResource<List<TripDto>>>(ApiResource.Loading())
    val state: StateFlow<ApiResource<List<TripDto>>>
        get() = _state

    fun fetchSavedTrips() {
        Log.d("gexapi", "fetchTrips called")

        viewModelScope.launch {
            Log.d("gexapi", "launching trip fetch...")
            _state.value = repo.getStarredTrips()
        }
    }
}