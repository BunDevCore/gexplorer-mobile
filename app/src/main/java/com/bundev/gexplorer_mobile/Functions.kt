package com.bundev.gexplorer_mobile

import android.content.Context
import android.content.pm.PackageManager
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.text.format.DateUtils
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import java.text.DateFormat
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun StackedTextButton(
    label: String,
    modifier: Modifier = Modifier,
    subLabel: String = "",
    enabled: Boolean = true,
    fontSizeLabel: TextUnit = 18.sp,
    fontSizeSubLabel: TextUnit = (fontSizeLabel.value - 6).sp,
    onClick: () -> Unit,
) {
    ButtonGenerator(
        label = label,
        subLabel = subLabel,
        modifier = modifier,
        enabled = enabled,
        fontSizeLabel = fontSizeLabel,
        fontSizeSubLabel = fontSizeSubLabel
    ) {
        onClick()
    }
}

@Composable
fun IconAndTextButton(
    label: String,
    modifier: Modifier = Modifier,
    subLabel: String = "",
    imageVector: ImageVector,
    imageDescription: String = "",
    fontSizeLabel: TextUnit = 18.sp,
    fontSizeSubLabel: TextUnit = (fontSizeLabel.value - 6).sp,
    onClick: () -> Unit,
) {
    ButtonGenerator(
        label = label,
        subLabel = subLabel,
        modifier = modifier,
        fontSizeLabel = fontSizeLabel,
        fontSizeSubLabel = fontSizeSubLabel,
        imageVector = imageVector,
        imageDescription = imageDescription
    ) {
        onClick()
    }
}

@Composable
fun CenteredTextButton(
    label: String,
    modifier: Modifier = Modifier,
    subLabel: String = "",
    fontSizeLabel: TextUnit = 20.sp,
    fontSizeSubLabel: TextUnit = (fontSizeLabel.value - 6).sp,
    fontWeightLabel: FontWeight = FontWeight.Normal,
    onClick: () -> Unit,
) {
    val textColor = LocalContentColor.current
    val textStyle = LocalTextStyle.current
    ButtonGenerator(
        label = label,
        subLabel = subLabel,
        modifier = modifier,
        fontSizeLabel = fontSizeLabel,
        fontSizeSubLabel = fontSizeSubLabel,
        textColor = textColor,
        textStyle = textStyle,
        fontWeightLabel = fontWeightLabel,
        shape = ButtonDefaults.textShape,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        onClick()
    }
}

@Composable
private fun ButtonGenerator(
    label: String,
    subLabel: String,
    modifier: Modifier,
    fontSizeLabel: TextUnit,
    fontSizeSubLabel: TextUnit,
    enabled: Boolean = true,
    imageVector: ImageVector? = null,
    imageDescription: String? = null,
    textStyle: TextStyle? = null,
    textColor: Color = Color.Unspecified,
    fontWeightLabel: FontWeight = FontWeight.Normal,
    shape: Shape = RoundedCornerShape(0.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        shape = shape,
        enabled = enabled,
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
            horizontalAlignment = horizontalAlignment
        ) {
            Text(
                fontSize = fontSizeLabel,
                fontStyle = textStyle?.fontStyle,
                fontWeight = if (fontWeightLabel != FontWeight.Normal) fontWeightLabel else textStyle?.fontWeight,
                color = textColor,
                text = label
            )
            if (subLabel.isNotEmpty())
                Text(
                    fontSize = fontSizeSubLabel,
                    fontStyle = textStyle?.fontStyle,
                    fontWeight = textStyle?.fontWeight,
                    color = textColor,
                    text = subLabel
                )
        }
    }
}

fun navigateTo(
    navController: NavHostController? = null,
    route: String,
    resetBackStack: Boolean = false,
    changePage: () -> Unit,
) {
    if (selectedTabSave != route) {
        selectedTabSave = route
        changePage()
        if (!resetBackStack)
            navController?.navigate(route)
        else {
            navController?.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = false
                restoreState = true
            }
        }
    }
}

@Composable
fun MiddleCard(display: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card {
            Column(modifier = Modifier.padding(10.dp)) {
                display()
            }
        }
    }
}

@Composable
fun LoadingCard(text: String) {
    MiddleCard {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(120.dp))
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(id = R.drawable.gexplorer_logo),
                    contentDescription = null
                )
            }
            Text(modifier = Modifier.padding(vertical = 5.dp), text = text)
        }
    }
}

@Composable
fun LoadingBar(text: String) {
    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    StackedTextButton(
        label = text,
        enabled = false
    ) {}

}

@Composable
fun ActionButton(imageVector: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    SmallFloatingActionButton(
        onClick = { onClick() },
        modifier = modifier
            .width(40.dp)
            .height(40.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = imageVector,
            contentDescription = null
        )
    }
}

fun GoToPreviousPage(navController: NavHostController?, changePage: () -> Unit) {
    selectedTabSave = navController?.previousBackStackEntry?.destination?.route.toString()
    changePage()
    navController?.popBackStack()
}

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
            TitleText(text = text)
            Spacer(modifier = Modifier.width(56.dp))
        }
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    )
}

fun formatDate(instant: Instant, format: Int = DateFormat.DEFAULT): String {
    return DateFormat.getDateInstance(format).format(instant.toEpochMilliseconds())
}

