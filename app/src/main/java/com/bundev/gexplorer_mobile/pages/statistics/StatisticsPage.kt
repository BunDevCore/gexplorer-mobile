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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StatisticsPage(navController: NavHostController? = null, changePage: () -> Unit) {
    val vm = hiltViewModel<StatisticsViewModel>()
    val state by vm.state.collectAsState()
    LaunchedEffect(Unit) { vm.fetchStats() }
    Column(modifier = Modifier.fillMaxSize()) {
        TitleBar(stringResource(id = R.string.statistics), navController) { changePage() }
        when (state) {
            is ApiResource.Error -> ErrorCard(error = state.error)
            is ApiResource.Loading -> LoadingCard(text = stringResource(id = R.string.loading))
            is ApiResource.Success -> {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
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
                            Text(text = formatArea(state.data!!.user.overallAreaAmount, distanceUnit))
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
//                    if(state is ApiResource.Success) TODO show user ranking
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 10.dp)
//                            .padding(horizontal = 10.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(10.dp),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Text(
//                                stringResource(id = R.string.place_in_leaderboard)
//                            )
//                            Text(text = state.data!!.user.tripAmount.toString())
//                        }
//                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        var districtsLeft = state.data!!.districtEntries.size
                        state.data!!.districtEntries.sortedByDescending { it.percentage }
                            .forEach { entry ->
                                Card(
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
                                            text = if (entry.exploredArea != 0.0)
                                                formatArea(
                                                    areaInCubicMeters = entry.exploredArea,
                                                    measureUnit = distanceUnit
                                                )
                                            else AnnotatedString("0m²"),
                                            textAlign = TextAlign.End
                                        )
                                        Text(
                                            text = if (entry.percentage != 0.0)
                                                formatPercentage("%.04f".format(entry.percentage * 100))
                                            else AnnotatedString("0%"),
                                            textAlign = TextAlign.End
                                        )
                                    }
                                    if (districtsLeft > 1) {
                                        districtsLeft--
                                        HorizontalDivider(
                                            modifier = Modifier.fillMaxWidth(),
                                            color = Color.Gray,
                                            thickness = 1.dp
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
fun formatPercentage(text: String): AnnotatedString {
    val stringSections = listOf(text.substring(0, 4), text.substring(4))
    return buildAnnotatedString {
        append(stringSections[0])
        withStyle(style = SpanStyle(fontSize = 12.sp)) {
            append(stringSections[1])
        }
        append("%")
    }
}