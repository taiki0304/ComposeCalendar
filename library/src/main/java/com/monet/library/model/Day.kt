package com.monet.library.model

import com.monet.library.model.holidayLogic.HolidayStrategy
import java.time.DayOfWeek
import java.time.LocalDate

/** カレンダーの1日を表現する */
internal data class Day(
    val day: LocalDate
    // TODO: イベントを追加
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
}

/** Monthの中の日付の種別 */
internal enum class DayOfMonthType {
    /** 平日 */
    WEEKDAY,

    /** 日曜日 */
    SUNDAY,

    /** 土曜日 */
    SATURDAY,

    /** 祝日 */
    HOLIDAY,

    /** 当月以外の日 */
    DAY_OF_OTHER_MONTH
}
