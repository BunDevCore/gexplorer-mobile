package me.thefen.gexplorerapi.dtos

import kotlinx.datetime.Instant
import java.util.UUID

data class AchievementGetDto(val achievementId: String, val timeAchieved: Instant, val achievedOnTripId: UUID)