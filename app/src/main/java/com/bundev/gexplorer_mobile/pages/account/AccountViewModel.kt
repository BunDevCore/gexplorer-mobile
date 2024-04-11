package com.bundev.gexplorer_mobile.pages.account

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bundev.gexplorer_mobile.TOKEN
import com.bundev.gexplorer_mobile.USERNAME
import com.bundev.gexplorer_mobile.USER_ID
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.dataStore
import com.bundev.gexplorer_mobile.repo.GexplorerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.thefen.gexplorerapi.dtos.UserDto
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repo: GexplorerRepository,
    @ApplicationContext val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow<ApiResource<UserDto>>(ApiResource.Loading())
    private var fetchAttempted: Boolean = false
    val state: StateFlow<ApiResource<UserDto>>
        get() = _state

    fun fetchSelf() {
        Log.d("gexapi", "fetchUser called")

        fetchAttempted = true
        viewModelScope.launch {
            Log.d("gexapi", "launching self fetch...")
            _state.value = repo.getSelf()
        }
    }

    fun logout() {
        viewModelScope.launch {
            repo.logout()
            context.dataStore.edit { it.remove(TOKEN); it.remove(USERNAME); it.remove(USER_ID) }
            Log.d("StoreData", "User token is null")
        }
    }
}