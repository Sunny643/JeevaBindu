package com.jeevabindu.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val _emergencyNotifications = MutableStateFlow(true)
    val emergencyNotifications: StateFlow<Boolean> = _emergencyNotifications

    private val _locationSharing = MutableStateFlow(false)
    val locationSharing: StateFlow<Boolean> = _locationSharing

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _isPhoneVerified = MutableStateFlow(false)
    val isPhoneVerified: StateFlow<Boolean> = _isPhoneVerified

    private val _isProfileComplete = MutableStateFlow(false)
    val isProfileComplete: StateFlow<Boolean> = _isProfileComplete

    fun toggleEmergencyNotifications() {
        _emergencyNotifications.value = !_emergencyNotifications.value
    }

    fun toggleLocationSharing() {
        _locationSharing.value = !_locationSharing.value
    }

    fun setPhoneVerified(verified: Boolean) {
        _isPhoneVerified.value = verified
    }

    fun setProfileComplete(complete: Boolean) {
        _isProfileComplete.value = complete
    }

    fun logout() {
        _isLoggedIn.value = false
        _isPhoneVerified.value = false
        _isProfileComplete.value = false
    }
}
