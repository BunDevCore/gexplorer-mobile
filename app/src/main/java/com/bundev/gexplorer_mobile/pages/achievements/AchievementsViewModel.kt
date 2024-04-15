package com.bundev.gexplorer_mobile.pages.achievements

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
import me.thefen.gexplorerapi.dtos.AchievementGetDto
import me.thefen.gexplorerapi.dtos.UserDto
import javax.inject.Inject

data class AchievementsModelState(
    val userDto: ApiResource<UserDto> = ApiResource.Loading(),
    val achievementDto: ApiResource<AchievementGetDto>? = null,
)

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val repo: GexplorerRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(AchievementsModelState())
    val state: StateFlow<AchievementsModelState>
        get() = _state

    fun fetchSelf() {
        Log.d("gexapi", "fetchUser called")

        viewModelScope.launch {
            Log.d("gexapi", "launching self fetch...")
            _state.update { AchievementsModelState(repo.getSelf(), it.achievementDto) }
        }
    }
}