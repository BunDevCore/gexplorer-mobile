package com.bundev.gexplorer_mobile.pages.leaderboard

import android.annotation.SuppressLint
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
import me.thefen.gexplorerapi.dtos.LeaderboardEntryDto
import me.thefen.gexplorerapi.dtos.UserDto
import javax.inject.Inject

data class LeaderboardModelState(
    val userDto: ApiResource<UserDto> = ApiResource.Loading(),
    val leaderboardDto: ApiResource<Map<Int, LeaderboardEntryDto<Double>>> = ApiResource.Loading(),
)

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class LeaderboardViewModel @Inject constructor(
    private val repo: GexplorerRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(LeaderboardModelState())
    val state: StateFlow<LeaderboardModelState>
        get() = _state

    fun fetchSelf() {
        Log.d("gexapi", "fetchUser called")

        viewModelScope.launch {
            Log.d("gexapi", "launching self fetch...")
            _state.update { it.copy(userDto = repo.getSelf()) }
        }
    }

    fun getOverallLeaderboard(){
        Log.d("gexapi", "getOverallLeaderboard called")

        viewModelScope.launch {
            Log.d("gexapi", "launching get overall leaderboard...")
            _state.update { it.copy(leaderboardDto = repo.getOverallLeaderboard(1)) }
        }
    }
}