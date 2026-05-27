package com.jeevabindu.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeevabindu.app.ui.components.BloodGroupSelector
import com.jeevabindu.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonorProfileSetupScreen(onProfileComplete: () -> Unit) {
    var selectedBloodGroup by remember { mutableStateOf("A+") }
    var age by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var ageError by remember { mutableStateOf("") }
    val locations = listOf("Hunasuru Town", "Periyapatna", "KR Nagar", "Saligrama", "T. Narasipura", "Nanjangud", "H.D. Kote")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.WaterDrop, null, tint = PrimaryRed, modifier = Modifier.size(24.dp)); Spacer(Modifier.width(8.dp)); Text("Jeeva-Bindu", color = DarkRed, fontWeight = FontWeight.Bold) } },
                actions = { Box(Modifier.size(36.dp).clip(RoundedCornerShape(18.dp)).background(PrimaryRed), contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(20.dp)) } ; Spacer(Modifier.width(12.dp)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).background(CreamBackground).padding(horizontal = 16.dp)) {
            // Red line
            Box(Modifier.fillMaxWidth().height(3.dp).background(PrimaryRed))
            Spacer(Modifier.height(24.dp))
            Text("Complete Your Profile", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Become a local hero. Your details help us match you with life-saving needs in your community.", style = MaterialTheme.typography.bodyLarge, color = SubtleText)
            Spacer(Modifier.height(24.dp))

            // Blood Group Card
            Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(20.dp)) {
                    Text("Select Blood Group", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(Modifier.height(16.dp))
                    BloodGroupSelector(selectedGroup = selectedBloodGroup, onGroupSelected = { selectedBloodGroup = it })
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Age (Years)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = age, onValueChange = { if (it.length <= 2 && it.all { c -> c.isDigit() }) { age = it; ageError = "" } },
                Modifier.fillMaxWidth(), placeholder = { Text("e.g. 25") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), shape = RoundedCornerShape(12.dp), singleLine = true, isError = ageError.isNotEmpty()
            )
            Text("Donors must be between 18-65 years old.", style = MaterialTheme.typography.bodySmall, color = SubtleText, modifier = Modifier.padding(top = 4.dp))
            if (ageError.isNotEmpty()) Text(ageError, color = EmergencyRed, fontSize = 12.sp)

            Spacer(Modifier.height(24.dp))
            Text("Panchayat / Town", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                OutlinedTextField(
                    value = selectedLocation.ifEmpty { "" }, onValueChange = {}, readOnly = true,
                    modifier = Modifier.fillMaxWidth().menuAnchor(), placeholder = { Text("Select your area") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    locations.forEach { loc -> DropdownMenuItem(text = { Text(loc) }, onClick = { selectedLocation = loc; expanded = false }) }
                }
            }

            Spacer(Modifier.height(24.dp))
            // Privacy card
            Row(Modifier.fillMaxWidth()) {
                Box(Modifier.width(4.dp).height(80.dp).clip(RoundedCornerShape(2.dp)).background(ReadyGreen))
                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp), colors = CardDefaults.cardColors(containerColor = SuccessGreenBg)) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.VerifiedUser, null, tint = ReadyGreen, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Privacy Guaranteed", color = ReadyGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Your exact location is only shared with verified emergency coordinators when a match is found.", style = MaterialTheme.typography.bodySmall, color = MediumText)
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
            Button(
                onClick = {
                    val ageInt = age.toIntOrNull() ?: 0
                    if (ageInt < 18 || ageInt > 65) { ageError = "Age must be between 18 and 65" }
                    else if (selectedLocation.isEmpty()) { /* show error */ }
                    else onProfileComplete()
                },
                Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = DarkRed), shape = RoundedCornerShape(16.dp)
            ) { Text("Register as Donor", fontWeight = FontWeight.Bold, fontSize = 16.sp); Spacer(Modifier.width(8.dp)); Icon(Icons.Default.ArrowForward, null) }

            Spacer(Modifier.height(24.dp))
        }
    }
}
