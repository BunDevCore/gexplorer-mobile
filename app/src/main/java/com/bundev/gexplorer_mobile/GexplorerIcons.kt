package com.bundev.gexplorer_mobile

import androidx.compose.ui.graphics.vector.ImageVector
import com.bundev.gexplorer_mobile.icons.filled.Analytics
import com.bundev.gexplorer_mobile.icons.filled.Bookmark
import com.bundev.gexplorer_mobile.icons.filled.Location
import com.bundev.gexplorer_mobile.icons.filled.Map
import com.bundev.gexplorer_mobile.icons.filled.SocialLeaderboard
import com.bundev.gexplorer_mobile.icons.filled.Trophy
import com.bundev.gexplorer_mobile.icons.outlined.Analytics
import com.bundev.gexplorer_mobile.icons.outlined.Bookmark
import com.bundev.gexplorer_mobile.icons.outlined.Location
import com.bundev.gexplorer_mobile.icons.outlined.Map
import com.bundev.gexplorer_mobile.icons.outlined.SocialLeaderboard
import com.bundev.gexplorer_mobile.icons.outlined.Trophy
import com.bundev.gexplorer_mobile.icons.simple.AvgPace
import com.bundev.gexplorer_mobile.icons.simple.Explore
import com.bundev.gexplorer_mobile.icons.simple.ExploreNearby
import com.bundev.gexplorer_mobile.icons.simple.Path
import com.bundev.gexplorer_mobile.icons.simple.Walk
import kotlin.collections.List as __KtList

@Suppress("unused")
object GexplorerIcons {
    object Simple {
        private var _simple: __KtList<ImageVector>? = null
        val simple: __KtList<ImageVector>
            get() {
                if (_simple != null) {
                    return _simple!!
                }
                _simple = listOf(
                    Simple.Walk,
                    Simple.Explore,
                    Simple.AvgPace,
                    Simple.Path,
                    Simple.ExploreNearby
                )
                return _simple!!
            }
    }

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
                    Filled.Trophy,
                    Filled.Bookmark,
                    Filled.Analytics
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
                    Outlined.Location,
                    Outlined.Trophy,
                    Outlined.Bookmark,
                    Outlined.Analytics
                )
                return _outlined!!
            }
    }
}