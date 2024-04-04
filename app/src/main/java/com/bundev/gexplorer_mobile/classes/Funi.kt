package com.bundev.gexplorer_mobile.classes

import kotlin.math.max
import kotlin.math.pow
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

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

    fun hasValue(): Boolean {
        return getValue() > 0L
    }

    fun getTimeRemaining(): Long {
        val currentTime = System.currentTimeMillis()
        val timeDifference = this.timeOut - currentTime
        return max(timeDifference, 0)
    }

    fun remove(){
        this.timeOut = 0
    }

    fun addTime(addSeconds: Long) {
        this.timeOut += addSeconds.seconds.toLong(DurationUnit.MILLISECONDS)
    }
}