package me.thefen.gexplorerapi.dtos

import com.mapbox.geojson.Geometry
import kotlinx.datetime.Instant
import java.util.*

data class DetailedTripDto(
    val id: UUID,
    val user: ShortUserDto,
    val area: Double,
    val gpsPolygon: Geometry,
    val length: Double,
    val newArea: Double,
    val newAchievements: List<AchievementGetDto>,
    val startTime: Instant,
    val endTime: Instant,
    val uploadTime: Instant,
)

data class TripDto(
    val id: UUID,
    val user: ShortUserDto,
    val area: Double,
    val length: Double,
    val newArea: Double,
    val startTime: Instant,
    val endTime: Instant,
    val uploadTime: Instant,
)
