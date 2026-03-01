package com.example.systemiq

import androidx.compose.ui.window.ComposeUIViewController
import com.example.systemiq.platform.IosBatteryDataSource
import com.example.systemiq.platform.IosPerformanceDataSource
import com.example.systemiq.platform.IosPrivacyDataSource

fun MainViewController() = ComposeUIViewController {
    App(
        performanceDataSource = IosPerformanceDataSource(),
        batteryDataSource = IosBatteryDataSource(),
        privacyDataSource = IosPrivacyDataSource()
    )
}
