package com.jeevabindu.app.ui.screens.route

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
import com.jeevabindu.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnRouteStatusScreen(
    progress: Float,
    onArrived: () -> Unit,
    onCancel: () -> Unit,
    onCallHospital: () -> Unit
) {
    val progressPercent = (progress * 100).toInt()

    Column(Modifier.fillMaxSize().background(CreamBackground).verticalScroll(rememberScrollState())) {
        // Top bar
        Row(Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.WaterDrop, null, tint = PrimaryRed, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(8.dp))
            Text("Jeeva-Bindu", color = DarkRed, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {}) { Icon(Icons.Outlined.Notifications, null) }
            Box(Modifier.size(36.dp).clip(CircleShape).background(TealAccent), contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(20.dp)) }
        }

        // Map placeholder
        Card(Modifier.fillMaxWidth().height(220.dp).padding(16.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.DirectionsCar, null, tint = PrimaryRed, modifier = Modifier.size(40.dp))
                    Text("Live Route", color = SubtleText)
                }
                // ETA card
                Card(Modifier.align(Alignment.BottomStart).padding(12.dp), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Row(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(PrimaryRed), contentAlignment = Alignment.Center) { Icon(Icons.Default.DirectionsCar, null, tint = Color.White, modifier = Modifier.size(18.dp)) }
                        Spacer(Modifier.width(8.dp))
                        Column { Text("Estimated Arrival", fontSize = 10.sp, color = SubtleText); Text("5 mins away", color = PrimaryRed, fontWeight = FontWeight.Bold, fontSize = 14.sp) }
                        Spacer(Modifier.width(16.dp))
                        Column { Text("Distance", fontSize = 10.sp, color = SubtleText); Text("1.2 km", fontWeight = FontWeight.Bold, fontSize = 14.sp) }
                    }
                }
            }
        }

        Column(Modifier.padding(horizontal = 16.dp)) {
            // Route info with red left border
            Row(Modifier.fillMaxWidth()) {
                Box(Modifier.width(4.dp).height(120.dp).clip(RoundedCornerShape(2.dp)).background(PrimaryRed))
                Column(Modifier.padding(start = 16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Heading to St.\nMary's", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Box(Modifier.clip(RoundedCornerShape(8.dp)).background(TealAccent).padding(horizontal = 12.dp, vertical = 6.dp)) { Text("EN\nROUTE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp, lineHeight = 14.sp) }
                    }
                    Spacer(Modifier.height(4.dp))
                    Text("Recipient: Urgent A+ Case", color = SubtleText, fontSize = 14.sp)
                    Spacer(Modifier.height(12.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Progress", color = ReadyGreen, fontWeight = FontWeight.SemiBold, fontSize = 13.sp); Text("${progressPercent}% Completed", color = SubtleText, fontSize = 13.sp) }
                    Spacer(Modifier.height(4.dp))
                    LinearProgressIndicator(progress = { progress }, Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)), color = ReadyGreen, trackColor = DividerColor)
                }
            }

            Spacer(Modifier.height(20.dp))
            // Action cards
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(Modifier.weight(1f), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Phone, null, tint = TealAccent, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.height(8.dp))
                        Text("Call Hospital", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
                Card(Modifier.weight(1f), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.ShareLocation, null, tint = TealAccent, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.height(8.dp))
                        Text("Share Live Status", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            // Motivational card
            Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = LightBlueBg)) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.VerifiedUser, null, tint = TealAccent, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Text("\"Your quick response is helping save a life today. The medical team has been notified of your arrival time.\"", fontStyle = FontStyle.Italic, color = MediumText, fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(28.dp))
            Button(onClick = onArrived, Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = DarkGreen), shape = RoundedCornerShape(16.dp)) {
                Icon(Icons.Default.CheckCircle, null, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(8.dp))
                Text("I Have Arrived", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(Modifier.height(12.dp))
            OutlinedButton(onClick = onCancel, Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(16.dp), border = androidx.compose.foundation.BorderStroke(1.5.dp, SubtleText)) {
                Icon(Icons.Default.Cancel, null, tint = SubtleText, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Cancel Donation", color = MediumText, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}
