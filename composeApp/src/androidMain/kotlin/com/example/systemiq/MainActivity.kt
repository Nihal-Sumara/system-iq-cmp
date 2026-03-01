package com.example.systemiq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.systemiq.platform.AndroidBatteryDataSource
import com.example.systemiq.platform.AndroidPerformanceDataSource
import com.example.systemiq.platform.AndroidPrivacyDataSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContext = applicationContext
        val performanceDataSource = AndroidPerformanceDataSource(appContext)
        val batteryDataSource = AndroidBatteryDataSource(appContext)
        val privacyDataSource = AndroidPrivacyDataSource(appContext)

        setContent {
            App(
                performanceDataSource = performanceDataSource,
                batteryDataSource = batteryDataSource,
                privacyDataSource = privacyDataSource
            )
        }
    }
}
