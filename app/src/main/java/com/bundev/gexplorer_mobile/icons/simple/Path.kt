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

val GexplorerIcons.Simple.Path: ImageVector
    get() {
        if (_path != null) {
            return _path!!
        }
        _path = Builder(name = "Path", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(760.0f, 840.0f)
                quadTo(721.0f, 840.0f, 690.0f, 817.5f)
                quadTo(659.0f, 795.0f, 647.0f, 760.0f)
                lineTo(440.0f, 760.0f)
                quadTo(374.0f, 760.0f, 327.0f, 713.0f)
                quadTo(280.0f, 666.0f, 280.0f, 600.0f)
                quadTo(280.0f, 534.0f, 327.0f, 487.0f)
                quadTo(374.0f, 440.0f, 440.0f, 440.0f)
                lineTo(520.0f, 440.0f)
                quadTo(553.0f, 440.0f, 576.5f, 416.5f)
                quadTo(600.0f, 393.0f, 600.0f, 360.0f)
                quadTo(600.0f, 327.0f, 576.5f, 303.5f)
                quadTo(553.0f, 280.0f, 520.0f, 280.0f)
                lineTo(313.0f, 280.0f)
                quadTo(300.0f, 315.0f, 269.5f, 337.5f)
                quadTo(239.0f, 360.0f, 200.0f, 360.0f)
                quadTo(150.0f, 360.0f, 115.0f, 325.0f)
                quadTo(80.0f, 290.0f, 80.0f, 240.0f)
                quadTo(80.0f, 190.0f, 115.0f, 155.0f)
                quadTo(150.0f, 120.0f, 200.0f, 120.0f)
                quadTo(239.0f, 120.0f, 269.5f, 142.5f)
                quadTo(300.0f, 165.0f, 313.0f, 200.0f)
                lineTo(520.0f, 200.0f)
                quadTo(586.0f, 200.0f, 633.0f, 247.0f)
                quadTo(680.0f, 294.0f, 680.0f, 360.0f)
                quadTo(680.0f, 426.0f, 633.0f, 473.0f)
                quadTo(586.0f, 520.0f, 520.0f, 520.0f)
                lineTo(440.0f, 520.0f)
                quadTo(407.0f, 520.0f, 383.5f, 543.5f)
                quadTo(360.0f, 567.0f, 360.0f, 600.0f)
                quadTo(360.0f, 633.0f, 383.5f, 656.5f)
                quadTo(407.0f, 680.0f, 440.0f, 680.0f)
                lineTo(647.0f, 680.0f)
                quadTo(660.0f, 645.0f, 690.5f, 622.5f)
                quadTo(721.0f, 600.0f, 760.0f, 600.0f)
                quadTo(810.0f, 600.0f, 845.0f, 635.0f)
                quadTo(880.0f, 670.0f, 880.0f, 720.0f)
                quadTo(880.0f, 770.0f, 845.0f, 805.0f)
                quadTo(810.0f, 840.0f, 760.0f, 840.0f)
                close()
                moveTo(200.0f, 280.0f)
                quadTo(217.0f, 280.0f, 228.5f, 268.5f)
                quadTo(240.0f, 257.0f, 240.0f, 240.0f)
                quadTo(240.0f, 223.0f, 228.5f, 211.5f)
                quadTo(217.0f, 200.0f, 200.0f, 200.0f)
                quadTo(183.0f, 200.0f, 171.5f, 211.5f)
                quadTo(160.0f, 223.0f, 160.0f, 240.0f)
                quadTo(160.0f, 257.0f, 171.5f, 268.5f)
                quadTo(183.0f, 280.0f, 200.0f, 280.0f)
                close()
            }
        }
        .build()
        return _path!!
    }

private var _path: ImageVector? = null
