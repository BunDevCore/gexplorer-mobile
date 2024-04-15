package com.bundev.gexplorer_mobile.pages.leaderboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.ui.TitleBar

@Composable
fun LeaderboardPage(navController: NavHostController, changePage: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TitleBar(text = stringResource(id = R.string.leaderboard), navController = navController) {
            changePage()
        }
    }
}