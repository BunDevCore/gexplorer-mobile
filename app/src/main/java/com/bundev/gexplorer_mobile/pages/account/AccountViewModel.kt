package com.bundev.gexplorer_mobile.pages.account

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bundev.gexplorer_mobile.data.ApiResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.thefen.gexplorerapi.dtos.UserDto
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.bundev.gexplorer_mobile.repo.GexplorerRepository
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repo: GexplorerRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ApiResource<UserDto>>(ApiResource.Loading())
    private var fetchAttempted: Boolean = false
    val state: StateFlow<ApiResource<UserDto>>
        get() = _state

    fun fetchUser(username: String) {
        Log.d("gexapi", "fetchUser called")

        fetchAttempted = true
        viewModelScope.launch {
            Log.d("gexapi", "launching user fetch...")
            _state.value = repo.getUser(username)
        }
    }
}