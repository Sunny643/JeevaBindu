package com.jeevabindu.app.data.repository

import com.jeevabindu.app.data.local.DonorDao
import com.jeevabindu.app.data.model.Donor
import kotlinx.coroutines.flow.Flow

class DonorRepository(private val donorDao: DonorDao) {

    fun getAllDonors(): Flow<List<Donor>> = donorDao.getAllDonors()

    fun getCurrentUser(): Flow<Donor?> = donorDao.getCurrentUser()

    fun getAvailableDonors(): Flow<List<Donor>> = donorDao.getAvailableDonors()

    fun getDonorsByBloodGroup(bloodGroup: String): Flow<List<Donor>> =
        donorDao.getDonorsByBloodGroup(bloodGroup)

    suspend fun insertDonor(donor: Donor) = donorDao.insertDonor(donor)

    suspend fun insertAll(donors: List<Donor>) = donorDao.insertAll(donors)

    suspend fun updateDonor(donor: Donor) = donorDao.updateDonor(donor)

    suspend fun recordDonation(date: Long) = donorDao.recordDonation(date)

    suspend fun updateAvailability(donorId: String, available: Boolean) =
        donorDao.updateAvailability(donorId, available)

    suspend fun deleteAll() = donorDao.deleteAll()
}
