package com.leonoretech.dragonicengine.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val HeaderFont = FontFamily.Monospace
val BodyFont = FontFamily.Monospace

val DragonicTypography = Typography(
    headlineSmall = TextStyle(fontFamily = HeaderFont, fontWeight = FontWeight.Bold, fontSize = 20.sp, letterSpacing = 1.sp),
    titleMedium = TextStyle(fontFamily = HeaderFont, fontWeight = FontWeight.Bold, fontSize = 16.sp, letterSpacing = 1.sp),
    bodyMedium = TextStyle(fontFamily = BodyFont, fontSize = 14.sp),
    labelSmall = TextStyle(fontFamily = BodyFont, fontSize = 11.sp, letterSpacing = 0.5.sp)
)
