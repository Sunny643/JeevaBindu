package com.jeevabindu.app.data.model

data class FeedPost(
    val id: String,
    val type: String,         // "ThankYou", "Request", "Impact"
    val userName: String = "",
    val hospitalName: String = "",
    val timeAgo: String = "",
    val message: String = "",
    val quote: String = "",
    val quotedBy: String = "",
    val bloodGroup: String = "",
    val distanceKm: Double = 0.0,
    val requiredBy: String = "",
    val status: String = "",  // "Success", "Awaiting", "Active"
    val likes: Int = 0,
    val imageRes: Int = 0
)
