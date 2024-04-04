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

val GexplorerIcons.Outlined.Speed: ImageVector
    get() {
        if (speed != null) {
            return speed!!
        }
        speed = Builder(name = "SpeedOutlined", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(418.0f, 620.0f)
                quadTo(442.0f, 644.0f, 480.0f, 643.5f)
                quadTo(518.0f, 643.0f, 536.0f, 616.0f)
                lineTo(760.0f, 280.0f)
                lineTo(424.0f, 504.0f)
                quadTo(397.0f, 522.0f, 395.5f, 559.0f)
                quadTo(394.0f, 596.0f, 418.0f, 620.0f)
                close()
                moveTo(480.0f, 160.0f)
                quadTo(539.0f, 160.0f, 593.5f, 176.5f)
                quadTo(648.0f, 193.0f, 696.0f, 226.0f)
                lineTo(620.0f, 274.0f)
                quadTo(587.0f, 257.0f, 551.5f, 248.5f)
                quadTo(516.0f, 240.0f, 480.0f, 240.0f)
                quadTo(347.0f, 240.0f, 253.5f, 333.5f)
                quadTo(160.0f, 427.0f, 160.0f, 560.0f)
                quadTo(160.0f, 602.0f, 171.5f, 643.0f)
                quadTo(183.0f, 684.0f, 204.0f, 720.0f)
                lineTo(756.0f, 720.0f)
                quadTo(779.0f, 682.0f, 789.5f, 641.0f)
                quadTo(800.0f, 600.0f, 800.0f, 556.0f)
                quadTo(800.0f, 520.0f, 791.5f, 486.0f)
                quadTo(783.0f, 452.0f, 766.0f, 420.0f)
                lineTo(814.0f, 344.0f)
                quadTo(844.0f, 391.0f, 861.5f, 444.0f)
                quadTo(879.0f, 497.0f, 880.0f, 554.0f)
                quadTo(881.0f, 611.0f, 867.0f, 663.0f)
                quadTo(853.0f, 715.0f, 826.0f, 762.0f)
                quadTo(815.0f, 780.0f, 796.0f, 790.0f)
                quadTo(777.0f, 800.0f, 756.0f, 800.0f)
                lineTo(204.0f, 800.0f)
                quadTo(183.0f, 800.0f, 164.0f, 790.0f)
                quadTo(145.0f, 780.0f, 134.0f, 762.0f)
                quadTo(108.0f, 717.0f, 94.0f, 666.5f)
                quadTo(80.0f, 616.0f, 80.0f, 560.0f)
                quadTo(80.0f, 477.0f, 111.5f, 404.5f)
                quadTo(143.0f, 332.0f, 197.5f, 277.5f)
                quadTo(252.0f, 223.0f, 325.0f, 191.5f)
                quadTo(398.0f, 160.0f, 480.0f, 160.0f)
                close()
                moveTo(487.0f, 473.0f)
                lineTo(487.0f, 473.0f)
                quadTo(487.0f, 473.0f, 487.0f, 473.0f)
                quadTo(487.0f, 473.0f, 487.0f, 473.0f)
                quadTo(487.0f, 473.0f, 487.0f, 473.0f)
                quadTo(487.0f, 473.0f, 487.0f, 473.0f)
                quadTo(487.0f, 473.0f, 487.0f, 473.0f)
                quadTo(487.0f, 473.0f, 487.0f, 473.0f)
                lineTo(487.0f, 473.0f)
                lineTo(487.0f, 473.0f)
                lineTo(487.0f, 473.0f)
                quadTo(487.0f, 473.0f, 487.0f, 473.0f)
                quadTo(487.0f, 473.0f, 487.0f, 473.0f)
                quadTo(487.0f, 473.0f, 487.0f, 473.0f)
                quadTo(487.0f, 473.0f, 487.0f, 473.0f)
                close()
            }
        }
        .build()
        return speed!!
    }

private var speed: ImageVector? = null
