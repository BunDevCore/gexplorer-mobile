package me.thefen.gexplorerapi.dtos

import java.util.Date
import java.util.UUID

data class UserDto(val id: UUID, val username: String, val overallAreaAmount: Double, val joinedAt: Date,
    val trips: List<TripDto>, val tripAmount: Int, val totalTripLength: Double, val districtAreas: Map<UUID, Double>, val achievements: List<AchievementGetDto>)

data class ShortUserDto(val id: UUID, val username: String, val overallAreaAmount: Double, val joinedAt: Date)