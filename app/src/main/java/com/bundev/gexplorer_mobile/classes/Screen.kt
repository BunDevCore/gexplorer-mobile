package com.bundev.gexplorer_mobile.classes

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.icons.filled.Analytics
import com.bundev.gexplorer_mobile.icons.filled.Bookmark
import com.bundev.gexplorer_mobile.icons.filled.Map
import com.bundev.gexplorer_mobile.icons.filled.SocialLeaderboard
import com.bundev.gexplorer_mobile.icons.filled.Trophy
import com.bundev.gexplorer_mobile.icons.outlined.Analytics
import com.bundev.gexplorer_mobile.icons.outlined.Bookmark
import com.bundev.gexplorer_mobile.icons.outlined.Map
import com.bundev.gexplorer_mobile.icons.outlined.SocialLeaderboard
import com.bundev.gexplorer_mobile.icons.outlined.Trophy
import com.bundev.gexplorer_mobile.icons.simple.ExploreNearby
import com.bundev.gexplorer_mobile.icons.simple.Walk

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val iconFilled: ImageVector,
    val iconOutline: ImageVector = iconFilled,
) {
    data object Map :
        Screen(
            "map",
            R.string.map,
            GexplorerIcons.Filled.Map,
            GexplorerIcons.Outlined.Map
        )

    data object Trips :
        Screen(
            "trips",
            R.string.trips,
            GexplorerIcons.Simple.Walk
        )

    data object SavedTrips :
        Screen(
            "savedTrips",
            R.string.saved_trips,
            GexplorerIcons.Filled.Bookmark,
            GexplorerIcons.Outlined.Bookmark
        )

    data object Achievements :
        Screen(
            "achievements",
            R.string.achievements,
            GexplorerIcons.Filled.Trophy,
            GexplorerIcons.Outlined.Trophy
        )

    data object Account :
        Screen(
            "account",
            R.string.account,
            Icons.Filled.Person,
            Icons.Outlined.Person
        )

    data object Settings :
        Screen(
            "settings",
            R.string.settings,
            Icons.Filled.Settings,
            Icons.Outlined.Settings
        )

    data object TripDetail :
        Screen(
            "trip/{tripId}",
            R.string.trip,
            GexplorerIcons.Simple.Walk
        )

    data object Places :
        Screen(
            "places",
            R.string.places,
            GexplorerIcons.Simple.ExploreNearby
        )

    data object Statistics :
        Screen(
            "statistics",
            R.string.statistics,
            GexplorerIcons.Filled.Analytics,
            GexplorerIcons.Outlined.Analytics
        )

    data object Login :
        Screen(
            "login",
            R.string.log_in,
            Icons.Filled.Person,
            Icons.Outlined.Person
        )

    data object Leaderboard :
        Screen(
            "leaderboard",
            R.string.leaderboard,
            GexplorerIcons.Filled.SocialLeaderboard,
            GexplorerIcons.Outlined.SocialLeaderboard
        )
}