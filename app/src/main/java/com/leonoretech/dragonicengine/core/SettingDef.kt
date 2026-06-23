package com.leonoretech.dragonicengine.core

data class ToggleSetting(
    val label: String,
    val description: String = "",
    val onCommand: String,
    val offCommand: String,
    val queryCommand: String? = null
)

data class ActionCommand(
    val label: String,
    val icon: String = "",
    val command: String
)
