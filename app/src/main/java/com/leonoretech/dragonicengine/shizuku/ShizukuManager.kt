package com.leonoretech.dragonicengine.shizuku

import androidx.compose.runtime.mutableStateOf
import rikka.shizuku.Shizuku

object ShizukuManager {

    val isEngineOnline = mutableStateOf(false)
    val isPermissionGranted = mutableStateOf(false)

    private const val REQUEST_CODE = 7421

    private val binderReceivedListener = Shizuku.OnBinderReceivedListener {
        isEngineOnline.value = true
        checkPermission()
    }

    private val binderDeadListener = Shizuku.OnBinderDeadListener {
        isEngineOnline.value = false
        isPermissionGranted.value = false
    }

    private val permissionResultListener =
        Shizuku.OnRequestPermissionResultListener { requestCode, grantResult ->
            if (requestCode == REQUEST_CODE) {
                isPermissionGranted.value = grantResult == android.content.pm.PackageManager.PERMISSION_GRANTED
            }
        }

    fun init() {
        Shizuku.addBinderReceivedListenerSticky(binderReceivedListener)
        Shizuku.addBinderDeadListener(binderDeadListener)
        Shizuku.addRequestPermissionResultListener(permissionResultListener)
        isEngineOnline.value = Shizuku.pingBinder()
        if (isEngineOnline.value) checkPermission()
    }

    fun dispose() {
        Shizuku.removeBinderReceivedListener(binderReceivedListener)
        Shizuku.removeBinderDeadListener(binderDeadListener)
        Shizuku.removeRequestPermissionResultListener(permissionResultListener)
    }

    private fun checkPermission() {
        isPermissionGranted.value = try {
            Shizuku.checkSelfPermission() == android.content.pm.PackageManager.PERMISSION_GRANTED
        } catch (e: Exception) {
            false
        }
    }

    fun requestPermission() {
        if (!isEngineOnline.value) return
        if (Shizuku.shouldShowRequestPermissionRationale()) {
            return
        }
        Shizuku.requestPermission(REQUEST_CODE)
    }

    fun isReady(): Boolean = isEngineOnline.value && isPermissionGranted.value
}
