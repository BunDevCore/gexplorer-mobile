package com.bundev.gexplorer_mobile.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.MiddleCard
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.TitleBar
import com.bundev.gexplorer_mobile.classes.Screen

@Composable
fun LogInPage(navController: NavHostController? = null, changePage: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TitleBar(stringResource(id = R.string.log_in), navController, Screen.Account) {
            changePage()
        }
        MiddleCard { Text(text = stringResource(id = R.string.log_in)) }
    }
}