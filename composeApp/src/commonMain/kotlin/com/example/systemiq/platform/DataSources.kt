package com.example.systemiq.platform

import com.example.systemiq.data.BatteryUiState
import com.example.systemiq.data.PerformanceUiState
import com.example.systemiq.data.PrivacyUiState

/**
 * Platform-specific data source for performance metrics (CPU, RAM, Storage).
 */
interface PerformanceDataSource {
    fun getPerformanceData(): PerformanceUiState
}

/**
 * Platform-specific data source for battery information and app usage.
 */
interface BatteryDataSource {
    fun getBatteryData(): BatteryUiState
}

/**
 * Platform-specific data source for scanning app permissions.
 */
interface PrivacyDataSource {
    fun scanApps(): PrivacyUiState
}
