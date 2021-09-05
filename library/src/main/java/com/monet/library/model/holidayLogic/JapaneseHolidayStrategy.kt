package com.monet.library.model.holidayLogic

import java.time.LocalDate

/** 日本の祝日判定ロジック */
class JapaneseHolidayStrategy: HolidayStrategy {

    override fun isHoliday(date: LocalDate): Boolean {
        // TODO: 実装
        // 参考) https://github.com/fumiyasac/handMadeCalendarAdvance/blob/master/CalculateCalendarLogic/CalculateCalendarLogic.swift
        return false
    }
}
