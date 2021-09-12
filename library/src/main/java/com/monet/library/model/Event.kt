package com.monet.library.model

import com.monet.library.EventDto
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.MonthDay

/** Event */
internal data class Event(
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val name: String? = null
) {
    companion object {

        /**
         * Create Event from EventDto
         * @return Event
         */
        fun of(dto: EventDto): Event = Event(dto.startDateTime, dto.endDateTime, dto.name)
    }

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
