package me.thefen.gexplorerapi.dtos

import java.util.*

data class AchievementGetDto(val achievementId: String, val timeAchieved: Date, val achievedOnTripId: UUID)