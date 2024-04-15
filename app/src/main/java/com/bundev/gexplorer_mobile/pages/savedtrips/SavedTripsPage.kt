package com.bundev.gexplorer_mobile.pages.savedtrips

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.TitleBar

@Composable
fun SavedTripsPage(navController: NavHostController, changePage: () -> Unit) {
    Column(Modifier.fillMaxSize()){
        TitleBar(text = stringResource(id = R.string.saved_trips), navController = navController) {
            changePage()
        }
        Text("Tu są zapisane podróże")
    }
}