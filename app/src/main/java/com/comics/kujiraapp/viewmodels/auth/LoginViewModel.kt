package com.comics.kujiraapp.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comics.kujiraapp.models.auth.LoginRequest
import com.comics.kujiraapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginState(
  val loading: Boolean = false,
  val success: Boolean = false,
  val error: String? = null
)

class LoginViewModel : ViewModel() {

  private val _state = MutableStateFlow(LoginState())
  val state: StateFlow<LoginState> = _state.asStateFlow()

  fun login(email: String, password: String) {
    viewModelScope.launch {
      _state.value = LoginState(loading = true)
      try {
        val response = RetrofitClient.authApi.login(LoginRequest(email, password))
        _state.value = LoginState(success = true)
      } catch (e: Exception) {
        _state.value = LoginState(error = e.message)
      }
    }
  }
}
