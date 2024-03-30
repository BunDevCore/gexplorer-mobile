package com.bundev.gexplorer_mobile.classes

import com.mapbox.geojson.Point

class Trip (val points: List<Point>, val timeBegun: Long, val timeEnded: Long, val distance: Double)