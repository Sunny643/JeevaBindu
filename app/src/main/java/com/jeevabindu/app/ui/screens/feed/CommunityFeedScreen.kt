package com.jeevabindu.app.ui.screens.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeevabindu.app.data.SampleData
import com.jeevabindu.app.data.model.FeedPost
import com.jeevabindu.app.ui.theme.*

@Composable
fun CommunityFeedScreen(onCreateEmergency: () -> Unit) {
    val feedPosts = SampleData.getSampleFeedPosts()

    Column(Modifier.fillMaxSize().background(CreamBackground).verticalScroll(rememberScrollState())) {
        // Top bar
        Row(Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(36.dp).clip(CircleShape).background(DarkText), contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(20.dp)) }
            Spacer(Modifier.width(12.dp))
            Text("Jeeva-Bindu", color = DarkRed, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {}) { Icon(Icons.Outlined.Notifications, null) }
        }

        Column(Modifier.padding(16.dp)) {
            // Stats row
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(Modifier.weight(1f), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = PrimaryRed)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Lives Impacted", color = Color.White.copy(0.8f), fontSize = 12.sp)
                        Spacer(Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.Bottom) { Text("1,204", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 28.sp); Spacer(Modifier.width(4.dp)); Icon(Icons.Default.TrendingUp, null, tint = Color.White.copy(0.7f), modifier = Modifier.size(20.dp)) }
                    }
                }
                Card(Modifier.weight(1f), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = DarkCardBg)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Community\nReadiness", color = Color.White.copy(0.8f), fontSize = 12.sp)
                        Spacer(Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.Bottom) { Text("94%", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 28.sp); Spacer(Modifier.width(4.dp)); Icon(Icons.Default.CheckCircle, null, tint = LightGreen, modifier = Modifier.size(20.dp)) }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Community Feed", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                TextButton(onClick = {}) { Icon(Icons.Default.FilterList, null, tint = PrimaryRed, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(4.dp)); Text("Filter", color = PrimaryRed) }
            }

            Spacer(Modifier.height(12.dp))

            // Feed posts
            feedPosts.forEach { post ->
                when (post.type) {
                    "ThankYou" -> ThankYouCard(post)
                    "Request" -> RequestCard(post, onCreateEmergency)
                    "Impact" -> ImpactCard(post)
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ThankYouCard(post: FeedPost) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Column {
            // Header
            Row(Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(36.dp).clip(CircleShape).background(LightPinkBg), contentAlignment = Alignment.Center) { Icon(Icons.Default.VolunteerActivism, null, tint = PrimaryRed, modifier = Modifier.size(18.dp)) }
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) { Text(post.userName, fontWeight = FontWeight.Bold, fontSize = 14.sp); Text("${post.timeAgo} • ${post.hospitalName}", color = SubtleText, fontSize = 12.sp) }
                Box(Modifier.clip(RoundedCornerShape(6.dp)).background(SuccessGreenBg).padding(horizontal = 8.dp, vertical = 4.dp)) { Text("Success", color = ReadyGreen, fontWeight = FontWeight.Bold, fontSize = 11.sp) }
            }
            // Image placeholder
            Box(Modifier.fillMaxWidth().height(180.dp).padding(horizontal = 16.dp, vertical = 8.dp).clip(RoundedCornerShape(12.dp)).background(LightPinkBg), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Default.Favorite, null, tint = PrimaryRed, modifier = Modifier.size(48.dp)); Text("Thank You!", fontWeight = FontWeight.Bold, color = PrimaryRed) }
            }
            // Quote
            Column(Modifier.padding(horizontal = 16.dp)) {
                Row { Box(Modifier.width(3.dp).height(60.dp).background(PrimaryRed)); Text(post.quote, fontStyle = FontStyle.Italic, color = MediumText, fontSize = 13.sp, modifier = Modifier.padding(start = 12.dp)) }
                Text("— ${post.quotedBy}", color = SubtleText, fontSize = 12.sp, modifier = Modifier.padding(start = 15.dp, top = 4.dp))
            }
            // Actions
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.FavoriteBorder, null, tint = PrimaryRed, modifier = Modifier.size(20.dp)); Spacer(Modifier.width(4.dp)); Text("${post.likes}", color = SubtleText, fontSize = 13.sp)
                Spacer(Modifier.width(16.dp))
                Icon(Icons.Default.Share, null, tint = SubtleText, modifier = Modifier.size(18.dp)); Spacer(Modifier.width(4.dp)); Text("Share", color = SubtleText, fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun RequestCard(post: FeedPost, onAction: () -> Unit) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row { Box(Modifier.clip(RoundedCornerShape(4.dp)).background(EmergencyRed).padding(horizontal = 8.dp, vertical = 3.dp)) { Text("REQUEST", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp) }; Spacer(Modifier.width(8.dp)); Text("${post.distanceKm}km away", color = SubtleText, fontSize = 12.sp) }
                Column(horizontalAlignment = Alignment.End) { Text("Required by", color = SubtleText, fontSize = 10.sp); Text(post.requiredBy.ifEmpty { "ASAP" }, fontWeight = FontWeight.Bold, fontSize = 14.sp) }
            }
            Spacer(Modifier.height(8.dp))
            Text("${post.bloodGroup} Blood Type", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(post.message, style = MaterialTheme.typography.bodyMedium, color = MediumText)
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onAction, Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = ReadyGreen), shape = RoundedCornerShape(12.dp)) { Text(if (post.status == "Active") "I Can Donate" else "Check Eligibility", fontWeight = FontWeight.SemiBold, fontSize = 13.sp) }
                OutlinedButton(onClick = {}, shape = RoundedCornerShape(12.dp), border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)) { Icon(Icons.Default.Map, null, tint = PrimaryRed) }
            }
            if (post.status == "Awaiting") { Spacer(Modifier.height(4.dp)); Text("Status: Awaiting", color = WarningOrange, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, fontStyle = FontStyle.Italic) }
        }
    }
}

@Composable
private fun ImpactCard(post: FeedPost) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(48.dp).clip(CircleShape).background(LightPinkBg), contentAlignment = Alignment.Center) { Icon(Icons.Default.EmojiEvents, null, tint = PrimaryRed) }
            Spacer(Modifier.width(12.dp))
            Column { Text("Your Impact Last Month", fontWeight = FontWeight.Bold, fontSize = 14.sp); Text(post.message, color = SubtleText, fontSize = 13.sp) }
        }
    }
}
