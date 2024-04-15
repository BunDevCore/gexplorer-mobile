package com.bundev.gexplorer_mobile.pages.account

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.ui.ConfirmDialog
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.ui.IconAndTextButton
import com.bundev.gexplorer_mobile.ui.LoadingBar
import com.bundev.gexplorer_mobile.ui.MiddleCard
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.ui.StackedTextButton
import com.bundev.gexplorer_mobile.classes.Screen
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.funi
import com.bundev.gexplorer_mobile.icons.outlined.Analytics
import com.bundev.gexplorer_mobile.icons.outlined.SocialLeaderboard
import com.bundev.gexplorer_mobile.icons.outlined.Trophy
import com.bundev.gexplorer_mobile.navigateTo

@Composable
fun AccountPage(navController: NavHostController? = null, changePage: () -> Unit) {
    //TODO check why login throws back to mapPage
    Log.d(
        "NAV CONTROLLER",
        "curr: ${navController?.currentBackStackEntry?.destination?.route.toString()}"
    )
    Log.d(
        "NAV CONTROLLER",
        "prev: ${navController?.previousBackStackEntry?.destination?.route.toString()}"
    )
    val vm = hiltViewModel<AccountViewModel>()
    val state by vm.state.collectAsState()
    val configuration = LocalConfiguration.current

    LaunchedEffect(Unit) { vm.fetchSelf() }

    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Log in/out button
        when (state) {
            is ApiResource.Success -> {
                val user = state.data!!
                Log.d("user is", "$user")
                Card(modifier = Modifier.width((configuration.screenWidthDp / 2).dp)) {
                    if (configuration.orientation == ORIENTATION_PORTRAIT) {
                        Column(
                            modifier = Modifier.padding(8.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center

                        ) {
                            Text(text = stringResource(id = R.string.welcome))
                            Text(
                                text = user.username,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(id = R.string.welcome))
                            Text(
                                text = user.username,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            is ApiResource.Loading -> LoadingBar(text = stringResource(id = R.string.loading_api))
            else -> IconAndTextButton(
                label = stringResource(id = R.string.log_in),
                imageVector = Icons.Outlined.Person,
            ) {
                navigateTo(navController, Screen.Login.route) { changePage() }
            }
        }
//TODO show icon when intenet is off or there is no connection to database
        //Achievements button
        IconAndTextButton(
            label = stringResource(id = R.string.achievements),
            imageVector = if (funi.getValue() != 2024L) GexplorerIcons.Outlined.Trophy else GexplorerIcons.Outlined.SocialLeaderboard
        ) { navigateTo(navController, Screen.Achievements.route) { changePage() } }

        //Leaderboard button
        IconAndTextButton(
            label = stringResource(id = R.string.leaderboard),
            imageVector = GexplorerIcons.Outlined.SocialLeaderboard
        ) { navigateTo(navController, Screen.Leaderboard.route) { changePage() } }

        //Statistics button
        if (state is ApiResource.Success)
            IconAndTextButton(
                label = stringResource(id = R.string.statistics),
                imageVector = GexplorerIcons.Outlined.Analytics
            ) {
                navigateTo(navController, Screen.Statistics.route) { changePage() }
            }

        //Settings button
        IconAndTextButton(
            label = stringResource(id = R.string.settings),
            imageVector = Screen.Settings.iconOutline,
        ) { navigateTo(navController, Screen.Settings.route) { changePage() } }

        if (funi.getValue() != 0L) {
            MiddleCard {
                Text(
                    text = "val:${
                        funi.getValue()
                    } time left:${funi.getTimeRemaining()}"
                )
            }
        }
        val logoutModifier = if (configuration.orientation == ORIENTATION_PORTRAIT)
            Modifier.fillMaxSize()
        else Modifier.fillMaxSize().safeDrawingPadding()
        Column(modifier = logoutModifier, verticalArrangement = Arrangement.Bottom) {
            val openLogoutDialog = rememberSaveable { mutableStateOf(false) }
            if (state is ApiResource.Success) {
                StackedTextButton(
                    label = stringResource(id = R.string.log_out),
                    textColor = Color.Gray
                ) { openLogoutDialog.value = true }
            }
            when {
                openLogoutDialog.value -> {
                    ConfirmDialog(
                        onDismissRequest = { openLogoutDialog.value = false },
                        confirmRequest = {
                            openLogoutDialog.value = false; vm.logout(); vm.fetchSelf()
                        },
                        text = stringResource(id = R.string.confirm_log_out)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountPagePreview() {
    AccountPage {}
}