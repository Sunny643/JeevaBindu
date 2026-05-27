package com.jeevabindu.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jeevabindu.app.data.SampleData
import com.jeevabindu.app.data.local.AppDatabase
import com.jeevabindu.app.data.model.Donor
import com.jeevabindu.app.data.model.DonationRecord
import com.jeevabindu.app.data.repository.DonorRepository
import com.jeevabindu.app.data.repository.EmergencyRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val donorRepo = DonorRepository(db.donorDao())
    private val emergencyRepo = EmergencyRepository(db.emergencyDao())

    private val _isDataLoaded = MutableStateFlow(false)

    val currentUser: StateFlow<Donor?> = donorRepo.getCurrentUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val donationHistory: StateFlow<List<DonationRecord>> = emergencyRepo.getAllDonationRecords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadSampleData()
    }

    private fun loadSampleData() {
        viewModelScope.launch {
            // Check if data already exists
            val existingUser = donorRepo.getCurrentUser().firstOrNull()
            if (existingUser == null) {
                // Insert sample data
                donorRepo.insertDonor(SampleData.getCurrentUser())
                donorRepo.insertAll(SampleData.getSampleDonors())
                SampleData.getSampleDonationRecords().forEach {
                    emergencyRepo.insertDonationRecord(it)
                }
                emergencyRepo.insertEmergency(SampleData.getSampleEmergency())
                _isDataLoaded.value = true
            } else {
                _isDataLoaded.value = true
            }
        }
    }

    fun registerDonor(name: String, phone: String, bloodGroup: String, age: Int, location: String) {
        viewModelScope.launch {
            val donor = Donor(
                id = "user_current",
                name = name,
                phone = phone,
                bloodGroup = bloodGroup,
                age = age,
                location = location,
                isAvailable = true,
                lastDonationDate = null,
                livesSaved = 0,
                isCurrentUser = true
            )
            donorRepo.insertDonor(donor)
        }
    }

    fun recordDonation() {
        viewModelScope.launch {
            donorRepo.recordDonation(System.currentTimeMillis())
            val record = DonationRecord(
                id = "rec_${System.currentTimeMillis()}",
                donorId = "user_current",
                hospitalName = "Local Hospital",
                date = System.currentTimeMillis(),
                volumeMl = 450,
                type = "Whole Blood Donation"
            )
            emergencyRepo.insertDonationRecord(record)
        }
    }
}
