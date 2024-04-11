package com.bundev.gexplorer_mobile.pages.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.TitleBar

@Composable
fun StatisticsPage(navController: NavHostController? = null) {
    val vm = hiltViewModel<StatisticsViewModel>()

    Column(modifier = Modifier.fillMaxSize()) {
        TitleBar(stringResource(id = R.string.statistics), navController)
        Text(text = "You have walked for some time")
        Text(text = "Get some rest")
        Text(text = "You earned it")
        Text(text = "Or not")
        Text(text = "Pole zajęte w mieście: 1cm^2")
        Text(text = "Śródmieście: 1cm^2")
    }
}