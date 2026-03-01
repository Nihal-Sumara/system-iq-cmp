package com.example.systemiq

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Battery4Bar
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.Battery4Bar
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.example.systemiq.data.AppScreen
import com.example.systemiq.platform.BatteryDataSource
import com.example.systemiq.platform.PerformanceDataSource
import com.example.systemiq.platform.PrivacyDataSource
import com.example.systemiq.ui.screens.BatteryScreen
import com.example.systemiq.ui.screens.DashboardScreen
import com.example.systemiq.ui.screens.PerformanceScreen
import com.example.systemiq.ui.screens.PrivacyScreen
import com.example.systemiq.ui.theme.SystemIQTheme
import com.example.systemiq.viewmodel.BatteryStateHolder
import com.example.systemiq.viewmodel.PerformanceStateHolder
import com.example.systemiq.viewmodel.PrivacyStateHolder

private data class NavItem(
    val screen: AppScreen,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

private val navItems = listOf(
    NavItem(AppScreen.Dashboard, Icons.Filled.Dashboard, Icons.Outlined.Dashboard),
    NavItem(AppScreen.Performance, Icons.Filled.Memory, Icons.Outlined.Memory),
    NavItem(AppScreen.Battery, Icons.Filled.Battery4Bar, Icons.Outlined.Battery4Bar),
    NavItem(AppScreen.Privacy, Icons.Filled.Shield, Icons.Outlined.Shield)
)

@Composable
fun App(
    performanceDataSource: PerformanceDataSource,
    batteryDataSource: BatteryDataSource,
    privacyDataSource: PrivacyDataSource
) {
    val scope = rememberCoroutineScope()
    val perfHolder = remember { PerformanceStateHolder(performanceDataSource, scope) }
    val battHolder = remember { BatteryStateHolder(batteryDataSource, scope) }
    val privHolder = remember { PrivacyStateHolder(privacyDataSource, scope) }

    var currentScreen by remember { mutableStateOf(AppScreen.Dashboard) }

    SystemIQTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    navItems.forEach { item ->
                        val selected = currentScreen == item.screen
                        NavigationBarItem(
                            selected = selected,
                            onClick = { currentScreen = item.screen },
                            icon = {
                                Icon(
                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.screen.title
                                )
                            },
                            label = {
                                Text(
                                    text = item.screen.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (currentScreen) {
                    AppScreen.Dashboard -> DashboardScreen(
                        perfHolder = perfHolder,
                        battHolder = battHolder,
                        privHolder = privHolder,
                        onNavigateToPerformance = { currentScreen = AppScreen.Performance },
                        onNavigateToBattery = { currentScreen = AppScreen.Battery },
                        onNavigateToPrivacy = { currentScreen = AppScreen.Privacy }
                    )
                    AppScreen.Performance -> PerformanceScreen(stateHolder = perfHolder)
                    AppScreen.Battery -> BatteryScreen(stateHolder = battHolder)
                    AppScreen.Privacy -> PrivacyScreen(stateHolder = privHolder)
                }
            }
        }
    }
}
