package com.bundev.gexplorer_mobile.classes

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class Onboard(
    val title: String,
    val description: String,
    val imageRes: Int = -1,
    val imageVector: ImageVector = ImageVector.Builder(
        "NO IMAGE VECTOR", 0.dp, 0.dp, 0f, 0f)
        .build()
)