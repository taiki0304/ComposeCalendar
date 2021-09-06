package com.monet.library.model

import com.monet.library.model.holidayLogic.HolidayStrategy
import com.monet.library.model.type.DayOfMonthType
import java.lang.Integer.min
import java.time.DayOfWeek
import java.time.LocalDate

/** カレンダーの1日を表現する */
internal data class Day(
    val day: LocalDate,
    val events: List<Event> = emptyList()
) {

    /**
     * 指定月内での日付種別を返却する
     */
    fun dayOfMonthType(thisMonth: Month, holidayStrategy: HolidayStrategy): DayOfMonthType {
        if (day.month != thisMonth.yearMonth.month) {
            return DayOfMonthType.DAY_OF_OTHER_MONTH
        }
        if (holidayStrategy.isHoliday(day)) {
            return DayOfMonthType.HOLIDAY
        }
        return when (day.dayOfWeek) {
            DayOfWeek.SUNDAY -> DayOfMonthType.SUNDAY
            DayOfWeek.SATURDAY -> DayOfMonthType.SATURDAY
            else -> DayOfMonthType.WEEKDAY
        }
    }

    fun addEvent(event: Event) = this.events + event

    fun addEvents(events: List<Event>) = this.events + events

    /**
     * @return Count of events
     */
    fun eventCount(maxCount: Int): Int = min(maxCount, events.size)
}
