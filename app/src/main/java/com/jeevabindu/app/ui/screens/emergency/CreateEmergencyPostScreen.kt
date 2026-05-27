package com.jeevabindu.app.ui.screens.emergency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeevabindu.app.ui.components.BloodGroupSelector
import com.jeevabindu.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEmergencyPostScreen(
    onPost: (String, String, String, String, Int) -> Unit,
    onBack: () -> Unit
) {
    var selectedBloodGroup by remember { mutableStateOf("A+") }
    var hospitalName by remember { mutableStateOf("") }
    var urgencyLevel by remember { mutableStateOf("Critical") }
    var contactNumber by remember { mutableStateOf("") }
    var unitsRequired by remember { mutableStateOf("2") }
    var unitsExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") } },
                title = { Text("Jeeva-Bindu", color = DarkRed, fontWeight = FontWeight.Bold) },
                actions = { IconButton(onClick = {}) { Icon(Icons.Outlined.Notifications, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).background(CreamBackground).padding(horizontal = 16.dp)) {
            Spacer(Modifier.height(20.dp))
            Text("New Emergency Request", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Fill in the details quickly to notify all eligible donors in your community.", style = MaterialTheme.typography.bodyLarge, color = SubtleText)
            Spacer(Modifier.height(24.dp))

            // Blood Group
            Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(20.dp)) {
                    Text("Select Required Blood Group", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(Modifier.height(12.dp))
                    BloodGroupSelector(selectedGroup = selectedBloodGroup, onGroupSelected = { selectedBloodGroup = it })
                }
            }
            Spacer(Modifier.height(20.dp))

            // Hospital
            Text("Hospital Name / Location", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = hospitalName, onValueChange = { hospitalName = it }, Modifier.fillMaxWidth(),
                placeholder = { Text("e.g., District Community Hospital") },
                leadingIcon = { Icon(Icons.Default.LocationOn, null, tint = SubtleText) },
                shape = RoundedCornerShape(12.dp), singleLine = true
            )
            Spacer(Modifier.height(20.dp))

            // Urgency Level
            Text("Urgency Level", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(8.dp))
            // Critical option
            Card(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = if (urgencyLevel == "Critical") LightPinkBg else Color.White),
                border = androidx.compose.foundation.BorderStroke(if (urgencyLevel == "Critical") 1.5.dp else 1.dp, if (urgencyLevel == "Critical") PrimaryRed else DividerColor)
            ) {
                Row(Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = urgencyLevel == "Critical", onClick = { urgencyLevel = "Critical" }, colors = RadioButtonDefaults.colors(selectedColor = PrimaryRed))
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text("Critical (Immediate Need)", fontWeight = FontWeight.Bold, color = if (urgencyLevel == "Critical") PrimaryRed else DarkText)
                        Text("Life-threatening situation. Notifying all donors now.", fontSize = 12.sp, color = SubtleText)
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            // High option
            Card(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = if (urgencyLevel == "High") LightPinkBg else Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, if (urgencyLevel == "High") PrimaryRed else DividerColor)
            ) {
                Row(Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = urgencyLevel == "High", onClick = { urgencyLevel = "High" }, colors = RadioButtonDefaults.colors(selectedColor = PrimaryRed))
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text("High (Within 6 hours)", fontWeight = FontWeight.Bold, color = if (urgencyLevel == "High") PrimaryRed else DarkText)
                        Text("Scheduled surgery or stabilizing patient.", fontSize = 12.sp, color = SubtleText)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Text("Contact Number", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = contactNumber, onValueChange = { if (it.length <= 15) contactNumber = it }, Modifier.fillMaxWidth(),
                placeholder = { Text("+91 00000 00000") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), shape = RoundedCornerShape(12.dp), singleLine = true
            )

            Spacer(Modifier.height(20.dp))
            Text("Units Required", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(8.dp))
            ExposedDropdownMenuBox(expanded = unitsExpanded, onExpandedChange = { unitsExpanded = it }) {
                OutlinedTextField(
                    value = "$unitsRequired Units", onValueChange = {}, readOnly = true,
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = unitsExpanded) },
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(expanded = unitsExpanded, onDismissRequest = { unitsExpanded = false }) {
                    (1..6).forEach { n -> DropdownMenuItem(text = { Text("$n Units") }, onClick = { unitsRequired = n.toString(); unitsExpanded = false }) }
                }
            }

            Spacer(Modifier.height(28.dp))
            Button(
                onClick = { onPost(selectedBloodGroup, hospitalName, urgencyLevel, contactNumber, unitsRequired.toIntOrNull() ?: 2) },
                Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = DarkRed), shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Send, null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Post to Community", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(Modifier.height(8.dp))
            Text("By posting, you agree to share this location and contact info with verified local donors.", style = MaterialTheme.typography.bodySmall, color = SubtleText, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(16.dp))
            // Community readiness
            Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = LightGreenBg)) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.VerifiedUser, null, tint = ReadyGreen, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("Community Readiness", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DarkGreen)
                        Text("There are currently 14 ${selectedBloodGroup} donors active in your 5km radius ready to help.", fontSize = 13.sp, color = ReadyGreen, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            TextButton(onClick = onBack, Modifier.align(Alignment.CenterHorizontally)) { Text("Cancel and Discard", color = DarkRed, textDecoration = TextDecoration.Underline) }
            Spacer(Modifier.height(24.dp))
        }
    }
}
