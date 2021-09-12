package com.monet.library.model.holidayLogic

import java.time.LocalDate

/** Logic of judge holiday */
interface HolidayStrategy {

    /**
     * Judge if date is holiday.
     * @param date 判定対象の日付
     * @return If date is holiday, return True.
     */
    fun isHoliday(date: LocalDate): Boolean
}

/** Default HolidayStrategy */
class DefaultHolidayStrategy : HolidayStrategy {

    /**
     * Always return false.
     * If you wanna custom holiday logic, you should make class to override `HolidayStrategy`.
     */
    override fun isHoliday(date: LocalDate): Boolean = false
}
