package com.bundev.gexplorer_mobile.classes

import com.mapbox.geojson.Point
import kotlinx.datetime.Instant

class Trip (val points: List<Point>, val timeBegun: Instant, val timeEnded: Instant, val distance: Double)