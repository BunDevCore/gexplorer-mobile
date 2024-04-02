package com.bundev.gexplorer_mobile.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bundev.gexplorer_mobile.GexplorerIcons
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.classes.Achievement
import com.bundev.gexplorer_mobile.icons.filled.Map
import com.bundev.gexplorer_mobile.icons.filled.Trophy
import com.bundev.gexplorer_mobile.icons.simple.Walk
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.DateFormat

@Composable
fun AchievementsPage() {
    Column(modifier = Modifier.fillMaxSize()) {
        val achievements = listOf(
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
            ),
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
        AchievementsList(achievements)
    }
}

@Composable
fun AchievementsList(achievements: List<Achievement>) {
    val achievementSection =
        achievements.groupBy { it.timeAchieved.toLocalDateTime(TimeZone.currentSystemDefault()).date }
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        achievementSection.forEach {
            AchievementSection(it.value)
        }
    }
}

@Composable
fun AchievementSection(achievements: List<Achievement>) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
    ) {
        val label = if (achievements[0].timeAchieved > Instant.DISTANT_PAST)
            formatDate(achievements[0].timeAchieved)
        else
            stringResource(id = R.string.locked_achievements)
        Text(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 7.5.dp),
            text = label
        )
        achievements.forEach { achievement ->
            val openAchievementDialog = remember { mutableStateOf(false) }
            when {
                openAchievementDialog.value -> {
                    AchievementDialog(achievement = achievement) {
                        openAchievementDialog.value = false
                    }
                }
            }
            AchievementItem(achievement = achievement) { openAchievementDialog.value = true }
        }
    }
}

@Composable
fun AchievementItem(achievement: Achievement, onClick: () -> Unit) {
    val imageVector =
        if (achievement.imageVector is ImageVector) achievement.imageVector
        else GexplorerIcons.Filled.Trophy
    val contentDescription: String? = null
    val label = achievement.name
    val description = achievement.description
    val timeAchieved =
        if (achievement.timeAchieved == Instant.DISTANT_PAST) null
        else achievement.timeAchieved

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
                Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            },
            supportingContent = {
                if (description is String)
                    Text(text = description)
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
fun AchievementDialog(achievement: Achievement, onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
//        AchievementDetailPage(achievement)
    }
}

@Composable
fun AchievementDetailPage(achievement: Achievement) {
    Card{}
}

@Preview
@Composable
fun AchievementCardPreview() {
    val achievements = listOf(
        Achievement(
            name = "Przykładowe długie osiągnięcie jest tu wpisane",
            description = "SIEMANECZKO",
            timeAchieved = Clock.System.now()
        ),
        Achievement(name = "hi", timeAchieved = Clock.System.now())
    )
    AchievementsList(achievements)
}

@Preview(locale = "pl")
@Composable
fun AchievementsPagePreview() {
    AchievementsPage()
}