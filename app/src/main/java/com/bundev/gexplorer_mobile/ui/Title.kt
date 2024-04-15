package com.bundev.gexplorer_mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.GoToPreviousPage

@Composable
fun TitleBar(
    text: String,
    navController: NavHostController?,
    changePage: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(imageVector = Icons.AutoMirrored.Default.ArrowBack) {
            GoToPreviousPage(navController) { changePage() }
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(8.dp))
            TitleText(text = text)
            if (text.length < 18)
                Spacer(modifier = Modifier.width(56.dp))
        }
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontSize = 32.sp,
        lineHeight = if (text.length > 18) 34.sp else TextUnit.Unspecified,
        fontWeight = FontWeight.Bold
    )
}