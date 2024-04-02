package com.bundev.gexplorer_mobile.pages

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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.os.LocaleListCompat
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.funi
import com.bundev.gexplorer_mobile.systemOfUnits

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
        DialogButton(
            label = stringResource(id = R.string.language),
            subLabel = stringResource(id = R.string.language_chosen),
            onClick = { openLanguageDialog.value = true })

        //Change theme dialog
        val themeOptions =
            listOf(R.string.light_theme, R.string.dark_theme, R.string.black_amoled_theme)
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
        DialogButton(
            label = stringResource(id = R.string.theme),
            subLabel = stringResource(id = selectedTheme),
            onClick = { openThemeDialog.value = true })

        //Change measuring system
        val systemOfUnitsOptions = listOf(R.string.metric, R.string.imperial)
        val systemOfUnitsMap = mapOf("metric" to R.string.metric, "imperial" to R.string.imperial)
        val (selectedSystemOfUnits, onSystemOfUnitsSelected) = remember {
            mutableIntStateOf(
                systemOfUnitsMap[systemOfUnits]
                    ?: R.string.metric
            )
        }
        val openSystemOfUnitsDialog = remember {
            mutableStateOf(false)
        }
        when {
            openSystemOfUnitsDialog.value -> {
                RadioDialog(
                    onDismissRequest = { openSystemOfUnitsDialog.value = false },
                    options = systemOfUnitsOptions,
                    selectedOption = selectedSystemOfUnits,
                    onOptionSelected = onSystemOfUnitsSelected
                )
                systemOfUnits = context.resources.getResourceEntryName(selectedSystemOfUnits)
            }
        }
        DialogButton(
            label = stringResource(id = R.string.system_of_units),
            subLabel = stringResource(id = selectedSystemOfUnits),
            onClick = { openSystemOfUnitsDialog.value = true })

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
        DialogButton(
            label = stringResource(id = R.string.about_us),
            onClick = { openAboutUsDialog.value = true })
        if (funi.getValue() != 0L) {
            Text(
                text = "val:${
                    funi.getValue().toString()
                } time left:${funi.getTimeRemaining()}"
            )
        }
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
fun AboutUsDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.bundev)
                )
                val fontSize = 20.sp
                val fontSizeSecond = 14.sp
                val topPadding = 10.dp

                val timeOut = 10L
                FuniButton(
                    fontSize = fontSize,
                    fontSizeSecond = fontSizeSecond,
                    name = "wiKapo",
                    responsibleFor = R.string.mobile_app,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = topPadding),
                    onClick = { funi.append(value = 20, timeOutInSeconds = timeOut) }
                )
                FuniButton(
                    fontSize = fontSize,
                    fontSizeSecond = fontSizeSecond,
                    name = "Lempek",
                    responsibleFor = R.string.web_app,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { funi.append(value = 1, timeOutInSeconds = timeOut) }
                )
                FuniButton(
                    fontSize = fontSize,
                    fontSizeSecond = fontSizeSecond,
                    name = "Fen",
                    responsibleFor = R.string.backend,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { funi.append(value = 3, timeOutInSeconds = timeOut) }
                )
                FuniButton(
                    fontSize = fontSize,
                    fontSizeSecond = fontSizeSecond,
                    name = "random",
                    responsibleFor = R.string.creative_department,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { funi.append(value = 4, timeOutInSeconds = timeOut) }
                )
                FuniButton(
                    fontSize = fontSize,
                    fontSizeSecond = fontSizeSecond,
                    name = "SR",
                    responsibleFor = R.string.creative_department,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { funi.append(value = 5, timeOutInSeconds = timeOut) }
                )
            }
        }
    }
}

@Composable
fun FuniButton(
    fontSize: TextUnit,
    fontSizeSecond: TextUnit,
    name: String,
    responsibleFor: Int,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val textColor = LocalContentColor.current
    val textStyle = LocalTextStyle.current
    TextButton(
        modifier = modifier,
        onClick = { onClick() }//,
//        colors = ButtonDefaults.textButtonColors(Color.Unspecified)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                fontSize = fontSize,
                fontStyle = textStyle.fontStyle,
                fontWeight = textStyle.fontWeight,
                color = textColor,
                text = name
            )
            Text(
                fontSize = fontSizeSecond,
                fontStyle = textStyle.fontStyle,
                fontWeight = textStyle.fontWeight,
                color = textColor,
                text = stringResource(id = responsibleFor)//"aplikacja mobilna"
            )
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

@Preview(showBackground = true, locale = "pl", name = "pl")
@Composable
fun SettingsPagePreview() {
    SettingsPage()
}