package com.bundev.gexplorer_mobile.pages

import android.icu.util.MeasureUnit
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.CenteredTextButton
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.RadioList
import com.bundev.gexplorer_mobile.StackedTextButton
import com.bundev.gexplorer_mobile.TitleBar
import com.bundev.gexplorer_mobile.changeLanguage
import com.bundev.gexplorer_mobile.classes.Screen
import com.bundev.gexplorer_mobile.distanceUnit
import com.bundev.gexplorer_mobile.funi

@Composable
fun SettingsPage(navController: NavHostController? = null, changePage: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBar(stringResource(id = R.string.settings), navController, Screen.Account) {
            changePage()
        }
        val context = LocalContext.current

        // Change language dialog
        val languageOptions = listOf(R.string.en, R.string.pl, R.string.de)
        val languageMap = mapOf("en" to R.string.en, "pl" to R.string.pl, "de" to R.string.de)
        val (selectedLanguage, onLanguageSelected) = remember {
            mutableIntStateOf(
                languageMap[AppCompatDelegate.getApplicationLocales().toLanguageTags()]
                    ?: R.string.en
            )
        }
        val openLanguageDialog = remember {
            mutableStateOf(false)
        }
        when {
            openLanguageDialog.value -> {
                RadioDialog(
                    onDismissRequest = { openLanguageDialog.value = false },
                    options = languageOptions,
                    selectedOption = selectedLanguage,
                    onOptionSelected = onLanguageSelected
                )
                changeLanguage(context, selectedLanguage)
            }
        }
        StackedTextButton(
            label = stringResource(id = R.string.language),
            subLabel = stringResource(id = R.string.language_chosen)
        ) { openLanguageDialog.value = true }

        //Change theme dialog
        val themeOptions =
            listOf(R.string.theme_light, R.string.theme_dark, R.string.theme_black_amoled)
        val (selectedTheme, onThemeSelected) = remember {
            mutableIntStateOf(themeOptions[1]) //TODO: On first load set phones defaults
        }
        val openThemeDialog = remember {
            mutableStateOf(false)
        }
        when {
            openThemeDialog.value -> {
                RadioDialog(
                    onDismissRequest = { openThemeDialog.value = false },
                    options = themeOptions,
                    selectedOption = selectedTheme,
                    onOptionSelected = onThemeSelected
                )
            }
        }
        StackedTextButton(
            label = stringResource(id = R.string.theme),
            subLabel = stringResource(id = selectedTheme)
        )
        { openThemeDialog.value = true }

        //Change measuring system
        val distanceUnitsOptions = listOf(R.string.metric, R.string.imperial)
        val distanceUnitsMap =
            mapOf(MeasureUnit.METER to R.string.metric, MeasureUnit.FOOT to R.string.imperial)
        val (selectedDistanceUnits, onDistanceUnitsSelected) = remember {
            mutableIntStateOf(
                distanceUnitsMap[distanceUnit]
                    ?: R.string.metric
            )
        }
        val openDistanceUnitsDialog = remember {
            mutableStateOf(false)
        }
        when {
            openDistanceUnitsDialog.value -> {
                RadioDialog(
                    onDismissRequest = { openDistanceUnitsDialog.value = false },
                    options = distanceUnitsOptions,
                    selectedOption = selectedDistanceUnits,
                    onOptionSelected = onDistanceUnitsSelected
                )
                distanceUnit =
                    distanceUnitsMap.filterValues { it == selectedDistanceUnits }.keys.first()
            }
        }
        StackedTextButton(
            label = stringResource(id = R.string.distance_units),
            subLabel = stringResource(id = selectedDistanceUnits)
        ) { openDistanceUnitsDialog.value = true }

        HorizontalDivider(thickness = 1.dp)
        //Open About us dialog
        val openAboutUsDialog = remember {
            mutableStateOf(false)
        }
        when {
            openAboutUsDialog.value -> {
                AboutUsDialog { openAboutUsDialog.value = false }
            }
        }
        StackedTextButton(
            label = stringResource(id = R.string.about_us)
        ) { openAboutUsDialog.value = true }
        if (funi.getValue() != 0L) {
            Text(
                text = "val:${
                    funi.getValue()
                } time left:${funi.getTimeRemaining()}"
            )
        }
    }
}

@Composable
private fun AboutUsDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CenteredTextButton(
                    label = stringResource(id = R.string.bundev),
                    modifier = Modifier.padding(bottom = 10.dp),
                    fontSizeLabel = 26.sp,
                    fontWeightLabel = FontWeight.Bold
                ) {
                    funi.remove()
                }
                val topPadding = 10.dp
                val timeOut = 10L
                CenteredTextButton(
                    label = "wiKapo",
                    subLabel = stringResource(R.string.mobile_app),
                    modifier = Modifier
                        .padding(top = topPadding)
                ) { funi.append(value = 0, timeOutInSeconds = timeOut) }
                CenteredTextButton(
                    label = "Lempek",
                    subLabel = stringResource(R.string.web_app),
                ) { funi.append(value = 1, timeOutInSeconds = timeOut) }
                CenteredTextButton(
                    label = "Fen",
                    subLabel = stringResource(R.string.backend)
                ) { funi.append(value = 2, timeOutInSeconds = timeOut) }
                CenteredTextButton(
                    label = "random",
                    subLabel = stringResource(R.string.creative_department)
                ) { funi.append(value = 3, timeOutInSeconds = timeOut) }
                CenteredTextButton(
                    label = "SR",
                    subLabel = stringResource(R.string.creative_department)
                ) { funi.append(value = 4, timeOutInSeconds = timeOut) }
            }
        }
    }
}

@Composable
private fun RadioDialog(
    onDismissRequest: () -> Unit,
    options: List<Int>,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height((56 * (options.size) + 68 * 2).dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.language),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(all = 20.dp)
            )
            RadioList(
                options = options,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.End
            ) {
                TextButton(
                    onClick = { onDismissRequest() }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "pl", name = "pl")
@Composable
private fun SettingsPagePreview() {
    SettingsPage {}
}