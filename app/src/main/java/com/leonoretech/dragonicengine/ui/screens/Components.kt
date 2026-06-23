package com.leonoretech.dragonicengine.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.leonoretech.dragonicengine.core.ActionCommand
import com.leonoretech.dragonicengine.core.ToggleSetting
import com.leonoretech.dragonicengine.shizuku.CommandExecutor
import com.leonoretech.dragonicengine.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(1.dp, BorderDim, RoundedCornerShape(4.dp))
            .background(PanelDark, RoundedCornerShape(4.dp))
            .padding(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.labelSmall, color = TextDim)
        Spacer(Modifier.height(10.dp))
        content()
    }
}

@Composable
fun ToggleRow(setting: ToggleSetting, onResult: (String) -> Unit) {
    var checked by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f)) {
            Text(setting.label, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
            if (setting.description.isNotBlank()) {
                Text(setting.description, style = MaterialTheme.typography.labelSmall, color = TextDim)
            }
        }
        Switch(
            checked = checked,
            enabled = !busy,
            onCheckedChange = { value ->
                checked = value
                busy = true
                scope.launch {
                    val cmd = if (value) setting.onCommand else setting.offCommand
                    val result = CommandExecutor.run(cmd)
                    busy = false
                    onResult("${setting.label}: ${if (result.success) "OK" else "FAILED — ${result.output}"}")
                    if (!result.success) checked = !value
                },
                colors = SwitchDefaults.colors(checkedThumbColor = NeonCyan, checkedTrackColor = NeonCyan.copy(alpha = 0.4f))
            )
        )
    }
}

@Composable
fun ActionRow(action: ActionCommand, onResult: (String) -> Unit) {
    var busy by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !busy) {
                busy = true
                scope.launch {
                    val result = CommandExecutor.run(action.command)
                    busy = false
                    onResult("${action.label}: ${if (result.success) "DONE" else "FAILED — ${result.output}"}")
                }
            }
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(action.icon + "  ", color = DangerRed)
        Text(
            if (busy) "RUNNING..." else action.label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (busy) TextDim else DangerRed
        )
    }
}

@Composable
fun EngineStatusBanner(online: Boolean, permitted: Boolean) {
    val (color, text) = when {
        online && permitted -> NeonCyan to "ENGINE READY"
        online && !permitted -> Color(0xFFFFA500) to "PERMISSION REQUIRED"
        else -> DangerRed to "ENGINE OFFLINE"
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color.copy(alpha = 0.08f))
            .border(1.dp, color.copy(alpha = 0.4f))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, RoundedCornerShape(50))
        )
        Spacer(Modifier.width(8.dp))
        Text(text, color = color, style = MaterialTheme.typography.labelSmall)
    }
}
