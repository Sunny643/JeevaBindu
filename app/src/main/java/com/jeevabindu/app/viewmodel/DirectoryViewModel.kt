package com.jeevabindu.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jeevabindu.app.data.local.AppDatabase
import com.jeevabindu.app.data.model.Donor
import com.jeevabindu.app.data.repository.DonorRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DirectoryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val donorRepo = DonorRepository(db.donorDao())

    private val _selectedBloodGroup = MutableStateFlow("All")
    val selectedBloodGroup: StateFlow<String> = _selectedBloodGroup

    private val _proximityKm = MutableStateFlow(10f)
    val proximityKm: StateFlow<Float> = _proximityKm

    val allDonors: StateFlow<List<Donor>> = donorRepo.getAvailableDonors()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredDonors: StateFlow<List<Donor>> = combine(
        allDonors,
        _selectedBloodGroup,
        _proximityKm
    ) { donors, bloodGroup, proximity ->
        donors.filter { donor ->
            val matchesGroup = bloodGroup == "All" || donor.bloodGroup == bloodGroup
            val matchesProximity = donor.distanceKm <= proximity
            matchesGroup && matchesProximity
        }.sortedBy { it.distanceKm }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setBloodGroupFilter(group: String) {
        _selectedBloodGroup.value = group
    }

    fun setProximity(km: Float) {
        _proximityKm.value = km
    }
}
