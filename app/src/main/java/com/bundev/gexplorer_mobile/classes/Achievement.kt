package com.bundev.gexplorer_mobile.classes

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.datetime.Instant
import java.util.UUID

class Achievement(
    val id: UUID? = null,
    val imageVector: ImageVector? = null,
    val name: String,
    val description: String? = null,
    val timeAchieved: Instant = Instant.DISTANT_PAST,
    val achievedOnTripId: UUID? = null
)