package com.jeevabindu.app.ui.screens.directory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeevabindu.app.data.model.Donor
import com.jeevabindu.app.ui.components.DonorCard
import com.jeevabindu.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveDirectoryScreen(
    donors: List<Donor>,
    selectedBloodGroup: String,
    proximityKm: Float,
    onBloodGroupSelected: (String) -> Unit,
    onProximityChanged: (Float) -> Unit
) {
    val filterGroups = listOf("All", "O+", "A-", "B+", "AB+", "O-", "A+", "B-", "AB-")
    var expandedDonorId by remember { mutableStateOf(donors.firstOrNull()?.id) }

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
            Text("Live Directory", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("Find local heroes ready to help. Real-time availability from our community.", style = MaterialTheme.typography.bodyMedium, color = SubtleText)
            Spacer(Modifier.height(20.dp))

            // Filter
            Text("Filter Blood Group", style = MaterialTheme.typography.titleSmall, color = SubtleText, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filterGroups) { group ->
                    val isSelected = selectedBloodGroup == group
                    FilterChip(
                        selected = isSelected, onClick = { onBloodGroupSelected(group) },
                        label = { Text(group, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium, fontSize = 13.sp) },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = PrimaryRed, selectedLabelColor = Color.White, containerColor = Color.White, labelColor = DarkText),
                        border = FilterChipDefaults.filterChipBorder(enabled = true, selected = isSelected, borderColor = BorderRed, selectedBorderColor = PrimaryRed)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            // Proximity slider
            Text("Proximity (KM)", style = MaterialTheme.typography.titleSmall, color = SubtleText, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, null, tint = SubtleText, modifier = Modifier.size(20.dp))
                Slider(
                    value = proximityKm, onValueChange = onProximityChanged, valueRange = 1f..50f,
                    modifier = Modifier.weight(1f),
                    colors = SliderDefaults.colors(thumbColor = PrimaryRed, activeTrackColor = PrimaryRed, inactiveTrackColor = LightPinkBg)
                )
                Text("${proximityKm.toInt()}km", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }

            Spacer(Modifier.height(16.dp))

            // Donor list
            if (donors.isEmpty()) {
                Card(Modifier.fillMaxWidth().padding(vertical = 20.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.SearchOff, null, tint = SubtleText, modifier = Modifier.size(48.dp))
                        Spacer(Modifier.height(12.dp))
                        Text("No donors found", fontWeight = FontWeight.SemiBold, color = SubtleText)
                        Text("Try adjusting your filters", fontSize = 13.sp, color = LightText)
                    }
                }
            } else {
                donors.forEach { donor ->
                    DonorCard(
                        donor = donor,
                        isExpanded = donor.id == expandedDonorId,
                        onCallClick = {},
                        onMessageClick = { expandedDonorId = donor.id },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            // Map placeholder
            Card(Modifier.fillMaxWidth().height(200.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8E8E8))) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Map, null, tint = SubtleText, modifier = Modifier.size(40.dp))
                        Spacer(Modifier.height(8.dp))
                        Text("Map View", color = SubtleText, fontSize = 14.sp)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = DarkRed), shape = RoundedCornerShape(20.dp)) {
                            Icon(Icons.Default.Map, null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Expand Map View", fontSize = 13.sp)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
