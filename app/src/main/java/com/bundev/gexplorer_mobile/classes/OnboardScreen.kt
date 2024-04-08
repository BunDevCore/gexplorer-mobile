package com.bundev.gexplorer_mobile.classes

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class OnboardScreen(
    val titleResource: Int,
    val descriptionResource: Int = -1,
    val userInteraction: @Composable (Unit) -> Unit = {},
    val preTitleResource: Int = -1,
    val imageResource: Int = -1,
    val imageVector: ImageVector = ImageVector.Builder(
        "NO IMAGE VECTOR", 0.dp, 0.dp, 0f, 0f)
        .build()
)