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
        fun of(firstDayOfWeek: LocalDate, events: List<Event> = emptyList()): Week {
            val weekDays = (0..6).map {
                val day = firstDayOfWeek.plusDays(it.toLong())
                Day(day, events.filter { e -> e.isEventDate(day) })
            }
            return Week(weekDays)
        }
    }
}
