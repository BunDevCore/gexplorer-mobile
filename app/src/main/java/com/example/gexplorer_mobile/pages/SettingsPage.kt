package com.example.gexplorer_mobile.pages

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.os.LocaleListCompat
import com.example.gexplorer_mobile.R

@Composable
fun SettingsPage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.settings),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(all = 10.dp)
        )
        val context = LocalContext.current

        // Change language dialog
        val languageOptions = listOf(R.string.en, R.string.pl, R.string.de)
        val languageIndex = when (AppCompatDelegate.getApplicationLocales().toLanguageTags()) {
            "en" -> 0
            "pl" -> 1
            "de" -> 2
            else -> 0
        }
        val (selectedLanguage, onLanguageSelected) = remember {
            mutableIntStateOf(
                languageOptions[languageIndex]
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
        DialogButton(
            label = stringResource(id = R.string.language),
            subLabel = stringResource(id = R.string.language_chosen),
            onClick = { openLanguageDialog.value = true })

        //Change theme dialog
        val themeOptions =
            listOf(R.string.light_theme, R.string.dark_theme, R.string.black_amoled_theme)
        val (selectedTheme, onThemeSelected) = remember {
            mutableIntStateOf(themeOptions[1])
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
        DialogButton(
            label = stringResource(id = R.string.theme),
            subLabel = stringResource(id = selectedTheme),
            onClick = { openThemeDialog.value = true })
        HorizontalDivider(thickness = 1.dp)

//        //Change measuring system
//        val measurementOptions = listOf("Metric", "Imperial")
//        val openMeasurementDialog = remember {
//            mutableStateOf(false)
//        }
//        DialogButton(
//            label = "System",
//            onClick = { openMeasurementDialog.value = true })
//        when ->RadioDialog(onDismissRequest = { openMeasurementDialog.value = false }, options = measurementOptions, selectedOption = ) {
//
//        }

        //Open About us dialog
        val openAboutUsDialog = remember {
            mutableStateOf(false)
        }
        when {
            openAboutUsDialog.value -> {
                Dialog(onDismissRequest = { openAboutUsDialog.value = false }) {
                    Card {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier.padding(bottom = 10.dp),
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                text = "BunDev team"
                            )
                            val fontSize = 20.sp
                            val fontSizeSecond = 14.sp
                            val topPadding = 10.dp
                            Text(
                                modifier = Modifier.padding(top = topPadding),
                                fontSize = fontSize,
                                text = "wiKapo"
                            )
                            Text(fontSize = fontSizeSecond, text = "aplikacja mobilna")
                            Text(
                                modifier = Modifier.padding(top = topPadding),
                                fontSize = fontSize,
                                text = "Lempek"
                            )
                            Text(fontSize = fontSizeSecond, text = "aplikacja webowa")
                            Text(
                                modifier = Modifier.padding(top = topPadding),
                                fontSize = fontSize,
                                text = "Fen"
                            )
                            Text(fontSize = fontSizeSecond, text = "backend")
                            Text(
                                modifier = Modifier.padding(top = topPadding),
                                fontSize = fontSize,
                                text = "random"
                            )
                            Text(
                                fontSize = fontSizeSecond,
                                text = "dokumentacja, design, prezentacja"
                            )
                            Text(
                                modifier = Modifier.padding(top = topPadding),
                                fontSize = fontSize,
                                text = "SR"
                            )
                            Text(
                                fontSize = fontSizeSecond,
                                text = "dokumentacja, design, prezentacja"
                            )
                        }
                    }
                }
            }
        }
        DialogButton(
            label = stringResource(id = R.string.about_us),
            onClick = { openAboutUsDialog.value = true })
    }
}

@Composable
fun DialogButton(
    label: String,
    subLabel: String = "",
    onClick: () -> Unit
) {
    TextButton(
        shape = RoundedCornerShape(0.dp),
        onClick = { onClick() }
    ) {
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

@Composable
fun RadioDialog(
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
            shape = RoundedCornerShape(16.dp),

            ) {
            Text(
                text = stringResource(id = R.string.language),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(all = 20.dp)
            )
            Column(modifier = Modifier.selectableGroup()) {
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .selectable(
                                selected = (option == selectedOption),
                                onClick = {
                                    onOptionSelected(option)
                                },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = null
                        )
                        Text(
                            text = stringResource(id = option),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
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

fun changeLanguage(
    context: Context,
    languageStringResource: Int
) {
    AppCompatDelegate.setApplicationLocales(
        LocaleListCompat.forLanguageTags(
            context.resources.getResourceEntryName(languageStringResource)
        )
    )
}