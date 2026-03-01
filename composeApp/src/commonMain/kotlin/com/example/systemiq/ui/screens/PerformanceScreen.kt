package com.example.systemiq.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.systemiq.ui.components.AnimatedCircularProgress
import com.example.systemiq.ui.components.GlassCard
import com.example.systemiq.ui.components.MetricRow
import com.example.systemiq.ui.components.SectionHeader
import com.example.systemiq.ui.theme.Amber40
import com.example.systemiq.ui.theme.Blue40
import com.example.systemiq.ui.theme.Teal40
import com.example.systemiq.viewmodel.PerformanceStateHolder
import kotlin.math.roundToInt

@Composable
fun PerformanceScreen(stateHolder: PerformanceStateHolder) {
    val state by stateHolder.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text("Performance", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        Text("Real-time system metrics", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp, bottom = 24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedCircularProgress(progress = state.cpu.usagePercent, size = 100.dp, strokeWidth = 8.dp, label = "CPU", progressColor = Teal40)
            AnimatedCircularProgress(progress = state.memory.usagePercent, size = 100.dp, strokeWidth = 8.dp, label = "RAM", progressColor = Blue40)
            AnimatedCircularProgress(progress = state.storage.usagePercent, size = 100.dp, strokeWidth = 8.dp, label = "Storage", progressColor = Amber40)
        }

        Spacer(modifier = Modifier.height(28.dp))

        SectionHeader(title = "CPU Details")
        GlassCard {
            Column {
                MetricRow(label = "Usage", value = "${((state.cpu.usagePercent * 10).roundToInt() / 10f)}%")
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 4.dp))
                MetricRow(label = "Cores", value = "${state.cpu.coreCount}", valueColor = MaterialTheme.colorScheme.onSurface)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader(title = "Memory Details")
        GlassCard {
            Column {
                MetricRow(label = "Used", value = "${state.memory.usedMb} MB")
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 4.dp))
                MetricRow(label = "Total", value = "${state.memory.totalMb} MB", valueColor = MaterialTheme.colorScheme.onSurface)
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 4.dp))
                MetricRow(label = "Usage", value = "${((state.memory.usagePercent * 10).roundToInt() / 10f)}%")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader(title = "Storage Details")
        GlassCard {
            Column {
                MetricRow(label = "Used", value = "${((state.storage.usedGb * 10).roundToInt() / 10f)} GB")
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 4.dp))
                MetricRow(label = "Total", value = "${((state.storage.totalGb * 10).roundToInt() / 10f)} GB", valueColor = MaterialTheme.colorScheme.onSurface)
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 4.dp))
                MetricRow(label = "Usage", value = "${((state.storage.usagePercent * 10).roundToInt() / 10f)}%")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
