package com.bundev.gexplorer_mobile.pages.map

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
class MapViewModel @Inject constructor(
    private val repo: GexplorerRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<ApiResource<UserDto>>(ApiResource.Loading())
    val state: StateFlow<ApiResource<UserDto>>
        get() = _state

    fun fetchSelf() {
        Log.d("gexapi", "fetchUser called")

        viewModelScope.launch {
            Log.d("gexapi", "launching self fetch...")
            _state.value = repo.getSelf()
        }
    }
}