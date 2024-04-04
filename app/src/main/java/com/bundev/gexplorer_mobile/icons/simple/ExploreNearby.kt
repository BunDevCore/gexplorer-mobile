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

val GexplorerIcons.Simple.ExploreNearby: ImageVector
    get() {
        if (explorenearby != null) {
            return explorenearby!!
        }
        explorenearby = Builder(name = "ExploreNearby", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
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
                moveTo(480.0f, 700.0f)
                quadTo(525.0f, 655.0f, 560.0f, 607.0f)
                quadTo(590.0f, 566.0f, 615.0f, 517.0f)
                quadTo(640.0f, 468.0f, 640.0f, 420.0f)
                quadTo(640.0f, 354.0f, 593.0f, 307.0f)
                quadTo(546.0f, 260.0f, 480.0f, 260.0f)
                quadTo(414.0f, 260.0f, 367.0f, 307.0f)
                quadTo(320.0f, 354.0f, 320.0f, 420.0f)
                quadTo(320.0f, 468.0f, 345.0f, 517.0f)
                quadTo(370.0f, 566.0f, 400.0f, 607.0f)
                quadTo(435.0f, 655.0f, 480.0f, 700.0f)
                close()
                moveTo(480.0f, 480.0f)
                quadTo(455.0f, 480.0f, 437.5f, 462.5f)
                quadTo(420.0f, 445.0f, 420.0f, 420.0f)
                quadTo(420.0f, 395.0f, 437.5f, 377.5f)
                quadTo(455.0f, 360.0f, 480.0f, 360.0f)
                quadTo(505.0f, 360.0f, 522.5f, 377.5f)
                quadTo(540.0f, 395.0f, 540.0f, 420.0f)
                quadTo(540.0f, 445.0f, 522.5f, 462.5f)
                quadTo(505.0f, 480.0f, 480.0f, 480.0f)
                close()
            }
        }
        .build()
        return explorenearby!!
    }

private var explorenearby: ImageVector? = null
