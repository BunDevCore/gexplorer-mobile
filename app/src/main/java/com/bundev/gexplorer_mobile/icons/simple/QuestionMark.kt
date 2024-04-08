@file:Suppress("UnusedReceiverParameter")

package com.bundev.gexplorer_mobile.icons.simple

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.bundev.gexplorer_mobile.GexplorerIcons

val GexplorerIcons.Simple.QuestionMark: ImageVector
    get() {
        if (questionmark != null) {
            return questionmark!!
        }
        questionmark = Builder(
            name = "QuestionMark", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(424.0f, 640.0f)
                quadTo(424.0f, 559.0f, 438.5f, 523.5f)
                quadTo(453.0f, 488.0f, 500.0f, 446.0f)
                quadTo(541.0f, 410.0f, 562.5f, 383.5f)
                quadTo(584.0f, 357.0f, 584.0f, 323.0f)
                quadTo(584.0f, 282.0f, 556.5f, 255.0f)
                quadTo(529.0f, 228.0f, 480.0f, 228.0f)
                quadTo(429.0f, 228.0f, 402.5f, 259.0f)
                quadTo(376.0f, 290.0f, 365.0f, 322.0f)
                lineTo(262.0f, 278.0f)
                quadTo(283.0f, 214.0f, 339.0f, 167.0f)
                quadTo(395.0f, 120.0f, 480.0f, 120.0f)
                quadTo(585.0f, 120.0f, 641.5f, 178.5f)
                quadTo(698.0f, 237.0f, 698.0f, 319.0f)
                quadTo(698.0f, 369.0f, 676.5f, 404.5f)
                quadTo(655.0f, 440.0f, 609.0f, 485.0f)
                quadTo(560.0f, 532.0f, 549.5f, 556.5f)
                quadTo(539.0f, 581.0f, 539.0f, 640.0f)
                lineTo(424.0f, 640.0f)
                close()
                moveTo(480.0f, 880.0f)
                quadTo(447.0f, 880.0f, 423.5f, 856.5f)
                quadTo(400.0f, 833.0f, 400.0f, 800.0f)
                quadTo(400.0f, 767.0f, 423.5f, 743.5f)
                quadTo(447.0f, 720.0f, 480.0f, 720.0f)
                quadTo(513.0f, 720.0f, 536.5f, 743.5f)
                quadTo(560.0f, 767.0f, 560.0f, 800.0f)
                quadTo(560.0f, 833.0f, 536.5f, 856.5f)
                quadTo(513.0f, 880.0f, 480.0f, 880.0f)
                close()
            }
        }
            .build()
        return questionmark!!
    }

private var questionmark: ImageVector? = null
