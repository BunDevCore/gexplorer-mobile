package me.thefen.gexplorerapi.dtos

import com.mapbox.geojson.Geometry
import java.util.*

data class DistrictDto(val id: UUID, val name: String, val geometry: Geometry, val area: Double)
data class ShortDistrictDto(val id: UUID, val name: String)