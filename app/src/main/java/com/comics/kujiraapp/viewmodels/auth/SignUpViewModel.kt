package com.comics.kujiraapp.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comics.kujiraapp.models.auth.SignUpRequest
import com.comics.kujiraapp.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SignUpState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)

class SignUpViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _state.value = SignUpState(loading = true)
            try {
                val response = ApiService.authApi.signUp(SignUpRequest(email, password))
                // TODO: Save the token
                _state.value = SignUpState(success = true)
            } catch (e: Exception) {
                _state.value = SignUpState(error = e.message)
            }
        }
    }
}
