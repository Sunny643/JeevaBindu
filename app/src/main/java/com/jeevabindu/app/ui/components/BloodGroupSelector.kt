package com.jeevabindu.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeevabindu.app.ui.theme.*

val bloodGroups = listOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

@Composable
fun BloodGroupSelector(
    selectedGroup: String,
    onGroupSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // First row: A+, A-, B+, B-
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            bloodGroups.take(4).forEach { group ->
                BloodGroupChip(
                    group = group,
                    isSelected = selectedGroup == group,
                    onClick = { onGroupSelected(group) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        // Second row: O+, O-, AB+, AB-
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            bloodGroups.drop(4).forEach { group ->
                BloodGroupChip(
                    group = group,
                    isSelected = selectedGroup == group,
                    onClick = { onGroupSelected(group) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun BloodGroupChip(
    group: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) PrimaryRed else Color.White,
            contentColor = if (isSelected) Color.White else DarkText
        ),
        border = BorderStroke(
            width = 1.5.dp,
            color = if (isSelected) PrimaryRed else BorderRed
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = group,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodGroupFilterChips(
    groups: List<String>,
    selectedGroup: String,
    onGroupSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        groups.forEach { group ->
            val isSelected = selectedGroup == group
            FilterChip(
                selected = isSelected,
                onClick = { onGroupSelected(group) },
                label = {
                    Text(
                        text = group,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 13.sp
                    )
                },
                shape = RoundedCornerShape(20.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = PrimaryRed,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White,
                    labelColor = DarkText
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = BorderRed,
                    selectedBorderColor = PrimaryRed
                )
            )
        }
    }
}
