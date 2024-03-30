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

val GexplorerIcons.Filled.Walk: ImageVector
    get() {
        if (walk != null) {
            return walk!!
        }
        walk = Builder(name = "Walk", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(280.0f, 920.0f)
                lineTo(392.0f, 356.0f)
                lineTo(320.0f, 384.0f)
                lineTo(320.0f, 520.0f)
                lineTo(240.0f, 520.0f)
                lineTo(240.0f, 332.0f)
                lineTo(442.0f, 246.0f)
                quadTo(456.0f, 240.0f, 471.5f, 239.0f)
                quadTo(487.0f, 238.0f, 501.0f, 243.0f)
                quadTo(515.0f, 248.0f, 527.5f, 257.0f)
                quadTo(540.0f, 266.0f, 548.0f, 280.0f)
                lineTo(588.0f, 344.0f)
                quadTo(614.0f, 386.0f, 658.5f, 413.0f)
                quadTo(703.0f, 440.0f, 760.0f, 440.0f)
                lineTo(760.0f, 520.0f)
                quadTo(690.0f, 520.0f, 635.0f, 491.0f)
                quadTo(580.0f, 462.0f, 541.0f, 417.0f)
                lineTo(516.0f, 540.0f)
                lineTo(600.0f, 620.0f)
                lineTo(600.0f, 920.0f)
                lineTo(520.0f, 920.0f)
                lineTo(520.0f, 660.0f)
                lineTo(436.0f, 596.0f)
                lineTo(364.0f, 920.0f)
                lineTo(280.0f, 920.0f)
                close()
                moveTo(540.0f, 220.0f)
                quadTo(507.0f, 220.0f, 483.5f, 196.5f)
                quadTo(460.0f, 173.0f, 460.0f, 140.0f)
                quadTo(460.0f, 107.0f, 483.5f, 83.5f)
                quadTo(507.0f, 60.0f, 540.0f, 60.0f)
                quadTo(573.0f, 60.0f, 596.5f, 83.5f)
                quadTo(620.0f, 107.0f, 620.0f, 140.0f)
                quadTo(620.0f, 173.0f, 596.5f, 196.5f)
                quadTo(573.0f, 220.0f, 540.0f, 220.0f)
                close()
            }
        }
        .build()
        return walk!!
    }

private var walk: ImageVector? = null
