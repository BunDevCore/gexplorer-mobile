package com.bundev.gexplorer_mobile

import androidx.compose.ui.graphics.vector.ImageVector
import com.bundev.gexplorer_mobile.icons.filled.Explore
import com.bundev.gexplorer_mobile.icons.filled.Location
import com.bundev.gexplorer_mobile.icons.filled.Map
import com.bundev.gexplorer_mobile.icons.filled.SocialLeaderboard
import com.bundev.gexplorer_mobile.icons.outlined.Location
import com.bundev.gexplorer_mobile.icons.outlined.Map
import com.bundev.gexplorer_mobile.icons.outlined.SocialLeaderboard
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
                    Filled.Map,
                    Filled.Location,
                    Filled.Explore
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
                    Outlined.Map,
                    Outlined.Location
                )
                return _outlined!!
            }
    }
}