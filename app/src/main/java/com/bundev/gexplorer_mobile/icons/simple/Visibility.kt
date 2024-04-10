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

val GexplorerIcons.Simple.Visibility: ImageVector
    get() {
        if (visibility != null) {
            return visibility!!
        }
        visibility = Builder(name = "Visibility", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(480.0f, 640.0f)
                quadTo(555.0f, 640.0f, 607.5f, 587.5f)
                quadTo(660.0f, 535.0f, 660.0f, 460.0f)
                quadTo(660.0f, 385.0f, 607.5f, 332.5f)
                quadTo(555.0f, 280.0f, 480.0f, 280.0f)
                quadTo(405.0f, 280.0f, 352.5f, 332.5f)
                quadTo(300.0f, 385.0f, 300.0f, 460.0f)
                quadTo(300.0f, 535.0f, 352.5f, 587.5f)
                quadTo(405.0f, 640.0f, 480.0f, 640.0f)
                close()
                moveTo(480.0f, 568.0f)
                quadTo(435.0f, 568.0f, 403.5f, 536.5f)
                quadTo(372.0f, 505.0f, 372.0f, 460.0f)
                quadTo(372.0f, 415.0f, 403.5f, 383.5f)
                quadTo(435.0f, 352.0f, 480.0f, 352.0f)
                quadTo(525.0f, 352.0f, 556.5f, 383.5f)
                quadTo(588.0f, 415.0f, 588.0f, 460.0f)
                quadTo(588.0f, 505.0f, 556.5f, 536.5f)
                quadTo(525.0f, 568.0f, 480.0f, 568.0f)
                close()
                moveTo(480.0f, 760.0f)
                quadTo(334.0f, 760.0f, 214.0f, 678.5f)
                quadTo(94.0f, 597.0f, 40.0f, 460.0f)
                quadTo(94.0f, 323.0f, 214.0f, 241.5f)
                quadTo(334.0f, 160.0f, 480.0f, 160.0f)
                quadTo(626.0f, 160.0f, 746.0f, 241.5f)
                quadTo(866.0f, 323.0f, 920.0f, 460.0f)
                quadTo(866.0f, 597.0f, 746.0f, 678.5f)
                quadTo(626.0f, 760.0f, 480.0f, 760.0f)
                close()
            }
        }
        .build()
        return visibility!!
    }

private var visibility: ImageVector? = null
