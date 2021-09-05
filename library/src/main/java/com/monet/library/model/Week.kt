package com.monet.library.model

import java.time.LocalDate

/** 1週間を表現する */
internal data class Week(
    val days: List<Day>
) {
    companion object {

        /**
         * 最初の日付から、1週間分の日付リストを作成する
         */
        fun of(firstDayOfWeek: LocalDate): Week {
            val weekDays = (0..6).map {
                Day(firstDayOfWeek.plusDays(it.toLong()))
            }
            return Week(weekDays)
        }
    }
}
