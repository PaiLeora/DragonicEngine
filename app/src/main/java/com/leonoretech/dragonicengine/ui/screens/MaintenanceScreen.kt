package com.leonoretech.dragonicengine.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leonoretech.dragonicengine.core.SettingsRepository
import com.leonoretech.dragonicengine.shizuku.ShizukuManager

@Composable
fun MaintenanceScreen() {
    var lastResult by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            EngineStatusBanner(
                online = ShizukuManager.isEngineOnline.value,
                permitted = ShizukuManager.isPermissionGranted.value
            )
            Spacer(Modifier.height(16.dp))
            Text("KERNEL PARAMETERS", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))

            SectionCard("POWER GOVERNOR & CPU") {
                SettingsRepository.perfToggles.take(3).forEach {
                    ToggleRow(it) { msg -> lastResult = msg }
                }
            }
        }

        item {
            SectionCard("THERMAL & STABILITY") {
                SettingsRepository.perfToggles.drop(3).forEach {
                    ToggleRow(it) { msg -> lastResult = msg }
                }
            }
        }

        item {
            SectionCard("NETWORK") {
                SettingsRepository.netToggles.forEach {
                    ToggleRow(it) { msg -> lastResult = msg }
                }
            }
        }

        item {
            SectionCard("SYSTEM") {
                SettingsRepository.sysToggles.forEach {
                    ToggleRow(it) { msg -> lastResult = msg }
                }
            }
        }

        item {
            SectionCard("MAINTENANCE COMMANDS") {
                SettingsRepository.maintenanceActions.forEach {
                    ActionRow(it) { msg -> lastResult = msg }
                }
            }
        }

        item {
            lastResult?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, style = MaterialTheme.typography.labelSmall)
            }
            if (!ShizukuManager.isPermissionGranted.value && ShizukuManager.isEngineOnline.value) {
                Spacer(Modifier.height(12.dp))
                Button(onClick = { ShizukuManager.requestPermission() }) {
                    Text("GRANT SHIZUKU PERMISSION")
                }
            }
        }
    }
}
