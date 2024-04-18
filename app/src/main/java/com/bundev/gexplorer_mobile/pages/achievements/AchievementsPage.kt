package com.bundev.gexplorer_mobile.pages.achievements

import android.annotation.SuppressLint
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.bundev.gexplorer_mobile.R
import com.bundev.gexplorer_mobile.classes.Achievement
import com.bundev.gexplorer_mobile.data.ApiResource
import com.bundev.gexplorer_mobile.formatDate
import com.bundev.gexplorer_mobile.formatTime
import com.bundev.gexplorer_mobile.ui.GroupingList
import com.bundev.gexplorer_mobile.ui.LoadingCard
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.thefen.gexplorerapi.dtos.AchievementGetDto
import java.text.DateFormat
import kotlin.math.roundToInt

@Composable
fun AchievementsPage() {
    val vm = hiltViewModel<AchievementsViewModel>()
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.fetchSelf()
        vm.getAchievements()
    }

    when (state.userDto) {
        is ApiResource.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AchievementProgressBar(
                    state.userDto.data!!.achievements.size,
                    state.achievementDto.data!!.achievementCount
                )
                GroupingList(
                    items = state.userDto.data!!.achievements,
                    groupBy = { it.timeAchieved.toLocalDateTime(TimeZone.currentSystemDefault()).date },
                    title = { formatDate(it) }
                ) { achievement ->
                    AchievementItem(achievement = convertAchievements(achievementGetDto = achievement))
                }

                GroupingList(
                    items = state.achievementDto.data!!.achievements.minus(state.userDto.data!!.achievements.map { it.achievementId }),
                    groupBy = { 1 },
                    title = { stringResource(id = R.string.locked_achievements) }
                ) { achievementId ->
                    LockedAchievementItem(achievementId = achievementId)
                }
            }
        }

        is ApiResource.Loading -> Column(modifier = Modifier.fillMaxSize()) {
            LoadingCard(text = stringResource(id = R.string.loading_api))
        }

        is ApiResource.Error -> Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                GroupingList(
                    items = state.achievementDto.data!!.achievements,
                    groupBy = { 1 },
                    title = { stringResource(id = R.string.locked_achievements) }
                ) { achievementId ->
                    LockedAchievementItem(achievementId = achievementId)
                }
            }
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
private fun LockedAchievementItem(achievementId: String) {
    val context = LocalContext.current

    val nameId = "Achievement_${achievementId}_name"
    val name = try {
        context.resources.getString(
            context.resources.getIdentifier(
                nameId, "string", context.packageName
            )
        )
    } catch (e: Exception) {
        achievementId
    }

    val descriptionId = "Achievement_${achievementId}_desc"
    val description = try {
//        context.resources.configuration.locales.toLanguageTags()

        context.resources.getString(
            context.resources.getIdentifier(
                descriptionId, "string", context.packageName
            )
        )
    } catch (e: Exception) {
        achievementId
    }
    val achievement = Achievement(
        id = achievementId,
        name = name,
        description = description
    )
    AchievementItem(achievement = achievement)
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

@SuppressLint("DiscouragedApi")
@Composable
fun convertAchievements(achievementGetDto: AchievementGetDto): Achievement {
    val context = LocalContext.current
    val nameId = "Achievement_${achievementGetDto.achievementId}_name"
    val name = try {
        context.resources.getString(
            context.resources.getIdentifier(
                nameId, "string", context.packageName
            )
        )
    } catch (e: Exception) {
        achievementGetDto.achievementId
    }

    val descriptionId = "Achievement_${achievementGetDto.achievementId}_desc"
    val description = try {
        context.resources.getString(
            context.resources.getIdentifier(
                descriptionId, "string", context.packageName
            )
        )
    } catch (e: Exception) {
        achievementGetDto.achievementId
    }
    return Achievement(
        id = achievementGetDto.achievementId,
        name = name,
        description = description,
        timeAchieved = achievementGetDto.timeAchieved,
        achievedOnTripId = achievementGetDto.achievedOnTripId
    )
}

@Preview(locale = "pl", showBackground = true)
@Composable
private fun AchievementsPagePreview() {
    AchievementsPage()
}