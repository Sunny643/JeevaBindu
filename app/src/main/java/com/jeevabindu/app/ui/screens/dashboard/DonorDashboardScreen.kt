package com.jeevabindu.app.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeevabindu.app.data.model.Donor
import com.jeevabindu.app.data.model.DonationRecord
import com.jeevabindu.app.ui.components.StatusCard
import com.jeevabindu.app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonorDashboardScreen(
    currentUser: Donor?,
    donationHistory: List<DonationRecord>,
    onNavigateDirectory: () -> Unit,
    onNavigateHealth: () -> Unit,
    onTriggerAlert: () -> Unit
) {
    val user = currentUser ?: return
    val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())

    Column(Modifier.fillMaxSize().background(CreamBackground).verticalScroll(rememberScrollState())) {
        // Top Bar
        Row(Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(40.dp).clip(CircleShape).background(PrimaryRed), contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = Color.White) }
            Spacer(Modifier.width(12.dp))
            Text("Jeeva-Bindu", color = DarkRed, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = onTriggerAlert) { Icon(Icons.Outlined.Notifications, "Notifications", tint = DarkText) }
        }
        Box(Modifier.fillMaxWidth().height(2.dp).background(PrimaryRed))

        Column(Modifier.padding(16.dp)) {
            // Status Card
            StatusCard(isEligible = user.isEligible, daysLeft = user.daysUntilEligible, daysSinceLast = user.daysSinceLastDonation)
            Spacer(Modifier.height(16.dp))

            // Stats Row
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(Modifier.weight(1f).height(120.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(Modifier.padding(16.dp)) {
                        Icon(Icons.Default.Favorite, null, tint = PrimaryRed, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.weight(1f))
                        Text("${user.livesSaved}", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Text("Lives Saved", style = MaterialTheme.typography.bodySmall, color = SubtleText)
                    }
                }
                Card(Modifier.weight(1f).height(120.dp).clickable { onNavigateHealth() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = TealAccent)) {
                    Column(Modifier.padding(16.dp)) {
                        Icon(Icons.Default.MonitorHeart, null, tint = Color.White, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.weight(1f))
                        Text("Health Log", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Stable Vitals", color = Color.White.copy(0.8f), fontSize = 12.sp)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Live Directory Banner
            Card(Modifier.fillMaxWidth().clickable { onNavigateDirectory() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = DarkRed)) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(Color.White.copy(0.2f)), contentAlignment = Alignment.Center) { Icon(Icons.Default.PeopleAlt, null, tint = Color.White) }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) { Text("Live Directory", color = Color.White, fontWeight = FontWeight.Bold); Text("Find nearby urgent requests", color = Color.White.copy(0.8f), fontSize = 12.sp) }
                    Icon(Icons.Default.ArrowForward, null, tint = Color.White)
                }
            }

            Spacer(Modifier.height(24.dp))

            // History
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Your History", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold); TextButton(onClick = {}) { Text("View All", color = PrimaryRed) } }
            Spacer(Modifier.height(8.dp))
            donationHistory.take(3).forEach { record ->
                Card(Modifier.fillMaxWidth().padding(vertical = 4.dp), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(LightPinkBg), contentAlignment = Alignment.Center) {
                            Icon(if (record.type.contains("Emergency")) Icons.Default.Emergency else Icons.Default.WaterDrop, null, tint = PrimaryRed)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) { Text(record.type, fontWeight = FontWeight.SemiBold, fontSize = 14.sp); Text(record.hospitalName, color = SubtleText, fontSize = 12.sp) }
                        Text(dateFormat.format(Date(record.date)), color = SubtleText, fontSize = 12.sp)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Impact Banner
            Card(Modifier.fillMaxWidth().height(160.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = DarkRed)) {
                Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, DarkRed))), contentAlignment = Alignment.BottomStart) {
                    Column(Modifier.padding(20.dp)) {
                        Text("Impact of One Bag", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("One donation can save up to three lives in your local village.", color = Color.White.copy(0.8f), fontSize = 13.sp)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
