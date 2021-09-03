package com.monet.library.model.logic

import java.time.LocalDate

/** 祝日判定ロジック */
interface HolidayStrategy {

    /**
     * dateが祝日かを判定する
     * @param date 判定対象の日付
     */
    fun isHoliday(date: LocalDate): Boolean
}
