package com.monet.library.model.logic

import java.time.LocalDate

/** 日本の祝日判定ロジック */
class JapaneseHolidayStrategy: HolidayStrategy {

    override fun isHoliday(date: LocalDate): Boolean {
        // TODO: 実装
        return false
    }
}