fun formatDate(date: LocalDate, format: Int = DateFormat.DEFAULT): String {
    return DateFormat.getDateInstance(format)
        .format(date.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds())
}

fun formatTime(instant: Instant, format: Int = DateFormat.DEFAULT): String {
    return DateFormat.getTimeInstance(format).format(instant.toEpochMilliseconds())
}

fun formatDuration(duration: Duration): String {
    return DateUtils.formatElapsedTime(duration.inWholeSeconds)
}

@Composable
fun formatDistance(distanceInMeters: Double, measureUnit: MeasureUnit): String {
    val measure = when (measureUnit) {
        MeasureUnit.METER -> {
            if (distanceInMeters < 1000.0)
                Measure(distanceInMeters.roundToInt(), MeasureUnit.METER)
            else
                Measure((distanceInMeters / 1000).roundTo(3), MeasureUnit.KILOMETER)
        }

        MeasureUnit.FOOT -> {
            if (distanceInMeters * 3.2808399 < 5280) {
                Measure((distanceInMeters * 3.2808399).roundToInt(), MeasureUnit.FOOT)
            } else {
                Measure((distanceInMeters * 0.000621371192).roundTo(3), MeasureUnit.MILE)
            }
        }

        else -> Measure(-1, MeasureUnit.METER)
    }

    return "${measure.number} ${stringResource(id = getShortUnitName(measure.unit))}"
}

@Composable
fun formatSpeed(distanceInMeters: Double, duration: Duration, measureUnit: MeasureUnit): String {
    val avgSpeedInKph = (distanceInMeters / 1000.0) / (duration.inWholeSeconds / 3600.0)
    val measure = when (measureUnit) {
        MeasureUnit.METER -> {
            Measure(avgSpeedInKph.roundTo(3), MeasureUnit.KILOMETER_PER_HOUR)
        }

        MeasureUnit.FOOT -> {
            Measure((avgSpeedInKph * 0.621371192).roundTo(3), MeasureUnit.MILE_PER_HOUR)
        }

        else -> Measure(-1, MeasureUnit.KILOMETER_PER_HOUR)
    }

    return "${measure.number} ${stringResource(id = getShortUnitName(measure.unit))}"
}

@Composable
fun formatPace(duration: Duration, distanceInMeters: Double, measureUnit: MeasureUnit): String {
    val avgPace: Duration
    val paceUnit: MeasureUnit
    when (measureUnit) {
        MeasureUnit.METER -> {
            avgPace =
                ((duration.inWholeSeconds / 60) / distanceInMeters * 1000).toDuration(DurationUnit.MINUTES)
            paceUnit = MeasureUnit.KILOMETER
        }

        MeasureUnit.FOOT -> {
            avgPace =
                ((duration.inWholeSeconds / 60) / (distanceInMeters * 0.000621371192)).toDuration(
                    DurationUnit.MINUTES
                )
            paceUnit = MeasureUnit.MILE
        }

        else -> {
            avgPace = (-1).toDuration(DurationUnit.MINUTES)
            paceUnit = MeasureUnit.KILOMETER
        }
    }
    return "${formatDuration(avgPace)}/${stringResource(id = getShortUnitName(paceUnit))}"
}

fun getShortUnitName(unit: MeasureUnit): Int {
    return when (unit) {
        MeasureUnit.METER -> R.string.unit_meter
        MeasureUnit.KILOMETER -> R.string.unit_kilometer
        MeasureUnit.FOOT -> R.string.unit_foot
        MeasureUnit.MILE -> R.string.unit_mile
        MeasureUnit.KILOMETER_PER_HOUR -> R.string.unit_kilometer_per_hour
        MeasureUnit.MILE_PER_HOUR -> R.string.unit_mile_per_hour
        else -> R.string.unit_not_found
    }
}

fun changeLanguage(
    context: Context,
    languageStringResource: Int,
) {
    AppCompatDelegate.setApplicationLocales(
        LocaleListCompat.forLanguageTags(
            context.resources.getResourceEntryName(languageStringResource)
        )
    )
}

@Composable
fun RadioList(
    options: List<Int>,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit,
) {
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
}

fun Double.roundTo(n: Int): Double {
    return round(this * 10f.pow(n)) / 10f.pow(n)
}

@Composable
fun RequestLocationPermission(
    requestCount: Int = 0,
    onPermissionDenied: () -> Unit,
    onPermissionReady: () -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissionsMap ->
        val granted = permissionsMap.values.all { it }
        if (granted) {
            onPermissionReady()
        } else {
            onPermissionDenied()
        }
    }
    LaunchedEffect(requestCount) {
        context.checkAndRequestLocationPermission(
            locationPermissions,
            launcher,
            onPermissionReady
        )
    }
}

private fun Context.checkAndRequestLocationPermission(
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    onPermissionReady: () -> Unit,
) {
    if (permissions.all {
            ContextCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        onPermissionReady()
    } else {
        launcher.launch(permissions)
    }
}

fun Context.checkLocationPermission() : Boolean {
    val permissions = locationPermissions
    return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
}

val locationPermissions = arrayOf(
    android.Manifest.permission.ACCESS_FINE_LOCATION,
    android.Manifest.permission.ACCESS_COARSE_LOCATION
)
