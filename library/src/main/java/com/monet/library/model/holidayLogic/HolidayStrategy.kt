package com.monet.library.model.holidayLogic

import java.time.LocalDate

/** 祝日判定ロジック */
interface HolidayStrategy {

    /**
     * dateが祝日かを判定する
     * @param date 判定対象の日付
     */
    fun isHoliday(date: LocalDate): Boolean
}

/** Default HolidayStrategy */
class DefaultHolidayStrategy: HolidayStrategy {

    /**
     * Always return false.
     * If you wanna custom holiday logic, you should make class to override `HolidayStrategy`.
     */
    override fun isHoliday(date: LocalDate): Boolean {
        return false
    }
}
