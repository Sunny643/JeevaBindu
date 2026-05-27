package com.jeevabindu.app.data.local

import androidx.room.*
import com.jeevabindu.app.data.model.Donor
import kotlinx.coroutines.flow.Flow

@Dao
interface DonorDao {
    @Query("SELECT * FROM donors ORDER BY isCurrentUser DESC, name ASC")
    fun getAllDonors(): Flow<List<Donor>>

    @Query("SELECT * FROM donors WHERE isCurrentUser = 1 LIMIT 1")
    fun getCurrentUser(): Flow<Donor?>

    @Query("SELECT * FROM donors WHERE bloodGroup = :bloodGroup AND isAvailable = 1")
    fun getDonorsByBloodGroup(bloodGroup: String): Flow<List<Donor>>

    @Query("SELECT * FROM donors WHERE isAvailable = 1 AND isCurrentUser = 0")
    fun getAvailableDonors(): Flow<List<Donor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDonor(donor: Donor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(donors: List<Donor>)

    @Update
    suspend fun updateDonor(donor: Donor)

    @Query("UPDATE donors SET lastDonationDate = :date, livesSaved = livesSaved + 1 WHERE isCurrentUser = 1")
    suspend fun recordDonation(date: Long)

    @Query("UPDATE donors SET isAvailable = :available WHERE id = :donorId")
    suspend fun updateAvailability(donorId: String, available: Boolean)

    @Query("DELETE FROM donors")
    suspend fun deleteAll()
}
