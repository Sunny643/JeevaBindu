package com.jeevabindu.app.data

import com.jeevabindu.app.data.model.Donor
import com.jeevabindu.app.data.model.DonationRecord
import com.jeevabindu.app.data.model.EmergencyRequest
import com.jeevabindu.app.data.model.FeedPost
import java.util.UUID

/**
 * Provides sample/seed data for the app to demonstrate functionality.
 */
object SampleData {

    private val currentTimeMillis = System.currentTimeMillis()
    private val oneDayMillis = 24 * 60 * 60 * 1000L

    fun getCurrentUser() = Donor(
        id = "user_current",
        name = "Ramesh Kumar",
        phone = "9876543210",
        bloodGroup = "O+",
        age = 28,
        location = "Hunasuru Town",
        isAvailable = true,
        lastDonationDate = currentTimeMillis - (76 * oneDayMillis), // 76 days ago
        livesSaved = 3,
        isCurrentUser = true,
        distanceKm = 0.0
    )

    fun getSampleDonors() = listOf(
        Donor(
            id = "donor_1",
            name = "Ravi Kumar",
            phone = "9876543211",
            bloodGroup = "O+",
            age = 32,
            location = "Hunasuru Town",
            isAvailable = true,
            lastDonationDate = currentTimeMillis - (100 * oneDayMillis),
            livesSaved = 5,
            distanceKm = 1.5
        ),
        Donor(
            id = "donor_2",
            name = "Priya Sharma",
            phone = "9876543212",
            bloodGroup = "A+",
            age = 25,
            location = "Periyapatna",
            isAvailable = true,
            lastDonationDate = currentTimeMillis - (120 * oneDayMillis),
            livesSaved = 2,
            distanceKm = 2.4
        ),
        Donor(
            id = "donor_3",
            name = "Anwar Pasha",
            phone = "9876543213",
            bloodGroup = "B+",
            age = 35,
            location = "KR Nagar",
            isAvailable = true,
            lastDonationDate = currentTimeMillis - (95 * oneDayMillis),
            livesSaved = 4,
            distanceKm = 4.1
        ),
        Donor(
            id = "donor_4",
            name = "Lakshmi M.",
            phone = "9876543214",
            bloodGroup = "O-",
            age = 29,
            location = "Saligrama",
            isAvailable = true,
            lastDonationDate = null,
            livesSaved = 1,
            distanceKm = 8.5
        ),
        Donor(
            id = "donor_5",
            name = "Somanna K.",
            phone = "9876543215",
            bloodGroup = "AB+",
            age = 40,
            location = "Hunasuru",
            isAvailable = true,
            lastDonationDate = currentTimeMillis - (200 * oneDayMillis),
            livesSaved = 7,
            distanceKm = 1.2
        ),
        Donor(
            id = "donor_6",
            name = "Meera Reddy",
            phone = "9876543216",
            bloodGroup = "A-",
            age = 27,
            location = "T. Narasipura",
            isAvailable = true,
            lastDonationDate = currentTimeMillis - (150 * oneDayMillis),
            livesSaved = 3,
            distanceKm = 5.8
        ),
        Donor(
            id = "donor_7",
            name = "Suresh B.",
            phone = "9876543217",
            bloodGroup = "B-",
            age = 45,
            location = "Nanjangud",
            isAvailable = false,
            lastDonationDate = currentTimeMillis - (30 * oneDayMillis),
            livesSaved = 6,
            distanceKm = 12.0
        )
    )

    fun getSampleDonationRecords() = listOf(
        DonationRecord(
            id = "rec_1",
            donorId = "user_current",
            hospitalName = "City General Hospital",
            date = currentTimeMillis - (76 * oneDayMillis),
            volumeMl = 450,
            type = "Whole Blood Donation"
        ),
        DonationRecord(
            id = "rec_2",
            donorId = "user_current",
            hospitalName = "Community Health Center",
            date = currentTimeMillis - (200 * oneDayMillis),
            volumeMl = 450,
            type = "Whole Blood Donation"
        ),
        DonationRecord(
            id = "rec_3",
            donorId = "user_current",
            hospitalName = "Mobile Donation Bus",
            date = currentTimeMillis - (310 * oneDayMillis),
            volumeMl = 450,
            type = "Emergency Response"
        )
    )

    fun getSampleEmergency() = EmergencyRequest(
        id = "emg_1",
        bloodGroup = "O-",
        hospitalName = "Civil Hospital, Taluka",
        hospitalAddress = "Main Road, Hunasuru Town",
        urgencyLevel = "Critical",
        contactNumber = "+91 98765 43210",
        unitsRequired = 3,
        coordinatorName = "Sister Mary Magdalene",
        coordinatorNote = "Patient in surgery. We need at least 2 donors within the next hour to maintain stable supply. Thank you for your generosity.",
        distanceKm = 2.0,
        travelTimeMinutes = 8,
        timestamp = currentTimeMillis,
        status = "Active",
        isVerified = true
    )

    fun getSampleFeedPosts() = listOf(
        FeedPost(
            id = "feed_1",
            type = "ThankYou",
            userName = "Thank You, Ramesh!",
            hospitalName = "City Hospital",
            timeAgo = "2 hours ago",
            quote = "\"Because of your timely O+ donation, our father's surgery went smoothly today. You are a real hero in our community. Thank you!\"",
            quotedBy = "The Sharma Family",
            status = "Success",
            likes = 124
        ),
        FeedPost(
            id = "feed_2",
            type = "Request",
            bloodGroup = "A-",
            hospitalName = "St. Jude's Care Center",
            distanceKm = 5.0,
            requiredBy = "Tomorrow",
            message = "Scheduled procedure at St. Jude's Care Center. Patient is stable but requires 2 units of A-blood as a safety buffer for tomorrow morning's surgery.",
            status = "Active"
        ),
        FeedPost(
            id = "feed_3",
            type = "Impact",
            message = "The Jeeva-Bindu community fulfilled 100% of non-critical requests in your area.",
            timeAgo = "Last Month"
        ),
        FeedPost(
            id = "feed_4",
            type = "Request",
            bloodGroup = "B+",
            hospitalName = "Regional Blood Bank",
            distanceKm = 12.0,
            message = "General donation request for the regional blood bank. Stock is currently at \"Moderate\" levels for B+ type. Every drop counts towards our community's safety net.",
            status = "Awaiting"
        )
    )
}
