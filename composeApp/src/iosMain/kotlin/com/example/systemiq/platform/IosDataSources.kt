package com.example.systemiq.platform

import com.example.systemiq.data.AppPermissionInfo
import com.example.systemiq.data.BatteryApp
import com.example.systemiq.data.BatteryInfo
import com.example.systemiq.data.BatteryUiState
import com.example.systemiq.data.CpuInfo
import com.example.systemiq.data.MemoryInfo
import com.example.systemiq.data.PerformanceUiState
import com.example.systemiq.data.PrivacyUiState
import com.example.systemiq.data.RiskLevel
import com.example.systemiq.data.StorageInfo

/**
 * iOS implementations with simulated data.
 * Replace with real iOS system API calls via Objective-C interop as needed.
 */

class IosPerformanceDataSource : PerformanceDataSource {
    override fun getPerformanceData(): PerformanceUiState {
        return PerformanceUiState(
            cpu = CpuInfo(usagePercent = (20..50).random().toFloat(), coreCount = 6),
            memory = MemoryInfo(usedMb = 3200, totalMb = 6144, usagePercent = 52f),
            storage = StorageInfo(usedGb = 87.3f, totalGb = 128f, usagePercent = 68.2f),
            isLoading = false
        )
    }
}

class IosBatteryDataSource : BatteryDataSource {
    override fun getBatteryData(): BatteryUiState {
        return BatteryUiState(
            battery = BatteryInfo(level = 72, isCharging = false, temperature = 31.5f, voltage = 3800, health = "Good", technology = "Li-Ion"),
            topApps = listOf(
                BatteryApp("Display", "com.apple.display", 120, 30f),
                BatteryApp("Safari", "com.apple.safari", 65, 16f),
                BatteryApp("Music", "com.apple.music", 40, 10f),
                BatteryApp("Maps", "com.apple.maps", 25, 6f)
            ),
            isLoading = false
        )
    }
}

class IosPrivacyDataSource : PrivacyDataSource {
    override fun scanApps(): PrivacyUiState {
        val sampleApps = listOf(
            AppPermissionInfo("Maps", "com.apple.maps", listOf("Precise Location", "Approximate Location"), RiskLevel.LOW),
            AppPermissionInfo("Camera", "com.apple.camera", listOf("Camera", "Microphone", "Precise Location"), RiskLevel.MEDIUM),
            AppPermissionInfo("Social App", "com.example.social", listOf("Camera", "Microphone", "Contacts", "Precise Location", "Calendar"), RiskLevel.HIGH)
        )
        return PrivacyUiState(
            overallScore = 78,
            apps = sampleApps,
            totalPermissions = sampleApps.sumOf { it.sensitivePermissions.size },
            highRiskCount = sampleApps.count { it.riskLevel == RiskLevel.HIGH || it.riskLevel == RiskLevel.CRITICAL },
            isLoading = false
        )
    }
}
