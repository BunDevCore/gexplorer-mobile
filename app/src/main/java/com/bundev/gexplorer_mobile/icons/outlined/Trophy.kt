@file:Suppress("UnusedReceiverParameter")

package com.bundev.gexplorer_mobile.icons.outlined

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

val GexplorerIcons.Outlined.Trophy: ImageVector
    get() {
        if (trophy != null) {
            return trophy!!
        }
        trophy = Builder(name = "TrophyOutlined", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(280.0f, 840.0f)
                lineTo(280.0f, 760.0f)
                lineTo(440.0f, 760.0f)
                lineTo(440.0f, 636.0f)
                quadTo(391.0f, 625.0f, 352.5f, 594.5f)
                quadTo(314.0f, 564.0f, 296.0f, 518.0f)
                quadTo(221.0f, 509.0f, 170.5f, 452.5f)
                quadTo(120.0f, 396.0f, 120.0f, 320.0f)
                lineTo(120.0f, 280.0f)
                quadTo(120.0f, 247.0f, 143.5f, 223.5f)
                quadTo(167.0f, 200.0f, 200.0f, 200.0f)
                lineTo(280.0f, 200.0f)
                lineTo(280.0f, 120.0f)
                lineTo(680.0f, 120.0f)
                lineTo(680.0f, 200.0f)
                lineTo(760.0f, 200.0f)
                quadTo(793.0f, 200.0f, 816.5f, 223.5f)
                quadTo(840.0f, 247.0f, 840.0f, 280.0f)
                lineTo(840.0f, 320.0f)
                quadTo(840.0f, 396.0f, 789.5f, 452.5f)
                quadTo(739.0f, 509.0f, 664.0f, 518.0f)
                quadTo(646.0f, 564.0f, 607.5f, 594.5f)
                quadTo(569.0f, 625.0f, 520.0f, 636.0f)
                lineTo(520.0f, 760.0f)
                lineTo(680.0f, 760.0f)
                lineTo(680.0f, 840.0f)
                lineTo(280.0f, 840.0f)
                close()
                moveTo(280.0f, 432.0f)
                lineTo(280.0f, 280.0f)
                lineTo(200.0f, 280.0f)
                lineTo(200.0f, 320.0f)
                quadTo(200.0f, 358.0f, 222.0f, 388.5f)
                quadTo(244.0f, 419.0f, 280.0f, 432.0f)
                close()
                moveTo(480.0f, 560.0f)
                quadTo(530.0f, 560.0f, 565.0f, 525.0f)
                quadTo(600.0f, 490.0f, 600.0f, 440.0f)
                lineTo(600.0f, 200.0f)
                lineTo(360.0f, 200.0f)
                lineTo(360.0f, 440.0f)
                quadTo(360.0f, 490.0f, 395.0f, 525.0f)
                quadTo(430.0f, 560.0f, 480.0f, 560.0f)
                close()
                moveTo(680.0f, 432.0f)
                quadTo(716.0f, 419.0f, 738.0f, 388.5f)
                quadTo(760.0f, 358.0f, 760.0f, 320.0f)
                lineTo(760.0f, 280.0f)
                lineTo(680.0f, 280.0f)
                lineTo(680.0f, 432.0f)
                close()
                moveTo(480.0f, 380.0f)
                quadTo(480.0f, 380.0f, 480.0f, 380.0f)
                quadTo(480.0f, 380.0f, 480.0f, 380.0f)
                lineTo(480.0f, 380.0f)
                lineTo(480.0f, 380.0f)
                lineTo(480.0f, 380.0f)
                quadTo(480.0f, 380.0f, 480.0f, 380.0f)
                quadTo(480.0f, 380.0f, 480.0f, 380.0f)
                close()
            }
        }
        .build()
        return trophy!!
    }

private var trophy: ImageVector? = null
