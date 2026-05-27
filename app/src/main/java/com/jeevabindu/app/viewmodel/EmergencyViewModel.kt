package com.jeevabindu.app.viewmodel

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jeevabindu.app.data.local.AppDatabase
import com.jeevabindu.app.data.model.EmergencyRequest
import com.jeevabindu.app.data.repository.EmergencyRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class EmergencyViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val emergencyRepo = EmergencyRepository(db.emergencyDao())

    val activeEmergencies: StateFlow<List<EmergencyRequest>> =
        emergencyRepo.getActiveEmergencies()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _showAlert = MutableStateFlow(false)
    val showAlert: StateFlow<Boolean> = _showAlert

    private val _currentAlert = MutableStateFlow<EmergencyRequest?>(null)
    val currentAlert: StateFlow<EmergencyRequest?> = _currentAlert

    private val _isEnRoute = MutableStateFlow(false)
    val isEnRoute: StateFlow<Boolean> = _isEnRoute

    private val _routeProgress = MutableStateFlow(0f)
    val routeProgress: StateFlow<Float> = _routeProgress

    fun createEmergencyPost(
        bloodGroup: String,
        hospitalName: String,
        urgencyLevel: String,
        contactNumber: String,
        unitsRequired: Int
    ) {
        viewModelScope.launch {
            val emergency = EmergencyRequest(
                id = UUID.randomUUID().toString(),
                bloodGroup = bloodGroup,
                hospitalName = hospitalName,
                urgencyLevel = urgencyLevel,
                contactNumber = contactNumber,
                unitsRequired = unitsRequired,
                coordinatorName = "Emergency Coordinator",
                coordinatorNote = "Urgent blood requirement. Please respond if available.",
                distanceKm = (1..10).random().toDouble(),
                travelTimeMinutes = (5..30).random(),
                timestamp = System.currentTimeMillis(),
                status = "Active",
                isVerified = true
            )
            emergencyRepo.insertEmergency(emergency)

            // Simulate FCM notification - alert within 5 seconds
            delay(3000)
            _currentAlert.value = emergency
            _showAlert.value = true
            sendLocalNotification(emergency)
        }
    }

    fun dismissAlert() {
        _showAlert.value = false
    }

    fun respondToEmergency(emergencyId: String) {
        viewModelScope.launch {
            _isEnRoute.value = true
            _showAlert.value = false
            // Simulate route progress
            for (i in 0..100 step 5) {
                _routeProgress.value = i / 100f
                delay(500)
            }
        }
    }

    fun markArrived() {
        _isEnRoute.value = false
        _routeProgress.value = 0f
        viewModelScope.launch {
            _currentAlert.value?.let {
                emergencyRepo.updateStatus(it.id, "Fulfilled")
            }
        }
    }

    fun cancelRoute() {
        _isEnRoute.value = false
        _routeProgress.value = 0f
    }

    fun triggerSampleAlert() {
        viewModelScope.launch {
            val sampleEmergency = EmergencyRequest(
                id = UUID.randomUUID().toString(),
                bloodGroup = "O-",
                hospitalName = "Civil Hospital, Taluka",
                hospitalAddress = "Main Road, Hunasuru Town",
                urgencyLevel = "Critical",
                contactNumber = "+91 98765 43210",
                unitsRequired = 3,
                coordinatorName = "Sister Mary Magdalene",
                coordinatorNote = "Patient in surgery. We need at least 2 donors within the next hour to maintain stable supply.",
                distanceKm = 2.0,
                travelTimeMinutes = 8,
                timestamp = System.currentTimeMillis(),
                status = "Active",
                isVerified = true
            )
            emergencyRepo.insertEmergency(sampleEmergency)
            // Alert appears within 5 seconds as per success criteria
            delay(2000)
            _currentAlert.value = sampleEmergency
            _showAlert.value = true
            sendLocalNotification(sampleEmergency)
        }
    }

    private fun sendLocalNotification(emergency: EmergencyRequest) {
        val context = getApplication<Application>()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "emergency_alerts",
                "Emergency Blood Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Urgent blood donation alerts"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "emergency_alerts")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("🚨 EMERGENCY ALERT")
            .setContentText("URGENT: ${emergency.bloodGroup} needed at ${emergency.hospitalName}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(emergency.id.hashCode(), notification)
    }
}
