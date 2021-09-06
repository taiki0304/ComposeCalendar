package com.monet.library.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.MonthDay

/** Event */
data class Event(
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val name: String? = null
) {

    /**
     * 指定日がイベントの開催日であるか判定する
     */
    fun isEventDate(date: LocalDate): Boolean {
        val startMonthDay = MonthDay.of(startDateTime.month, startDateTime.dayOfMonth)
        val endMonthDay = MonthDay.of(endDateTime.month, endDateTime.dayOfMonth)
        val targetDay = MonthDay.of(date.month, date.dayOfMonth)
        return startMonthDay == targetDay
                || targetDay == endMonthDay
                || (startMonthDay.isAfter(targetDay) && targetDay.isAfter(endMonthDay))
    }
}
