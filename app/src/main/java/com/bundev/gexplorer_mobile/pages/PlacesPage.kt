package com.bundev.gexplorer_mobile.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bundev.gexplorer_mobile.LoadingCard
import com.bundev.gexplorer_mobile.R

@Composable
fun PlacesPage(){
    Column(modifier = Modifier.fillMaxSize()){
        Text(text = "States: (saved/not) (visited/not)")
        LoadingCard(text = stringResource(id = R.string.loading))
    }
}