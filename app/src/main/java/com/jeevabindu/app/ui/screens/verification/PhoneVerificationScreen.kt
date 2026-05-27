package com.jeevabindu.app.ui.screens.verification

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeevabindu.app.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneVerificationScreen(onVerified: () -> Unit) {
    var phoneNumber by remember { mutableStateOf("") }
    var otpSent by remember { mutableStateOf(false) }
    var otpCode by remember { mutableStateOf("") }
    var generatedOtp by remember { mutableStateOf("") }
    var isVerifying by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.WaterDrop, null, tint = PrimaryRed, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Jeeva-Bindu", color = DarkRed, fontWeight = FontWeight.Bold)
                    }
                },
                actions = { IconButton(onClick = {}) { Icon(Icons.Default.HelpOutline, "Help") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).background(CreamBackground)
        ) {
            // Hero Banner
            Box(
                Modifier.fillMaxWidth().height(180.dp).padding(16.dp).clip(RoundedCornerShape(16.dp))
                    .background(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(PrimaryRed.copy(0.8f), DarkRed))),
                contentAlignment = Alignment.BottomStart
            ) {
                Text("Joining a network of life-savers.", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(20.dp))
            }

            Column(Modifier.padding(horizontal = 16.dp)) {
                Spacer(Modifier.height(16.dp))
                Text("Verify your phone", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("We'll send a 6-digit code to verify your identity and keep the community secure.", style = MaterialTheme.typography.bodyLarge, color = SubtleText)
            }

            Spacer(Modifier.height(24.dp))

            // Input Card
            Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(20.dp)) {
                    Text("Mobile Number", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = phoneNumber, onValueChange = { if (it.length <= 10 && it.all { c -> c.isDigit() }) phoneNumber = it },
                        Modifier.fillMaxWidth(), prefix = { Text("+91 ") }, placeholder = { Text("00000 00000") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), shape = RoundedCornerShape(12.dp), singleLine = true
                    )
                    if (otpSent) {
                        Spacer(Modifier.height(16.dp))
                        Text("Enter OTP", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = otpCode, onValueChange = { if (it.length <= 6 && it.all { c -> c.isDigit() }) otpCode = it },
                            Modifier.fillMaxWidth(), placeholder = { Text("Enter 6-digit OTP") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), shape = RoundedCornerShape(12.dp), singleLine = true
                        )
                    }
                    if (errorMessage.isNotEmpty()) { Spacer(Modifier.height(4.dp)); Text(errorMessage, color = EmergencyRed, fontSize = 12.sp) }
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (!otpSent) {
                                if (phoneNumber.length == 10) { generatedOtp = (100000..999999).random().toString(); otpSent = true; scope.launch { snackbarHostState.showSnackbar("Simulated OTP: $generatedOtp", duration = SnackbarDuration.Long) } }
                                else errorMessage = "Please enter a valid 10-digit number"
                            } else {
                                if (otpCode == generatedOtp) { isVerifying = true; scope.launch { delay(1000); onVerified() } }
                                else errorMessage = "Invalid OTP. Please try again."
                            }
                        },
                        Modifier.fillMaxWidth().height(52.dp), colors = ButtonDefaults.buttonColors(containerColor = DarkRed), shape = RoundedCornerShape(12.dp), enabled = !isVerifying
                    ) {
                        if (isVerifying) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                        else { Text(if (otpSent) "Verify OTP" else "Send OTP", fontWeight = FontWeight.Bold, fontSize = 16.sp); Spacer(Modifier.width(8.dp)); Icon(Icons.Default.ArrowForward, null) }
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(SuccessGreenBg).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.VerifiedUser, null, tint = ReadyGreen, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Your data is encrypted. We only use your number for urgent blood donation alerts.", style = MaterialTheme.typography.bodySmall, color = MediumText)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Simulation Note
            Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Box(Modifier.width(4.dp).height(120.dp).background(PrimaryRed))
                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp), colors = CardDefaults.cardColors(containerColor = LightPinkBg.copy(0.5f))) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Info, null, tint = PrimaryRed, modifier = Modifier.size(18.dp)); Spacer(Modifier.width(6.dp)); Text("Simulation Note", color = PrimaryRed, fontWeight = FontWeight.Bold, fontSize = 14.sp) }
                        Spacer(Modifier.height(8.dp))
                        Text("Once you click \"Send OTP\", you will receive a simulated OTP below. In production, this would be an SMS.", style = MaterialTheme.typography.bodyMedium, color = MediumText)
                        if (otpSent && generatedOtp.isNotEmpty()) {
                            Spacer(Modifier.height(12.dp))
                            Box(
                                Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(PrimaryRed.copy(0.1f)).padding(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("YOUR SIMULATED OTP IS:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SubtleText)
                                    Text(generatedOtp, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryRed, letterSpacing = 4.sp)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            TextButton(onClick = {}, Modifier.align(Alignment.CenterHorizontally)) { Text("Use Email Instead", color = DarkRed, fontWeight = FontWeight.SemiBold, textDecoration = TextDecoration.Underline) }
            Text("By continuing, you agree to Jeeva-Bindu's\nTerms of Service and Privacy Policy.", style = MaterialTheme.typography.bodySmall, color = SubtleText, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(16.dp))
            Spacer(Modifier.height(16.dp))
        }
    }
}
