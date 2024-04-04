package com.bundev.gexplorer_mobile.classes

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.icons.filled.Trophy
import kotlinx.datetime.Instant
import java.util.UUID

class Achievement(
    val id: UUID? = null,
    val imageVector: ImageVector = GexplorerIcons.Filled.Trophy,
    val color: Color? = null,
    val name: String,
    val description: String? = null,
    val timeAchieved: Instant = Instant.DISTANT_PAST,
    val achievedOnTripId: UUID? = null
)