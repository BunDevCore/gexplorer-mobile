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

val GexplorerIcons.Filled.Location: ImageVector
    get() {
        if (location != null) {
            return location!!
        }
        location = Builder(name = "MyLocation-24px", defaultWidth = 24.0.dp, defaultHeight
                = 24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(440.0f, 918.0f)
                lineTo(440.0f, 838.0f)
                quadTo(315.0f, 824.0f, 225.5f, 734.5f)
                quadTo(136.0f, 645.0f, 122.0f, 520.0f)
                lineTo(42.0f, 520.0f)
                lineTo(42.0f, 440.0f)
                lineTo(122.0f, 440.0f)
                quadTo(136.0f, 315.0f, 225.5f, 225.5f)
                quadTo(315.0f, 136.0f, 440.0f, 122.0f)
                lineTo(440.0f, 42.0f)
                lineTo(520.0f, 42.0f)
                lineTo(520.0f, 122.0f)
                quadTo(645.0f, 136.0f, 734.5f, 225.5f)
                quadTo(824.0f, 315.0f, 838.0f, 440.0f)
                lineTo(918.0f, 440.0f)
                lineTo(918.0f, 520.0f)
                lineTo(838.0f, 520.0f)
                quadTo(824.0f, 645.0f, 734.5f, 734.5f)
                quadTo(645.0f, 824.0f, 520.0f, 838.0f)
                lineTo(520.0f, 918.0f)
                lineTo(440.0f, 918.0f)
                close()
                moveTo(480.0f, 760.0f)
                quadTo(596.0f, 760.0f, 678.0f, 678.0f)
                quadTo(760.0f, 596.0f, 760.0f, 480.0f)
                quadTo(760.0f, 364.0f, 678.0f, 282.0f)
                quadTo(596.0f, 200.0f, 480.0f, 200.0f)
                quadTo(364.0f, 200.0f, 282.0f, 282.0f)
                quadTo(200.0f, 364.0f, 200.0f, 480.0f)
                quadTo(200.0f, 596.0f, 282.0f, 678.0f)
                quadTo(364.0f, 760.0f, 480.0f, 760.0f)
                close()
                moveTo(480.0f, 640.0f)
                quadTo(414.0f, 640.0f, 367.0f, 593.0f)
                quadTo(320.0f, 546.0f, 320.0f, 480.0f)
                quadTo(320.0f, 414.0f, 367.0f, 367.0f)
                quadTo(414.0f, 320.0f, 480.0f, 320.0f)
                quadTo(546.0f, 320.0f, 593.0f, 367.0f)
                quadTo(640.0f, 414.0f, 640.0f, 480.0f)
                quadTo(640.0f, 546.0f, 593.0f, 593.0f)
                quadTo(546.0f, 640.0f, 480.0f, 640.0f)
                close()
            }
        }
        .build()
        return location!!
    }

private var location: ImageVector? = null
