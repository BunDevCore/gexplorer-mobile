package com.bundev.gexplorer_mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bundev.gexplorer_mobile.R

@Composable
fun MiddleCard(modifier: Modifier = Modifier, display: @Composable () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                display()
            }
        }
    }
}

@Composable
fun ErrorCard(error: Throwable?, modifier: Modifier = Modifier) {
    MiddleCard(modifier) {
        Text(text = stringResource(id = R.string.api_error))
        Text(text = error.toString())
    }
}