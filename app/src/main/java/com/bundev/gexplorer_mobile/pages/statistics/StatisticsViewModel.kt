package com.bundev.gexplorer_mobile.pages.statistics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.repo.GexplorerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.thefen.gexplorerapi.dtos.UserDto
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repo: GexplorerRepository,
) : ViewModel() {
    private val _state =
        MutableStateFlow<ApiResource<StatisticsViewModelState>>(ApiResource.Loading())
    private var fetchAttempted: Boolean = false
    val state: StateFlow<ApiResource<StatisticsViewModelState>>
        get() = _state

    fun fetchStats() {
        Log.d("gexapi", "fetchUser called")

        fetchAttempted = true
        viewModelScope.launch {
            Log.d("gexapi", "launching self fetch...")
            _state.value = repo.getSelf().mapSuccess { user ->
                val districtEntries = user.districtAreas.map { (id, exploredArea) ->
                    val district = repo.districts.get(id)
                    //TODO error handling dla braku dzielnic
                    val percentage = exploredArea / (district?.area ?: 1.0)
                    DistrictEntry(district?.name ?: "THE DZIELNIA", exploredArea, percentage)
                }
                StatisticsViewModelState(user, districtEntries)
            }
        }
    }
}

data class StatisticsViewModelState(val user: UserDto, val districtEntries: List<DistrictEntry>)
data class DistrictEntry(val name: String, val exploredArea: Double, val percentage: Double)