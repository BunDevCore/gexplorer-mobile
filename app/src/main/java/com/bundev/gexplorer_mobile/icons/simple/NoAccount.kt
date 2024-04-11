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

val GexplorerIcons.Simple.NoAccount: ImageVector
    get() {
        if (noAccount != null) {
            return noAccount!!
        }
        noAccount = Builder(name = "NoAccount", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(608.0f, 438.0f)
                lineTo(422.0f, 252.0f)
                quadTo(436.0f, 246.0f, 450.5f, 243.0f)
                quadTo(465.0f, 240.0f, 480.0f, 240.0f)
                quadTo(539.0f, 240.0f, 579.5f, 280.5f)
                quadTo(620.0f, 321.0f, 620.0f, 380.0f)
                quadTo(620.0f, 395.0f, 617.0f, 409.5f)
                quadTo(614.0f, 424.0f, 608.0f, 438.0f)
                close()
                moveTo(234.0f, 684.0f)
                quadTo(285.0f, 645.0f, 348.0f, 622.5f)
                quadTo(411.0f, 600.0f, 480.0f, 600.0f)
                quadTo(498.0f, 600.0f, 514.5f, 601.5f)
                quadTo(531.0f, 603.0f, 549.0f, 606.0f)
                lineTo(461.0f, 518.0f)
                quadTo(414.0f, 512.0f, 380.5f, 478.5f)
                quadTo(347.0f, 445.0f, 341.0f, 398.0f)
                lineTo(227.0f, 284.0f)
                quadTo(195.0f, 325.0f, 177.5f, 374.5f)
                quadTo(160.0f, 424.0f, 160.0f, 480.0f)
                quadTo(160.0f, 539.0f, 179.5f, 591.0f)
                quadTo(199.0f, 643.0f, 234.0f, 684.0f)
                close()
                moveTo(732.0f, 676.0f)
                quadTo(764.0f, 635.0f, 782.0f, 585.5f)
                quadTo(800.0f, 536.0f, 800.0f, 480.0f)
                quadTo(800.0f, 347.0f, 706.5f, 253.5f)
                quadTo(613.0f, 160.0f, 480.0f, 160.0f)
                quadTo(424.0f, 160.0f, 374.5f, 178.0f)
                quadTo(325.0f, 196.0f, 284.0f, 228.0f)
                lineTo(732.0f, 676.0f)
                close()
                moveTo(480.0f, 880.0f)
                quadTo(398.0f, 880.0f, 325.0f, 848.5f)
                quadTo(252.0f, 817.0f, 197.5f, 762.5f)
                quadTo(143.0f, 708.0f, 111.5f, 635.0f)
                quadTo(80.0f, 562.0f, 80.0f, 480.0f)
                quadTo(80.0f, 397.0f, 111.5f, 324.5f)
                quadTo(143.0f, 252.0f, 197.5f, 197.5f)
                quadTo(252.0f, 143.0f, 325.0f, 111.5f)
                quadTo(398.0f, 80.0f, 480.0f, 80.0f)
                quadTo(563.0f, 80.0f, 635.5f, 111.5f)
                quadTo(708.0f, 143.0f, 762.5f, 197.5f)
                quadTo(817.0f, 252.0f, 848.5f, 324.5f)
                quadTo(880.0f, 397.0f, 880.0f, 480.0f)
                quadTo(880.0f, 562.0f, 848.5f, 635.0f)
                quadTo(817.0f, 708.0f, 762.5f, 762.5f)
                quadTo(708.0f, 817.0f, 635.5f, 848.5f)
                quadTo(563.0f, 880.0f, 480.0f, 880.0f)
                close()
            }
        }
        .build()
        return noAccount!!
    }

private var noAccount: ImageVector? = null
