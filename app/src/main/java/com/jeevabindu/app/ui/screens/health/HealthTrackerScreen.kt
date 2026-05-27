package com.jeevabindu.app.ui.screens.health

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeevabindu.app.data.model.Donor
import com.jeevabindu.app.data.model.DonationRecord
import com.jeevabindu.app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthTrackerScreen(currentUser: Donor?, donationHistory: List<DonationRecord>) {
    val user = currentUser ?: return
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val lastDate = user.lastDonationDate?.let { dateFormat.format(Date(it)) } ?: "None"
    val nextDate = user.lastDonationDate?.let { dateFormat.format(Date(it + 90L * 24 * 60 * 60 * 1000)) } ?: "Now"

    Column(Modifier.fillMaxSize().background(CreamBackground).verticalScroll(rememberScrollState())) {
        // Top bar
        Row(Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(36.dp).clip(CircleShape).background(PrimaryRed), contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(20.dp)) }
            Spacer(Modifier.width(12.dp))
            Text("Jeeva-Bindu", color = DarkRed, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {}) { Icon(Icons.Outlined.Notifications, null) }
        }
        Box(Modifier.fillMaxWidth().height(2.dp).background(PrimaryRed))

        Column(Modifier.padding(16.dp)) {
            // Header card
            Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(20.dp)) {
                    Text("CURRENT STATUS", style = MaterialTheme.typography.labelMedium, color = SubtleText, letterSpacing = 1.sp)
                    Spacer(Modifier.height(4.dp))
                    Text("Health Tracker", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Box(Modifier.clip(RoundedCornerShape(20.dp)).background(if (user.isEligible) SuccessGreenBg else LightPinkBg).padding(horizontal = 12.dp, vertical = 6.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(if (user.isEligible) Icons.Default.CheckCircle else Icons.Default.Cancel, null, tint = if (user.isEligible) ReadyGreen else PrimaryRed, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(if (user.isEligible) "Ready to Donate" else "Not Eligible", color = if (user.isEligible) ReadyGreen else PrimaryRed, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        }
                    }

                    Spacer(Modifier.height(20.dp))
                    // 90-day cycle
                    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = CreamBackground)) {
                        Column(Modifier.padding(16.dp)) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("90-Day\nEligibility Cycle", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text("${user.daysSinceLastDonation} Days Since Last\nDonation", color = PrimaryRed, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Spacer(Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = minOf(user.daysSinceLastDonation / 90f, 1f),
                                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                                color = PrimaryRed, trackColor = LightPinkBg
                            )
                            Spacer(Modifier.height(8.dp))
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Last: $lastDate", fontSize = 11.sp, color = SubtleText)
                                Text("Next Eligible: $nextDate", fontSize = 11.sp, color = SubtleText)
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))
                    Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(LightBlueBg).padding(12.dp)) {
                        Icon(Icons.Default.Info, null, tint = TealAccent, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("To ensure donor safety and maintain iron levels, a mandatory interval of 90 days is required between whole blood donations.", style = MaterialTheme.typography.bodySmall, color = MediumText)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            // Donation History
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Donation History", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Icon(Icons.Default.History, null, tint = SubtleText)
            }
            Spacer(Modifier.height(12.dp))
            donationHistory.forEach { record ->
                Card(Modifier.fillMaxWidth().padding(vertical = 4.dp), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(36.dp).clip(CircleShape).background(LightPinkBg), contentAlignment = Alignment.Center) { Icon(Icons.Default.WaterDrop, null, tint = PrimaryRed, modifier = Modifier.size(18.dp)) }
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) { Text(record.hospitalName, fontWeight = FontWeight.SemiBold, fontSize = 14.sp); Text("${dateFormat.format(Date(record.date))} • ${record.volumeMl}ml", color = SubtleText, fontSize = 12.sp) }
                        Icon(Icons.Default.ChevronRight, null, tint = SubtleText)
                    }
                }
            }
            TextButton(onClick = {}, Modifier.align(Alignment.CenterHorizontally)) { Text("View Full History", color = PrimaryRed) }

            Spacer(Modifier.height(16.dp))
            // Health Checklist
            Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = DarkCardBg)) {
                Column(Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Checklist, null, tint = Color.White); Spacer(Modifier.width(8.dp)); Text("Health Checklist", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp) }
                    Spacer(Modifier.height(16.dp))
                    listOf("Weight > 50kg", "Hemoglobin > 12.5 g/dL", "No medication in last 72h", "No major surgery in 6 months").forEach { item ->
                        Row(Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, null, tint = LightGreen, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(12.dp))
                            Text(item, color = Color.White, fontSize = 14.sp)
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    OutlinedButton(onClick = {}, Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), border = androidx.compose.foundation.BorderStroke(1.dp, Color.White)) {
                        Text("Check Eligibility Now", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            // Pre-Donation Prep
            Text("Pre-Donation Prep", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(Modifier.weight(1f), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.WaterDrop, null, tint = TealAccent, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.height(8.dp))
                        Text("Hydrate Well", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
                Card(Modifier.weight(1f), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Restaurant, null, tint = PrimaryRed, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.height(8.dp))
                        Text("Healthy Meal", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            // Why 90 Days
            Card(Modifier.fillMaxWidth().height(140.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = DarkRed)) {
                Box(Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.BottomStart) {
                    Column { Text("Why 90 Days Matter", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp); Text("Understanding the science of iron replenishment and red blood cell regeneration.", color = Color.White.copy(0.8f), fontSize = 12.sp); Spacer(Modifier.height(4.dp)); Text("Read Guidelines ↗", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
