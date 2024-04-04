package com.bundev.gexplorer_mobile.classes

import kotlinx.datetime.Instant
import java.util.UUID

class Trip(
    val id: UUID? = null,
    val timeBegun: Instant,
    val timeEnded: Instant,
    val distance: Double,
    var saved: Boolean = false
)