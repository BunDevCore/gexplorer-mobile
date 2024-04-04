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

val GexplorerIcons.Filled.Analytics: ImageVector
    get() {
        if (analytics != null) {
            return analytics!!
        }
        analytics = Builder(name = "Analytics-24px", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(280.0f, 680.0f)
                lineTo(360.0f, 680.0f)
                lineTo(360.0f, 480.0f)
                lineTo(280.0f, 480.0f)
                lineTo(280.0f, 680.0f)
                close()
                moveTo(600.0f, 680.0f)
                lineTo(680.0f, 680.0f)
                lineTo(680.0f, 280.0f)
                lineTo(600.0f, 280.0f)
                lineTo(600.0f, 680.0f)
                close()
                moveTo(440.0f, 680.0f)
                lineTo(520.0f, 680.0f)
                lineTo(520.0f, 560.0f)
                lineTo(440.0f, 560.0f)
                lineTo(440.0f, 680.0f)
                close()
                moveTo(440.0f, 480.0f)
                lineTo(520.0f, 480.0f)
                lineTo(520.0f, 400.0f)
                lineTo(440.0f, 400.0f)
                lineTo(440.0f, 480.0f)
                close()
                moveTo(200.0f, 840.0f)
                quadTo(167.0f, 840.0f, 143.5f, 816.5f)
                quadTo(120.0f, 793.0f, 120.0f, 760.0f)
                lineTo(120.0f, 200.0f)
                quadTo(120.0f, 167.0f, 143.5f, 143.5f)
                quadTo(167.0f, 120.0f, 200.0f, 120.0f)
                lineTo(760.0f, 120.0f)
                quadTo(793.0f, 120.0f, 816.5f, 143.5f)
                quadTo(840.0f, 167.0f, 840.0f, 200.0f)
                lineTo(840.0f, 760.0f)
                quadTo(840.0f, 793.0f, 816.5f, 816.5f)
                quadTo(793.0f, 840.0f, 760.0f, 840.0f)
                lineTo(200.0f, 840.0f)
                close()
            }
        }
        .build()
        return analytics!!
    }

private var analytics: ImageVector? = null
