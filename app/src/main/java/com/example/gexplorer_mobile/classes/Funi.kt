package com.example.gexplorer_mobile.classes

import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

class Funi {
    private var value: Int = 0
    private var timeOut: Long = System.currentTimeMillis()

    fun set(value: Int, timeOutInSeconds: Long){
        this.value = value
        this.timeOut = System.currentTimeMillis() + timeOutInSeconds.seconds.toLong(DurationUnit.MILLISECONDS)
    }

    fun get(): Int{
        val currentTime = System.currentTimeMillis()
        if (currentTime > timeOut) {
            value = 0
        }
        return value
    }
}