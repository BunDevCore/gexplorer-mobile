@file:Suppress("UnusedReceiverParameter")

package com.bundev.gexplorer_mobile.icons.filled

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

val GexplorerIcons.Filled.Explore: ImageVector
    get() {
        if (explore != null) {
            return explore!!
        }
        explore = Builder(name = "Explore", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(260.0f, 700.0f)
                lineTo(560.0f, 560.0f)
                lineTo(700.0f, 260.0f)
                lineTo(400.0f, 400.0f)
                lineTo(260.0f, 700.0f)
                close()
                moveTo(480.0f, 520.0f)
                quadTo(463.0f, 520.0f, 451.5f, 508.5f)
                quadTo(440.0f, 497.0f, 440.0f, 480.0f)
                quadTo(440.0f, 463.0f, 451.5f, 451.5f)
                quadTo(463.0f, 440.0f, 480.0f, 440.0f)
                quadTo(497.0f, 440.0f, 508.5f, 451.5f)
                quadTo(520.0f, 463.0f, 520.0f, 480.0f)
                quadTo(520.0f, 497.0f, 508.5f, 508.5f)
                quadTo(497.0f, 520.0f, 480.0f, 520.0f)
                close()
                moveTo(480.0f, 880.0f)
                quadTo(397.0f, 880.0f, 324.0f, 848.5f)
                quadTo(251.0f, 817.0f, 197.0f, 763.0f)
                quadTo(143.0f, 709.0f, 111.5f, 636.0f)
                quadTo(80.0f, 563.0f, 80.0f, 480.0f)
                quadTo(80.0f, 397.0f, 111.5f, 324.0f)
                quadTo(143.0f, 251.0f, 197.0f, 197.0f)
                quadTo(251.0f, 143.0f, 324.0f, 111.5f)
                quadTo(397.0f, 80.0f, 480.0f, 80.0f)
                quadTo(563.0f, 80.0f, 636.0f, 111.5f)
                quadTo(709.0f, 143.0f, 763.0f, 197.0f)
                quadTo(817.0f, 251.0f, 848.5f, 324.0f)
                quadTo(880.0f, 397.0f, 880.0f, 480.0f)
                quadTo(880.0f, 563.0f, 848.5f, 636.0f)
                quadTo(817.0f, 709.0f, 763.0f, 763.0f)
                quadTo(709.0f, 817.0f, 636.0f, 848.5f)
                quadTo(563.0f, 880.0f, 480.0f, 880.0f)
                close()
            }
        }
        .build()
        return explore!!
    }

private var explore: ImageVector? = null
