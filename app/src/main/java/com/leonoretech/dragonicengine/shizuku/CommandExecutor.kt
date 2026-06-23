package com.leonoretech.dragonicengine.shizuku

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rikka.shizuku.Shizuku

object CommandExecutor {

    suspend fun run(command: String): Result = withContext(Dispatchers.IO) {
        if (!ShizukuManager.isReady()) {
            return@withContext Result(success = false, output = "Engine offline: Shizuku not bound or not permitted.")
        }
        try {
            val process = Shizuku.newProcess(arrayOf("sh", "-c", command), null, null)
            val out = process.inputStream.bufferedReader().readText()
            val err = process.errorStream.bufferedReader().readText()
            process.waitFor()
            val exit = process.exitValue()
            Result(
                success = exit == 0,
                output = if (err.isNotBlank()) "$out\n$err".trim() else out.trim()
            )
        } catch (e: Exception) {
            Result(success = false, output = e.message ?: "Unknown error")
        }
    }

    data class Result(val success: Boolean, val output: String)
}
