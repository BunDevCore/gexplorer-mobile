package com.example.gexplorer_mobile

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.gexplorer_mobile.icons.filled.Map
import com.example.gexplorer_mobile.icons.outlined.Map
import com.example.gexplorer_mobile.icons.filled.SocialLeaderboard
import com.example.gexplorer_mobile.icons.outlined.SocialLeaderboard
import kotlin.collections.List as __KtList

@Suppress("unused")
object GexplorerIcons {
    object Filled {
        private var _filled: __KtList<ImageVector>? = null
        val filled: __KtList<ImageVector>
            get() {
                if (_filled != null) {
                    return _filled!!
                }
                _filled = listOf(
                    Filled.SocialLeaderboard,
                    Filled.Map
                )
                return _filled!!
            }
    }
    object Outlined {
        private var _outlined: __KtList<ImageVector>? = null
        val outlined: __KtList<ImageVector>
            get() {
                if (_outlined != null) {
                    return _outlined!!
                }
                _outlined = listOf(
                    Outlined.SocialLeaderboard,
                    Outlined.Map
                )
                return _outlined!!
            }
    }
}