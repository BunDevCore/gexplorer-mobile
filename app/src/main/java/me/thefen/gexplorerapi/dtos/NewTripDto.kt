package me.thefen.gexplorerapi.dtos

data class TimedPoint (val longitude: Double, val latitude: Double, val timestamp: Long)

class NewTripDto (val points: List<TimedPoint>)