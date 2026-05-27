package com.jeevabindu.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emergency_requests")
data class EmergencyRequest(
    @PrimaryKey val id: String,
    val bloodGroup: String,
    val hospitalName: String,
    val hospitalAddress: String = "",
    val urgencyLevel: String,
    val contactNumber: String,
    val unitsRequired: Int,
    val coordinatorName: String = "",
    val coordinatorNote: String = "",
    val distanceKm: Double = 0.0,
    val travelTimeMinutes: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "Active",
    val isVerified: Boolean = false
)
