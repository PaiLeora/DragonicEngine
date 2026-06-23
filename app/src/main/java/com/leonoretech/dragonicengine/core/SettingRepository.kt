package com.leonoretech.dragonicengine.core

object SettingsRepository {

    val maintenanceActions = listOf(
        ActionCommand(label = "DROP RAM", icon = "🧠", command = "am kill-all"),
        ActionCommand(label = "DROP CACHE", icon = "🗑️", command = "pm trim-caches 999G"),
        ActionCommand(label = "FORCE DOZE", icon = "🌙", command = "dumpsys deviceidle force-idle"),
        ActionCommand(label = "CLEAR LOGS", icon = "📄", command = "logcat -c"),
        ActionCommand(label = "OPTIMIZE I/O", icon = "💾", command = "sync"),
        ActionCommand(label = "DEX COMPILE", icon = "⚙️", command = "cmd package bg-dexopt-job")
    )

    val perfToggles = listOf(
        ToggleSetting(
            label = "HIGH PERFORMANCE MODE",
            description = "Disables power-saving throttling for foreground app.",
            onCommand = "cmd power set-fixed-performance-mode-enabled true",
            offCommand = "cmd power set-fixed-performance-mode-enabled false"
        ),
        ToggleSetting(
            label = "AGGRESSIVE DOZE",
            description = "Lets the system enter Doze sooner when screen is off.",
            onCommand = "dumpsys deviceidle enable",
            offCommand = "dumpsys deviceidle disable"
        ),
        ToggleSetting(
            label = "SUSPEND CACHED APPS",
            description = "Lowers the max cached-process count so idle apps get killed sooner.",
            onCommand = "settings put global activity_manager_constants max_cached_processes=4:max_empty_processes=2",
            offCommand = "settings delete global activity_manager_constants"
        ),
        ToggleSetting(
            label = "INSTANT UI ANIMATIONS",
            description = "Sets all animation scales to 0 — UI feels instant.",
            onCommand = "settings put global window_animation_scale 0 && settings put global transition_animation_scale 0 && settings put global animator_duration_scale 0",
            offCommand = "settings put global window_animation_scale 1 && settings put global transition_animation_scale 1 && settings put global animator_duration_scale 1"
        ),
        ToggleSetting(
            label = "BYPASS THERMAL LIMIT",
            description = "Overrides thermal status to NONE so the CPU/GPU aren't throttled early.",
            onCommand = "cmd thermalservice override-status 0",
            offCommand = "cmd thermalservice reset"
        ),
        ToggleSetting(
            label = "DISABLE OS CRASH RAMDUMP",
            description = "Stops the system writing a ramdump on native crashes.",
            onCommand = "settings put global ramdump_enabled 0",
            offCommand = "settings put global ramdump_enabled 1"
        )
    )

    val netToggles = listOf(
        ToggleSetting(
            label = "WI-FI BG SCAN",
            description = "Background Wi-Fi scanning for location/network suggestions.",
            onCommand = "settings put global wifi_scan_always_enabled 1",
            offCommand = "settings put global wifi_scan_always_enabled 0"
        ),
        ToggleSetting(
            label = "DISABLE BLE/GPS SCANNING",
            description = "Stops always-on Bluetooth LE scanning used for location.",
            onCommand = "settings put global ble_scan_always_enabled 0",
            offCommand = "settings put global ble_scan_always_enabled 1"
        ),
        ToggleSetting(
            label = "GOOGLE DNS",
            description = "Routes DNS over TLS via 8.8.8.8.",
            onCommand = "settings put global private_dns_mode hostname && settings put global private_dns_specifier dns.google",
            offCommand = "settings put global private_dns_mode off"
        ),
        ToggleSetting(
            label = "CLOUDFLARE DNS",
            description = "Routes DNS over TLS via 1.1.1.1.",
            onCommand = "settings put global private_dns_mode hostname && settings put global private_dns_specifier one.one.one.one",
            offCommand = "settings put global private_dns_mode off"
        )
    )

    val sysToggles = listOf(
        ToggleSetting(
            label = "DISABLE HAPTIC ENGINE",
            description = "Turns off vibration feedback for UI interactions.",
            onCommand = "settings put system haptic_feedback_enabled 0",
            offCommand = "settings put system haptic_feedback_enabled 1"
        ),
        ToggleSetting(
            label = "AFK MODE (SCREEN ALWAYS ON)",
            description = "Keeps screen on while charging.",
            onCommand = "settings put global stay_on_while_plugged_in 3",
            offCommand = "settings put global stay_on_while_plugged_in 0"
        ),
        ToggleSetting(
            label = "DISABLE DIGITAL WELLBEING",
            description = "Disables the Digital Wellbeing package for this user profile.",
            onCommand = "pm disable-user --user 0 com.google.android.apps.wellbeing",
            offCommand = "pm enable com.google.android.apps.wellbeing"
        ),
        ToggleSetting(
            label = "DISABLE SYSTEM ANALYTICS",
            description = "Disables Google's usage/diagnostic data collection service.",
            onCommand = "pm disable-user --user 0 com.google.android.apps.gcs",
            offCommand = "pm enable com.google.android.apps.gcs"
        )
    )

    fun forceAngleCommand(pkg: String, enable: Boolean): String =
        if (enable)
            "settings put global angle_gl_driver_selection_pkgs $pkg && settings put global angle_gl_driver_selection_values angle"
        else
            "settings delete global angle_gl_driver_selection_pkgs"

    fun forceGameDriverCommand(pkg: String, enable: Boolean): String =
        if (enable)
            "settings put global game_driver_opt_in_apps $pkg"
        else
            "settings put global game_driver_opt_out_apps $pkg"
}
