package com.example.gexplorer_mobile.classes

import android.util.Log
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.math.pow

class Funi {
    private var value: Long = 0
    private var timeOut: Long = System.currentTimeMillis()

    fun set(value: Long, timeOutInSeconds: Long) {
        this.value = value
        this.timeOut =
            System.currentTimeMillis() + timeOutInSeconds.seconds.toLong(DurationUnit.MILLISECONDS)
    }

    fun append(value: Long, timeOutInSeconds: Long) {
        val thisValueLength = this.value.toString().length
        val valueLength = value.toString().length
        if (thisValueLength + valueLength > 18) {
            this.value =
                this.value.toString().subSequence(valueLength, thisValueLength).toString()
                    .toLong()
        }
        this.value = this.value * 10f.pow(valueLength).toInt() + value
        this.timeOut =
            System.currentTimeMillis() + timeOutInSeconds.seconds.toLong(DurationUnit.MILLISECONDS)
    }

    fun getValue(): Long {
        val currentTime = System.currentTimeMillis()
        if (currentTime > this.timeOut) {
            value = 0
        }
        return value
    }

    fun getTimeRemaining(getDurationIn: String): Double {
        val currentTime = System.currentTimeMillis()
        var timeDifference = this.timeOut - currentTime
        if (timeDifference > 0) {
            timeDifference = when (getDurationIn.lowercase()) {
                "nanoseconds", "ns" ->
                    timeDifference.milliseconds.inWholeNanoseconds

                "microseconds", "us", "μs" ->
                    timeDifference.milliseconds.inWholeMicroseconds

                "miliseconds", "ms" -> timeDifference
                "seconds", "s" -> timeDifference.milliseconds.inWholeSeconds
                "minutes", "min" -> timeDifference.milliseconds.inWholeMinutes
                "hours", "h" -> timeDifference.milliseconds.inWholeHours
                "days", "d" -> timeDifference.milliseconds.inWholeDays
                else -> -1
            }
            if (timeDifference == (-1).toLong()) {
                Log.e("time conversion", "non existent duration type: $getDurationIn")
                return 0.0
            }
            if (!(getDurationIn.lowercase() == "nanoseconds" || getDurationIn.lowercase() == "ns") ){
                var remainder = when (getDurationIn.lowercase()) {//TODO complete it
                    "microseconds", "us", "μs" ->
                        timeDifference.milliseconds.inWholeMicroseconds

                    "miliseconds", "ms" -> timeDifference
                    "seconds", "s" -> timeDifference.milliseconds.inWholeSeconds
                    "minutes", "min" -> timeDifference.milliseconds.inWholeMinutes
                    "hours", "h" -> timeDifference.milliseconds.inWholeHours
                    "days", "d" -> timeDifference.milliseconds.inWholeDays
                    else -> 0.0
                }
            }
            return timeDifference.toDouble()
        }
        return 0.0
    }

    fun addTime(addSeconds: Long) {
        this.timeOut += addSeconds.seconds.toLong(DurationUnit.MILLISECONDS)
    }
}