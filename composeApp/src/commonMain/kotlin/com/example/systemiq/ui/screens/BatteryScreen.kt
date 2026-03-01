package com.example.systemiq.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.systemiq.ui.components.AnimatedCircularProgress
import com.example.systemiq.ui.components.GlassCard
import com.example.systemiq.ui.components.MetricRow
import com.example.systemiq.ui.components.SectionHeader
import com.example.systemiq.ui.components.UsageBar
import com.example.systemiq.ui.theme.StatusAmber
import com.example.systemiq.ui.theme.StatusGreen
import com.example.systemiq.ui.theme.StatusRed
import com.example.systemiq.viewmodel.BatteryStateHolder
import kotlin.math.roundToInt

@Composable
fun BatteryScreen(stateHolder: BatteryStateHolder) {
    val state by stateHolder.uiState.collectAsState()
    var showOptimizeDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text("Battery", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        Text("Battery health & optimization", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp, bottom = 24.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AnimatedCircularProgress(
                    progress = state.battery.level.toFloat(),
                    size = 160.dp,
                    strokeWidth = 14.dp,
                    progressColor = when {
                        state.battery.level > 50 -> StatusGreen
                        state.battery.level > 20 -> StatusAmber
                        else -> StatusRed
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (state.battery.isCharging) Icons.Filled.BatteryChargingFull else Icons.Filled.BatteryFull,
                        contentDescription = "Battery",
                        tint = if (state.battery.isCharging) StatusGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (state.battery.isCharging) "Charging" else "On Battery",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (state.battery.isCharging) StatusGreen else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionHeader(title = "Battery Details")
        GlassCard {
            Column {
                MetricRow(label = "Level", value = "${state.battery.level}%")
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 4.dp))
                MetricRow(
                    label = "Temperature", value = "${((state.battery.temperature * 10).roundToInt() / 10f)}°C",
                    valueColor = when {
                        state.battery.temperature < 35 -> StatusGreen
                        state.battery.temperature < 42 -> StatusAmber
                        else -> StatusRed
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 4.dp))
                MetricRow(label = "Voltage", value = "${state.battery.voltage} mV", valueColor = MaterialTheme.colorScheme.onSurface)
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 4.dp))
                MetricRow(label = "Health", value = state.battery.health, valueColor = if (state.battery.health == "Good") StatusGreen else StatusAmber)
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 4.dp))
                MetricRow(label = "Technology", value = state.battery.technology, valueColor = MaterialTheme.colorScheme.onSurface)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader(title = "Top Battery Usage")
        GlassCard {
            Column {
                state.topApps.forEachIndexed { index, app ->
                    Column(modifier = Modifier.padding(vertical = 6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(app.appName, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                            Text("${app.usageMinutes} min", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        UsageBar(
                            progress = app.usagePercent,
                            color = when {
                                app.usagePercent > 25 -> StatusRed
                                app.usagePercent > 10 -> StatusAmber
                                else -> StatusGreen
                            }
                        )
                    }
                    if (index < state.topApps.lastIndex) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { showOptimizeDialog = true },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Optimize Battery", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    if (showOptimizeDialog) {
        AlertDialog(
            onDismissRequest = { showOptimizeDialog = false },
            title = { Text("Battery Optimization Tips", fontWeight = FontWeight.SemiBold) },
            text = {
                Column {
                    listOf(
                        "• Reduce screen brightness & timeout",
                        "• Disable unused connectivity (Bluetooth, NFC)",
                        "• Restrict background app refresh",
                        "• Enable adaptive battery",
                        "• Use dark mode to save OLED power",
                        "• Turn off location when not needed"
                    ).forEach { tip ->
                        Text(tip, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 3.dp))
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showOptimizeDialog = false }) { Text("Got it") } }
        )
    }
}
