package com.bundev.gexplorer_mobile.pages

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.icu.util.MeasureUnit
import androidx.appcompat.app.AppCompatDelegate
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.RadioList
import com.bundev.gexplorer_mobile.StackedTextButton
import com.bundev.gexplorer_mobile.changeLanguage
import com.bundev.gexplorer_mobile.classes.OnboardScreen
import com.bundev.gexplorer_mobile.distanceUnit
import com.bundev.gexplorer_mobile.icons.filled.Palette
import com.bundev.gexplorer_mobile.icons.filled.Straighten
import com.bundev.gexplorer_mobile.icons.simple.Language

val onboardScreenPagesLists = listOf(
    OnboardScreen(
        preTitleResource = R.string.onboard_welcome,
        titleResource = R.string.app_name,
        descriptionResource = R.string.onboard_app_description,
        imageResource = R.drawable.gexplorer_logo
    ),
    OnboardScreen(
        titleResource = R.string.settings,
        descriptionResource = R.string.onboard_description,
        imageVector = Icons.Filled.Settings
    ),
    OnboardScreen(
        R.string.onboard_language,
        userInteraction = {
            val context = LocalContext.current
            val languageOptions = listOf(R.string.en, R.string.pl, R.string.de)
            val languageMap = mapOf("en" to R.string.en, "pl" to R.string.pl, "de" to R.string.de)
            val (selectedLanguage, onLanguageSelected) = remember {
                mutableIntStateOf(
                    languageMap[AppCompatDelegate.getApplicationLocales().toLanguageTags()]
                        ?: R.string.en
                )
            }
            RadioList(languageOptions, selectedLanguage, onLanguageSelected)
            changeLanguage(context, selectedLanguage)
        },
        imageVector = GexplorerIcons.Simple.Language
    ),
    OnboardScreen(
        R.string.onboard_theme,
        userInteraction = {
            //TODO handle theme change
            val themeOptions =
                listOf(R.string.theme_light, R.string.theme_dark, R.string.theme_black_amoled)
            val (selectedTheme, onThemeSelected) = remember {
                mutableIntStateOf(themeOptions[1]) //TODO: On first load set phones defaults
            }
            RadioList(themeOptions, selectedTheme, onThemeSelected)
        },
        imageVector = GexplorerIcons.Filled.Palette
    ),
    OnboardScreen(
        R.string.onboard_distance_unit,
        userInteraction = {
            val distanceUnitsOptions = listOf(R.string.metric, R.string.imperial)
            val distanceUnitsMap =
                mapOf(MeasureUnit.METER to R.string.metric, MeasureUnit.FOOT to R.string.imperial)
            val (selectedDistanceUnits, onDistanceUnitsSelected) = remember {
                mutableIntStateOf(
                    distanceUnitsMap[distanceUnit]
                        ?: R.string.metric
                )
            }
            RadioList(distanceUnitsOptions, selectedDistanceUnits, onDistanceUnitsSelected)
            distanceUnit =
                distanceUnitsMap.filterValues { it == selectedDistanceUnits }.keys.first()
        },
        imageVector = GexplorerIcons.Filled.Straighten
    ),
    OnboardScreen(
        R.string.onboard_permissions,
        R.string.location_needed,
        userInteraction = {
            StackedTextButton(label = stringResource(id = R.string.onboard_permissions_button)) {}
        },
        imageVector = Icons.Filled.LocationOn
    )
)

@Composable
private fun OnboardImageView(
    modifier: Modifier = Modifier,
    imageRes: Int,
    imageVector: ImageVector,
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
                        Pair(0.7f, Color.Transparent),
                        Pair(1f, NavigationBarDefaults.containerColor)
                    )
                )
            ))
    }
}

@Composable
private fun OnboardDetails(
    modifier: Modifier = Modifier, currentPage: OnboardScreen, onUserInteraction: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        if (currentPage.preTitleResource != -1)
            Text(
                text = stringResource(id = currentPage.preTitleResource),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        Text(
            text = stringResource(id = currentPage.titleResource),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        if (currentPage.descriptionResource != -1)
            Text(
                text = stringResource(id = currentPage.descriptionResource),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            )
        if (currentPage.userInteraction != {}) {
            Spacer(modifier = Modifier.padding(top = 5.dp))
            currentPage.userInteraction(onUserInteraction())
        }
    }
}

@Composable
private fun OnboardNavButton(
    modifier: Modifier = Modifier,
    currentPage: Int,
    noOfPages: Int,
    onNextClicked: () -> Unit,
    onCompletion: () -> Unit,
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
        Text(
            text =
            if (currentPage < noOfPages - 1) stringResource(id = R.string.next)
            else stringResource(id = R.string.get_started)
        )
    }
}

@Composable
private fun TabSelector(
    onboards: List<OnboardScreen>,
    currentPage: Int,
    onTabSelected: (Int) -> Unit,
) {
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
fun OnboardScreen(defaultPage: Int = 0, onCompletion: () -> Unit) {
    val onboardPages = onboardScreenPagesLists
    val currentPage = rememberSaveable { mutableIntStateOf(defaultPage) }
    val orientation = LocalConfiguration.current.orientation

    if (orientation == ORIENTATION_PORTRAIT)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            Spacer(modifier = Modifier.padding(top = 10.dp))
            OnboardImageView(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                imageRes = onboardPages[currentPage.intValue].imageResource,
                imageVector = onboardPages[currentPage.intValue].imageVector
            )
            OnboardDetails(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                currentPage = onboardPages[currentPage.intValue]
            ) {
//                currentPage.intValue++
            }
            OnboardNavButton(
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
    else Row(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        OnboardImageView(
            modifier = Modifier
                .width(((LocalConfiguration.current.screenWidthDp / 5) * 2).dp)
                .fillMaxHeight(),
            imageRes = onboardPages[currentPage.intValue].imageResource,
            imageVector = onboardPages[currentPage.intValue].imageVector
        )
        Column {
            OnboardDetails(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                currentPage = onboardPages[currentPage.intValue]
            ) {
//                currentPage.intValue++
            }
            OnboardNavButton(
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

@Preview(showBackground = true, locale = "pl")
@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun OnboardPagePreview() {
    OnboardScreen(3) {}
}