package com.bundev.gexplorer_mobile.pages

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.classes.Onboard

val onboardPagesList = listOf(
    Onboard(
        "Welcome to gexplorer",
        "Yes, hello.\nYou can change the settings later",
        imageRes = R.drawable.gexplorer_logo
    ),
    Onboard(
        "Settings here",
        "Set here everything you need",
        imageVector = Icons.Filled.Settings
    ),
    Onboard(
        "Gib permission",
        "pls we need de yummy data",
        imageVector = Icons.Filled.LocationOn
    )
)

@Composable
fun OnBoardImageView(
    modifier: Modifier = Modifier,
    imageRes: Int,
    imageVector: ImageVector
) {
    val NO_IMAGE_VECTOR = ImageVector.Builder(
        "NO IMAGE VECTOR", 0.dp, 0.dp, 0f, 0f
    )
        .build()

    Box(modifier = modifier) {
        if (imageRes != -1)
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 5.dp),
                contentScale = ContentScale.Fit
            )
        if (imageVector != NO_IMAGE_VECTOR)
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )
            }
        Box(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.BottomCenter)
            .graphicsLayer {
                // Apply alpha to create the fading effect
                alpha = 0.6f
            }
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        Pair(0.75f, Color.Transparent),
                        Pair(1f, NavigationBarDefaults.containerColor)
                    )
                )
            ))
    }
}

@Composable
fun OnBoardDetails(
    modifier: Modifier = Modifier, currentPage: Onboard
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = currentPage.title,
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = currentPage.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun OnBoardNavButton(
    modifier: Modifier = Modifier,
    currentPage: Int,
    noOfPages: Int,
    onNextClicked: () -> Unit,
    onCompletion: () -> Unit
) {
    Button(
        onClick = {
            if (currentPage < noOfPages - 1) {
                onNextClicked()
            } else {
                onCompletion()
            }
        }, modifier = modifier
    ) {
        Text(text = if (currentPage < noOfPages - 1) "Next" else "Get Started")
    }
}

@Composable
fun TabSelector(onboards: List<Onboard>, currentPage: Int, onTabSelected: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        onboards.forEachIndexed { index, _ ->
            Surface(
                color = NavigationBarDefaults.containerColor,
                tonalElevation = NavigationBarDefaults.Elevation
            ) {
                Tab(selected = index == currentPage,
                    onClick = { onTabSelected(index) },
                    modifier = Modifier.height(40.dp),
                    content = {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = if (index == currentPage) MaterialTheme.colorScheme.primary
                                    else Color.LightGray,
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    })
            }
        }
    }
}

@Composable
fun OnboardScreen(onCompletion: () -> Unit) {
    val onboardPages = onboardPagesList
    val currentPage = remember { mutableIntStateOf(0) }

    val orientation = LocalConfiguration.current.orientation

    if (orientation == ORIENTATION_PORTRAIT)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            Spacer(modifier = Modifier.padding(top = 10.dp))
            OnBoardImageView(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                imageRes = onboardPages[currentPage.intValue].imageRes,
                imageVector = onboardPages[currentPage.intValue].imageVector
            )
            OnBoardDetails(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                currentPage = onboardPages[currentPage.intValue]
            )
            OnBoardNavButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                currentPage = currentPage.intValue,
                noOfPages = onboardPages.size,
                onNextClicked = { currentPage.intValue++ }
            ) {
                onCompletion()
            }
            Spacer(Modifier.padding(bottom = 10.dp))
            TabSelector(
                onboards = onboardPages,
                currentPage = currentPage.intValue
            ) { index ->
                currentPage.intValue = index
            }
        }
    else Row(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
        OnBoardImageView(
            modifier = Modifier
                .width((LocalConfiguration.current.screenWidthDp / 3).dp)
                .fillMaxHeight(),
            imageRes = onboardPages[currentPage.intValue].imageRes,
            imageVector = onboardPages[currentPage.intValue].imageVector
        )
        Column {
            OnBoardDetails(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                currentPage = onboardPages[currentPage.intValue]
            )
            OnBoardNavButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                currentPage = currentPage.intValue,
                noOfPages = onboardPages.size,
                onNextClicked = { currentPage.intValue++ }
            ) {
                onCompletion()
            }
            Spacer(Modifier.padding(bottom = 10.dp))
            TabSelector(
                onboards = onboardPages,
                currentPage = currentPage.intValue
            ) { index ->
                currentPage.intValue = index
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun OnboardPagePreview() {
    OnboardScreen {}
}