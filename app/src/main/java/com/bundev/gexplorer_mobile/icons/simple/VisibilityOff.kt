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

val GexplorerIcons.Simple.VisibilityOff: ImageVector
    get() {
        if (visibilityoff != null) {
            return visibilityoff!!
        }
        visibilityoff = Builder(
            name = "VisibilityOff", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(792.0f, 904.0f)
                lineTo(624.0f, 738.0f)
                quadTo(589.0f, 749.0f, 553.5f, 754.5f)
                quadTo(518.0f, 760.0f, 480.0f, 760.0f)
                quadTo(329.0f, 760.0f, 211.0f, 676.5f)
                quadTo(93.0f, 593.0f, 40.0f, 460.0f)
                quadTo(61.0f, 407.0f, 93.0f, 361.5f)
                quadTo(125.0f, 316.0f, 166.0f, 280.0f)
                lineTo(56.0f, 168.0f)
                lineTo(112.0f, 112.0f)
                lineTo(848.0f, 848.0f)
                lineTo(792.0f, 904.0f)
                close()
                moveTo(480.0f, 640.0f)
                quadTo(491.0f, 640.0f, 500.5f, 639.0f)
                quadTo(510.0f, 638.0f, 521.0f, 635.0f)
                lineTo(305.0f, 419.0f)
                quadTo(302.0f, 430.0f, 301.0f, 439.5f)
                quadTo(300.0f, 449.0f, 300.0f, 460.0f)
                quadTo(300.0f, 535.0f, 352.5f, 587.5f)
                quadTo(405.0f, 640.0f, 480.0f, 640.0f)
                close()
                moveTo(772.0f, 658.0f)
                lineTo(645.0f, 532.0f)
                quadTo(652.0f, 515.0f, 656.0f, 497.5f)
                quadTo(660.0f, 480.0f, 660.0f, 460.0f)
                quadTo(660.0f, 385.0f, 607.5f, 332.5f)
                quadTo(555.0f, 280.0f, 480.0f, 280.0f)
                quadTo(460.0f, 280.0f, 442.5f, 284.0f)
                quadTo(425.0f, 288.0f, 408.0f, 296.0f)
                lineTo(306.0f, 194.0f)
                quadTo(347.0f, 177.0f, 390.0f, 168.5f)
                quadTo(433.0f, 160.0f, 480.0f, 160.0f)
                quadTo(631.0f, 160.0f, 749.0f, 243.5f)
                quadTo(867.0f, 327.0f, 920.0f, 460.0f)
                quadTo(897.0f, 519.0f, 859.5f, 569.5f)
                quadTo(822.0f, 620.0f, 772.0f, 658.0f)
                close()
                moveTo(587.0f, 474.0f)
                lineTo(467.0f, 354.0f)
                quadTo(495.0f, 349.0f, 518.5f, 358.5f)
                quadTo(542.0f, 368.0f, 559.0f, 386.0f)
                quadTo(576.0f, 404.0f, 583.5f, 427.5f)
                quadTo(591.0f, 451.0f, 587.0f, 474.0f)
                close()
            }
        }
            .build()
        return visibilityoff!!
    }

private var visibilityoff: ImageVector? = null
