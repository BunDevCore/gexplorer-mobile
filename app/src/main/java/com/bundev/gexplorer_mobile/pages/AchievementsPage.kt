package com.bundev.gexplorer_mobile.pages

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.TitleBar
import com.bundev.gexplorer_mobile.classes.Achievement
import com.bundev.gexplorer_mobile.formatDate
import com.bundev.gexplorer_mobile.formatTime
import com.bundev.gexplorer_mobile.icons.filled.Map
import com.bundev.gexplorer_mobile.icons.simple.Walk
import com.bundev.gexplorer_mobile.ui.GroupingList
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.DateFormat
import kotlin.math.roundToInt

private val achievementsGot = listOf(
    Achievement(
        name = "Moje pierwsze osiągnięcie",
        description = "I jego opis",
        timeAchieved = Clock.System.now()
    ),
    Achievement(
        name = "short",
        timeAchieved = Clock.System.now(),
        imageVector = GexplorerIcons.Simple.Walk
    ),
    Achievement(
        timeAchieved = Clock.System.now(),
        name = "Bardzo długa nazwa nie wiem po co, ale się domyślam",
        description = "I jego opis, który też zajmie co najmniej 2 linijki, a może nawet i 3 to będzie zajmować",
        imageVector = GexplorerIcons.Filled.Map
    )
)
private val lockedAchievements = listOf(
    Achievement(name = "Nowe osiągnięcie, którego nikt jeszcze nie ma!!!"),
    Achievement(name = "Ta? to pa"),
    Achievement(name = "nice ok"),
    Achievement(name = "bruh"),
    Achievement(name = "wtf is going on here", imageVector = Icons.Default.Close),
    Achievement(
        name = "Hello world",
        description = "Open the app for the first time",
        imageVector = Icons.Default.Favorite
    ),
    Achievement(
        name = "KOLEJNE\nDŁUGIE\nBEZSENSOWNE\nOSIĄGNIĘCIE",
        description = "Bo chcę sprawdzić, czy scroll działa"
    ),
    Achievement(name = "69 nice")
)

@Composable
fun AchievementsPage(navController: NavHostController? = null, changePage: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TitleBar(stringResource(id = R.string.achievements), navController)
        AchievementProgressBar(achievementsGot.size, achievementsGot.size + lockedAchievements.size)
        GroupingList(
            items = achievementsGot,
            groupBy = { it.timeAchieved.toLocalDateTime(TimeZone.currentSystemDefault()).date },
            title = { formatDate(it) }
        ) { achievement ->
            AchievementItem(achievement = achievement)
        }
        GroupingList(
            items = lockedAchievements,
            groupBy = { it.timeAchieved.toLocalDateTime(TimeZone.currentSystemDefault()).date },
            title = { stringResource(id = R.string.locked_achievements) }
        ) { achievement ->
            AchievementItem(achievement = achievement)
        }
    }
}

@Composable
private fun AchievementItem(achievement: Achievement) {
    //values from achievement
    val imageVector = achievement.imageVector
    val contentDescription: String? = null
    val label = achievement.name
    val description = achievement.description
    val timeAchieved =
        if (achievement.timeAchieved == Instant.DISTANT_PAST) null
        else achievement.timeAchieved

    //check the orientation of the device
    val orientation = LocalConfiguration.current.orientation

    //open the achievement dialog
    val openAchievementDialog = remember { mutableStateOf(false) }
    val onClick = { openAchievementDialog.value = true }
    when {
        openAchievementDialog.value -> {
            AchievementDialog(achievement = achievement) {
                openAchievementDialog.value = false
            }
        }
    }
    TextButton(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 7.5.dp),
        onClick = { onClick() },
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = if (orientation == ORIENTATION_PORTRAIT) 2 else 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            supportingContent = {
                if (description.isNotEmpty())
                    Text(text = description, maxLines = 1, overflow = TextOverflow.Ellipsis)
            },
            leadingContent = {
                Icon(
                    modifier = Modifier
                        .width(30.dp)
                        .aspectRatio(1f),
                    imageVector = imageVector,
                    contentDescription = contentDescription,
                    tint = if (timeAchieved != null) LocalContentColor.current else Color.Gray
                )
            },
            trailingContent = {
                if (timeAchieved != null)
                    Text(text = formatTime(timeAchieved, DateFormat.SHORT))
            }
        )
    }
}

@Composable
private fun AchievementDialog(achievement: Achievement, onDismissRequest: () -> Unit) {
    val timeAchieved =
        if (achievement.timeAchieved == Instant.DISTANT_PAST) null
        else achievement.timeAchieved
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(30.dp),
                        imageVector = achievement.imageVector,
                        contentDescription = null,
                        tint = if (timeAchieved != null) LocalContentColor.current else Color.Gray
                    )
                    Text(
                        text = achievement.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                if (achievement.description.isNotEmpty())
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = achievement.description)
                    }
            }
        }
    }
}

@Composable
private fun AchievementProgressBar(got: Int, outOf: Int) {
    if (got == 0) return //TODO when user is logged in show progress bar at 0%
    val orientation = LocalConfiguration.current.orientation
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
    ) {
        if (orientation == ORIENTATION_PORTRAIT) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp)
                    .padding(top = 7.5.dp)
            ) {
                AchievementProgressIndicator(got = got, outOf = outOf)
            }
        } else {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(vertical = 5.dp)
            ) {
                AchievementProgressIndicator(got = got, outOf = outOf)
            }
        }
    }
}

@Composable
private fun AchievementProgressIndicator(got: Int, outOf: Int) {
    Text(
        achievementsDoneAnnotatedString(got, outOf),
        modifier = Modifier.width(IntrinsicSize.Max)
    )
    Spacer(modifier = Modifier.width(10.dp))
    LinearProgressIndicator(
        progress = { (got / outOf.toFloat()) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .height(10.dp),
        color = LocalContentColor.current,
        trackColor = ListItemDefaults.containerColor,
        strokeCap = StrokeCap.Round
    )
}

@Composable
private fun achievementsDoneAnnotatedString(got: Int, outOf: Int): AnnotatedString {
    val stringSections = stringResource(id = R.string.achievements_got_out_of).split("[]")
    return buildAnnotatedString {
        append(stringSections[0])
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$got")
        }
        append(stringSections[1])
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$outOf")
        }
        append(stringSections[2])
        append("${((got / outOf.toFloat()) * 100).roundToInt()}%")
        append(stringSections[3])
    }
}

@Preview(locale = "pl", showBackground = true)
@Composable
private fun AchievementsPagePreview() {
    AchievementsPage {}
}