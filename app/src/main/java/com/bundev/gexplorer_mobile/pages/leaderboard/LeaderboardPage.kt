package com.bundev.gexplorer_mobile.pages.leaderboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.distanceUnit
import com.bundev.gexplorer_mobile.formatArea
import com.bundev.gexplorer_mobile.ui.ErrorCard
import com.bundev.gexplorer_mobile.ui.LoadingCard
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
            is ApiResource.Error -> ErrorCard(error = state.userDto.error)
            is ApiResource.Loading -> LoadingCard(text = stringResource(id = R.string.loading_api))
            is ApiResource.Success -> {
                when (state.leaderboardDto) {
                    is ApiResource.Error -> ErrorCard(error = state.leaderboardDto.error)
                    is ApiResource.Loading -> LoadingCard(text = stringResource(id = R.string.loading))
                    is ApiResource.Success -> Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        Card(modifier = Modifier.padding(5.dp)) {
                            var entriesLeft = state.leaderboardDto.data!!.entries.size
                            state.leaderboardDto.data!!.entries.forEach { entry ->
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(entry.key.toString(),
                                        modifier = Modifier.width(36.dp),
                                        fontSize = 24.sp)
                                    Text(
                                        entry.value.user.username,
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        formatArea(
                                            areaInCubicMeters = entry.value.user.overallAreaAmount,
                                            measureUnit = distanceUnit
                                        ),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.End
                                    )
                                }
                                if (entriesLeft > 1) {
                                    entriesLeft--
                                    HorizontalDivider(
                                        modifier = Modifier.fillMaxWidth(),
                                        color = Color.Gray,
                                        thickness = 1.dp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}