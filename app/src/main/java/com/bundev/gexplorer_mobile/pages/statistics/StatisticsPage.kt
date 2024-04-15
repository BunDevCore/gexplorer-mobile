package com.bundev.gexplorer_mobile.pages.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.ui.LoadingCard
import com.bundev.gexplorer_mobile.ui.MiddleCard
import com.bundev.gexplorer_mobile.ui.TitleBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StatisticsPage(navController: NavHostController? = null, changePage: () -> Unit) {
    val vm = hiltViewModel<StatisticsViewModel>()
    val state by vm.state.collectAsState()
    LaunchedEffect(Unit) { vm.fetchStats() }
//    Całkowite pole (metry i procenty)
//    Długość i ilość podróży
//    Pole poszczególnych dzielnic (metry i procenty)
    Column(modifier = Modifier.fillMaxSize()) {
        TitleBar(stringResource(id = R.string.statistics), navController) { changePage() }
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            when (state) {
                is ApiResource.Error -> {
                    MiddleCard {
                        Text(text = "BRUH")
                    }
                }

                is ApiResource.Loading -> {
                    LoadingCard(text = stringResource(id = R.string.loading))
                }

                is ApiResource.Success -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .padding(horizontal = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                stringResource(id = R.string.total_area)
                            )
                            Text(text = "${"%.0f".format(state.data!!.user.overallAreaAmount)}m²")
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .padding(horizontal = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                stringResource(id = R.string.trip_amount)
                            )
                            Text(text = state.data!!.user.tripAmount.toString())
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .padding(horizontal = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                stringResource(id = R.string.total_trip_length)
                            )
                            Text(text = "${"%.02f".format(state.data!!.user.totalTripLength)}m")
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        state.data!!.districtEntries.sortedByDescending { it.percentage }
                            .forEach { entry ->
                                ListItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 5.dp)
                                        .padding(top = 5.dp),
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(0.5f),
                                            text = entry.name
                                        )
                                        Text(
                                            text = "${"%.0f".format(entry.exploredArea)}m²",
                                            textAlign = TextAlign.End
                                        )
                                        Text(
                                            text = "${"%.04f".format(entry.percentage * 100)}%",
                                            textAlign = TextAlign.End
                                        )
                                    }
                                }
                            }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }
    }
}