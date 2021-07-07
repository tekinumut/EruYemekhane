package com.tekinumut.eruyemekhane.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    // Default Time Format
    private val defaultDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale("tr"))

    // Returns the current time stamp
    private fun currentTimeStamp(): Long = defaultDateFormat.parse(
        defaultDateFormat.format(Date())
    )!!.time

    // Returns the time stamp of the next month
    fun getNextMonthTimeStamp(): Long {
        val oneMonth: Long = 30 * 24 * 60 * 60 // Day * Hour * Minute * Second
        return currentTimeStamp() + oneMonth * 1000
    }

    fun Long.toFormattedDate(): String = defaultDateFormat.format(Date(this))

    fun isGivenTimePassed(timeStamp: Long) = timeStamp <= currentTimeStamp()

}