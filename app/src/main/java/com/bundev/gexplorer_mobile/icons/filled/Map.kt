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

val GexplorerIcons.Filled.Map: ImageVector
    get() {
        if (map != null) {
            return map!!
        }
        map = Builder(name = "Map-filled", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(600.0f, 840.0f)
                lineTo(360.0f, 756.0f)
                lineTo(174.0f, 828.0f)
                quadTo(154.0f, 836.0f, 137.0f, 823.5f)
                quadTo(120.0f, 811.0f, 120.0f, 790.0f)
                lineTo(120.0f, 230.0f)
                quadTo(120.0f, 217.0f, 127.5f, 207.0f)
                quadTo(135.0f, 197.0f, 148.0f, 192.0f)
                lineTo(360.0f, 120.0f)
                lineTo(600.0f, 204.0f)
                lineTo(786.0f, 132.0f)
                quadTo(806.0f, 124.0f, 823.0f, 136.5f)
                quadTo(840.0f, 149.0f, 840.0f, 170.0f)
                lineTo(840.0f, 730.0f)
                quadTo(840.0f, 743.0f, 832.5f, 753.0f)
                quadTo(825.0f, 763.0f, 812.0f, 768.0f)
                lineTo(600.0f, 840.0f)
                close()
                moveTo(560.0f, 742.0f)
                lineTo(560.0f, 274.0f)
                lineTo(400.0f, 218.0f)
                lineTo(400.0f, 686.0f)
                lineTo(560.0f, 742.0f)
                close()
            }
        }
        .build()
        return map!!
    }

private var map: ImageVector? = null
