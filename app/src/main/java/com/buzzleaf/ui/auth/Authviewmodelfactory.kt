package com.buzzleaf.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.buzzleaf.data.remote.FirebaseManager
import com.buzzleaf.utils.PreferencesManager
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class AuthViewModelFactory @AssistedInject constructor(
    private val firebaseManager: FirebaseManager,
    private val preferencesManager: PreferencesManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(firebaseManager, preferencesManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@AssistedFactory
interface AuthViewModelAssistedFactory {
    fun create(): AuthViewModelFactory
}