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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.thefen.gexplorerapi.dtos.LoginDto
import me.thefen.gexplorerapi.dtos.RegisterDto
import javax.inject.Inject

data class LoginViewModelState(
    val login: ApiResource<String> = ApiResource.Loading(),
    val register: ApiResource<String> = ApiResource.Loading(),
)

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: GexplorerRepository,
    @ApplicationContext val context: Context,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginViewModelState())
    val state: StateFlow<LoginViewModelState>
        get() = _state

    fun login(userName: String, password: String) {
        Log.d("gexapi", "login called")

        viewModelScope.launch {
            _state.update { LoginViewModelState(repo.login(LoginDto(userName, password)), it.register) }
            if (_state.value.login is ApiResource.Success) {
                context.dataStore.edit { preferences ->
                    preferences[TOKEN] = _state.value.login.data.toString()
                    preferences[USERNAME] = repo.username!!
                    preferences[USER_ID] = repo.id!!.toString()
                }
                Log.d("DataStore", "User token is set ${_state.value.login.data.toString()}")
            }
        }
    }

    fun register(userName: String, email: String, password: String) {
        Log.d("gexapi", "register called")

        viewModelScope.launch {
            _state.update { LoginViewModelState(it.login, repo.register(RegisterDto(userName, email, password))) }
        }
    }
}