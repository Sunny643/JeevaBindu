package com.jeevabindu.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "donation_records")
data class DonationRecord(
    @PrimaryKey val id: String,
    val donorId: String,
    val hospitalName: String,
    val date: Long,
    val volumeMl: Int = 450,
    val type: String = "Whole Blood Donation"
)
