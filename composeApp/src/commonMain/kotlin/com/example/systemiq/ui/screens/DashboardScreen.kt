package com.example.systemiq.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Battery4Bar
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.systemiq.data.BatteryUiState
import com.example.systemiq.data.PerformanceUiState
import com.example.systemiq.data.PrivacyUiState
import com.example.systemiq.ui.components.AnimatedCircularProgress
import com.example.systemiq.ui.theme.Amber40
import com.example.systemiq.ui.theme.Blue40
import com.example.systemiq.ui.theme.GlassBorder
import com.example.systemiq.ui.theme.GlassWhite
import com.example.systemiq.ui.theme.StatusAmber
import com.example.systemiq.ui.theme.StatusGreen
import com.example.systemiq.ui.theme.StatusRed
import com.example.systemiq.ui.theme.Teal40
import com.example.systemiq.viewmodel.BatteryStateHolder
import com.example.systemiq.viewmodel.PerformanceStateHolder
import com.example.systemiq.viewmodel.PrivacyStateHolder

@Composable
fun DashboardScreen(
    perfHolder: PerformanceStateHolder,
    battHolder: BatteryStateHolder,
    privHolder: PrivacyStateHolder,
    onNavigateToPerformance: () -> Unit,
    onNavigateToBattery: () -> Unit,
    onNavigateToPrivacy: () -> Unit
) {
    val perfState by perfHolder.uiState.collectAsState()
    val battState by battHolder.uiState.collectAsState()
    val privState by privHolder.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "System Intelligence",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Your device at a glance",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        OverallHealthRing(perfState, battState, privState)

        Spacer(modifier = Modifier.height(24.dp))

        DashboardCard(
            icon = Icons.Filled.Memory,
            title = "Performance",
            subtitle = "CPU ${perfState.cpu.usagePercent.toInt()}% • RAM ${perfState.memory.usagePercent.toInt()}%",
            progress = perfState.cpu.usagePercent,
            accentColor = Teal40,
            onClick = onNavigateToPerformance
        )
        Spacer(modifier = Modifier.height(16.dp))

        DashboardCard(
            icon = Icons.Filled.Battery4Bar,
            title = "Battery",
            subtitle = "${battState.battery.level}% • ${if (battState.battery.isCharging) "Charging" else "Discharging"}",
            progress = battState.battery.level.toFloat(),
            accentColor = Blue40,
            progressColor = when {
                battState.battery.level > 50 -> StatusGreen
                battState.battery.level > 20 -> StatusAmber
                else -> StatusRed
            },
            onClick = onNavigateToBattery
        )
        Spacer(modifier = Modifier.height(16.dp))

        DashboardCard(
            icon = Icons.Filled.Shield,
            title = "Privacy",
            subtitle = "Score ${privState.overallScore}/100 • ${privState.highRiskCount} high risk",
            progress = privState.overallScore.toFloat(),
            accentColor = Amber40,
            progressColor = when {
                privState.overallScore >= 70 -> StatusGreen
                privState.overallScore >= 40 -> StatusAmber
                else -> StatusRed
            },
            onClick = onNavigateToPrivacy
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun OverallHealthRing(
    perfState: PerformanceUiState,
    battState: BatteryUiState,
    privState: PrivacyUiState
) {
    val overallHealth = (
        (100f - perfState.cpu.usagePercent) * 0.3f +
            battState.battery.level.toFloat() * 0.35f +
            privState.overallScore.toFloat() * 0.35f
        ).coerceIn(0f, 100f)

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        AnimatedCircularProgress(
            progress = overallHealth,
            size = 160.dp,
            strokeWidth = 14.dp,
            label = "Overall Health",
            progressColor = when {
                overallHealth >= 70 -> StatusGreen
                overallHealth >= 40 -> StatusAmber
                else -> StatusRed
            }
        )
    }
}

@Composable
private fun DashboardCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    progress: Float,
    accentColor: Color,
    progressColor: Color? = null,
    onClick: () -> Unit
) {
    var animTarget by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(progress) { animTarget = progress }
    val animProgress by animateFloatAsState(
        targetValue = animTarget,
        animationSpec = tween(800),
        label = "card"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(GlassWhite, accentColor.copy(alpha = 0.08f))
                )
            )
            .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(accentColor.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = accentColor, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 2.dp))
            }
            AnimatedCircularProgress(progress = animProgress, size = 52.dp, strokeWidth = 5.dp, progressColor = progressColor)
        }
    }
}
