package com.leonoretech.dragonicengine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.leonoretech.dragonicengine.shizuku.ShizukuManager
import com.leonoretech.dragonicengine.ui.screens.MaintenanceScreen
import com.leonoretech.dragonicengine.ui.theme.BgBlack
import com.leonoretech.dragonicengine.ui.theme.DragonicEngineTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ShizukuManager.init()

        setContent {
            DragonicEngineTheme {
                Surface(modifier = Modifier.fillMaxSize().background(BgBlack)) {
                    MaintenanceScreen()
                }
            }
        }
    }

    override fun onDestroy() {
        ShizukuManager.dispose()
        super.onDestroy()
    }
}
