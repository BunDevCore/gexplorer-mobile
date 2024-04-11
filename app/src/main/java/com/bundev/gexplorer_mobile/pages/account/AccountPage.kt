package com.bundev.gexplorer_mobile.pages.account

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.IconAndTextButton
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.classes.Screen
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.funi
import com.bundev.gexplorer_mobile.icons.outlined.Analytics
import com.bundev.gexplorer_mobile.icons.outlined.SocialLeaderboard
import com.bundev.gexplorer_mobile.icons.outlined.Trophy
import com.bundev.gexplorer_mobile.navigateTo

@Composable
fun AccountPage(navController: NavHostController? = null, changePage: () -> Unit) {
    val vm = hiltViewModel<AccountViewModel>()
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) { vm.fetchSelf() }

    if (funi.getValue() == 2024L) {
        //TODO give achievement "The first icon"
    }

    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Log in/out button
        if (state is ApiResource.Success) {
            val user = state.data!!
            Log.d("user is", "$user")
            Text(text = "Witaj ${user.username}")
            IconAndTextButton(
                label = stringResource(id = R.string.log_out),
                imageVector = Icons.Filled.Person,
            ) { vm.logout(); vm.fetchSelf() }
        } else {
            IconAndTextButton(
                label = stringResource(id = R.string.log_in),
                imageVector = Icons.Outlined.Person,
            ) {
                navigateTo(navController, Screen.LogIn.route) { changePage() }
            }
        }

        //Settings button
        IconAndTextButton(
            label = stringResource(id = R.string.settings),
            imageVector = Screen.Settings.iconOutline,
        ) { navigateTo(navController, Screen.Settings.route) { changePage() } }

        //Achievements button
        IconAndTextButton(
            label = stringResource(id = R.string.achievements),
            imageVector = if (funi.getValue() != 2024L) GexplorerIcons.Outlined.Trophy else GexplorerIcons.Outlined.SocialLeaderboard
        ) { navigateTo(navController, Screen.Achievements.route) { changePage() } }

        //Statistics button
        IconAndTextButton(
            label = stringResource(id = R.string.statistics),
            imageVector = GexplorerIcons.Outlined.Analytics
        ) {
            navigateTo(navController, Screen.Statistics.route) { changePage() }
        }
        Text(text = "połączenie z API")
        if (state is ApiResource.Loading) {
            Text("loading.....")
        }
        if (state is ApiResource.Success) {
            Text(state.data?.overallAreaAmount.toString())
        }
        if (funi.getValue() != 0L) {
            Text(
                text = "val:${
                    funi.getValue()
                } time left:${funi.getTimeRemaining()}"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountPagePreview() {
    AccountPage {}
}