package com.bundev.gexplorer_mobile.pages.account

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.bundev.gexplorer_mobile.Screen
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.funi
import com.bundev.gexplorer_mobile.icons.outlined.Trophy
import com.bundev.gexplorer_mobile.navigateTo

@Composable
fun AccountPage(navController: NavHostController? = null, changePage: () -> Unit) {
    val username = "fen."
    val vm = hiltViewModel<AccountViewModel>()
    val state by vm.state.collectAsState()
    LaunchedEffect(vm) {
        vm.fetchUser(username)
    }
    val user = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user.value.isNotEmpty()) {
            Text(text = "Witaj ${user.value}")
            IconAndTextButton(
                label = stringResource(id = R.string.log_out),
                imageVector = Icons.Filled.Person,
            ) { user.value = "" }
        } else {
            IconAndTextButton(
                label = stringResource(id = R.string.log_in),
                imageVector = Icons.Outlined.Person,
            ) { user.value = "UŻYTKOWNIK" }
        }
        IconAndTextButton(
            label = stringResource(id = R.string.settings),
            imageVector = Screen.Settings.iconOutline,
        ) { navigateTo(navController, Screen.Settings.route) { changePage() } }
        IconAndTextButton(
            label = stringResource(id = R.string.achievements),
            imageVector = GexplorerIcons.Outlined.Trophy
        ) { navigateTo(navController, Screen.Achievements.route) { changePage() } }
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