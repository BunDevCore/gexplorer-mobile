package com.example.gexplorer_mobile.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gexplorer_mobile.classes.JustAVariable

@Composable
fun ScoresPage(systemOfUnits: JustAVariable) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "System jednostek! -> ${systemOfUnits.value}")
        Text(text = "Tu będzie tablica wyników")
        Text(text = "Powodzenia Fen")

    }
}