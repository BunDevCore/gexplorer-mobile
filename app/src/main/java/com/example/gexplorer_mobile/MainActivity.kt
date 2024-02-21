package com.example.gexplorer_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gexplorer_mobile.ui.theme.GexplorermobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GexplorermobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GexplorerApp()
                }
            }
        }
    }
}

@Composable
fun MainPage(onNavigateToSettingsPage: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Witaj w:", fontSize = 30.sp)
            Spacer(modifier = Modifier.height(15.dp))
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                shadowElevation = 5.dp,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Gexplorer", fontSize = 50.sp, modifier = Modifier.padding(10.dp))
            }
            Text(text = "Theme, language -> default from android")
            Text(text = "achievements")
            Text(text = "(połączenie z API)")


        }
        Row(
            modifier = Modifier
                .background(color = Color(R.color.primary))
                .fillMaxWidth()
                .weight(1f, false)
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Home, contentDescription = "home and map page")
                Text(text = "Home")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = "home and map page")
                Text(text = "Map")
            }
            Button(onClick = { onNavigateToSettingsPage() }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Settings, contentDescription = "settings")
                    Text(text = "Settings")
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.AccountCircle, contentDescription = "account")
                Text(text = "Account")
            }
        }
    }
}

@Composable
fun SettingsPage(onNavigateToMainPage: () -> Unit) {
    Text(text = "Settings page")
    Button(onClick = { onNavigateToMainPage() }) {
        Text(
            text = "Go to main page",
            modifier = Modifier.background(Color(R.color.primary))
        )
    }
}

@Composable
fun GexplorerApp(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainPage(onNavigateToSettingsPage = { navController.navigate("settings")}) }
        composable("settings") { SettingsPage(onNavigateToMainPage = { navController.navigate("main")}) }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
//fun MainPagePreview() {
//    MainPage()
fun GexplorerAppPreview() {
    GexplorerApp()
}
//
//@Preview(showSystemUi = true)
//@Composable
//fun SettingsPagePreview() {
//    SettingsPage()
//}