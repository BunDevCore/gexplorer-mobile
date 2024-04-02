package com.bundev.gexplorer_mobile.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.Screen
import com.bundev.gexplorer_mobile.funi
import com.bundev.gexplorer_mobile.selectedTabSave
import me.thefen.gexplorerapi.dtos.DistrictDto

@Composable
fun AccountPage(navController: NavHostController? = null, goToSettings: () -> Unit) {
    val districts = rememberSaveable {
        mutableStateOf<List<DistrictDto>>(listOf())
    }
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AccountButton(
            label = stringResource(id = R.string.settings),
            imageVector = Screen.Settings.iconOutline,
        ) {
            if (selectedTabSave != Screen.Settings.route) {
                selectedTabSave = Screen.Settings.route
                goToSettings()
                navController?.navigate(Screen.Settings.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = false
                    restoreState = true
                }
            }
        }
        Text(text = "Witaj w:", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            shadowElevation = 5.dp,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Gexplorer", fontSize = 50.sp, modifier = Modifier.padding(10.dp))
        }
        Text(text = "ta strona będzie dla użytkownika, ale to później")
        Text(text = "achievements")
        Text(text = "(połączenie z API)")
        Text("hooooaa ${districts.value.size}")
        if (funi.hasValue()) {
            Text(
                text = "val:${
                    funi.getValue()
                } time left:${funi.getTimeRemaining()}"
            )
        }
    }
}

@Composable
fun AccountButton(
    label: String,
    subLabel: String = "",
    imageVector: ImageVector? = null,
    imageDescription: String = "",
    onClick: () -> Unit
) {
    TextButton(
        shape = RoundedCornerShape(0.dp),
        onClick = { onClick() }
    ) {
        if (imageVector is ImageVector)
            Icon(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .padding(vertical = 5.dp),
                imageVector = imageVector,
                contentDescription = imageDescription
            )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                fontSize = 18.sp,
                text = label
            )
            if (subLabel.isNotEmpty()) {
                Text(
                    fontSize = 12.sp,
                    text = subLabel
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountPagePreview() {
    AccountPage {}
}