package com.bundev.gexplorer_mobile.pages.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.GoToPreviousPage
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.icons.filled.Error
import com.bundev.gexplorer_mobile.icons.simple.Visibility
import com.bundev.gexplorer_mobile.icons.simple.VisibilityOff
import com.bundev.gexplorer_mobile.ui.TitleBar

@Composable
fun LoginPage(navController: NavHostController? = null, changePage: () -> Unit) {
    val vm = hiltViewModel<LoginViewModel>()
    val state by vm.state.collectAsState()
    var register by rememberSaveable { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        TitleBar(
            stringResource(id = if (register) R.string.register else R.string.log_in),
            navController
        ) { changePage() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (register) RegisterCard(vm, state.register, navController, changePage) {
                register = false
            }
            else LoginCard(vm, state.login, navController, changePage) { register = true }
        }
    }
}

@Composable
fun LoginCard(
    vm: LoginViewModel? = null,
    state: ApiResource<String>? = null,
    navController: NavHostController? = null,
    changePage: () -> Unit,
    changeCard: () -> Unit,
) {
    var userName by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var loginAttempted by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    if (state is ApiResource.Success)
        GoToPreviousPage(navController) { changePage() }

    Card {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ErrorHandlingTextField(
                value = userName,
                onValueChange = { userName = it },
                labelResource = R.string.login_name,
                errorResource = { if (userName.isEmpty()) R.string.login_name_empty else -1 },
                loginAttempted = loginAttempted
            )
            ErrorHandlingTextField(
                value = password,
                onValueChange = { password = it },
                labelResource = R.string.login_pass,
                errorResource = { if (password.isEmpty()) R.string.login_pass_empty else -1 },
                hideText = true,
                loginAttempted = loginAttempted
            )
            BottomLoginButtons(
                leftButtonLabelResource = R.string.register,
                rightButtonLabelResource = R.string.log_in,
                changeCard = {
                    loginAttempted = false
                    userName = ""
                    password = ""
                    changeCard()
                }
            ) {
                loginAttempted = true
                if (userName == "" || password == "") return@BottomLoginButtons
                vm?.login(userName, password)
            }
            if (state is ApiResource.Success) {
                Toast.makeText(context, "Logowanie zakończono pomyślnie", Toast.LENGTH_SHORT)
                    .show()
                changeCard()
            }
        }
    }
}

@Composable
fun RegisterCard(
    vm: LoginViewModel? = null,
    state: ApiResource<String>? = null,
    navController: NavHostController? = null,
    changePage: () -> Unit,
    changeCard: () -> Unit,
) {
    var userName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }
    var loginAttempted by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    if (state is ApiResource.Success)
        GoToPreviousPage(navController) { changePage() }

    Card {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ErrorHandlingTextField(
                value = userName,
                onValueChange = { userName = it },
                labelResource = R.string.login_name,
                errorResource = { if (userName.isEmpty()) R.string.login_name_empty else -1 },
                loginAttempted = loginAttempted
            )
            ErrorHandlingTextField(
                value = email,
                onValueChange = { email = it },
                labelResource = R.string.register_email,
                errorResource = { if (email.isEmpty()) R.string.register_email_empty else -1 },
                loginAttempted = loginAttempted
            )
            ErrorHandlingTextField(
                value = password,
                onValueChange = { password = it },
                labelResource = R.string.login_pass,
                errorResource = { if (password.isEmpty()) R.string.login_pass_empty else -1 },
                hideText = true,
                loginAttempted = loginAttempted
            )
            ErrorHandlingTextField(
                value = confirm,
                onValueChange = { confirm = it },
                labelResource = R.string.register_confirm,
                errorResource = { if (confirm.isEmpty()) R.string.register_pass_confirm else if (confirm != password) R.string.register_pass_no_match else -1 },
                hideText = true,
                loginAttempted = loginAttempted
            )
            BottomLoginButtons(
                leftButtonLabelResource = R.string.log_in,
                rightButtonLabelResource = R.string.register,
                changeCard = {
                    loginAttempted = false
                    userName = ""
                    email = ""
                    password = ""
                    confirm = ""
                    changeCard()
                }
            ) {
                loginAttempted = true
                if (userName == "" || email == "" || password == "" || password != confirm) return@BottomLoginButtons
                vm?.register(userName, email, password)
            }
            if (state is ApiResource.Success) {
                Toast.makeText(context, "Rejestracja zakończona pomyślnie", Toast.LENGTH_SHORT)
                    .show()
                changeCard()
            }
        }
    }
}

@Composable
fun ErrorHandlingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelResource: Int,
    errorResource: () -> Int,
    loginAttempted: Boolean,
    hideText: Boolean = false,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    //TODO https://blog.canopas.com/keyboard-handling-in-jetpack-compose-all-you-need-to-know-3e6fddd30d9a
//    val bringIntoViewRequester = rememberSaveable { BringIntoViewRequester() }

    Box {
        if (hideText)
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(stringResource(id = labelResource)) },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) GexplorerIcons.Simple.Visibility
                    else GexplorerIcons.Simple.VisibilityOff

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                },
                supportingText = { SupportingText(loginAttempted) { errorResource() } }
            )
        else OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(stringResource(id = labelResource)) },
            singleLine = true,
            supportingText = { SupportingText(loginAttempted) { errorResource() } }
        )
        if (loginAttempted && errorResource() != -1)
            Icon(
                modifier = Modifier
                    .padding(top = 68.dp)
                    .size(16.dp),
                imageVector = GexplorerIcons.Filled.Error,
                contentDescription = null,
                tint = Color.Red
            )
    }
}

@Composable
fun SupportingText(loginTried: Boolean, errorResource: () -> Int) {
    if (loginTried && errorResource() != -1)
        Text(
            stringResource(id = errorResource()),
            modifier = Modifier.padding(start = 5.dp),
            color = Color.Red
        )
}

@Composable
fun BottomLoginButtons(
    leftButtonLabelResource: Int,
    rightButtonLabelResource: Int,
    changeCard: () -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            onClick = { changeCard() }
        ) { Text(stringResource(id = leftButtonLabelResource)) }
        Button(
            onClick = { onClick() }
        ) { Text(stringResource(id = rightButtonLabelResource)) }
    }
}

@Preview
@Composable
fun LoginCardPreview() {
    LoginCard(changePage = {}) {}
}

@Preview
@Composable
fun RegisterCardPreview() {
    RegisterCard(changePage = {}) {}
}