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

val GexplorerIcons.Outlined.Bookmark: ImageVector
    get() {
        if (bookmark != null) {
            return bookmark!!
        }
        bookmark = Builder(name = "BookmarkOutlined", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 960.0f, viewportHeight = 960.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(200.0f, 840.0f)
                lineTo(200.0f, 200.0f)
                quadTo(200.0f, 167.0f, 223.5f, 143.5f)
                quadTo(247.0f, 120.0f, 280.0f, 120.0f)
                lineTo(680.0f, 120.0f)
                quadTo(713.0f, 120.0f, 736.5f, 143.5f)
                quadTo(760.0f, 167.0f, 760.0f, 200.0f)
                lineTo(760.0f, 840.0f)
                lineTo(480.0f, 720.0f)
                lineTo(200.0f, 840.0f)
                close()
                moveTo(280.0f, 718.0f)
                lineTo(480.0f, 632.0f)
                lineTo(680.0f, 718.0f)
                lineTo(680.0f, 200.0f)
                quadTo(680.0f, 200.0f, 680.0f, 200.0f)
                quadTo(680.0f, 200.0f, 680.0f, 200.0f)
                lineTo(280.0f, 200.0f)
                quadTo(280.0f, 200.0f, 280.0f, 200.0f)
                quadTo(280.0f, 200.0f, 280.0f, 200.0f)
                lineTo(280.0f, 718.0f)
                close()
                moveTo(280.0f, 200.0f)
                lineTo(280.0f, 200.0f)
                quadTo(280.0f, 200.0f, 280.0f, 200.0f)
                quadTo(280.0f, 200.0f, 280.0f, 200.0f)
                lineTo(680.0f, 200.0f)
                quadTo(680.0f, 200.0f, 680.0f, 200.0f)
                quadTo(680.0f, 200.0f, 680.0f, 200.0f)
                lineTo(680.0f, 200.0f)
                lineTo(480.0f, 200.0f)
                lineTo(280.0f, 200.0f)
                close()
            }
        }
        .build()
        return bookmark!!
    }

private var bookmark: ImageVector? = null
