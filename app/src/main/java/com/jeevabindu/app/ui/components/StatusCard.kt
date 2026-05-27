package com.jeevabindu.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.jeevabindu.app.ui.theme.*

@Composable
fun DonorCard(
    donor: Donor,
    isExpanded: Boolean = false,
    onCallClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 4.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Blood group badge
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isExpanded) PrimaryRed else LightPinkBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = donor.bloodGroup,
                        color = if (isExpanded) Color.White else PrimaryRed,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = donor.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        if (donor.isAvailable) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(ActiveGreenDot)
                            )
                            if (isExpanded) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "ACTIVE NOW",
                                    color = ReadyGreen,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = SubtleText
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${donor.location} • ${donor.distanceKm}km",
                            style = MaterialTheme.typography.bodySmall,
                            color = SubtleText
                        )
                    }
                }

                if (!isExpanded) {
                    IconButton(onClick = onMessageClick) {
                        Icon(
                            Icons.Default.Chat,
                            contentDescription = "Message",
                            tint = PrimaryRed
                        )
                    }
                }
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onCallClick,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkRed),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Call Now", fontWeight = FontWeight.SemiBold)
                    }
                    OutlinedButton(
                        onClick = {},
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderRed)
                    ) {
                        Icon(
                            Icons.Default.NearMe,
                            contentDescription = "Directions",
                            tint = PrimaryRed
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatusCard(
    isEligible: Boolean,
    daysLeft: Int,
    daysSinceLast: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "CURRENT STATUS",
                        style = MaterialTheme.typography.labelMedium,
                        color = SubtleText,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isEligible) "Ready to Donate" else "Not Eligible Yet",
                        style = MaterialTheme.typography.headlineMedium,
                        color = if (isEligible) ReadyGreen else PrimaryRed,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    imageVector = if (isEligible) Icons.Default.CheckCircle else Icons.Default.Cancel,
                    contentDescription = null,
                    tint = if (isEligible) ReadyGreen else PrimaryRed,
                    modifier = Modifier.size(36.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isEligible)
                    "Your health vitals are optimal. You are cleared for whole blood and platelet donation."
                else
                    "You need to wait ${daysLeft} more days before your next donation.",
                style = MaterialTheme.typography.bodyMedium,
                color = MediumText
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Eligibility Window
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Eligibility Window",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = if (isEligible) "Eligible!" else "$daysLeft Days Left",
                    style = MaterialTheme.typography.titleSmall,
                    color = if (isEligible) ReadyGreen else PrimaryRed,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Progress bar
            LinearProgressIndicator(
                progress = minOf(daysSinceLast / 90f, 1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = if (isEligible) ReadyGreen else PrimaryRed,
                trackColor = LightPinkBg,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${minOf(daysSinceLast, 90)}/90 days since last donation",
                style = MaterialTheme.typography.bodySmall,
                color = SubtleText
            )
        }
    }
}
