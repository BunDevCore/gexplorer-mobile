package com.bundev.gexplorer_mobile.pages.leaderboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.ui.LoadingCard
import com.bundev.gexplorer_mobile.ui.MiddleCard
import com.bundev.gexplorer_mobile.ui.TitleBar

@Composable
fun LeaderboardPage(navController: NavHostController, changePage: () -> Unit) {

    val vm = hiltViewModel<LeaderboardViewModel>()
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.fetchSelf()
        vm.getOverallLeaderboard()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TitleBar(text = stringResource(id = R.string.leaderboard), navController = navController) {
            changePage()
        }
        when (state.userDto) {
            is ApiResource.Success -> {
                when (state.leaderboardDto) {
                    is ApiResource.Success -> {
                        MiddleCard {
                            Text(state.leaderboardDto.data!!.entries.toString())
                        }
                    }

                    is ApiResource.Loading -> {
                        LoadingCard(text = stringResource(id = R.string.loading))
                    }

                    is ApiResource.Error -> {
                        MiddleCard {
                            Text("Hejo")
                        }
                    }
                }
            }

            is ApiResource.Loading -> {
                LoadingCard(text = stringResource(id = R.string.loading_api))
            }

            is ApiResource.Error -> {
                MiddleCard {
                    Text("nope")
                }
            }
        }
    }
}