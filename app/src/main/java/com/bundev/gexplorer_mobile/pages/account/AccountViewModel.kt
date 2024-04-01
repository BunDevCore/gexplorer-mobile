package com.bundev.gexplorer_mobile.pages.account

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bundev.gexplorer_mobile.data.ApiResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.thefen.gexplorerapi.dtos.UserDto
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.thefen.gexplorerapi.GexplorerClient

class AccountViewModel : ViewModel() {
    private val _state = MutableStateFlow<ApiResource<UserDto>>(ApiResource.Loading())
    private var fetchAttempted: Boolean = false
    val state: StateFlow<ApiResource<UserDto>>
        get() = _state

    fun fetchUser(username: String) {
        Log.d("gexapi", "fetchUser called")

        fetchAttempted = true
        viewModelScope.launch {
            Log.d("gexapi", "launching user fetch...")
            val result = GexplorerClient.runCatching {
                this.gexplorerApi.getUser(username)
            }
            Log.d("gexapi", "got result!!")
            _state.value = result.fold(
                { ApiResource.Success(it) },
                { fetchAttempted = false; ApiResource.Error() }
            )
        }
    }
}