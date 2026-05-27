package com.jeevabindu.app.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeevabindu.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPrivacyScreen(
    emergencyNotifications: Boolean,
    locationSharing: Boolean,
    onToggleEmergency: () -> Unit,
    onToggleLocation: () -> Unit,
    onLogout: () -> Unit
) {
    Column(Modifier.fillMaxSize().background(CreamBackground)) {
        // Top bar
        Row(Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(36.dp).clip(CircleShape).background(TealAccent), contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(20.dp)) }
            Spacer(Modifier.width(12.dp))
            Text("Jeeva-Bindu", color = DarkRed, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {}) { Icon(Icons.Outlined.Notifications, null, tint = PrimaryRed) }
        }
        Box(Modifier.fillMaxWidth().height(2.dp).background(PrimaryRed))

        Column(Modifier.padding(16.dp)) {
            Spacer(Modifier.height(8.dp))
            Text("Settings & Privacy", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Manage your donor preferences and account security.", style = MaterialTheme.typography.bodyLarge, color = SubtleText)
            Spacer(Modifier.height(24.dp))

            // Alert Preferences
            Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(20.dp)) {
                    Text("ALERT PREFERENCES", color = PrimaryRed, fontWeight = FontWeight.Bold, fontSize = 13.sp, letterSpacing = 1.5.sp)
                    Spacer(Modifier.height(16.dp))
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Emergency, null, tint = PrimaryRed, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) { Text("Emergency Notifications", fontWeight = FontWeight.SemiBold); Text("Immediate alerts for urgent blood needs", color = SubtleText, fontSize = 12.sp) }
                        Switch(checked = emergencyNotifications, onCheckedChange = { onToggleEmergency() }, colors = SwitchDefaults.colors(checkedTrackColor = PrimaryRed, checkedThumbColor = Color.White))
                    }
                    HorizontalDivider(Modifier.padding(vertical = 12.dp), color = DividerColor)
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, null, tint = SubtleText, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) { Text("Location Sharing", fontWeight = FontWeight.SemiBold); Text("Allow search at the town level", color = SubtleText, fontSize = 12.sp) }
                        Switch(checked = locationSharing, onCheckedChange = { onToggleLocation() }, colors = SwitchDefaults.colors(checkedTrackColor = PrimaryRed, checkedThumbColor = Color.White))
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            // Legal & Security
            Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(20.dp)) {
                    Text("LEGAL & SECURITY", color = PrimaryRed, fontWeight = FontWeight.Bold, fontSize = 13.sp, letterSpacing = 1.5.sp)
                    Spacer(Modifier.height(16.dp))
                    listOf(
                        Triple(Icons.Default.Lock, "Privacy Policy", ""),
                        Triple(Icons.Default.Description, "Terms of Service", ""),
                        Triple(Icons.Default.VerifiedUser, "Data Usage Rights", "")
                    ).forEachIndexed { index, (icon, title, _) ->
                        Row(Modifier.fillMaxWidth().clickable {}.padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(icon, null, tint = SubtleText, modifier = Modifier.size(22.dp))
                            Spacer(Modifier.width(12.dp))
                            Text(title, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                            Icon(Icons.Default.ChevronRight, null, tint = SubtleText)
                        }
                        if (index < 2) HorizontalDivider(color = DividerColor)
                    }
                }
            }

            Spacer(Modifier.height(28.dp))
            Button(onClick = onLogout, Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed), shape = RoundedCornerShape(16.dp)) {
                Icon(Icons.Default.Logout, null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Logout", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(20.dp))
            Text("Version 2.4.0-Stable", color = SubtleText, fontSize = 13.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Text("Jeeva-Bindu Community Network", color = LightText, fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    }
}
