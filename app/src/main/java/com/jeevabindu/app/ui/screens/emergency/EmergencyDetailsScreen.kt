package com.jeevabindu.app.ui.screens.emergency

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
import com.jeevabindu.app.data.model.EmergencyRequest
import com.jeevabindu.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyDetailsScreen(
    emergency: EmergencyRequest?,
    onImComing: () -> Unit,
    onCallHospital: () -> Unit,
    onBack: () -> Unit
) {
    val emg = emergency ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") } },
                title = { Text("Jeeva-Bindu", color = DarkRed, fontWeight = FontWeight.Bold) },
                actions = {
                    Box(Modifier.size(32.dp).clip(CircleShape).background(TealAccent), contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(18.dp)) }
                    Spacer(Modifier.width(8.dp))
                    IconButton(onClick = {}) { Icon(Icons.Outlined.Notifications, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).background(CreamBackground)) {
            // Map placeholder
            Box(Modifier.fillMaxWidth().height(220.dp).background(Color(0xFF3C3C3C)), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.LocalHospital, null, tint = PrimaryRed, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(8.dp))
                    Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Text("${emg.hospitalName}", modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }

            // Distance badge
            Box(Modifier.padding(start = 16.dp).offset(y = (-12).dp).clip(RoundedCornerShape(20.dp)).background(ReadyGreen).padding(horizontal = 12.dp, vertical = 6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Navigation, null, tint = Color.White, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("${emg.distanceKm} km away", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Column(Modifier.padding(horizontal = 16.dp)) {
                // Critical requirement header
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("CRITICAL REQUIREMENT", color = PrimaryRed, fontWeight = FontWeight.Bold, fontSize = 13.sp, letterSpacing = 1.sp)
                    Box(Modifier.clip(RoundedCornerShape(6.dp)).background(EmergencyRed).padding(horizontal = 10.dp, vertical = 4.dp)) { Text("URGENT", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp) }
                }
                Spacer(Modifier.height(8.dp))
                Text("${emg.bloodGroup} Needed", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))

                // Detail cards
                DetailInfoCard(icon = Icons.Default.LocalHospital, label = "Medical Facility", title = emg.hospitalName, subtitle = emg.hospitalAddress.ifEmpty { "Blood Bank - 2nd Floor" })
                Spacer(Modifier.height(12.dp))
                DetailInfoCard(icon = Icons.Default.ErrorOutline, label = "Volume Required", title = "${emg.unitsRequired} Units", subtitle = "Whole Blood or Platelets")
                Spacer(Modifier.height(12.dp))

                // Coordinator card
                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AccountCircle, null, tint = PrimaryRed, modifier = Modifier.size(28.dp))
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text("Contact Coordinator", color = SubtleText, fontSize = 12.sp)
                            Text(emg.coordinatorName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        if (emg.isVerified) Box(Modifier.clip(RoundedCornerShape(6.dp)).background(SuccessGreenBg).padding(horizontal = 8.dp, vertical = 4.dp)) { Text("VERIFIED", color = ReadyGreen, fontWeight = FontWeight.Bold, fontSize = 10.sp) }
                    }
                }

                if (emg.coordinatorNote.isNotEmpty()) {
                    Spacer(Modifier.height(16.dp))
                    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = LightBlueBg)) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                            Icon(Icons.Default.Info, null, tint = TealAccent, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text("Coordinator's Note", color = TealAccent, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(Modifier.height(4.dp))
                                Text("\"${emg.coordinatorNote}\"", style = MaterialTheme.typography.bodyMedium, color = MediumText)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // I'M COMING button
                Button(onClick = onImComing, Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = DarkGreen), shape = RoundedCornerShape(16.dp)) {
                    Icon(Icons.Default.CheckCircle, null, modifier = Modifier.size(22.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("I'M COMING", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Spacer(Modifier.height(12.dp))
                OutlinedButton(onClick = onCallHospital, Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(16.dp), border = androidx.compose.foundation.BorderStroke(1.5.dp, BorderRed)) {
                    Icon(Icons.Default.Phone, null, tint = PrimaryRed, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Call Hospital", color = DarkRed, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun DetailInfoCard(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, title: String, subtitle: String) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = PrimaryRed, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(12.dp))
            Column { Text(label, color = SubtleText, fontSize = 12.sp); Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp); Text(subtitle, color = SubtleText, fontSize = 13.sp) }
        }
    }
}
