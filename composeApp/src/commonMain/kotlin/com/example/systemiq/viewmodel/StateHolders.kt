package com.example.systemiq.viewmodel

import com.example.systemiq.data.BatteryUiState
import com.example.systemiq.data.PerformanceUiState
import com.example.systemiq.data.PrivacyUiState
import com.example.systemiq.platform.BatteryDataSource
import com.example.systemiq.platform.PerformanceDataSource
import com.example.systemiq.platform.PrivacyDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerformanceStateHolder(
    private val dataSource: PerformanceDataSource,
    scope: CoroutineScope
) {
    private val _uiState = MutableStateFlow(PerformanceUiState())
    val uiState: StateFlow<PerformanceUiState> = _uiState.asStateFlow()

    init {
        scope.launch {
            while (isActive) {
                _uiState.value = dataSource.getPerformanceData()
                delay(2000L)
            }
        }
    }
}

class BatteryStateHolder(
    private val dataSource: BatteryDataSource,
    scope: CoroutineScope
) {
    private val _uiState = MutableStateFlow(BatteryUiState())
    val uiState: StateFlow<BatteryUiState> = _uiState.asStateFlow()

    init {
        scope.launch {
            while (isActive) {
                _uiState.value = dataSource.getBatteryData()
                delay(5000L)
            }
        }
    }
}

class PrivacyStateHolder(
    private val dataSource: PrivacyDataSource,
    scope: CoroutineScope
) {
    private val _uiState = MutableStateFlow(PrivacyUiState())
    val uiState: StateFlow<PrivacyUiState> = _uiState.asStateFlow()

    init {
        scope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = withContext(Dispatchers.Default) {
                dataSource.scanApps()
            }
            _uiState.value = result
        }
    }

    fun rescan(scope: CoroutineScope) {
        scope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = withContext(Dispatchers.Default) {
                dataSource.scanApps()
            }
            _uiState.value = result
        }
    }
}
