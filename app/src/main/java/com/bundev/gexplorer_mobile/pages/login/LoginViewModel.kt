package com.bundev.gexplorer_mobile.pages.login

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
import me.thefen.gexplorerapi.dtos.LoginDto
import me.thefen.gexplorerapi.dtos.RegisterDto
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: GexplorerRepository,
    @ApplicationContext val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow<ApiResource<String>>(ApiResource.Loading())
    val state: StateFlow<ApiResource<String>>
        get() = _state

    fun login(userName: String, password: String) {
        Log.d("gexapi", "login called")

        viewModelScope.launch {
            _state.value = repo.login(LoginDto(userName, password))
            if (_state.value is ApiResource.Success) {
                context.dataStore.edit { preferences ->
                    preferences[TOKEN] = _state.value.data.toString()
                    preferences[USERNAME] = repo.username!!
                    preferences[USER_ID] = repo.id!!.toString()
                }
                Log.d("StoreData", "User token is set ${_state.value.data.toString()}")
            }
        }
    }

    fun register(userName: String, email: String, password: String) {
        Log.d("gexapi", "register called")

        viewModelScope.launch {
            _state.value = repo.register(RegisterDto(userName, email, password))
        }
    }
}