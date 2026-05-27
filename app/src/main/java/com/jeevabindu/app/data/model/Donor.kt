package com.jeevabindu.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "donors")
data class Donor(
    @PrimaryKey val id: String,
    val name: String,
    val phone: String,
    val bloodGroup: String,
    val age: Int,
    val location: String,
    val isAvailable: Boolean = true,
    val lastDonationDate: Long? = null,
    val livesSaved: Int = 0,
    val isCurrentUser: Boolean = false,
    val distanceKm: Double = 0.0
) {
    val isEligible: Boolean
        get() {
            if (lastDonationDate == null) return true
            val daysSinceDonation = (System.currentTimeMillis() - lastDonationDate) / (1000 * 60 * 60 * 24)
            return daysSinceDonation >= 90
        }

    val daysUntilEligible: Int
        get() {
            if (lastDonationDate == null) return 0
            val daysSinceDonation = ((System.currentTimeMillis() - lastDonationDate) / (1000 * 60 * 60 * 24)).toInt()
            return maxOf(0, 90 - daysSinceDonation)
        }

    val daysSinceLastDonation: Int
        get() {
            if (lastDonationDate == null) return 90
            return ((System.currentTimeMillis() - lastDonationDate) / (1000 * 60 * 60 * 24)).toInt()
        }
}
