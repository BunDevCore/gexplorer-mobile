package me.thefen.gexplorerapi.dtos

import com.mapbox.geojson.Geometry
import java.util.*

data class DetailedTripDto(
    val id: UUID,
    val user: ShortUserDto,
    val area: Double,
    val gpsPolygon: Geometry,
    val length: Double,
    val newArea: Double,
    val newAchievements: List<AchievementGetDto>
)

data class TripDto(
    val id: UUID,
    val user: ShortUserDto,
    val area: Double,
    val length: Double,
    val newArea: Double,
)
