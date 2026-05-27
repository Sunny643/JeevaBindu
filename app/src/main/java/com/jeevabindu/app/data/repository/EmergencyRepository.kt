package com.jeevabindu.app.data.repository

import com.jeevabindu.app.data.local.EmergencyDao
import com.jeevabindu.app.data.model.DonationRecord
import com.jeevabindu.app.data.model.EmergencyRequest
import kotlinx.coroutines.flow.Flow

class EmergencyRepository(private val emergencyDao: EmergencyDao) {

    fun getAllEmergencies(): Flow<List<EmergencyRequest>> = emergencyDao.getAllEmergencies()

    fun getActiveEmergencies(): Flow<List<EmergencyRequest>> = emergencyDao.getActiveEmergencies()

    suspend fun getEmergencyById(id: String): EmergencyRequest? =
        emergencyDao.getEmergencyById(id)

    suspend fun insertEmergency(emergency: EmergencyRequest) =
        emergencyDao.insertEmergency(emergency)

    suspend fun updateStatus(id: String, status: String) =
        emergencyDao.updateStatus(id, status)

    fun getDonationHistory(donorId: String): Flow<List<DonationRecord>> =
        emergencyDao.getDonationHistory(donorId)

    suspend fun insertDonationRecord(record: DonationRecord) =
        emergencyDao.insertDonationRecord(record)

    fun getAllDonationRecords(): Flow<List<DonationRecord>> =
        emergencyDao.getAllDonationRecords()
}
