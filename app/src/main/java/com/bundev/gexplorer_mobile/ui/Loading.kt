package com.bundev.gexplorer_mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bundev.gexplorer_mobile.R

@Composable
fun LoadingCard(text: String) {
    MiddleCard {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(120.dp))
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(id = R.drawable.gexplorer_logo),
                    contentDescription = null
                )
            }
            Text(modifier = Modifier.padding(vertical = 5.dp), text = text)
        }
    }
}

@Composable
fun LoadingBar(text: String) {
    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    StackedTextButton(
        label = text,
        enabled = false
    ) {}

}