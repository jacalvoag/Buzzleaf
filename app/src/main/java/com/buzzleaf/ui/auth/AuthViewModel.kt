package com.buzzleaf.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buzzleaf.data.remote.FirebaseManager
import com.buzzleaf.utils.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val userId: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false
)

class AuthViewModel(
    private val firebaseManager: FirebaseManager,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        viewModelScope.launch {
            preferencesManager.isLoggedInFlow.collect { isLoggedIn ->
                _uiState.value = _uiState.value.copy(isLoggedIn = isLoggedIn)
            }
        }
    }

    fun registerWithEmail(email: String, password: String, confirmPassword: String) {
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _authState.value = AuthState.Error("Por favor completa todos los campos")
            return
        }

        if (!isValidEmail(email)) {
            _authState.value = AuthState.Error("Correo electrónico inválido")
            return
        }

        if (password.length < 8) {
            _authState.value = AuthState.Error("La contraseña debe tener al menos 8 caracteres")
            return
        }

        if (password != confirmPassword) {
            _authState.value = AuthState.Error("Las contraseñas no coinciden")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = firebaseManager.registerWithEmail(email, password)

            result.onSuccess { user ->
                preferencesManager.setLoggedIn(true)
                preferencesManager.setUserId(user.uid)

                _authState.value = AuthState.Success(user.uid)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = true
                )
            }.onFailure { error ->
                val errorMessage = getErrorMessage(error)
                _authState.value = AuthState.Error(errorMessage)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = errorMessage
                )
            }
        }
    }

    fun loginWithEmail(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Por favor completa todos los campos")
            return
        }

        if (!isValidEmail(email)) {
            _authState.value = AuthState.Error("Correo electrónico inválido")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = firebaseManager.loginWithEmail(email, password)

            result.onSuccess { user ->
                preferencesManager.setLoggedIn(true)
                preferencesManager.setUserId(user.uid)

                _authState.value = AuthState.Success(user.uid)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = true
                )
            }.onFailure { error ->
                val errorMessage = getErrorMessage(error)
                _authState.value = AuthState.Error(errorMessage)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = errorMessage
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            firebaseManager.logout()
            preferencesManager.clearAll()

            _authState.value = AuthState.Idle
            _uiState.value = AuthUiState(isLoggedIn = false)
        }
    }

    fun clearError() {
        _authState.value = AuthState.Idle
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun getErrorMessage(error: Throwable): String {
        return when {
            error.message?.contains("network", ignoreCase = true) == true ->
                "Error de conexión. Verifica tu internet"

            error.message?.contains("password", ignoreCase = true) == true ->
                "Contraseña incorrecta"

            error.message?.contains("user not found", ignoreCase = true) == true ||
                    error.message?.contains("no user record", ignoreCase = true) == true ->
                "No existe una cuenta con este correo"

            error.message?.contains("email already in use", ignoreCase = true) == true ->
                "Este correo ya está registrado"

            error.message?.contains("invalid email", ignoreCase = true) == true ->
                "Correo electrónico inválido"

            error.message?.contains("weak password", ignoreCase = true) == true ->
                "La contraseña es muy débil"

            else -> "Error: ${error.message ?: "Algo salió mal"}"
        }
    }
}