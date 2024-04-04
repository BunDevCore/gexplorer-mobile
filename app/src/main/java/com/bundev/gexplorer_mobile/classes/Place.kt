package com.bundev.gexplorer_mobile.classes

import com.mapbox.geojson.Point
import java.util.UUID

class Place(
    val id: UUID? = null,
    val place: List<Point>,
    val name: String,
    val description: String,
    val visited: Boolean = false,
    val saved: Boolean = false,
)