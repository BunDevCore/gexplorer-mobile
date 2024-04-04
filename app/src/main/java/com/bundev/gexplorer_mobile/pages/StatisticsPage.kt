package com.bundev.gexplorer_mobile.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsPage() {
    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Text(text = "You have walked for some time")
        Text(text = "Get some rest")
        Text(text = "You earned it")
        Text(text = "Or not")
        Text(text = "Pole zajęte w mieście: 1cm^2")
        Text(text = "Śródmieście: 1cm^2")
    }
}