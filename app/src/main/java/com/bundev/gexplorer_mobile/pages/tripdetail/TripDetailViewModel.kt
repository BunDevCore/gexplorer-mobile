package com.bundev.gexplorer_mobile.pages.tripdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.repo.GexplorerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.thefen.gexplorerapi.dtos.DetailedTripDto
import me.thefen.gexplorerapi.dtos.LoginDto
import javax.inject.Inject

@HiltViewModel
class TripDetailViewModel @Inject constructor(
    private val repo: GexplorerRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ApiResource<DetailedTripDto>>(ApiResource.Loading())
    private var fetchAttempted: Boolean = false
    val state: StateFlow<ApiResource<DetailedTripDto>>
        get() = _state

    init {
        viewModelScope.launch {
            repo.login(LoginDto("fen.", "testTEST123"))
        }
    }

    fun fetchTrip(tripId: String) {
        Log.d("gexapi", "fetchTrip called")

        fetchAttempted = true
        viewModelScope.launch {
            Log.d("gexapi", "launching trip fetch...")
            _state.value = repo.getTrip(tripId)
        }
    }
}