@file:Suppress("UnusedReceiverParameter")

package com.example.gexplorer_mobile.slicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.example.gexplorer_mobile.SLIcons

val SLIcons.Filled: ImageVector
    get() {
        if (socialLeaderboardFilled != null) {
            return socialLeaderboardFilled!!
        }
        socialLeaderboardFilled = Builder(
            name = "SocialLeaderboardFilled", defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(299.0f, 840.0f)
                quadTo(208.0f, 840.0f, 144.0f, 776.0f)
                quadTo(80.0f, 712.0f, 80.0f, 621.0f)
                quadTo(80.0f, 535.0f, 137.5f, 472.5f)
                quadTo(195.0f, 410.0f, 281.0f, 403.0f)
                quadTo(281.0f, 403.0f, 281.0f, 403.0f)
                quadTo(281.0f, 403.0f, 281.0f, 403.0f)
                lineTo(120.0f, 80.0f)
                lineTo(400.0f, 80.0f)
                lineTo(480.0f, 240.0f)
                lineTo(560.0f, 80.0f)
                lineTo(840.0f, 80.0f)
                lineTo(680.0f, 401.0f)
                quadTo(680.0f, 401.0f, 680.0f, 401.0f)
                quadTo(680.0f, 401.0f, 680.0f, 401.0f)
                quadTo(765.0f, 409.0f, 822.5f, 471.5f)
                quadTo(880.0f, 534.0f, 880.0f, 620.0f)
                quadTo(880.0f, 712.0f, 816.0f, 776.0f)
                quadTo(752.0f, 840.0f, 660.0f, 840.0f)
                quadTo(651.0f, 840.0f, 641.5f, 839.5f)
                quadTo(632.0f, 839.0f, 623.0f, 837.0f)
                quadTo(678.0f, 801.0f, 709.0f, 743.5f)
                quadTo(740.0f, 686.0f, 740.0f, 620.0f)
                quadTo(740.0f, 511.0f, 664.5f, 435.5f)
                quadTo(589.0f, 360.0f, 480.0f, 360.0f)
                quadTo(371.0f, 360.0f, 295.5f, 435.5f)
                quadTo(220.0f, 511.0f, 220.0f, 620.0f)
                quadTo(220.0f, 688.0f, 248.0f, 748.0f)
                quadTo(276.0f, 808.0f, 336.0f, 837.0f)
                quadTo(327.0f, 839.0f, 317.5f, 839.5f)
                quadTo(308.0f, 840.0f, 299.0f, 840.0f)
                close()
                moveTo(480.0f, 800.0f)
                quadTo(405.0f, 800.0f, 352.5f, 747.5f)
                quadTo(300.0f, 695.0f, 300.0f, 620.0f)
                quadTo(300.0f, 545.0f, 352.5f, 492.5f)
                quadTo(405.0f, 440.0f, 480.0f, 440.0f)
                quadTo(555.0f, 440.0f, 607.5f, 492.5f)
                quadTo(660.0f, 545.0f, 660.0f, 620.0f)
                quadTo(660.0f, 695.0f, 607.5f, 747.5f)
                quadTo(555.0f, 800.0f, 480.0f, 800.0f)
                close()
                moveTo(406.0f, 730.0f)
                lineTo(480.0f, 674.0f)
                lineTo(554.0f, 730.0f)
                lineTo(526.0f, 639.0f)
                lineTo(600.0f, 586.0f)
                lineTo(509.0f, 586.0f)
                lineTo(480.0f, 490.0f)
                lineTo(451.0f, 586.0f)
                lineTo(360.0f, 586.0f)
                lineTo(434.0f, 639.0f)
                lineTo(406.0f, 730.0f)
                close()
            }
        }
            .build()
        return socialLeaderboardFilled!!
    }

private var socialLeaderboardFilled: ImageVector? = null
