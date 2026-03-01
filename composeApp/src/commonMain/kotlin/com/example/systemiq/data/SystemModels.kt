package com.example.systemiq.data

data class CpuInfo(
    val usagePercent: Float = 0f,
    val coreCount: Int = 0
)

data class MemoryInfo(
    val usedMb: Long = 0L,
    val totalMb: Long = 0L,
    val usagePercent: Float = 0f
)

data class StorageInfo(
    val usedGb: Float = 0f,
    val totalGb: Float = 0f,
    val usagePercent: Float = 0f
)

data class BatteryInfo(
    val level: Int = 0,
    val isCharging: Boolean = false,
    val temperature: Float = 0f,
    val voltage: Int = 0,
    val health: String = "Unknown",
    val technology: String = "Unknown"
)

data class BatteryApp(
    val appName: String,
    val packageName: String,
    val usageMinutes: Long,
    val usagePercent: Float
)

data class AppPermissionInfo(
    val appName: String,
    val packageName: String,
    val sensitivePermissions: List<String>,
    val riskLevel: RiskLevel
)

enum class RiskLevel {
    LOW, MEDIUM, HIGH, CRITICAL
}

data class PerformanceUiState(
    val cpu: CpuInfo = CpuInfo(),
    val memory: MemoryInfo = MemoryInfo(),
    val storage: StorageInfo = StorageInfo(),
    val isLoading: Boolean = true
)

data class BatteryUiState(
    val battery: BatteryInfo = BatteryInfo(),
    val topApps: List<BatteryApp> = emptyList(),
    val isLoading: Boolean = true
)

data class PrivacyUiState(
    val overallScore: Int = 100,
    val apps: List<AppPermissionInfo> = emptyList(),
    val totalPermissions: Int = 0,
    val highRiskCount: Int = 0,
    val isLoading: Boolean = true
)

enum class AppScreen(val title: String) {
    Dashboard("Dashboard"),
    Performance("Performance"),
    Battery("Battery"),
    Privacy("Privacy")
}
