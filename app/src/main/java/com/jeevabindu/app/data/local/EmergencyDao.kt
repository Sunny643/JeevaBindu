package com.jeevabindu.app.data.local

import androidx.room.*
import com.jeevabindu.app.data.model.EmergencyRequest
import com.jeevabindu.app.data.model.DonationRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface EmergencyDao {
    @Query("SELECT * FROM emergency_requests ORDER BY timestamp DESC")
    fun getAllEmergencies(): Flow<List<EmergencyRequest>>

    @Query("SELECT * FROM emergency_requests WHERE status = 'Active' ORDER BY timestamp DESC")
    fun getActiveEmergencies(): Flow<List<EmergencyRequest>>

    @Query("SELECT * FROM emergency_requests WHERE id = :id")
    suspend fun getEmergencyById(id: String): EmergencyRequest?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmergency(emergency: EmergencyRequest)

    @Query("UPDATE emergency_requests SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: String, status: String)

    @Query("SELECT * FROM donation_records WHERE donorId = :donorId ORDER BY date DESC")
    fun getDonationHistory(donorId: String): Flow<List<DonationRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDonationRecord(record: DonationRecord)

    @Query("SELECT * FROM donation_records ORDER BY date DESC")
    fun getAllDonationRecords(): Flow<List<DonationRecord>>
}
