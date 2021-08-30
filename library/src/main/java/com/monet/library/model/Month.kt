package com.monet.library.model

import java.time.LocalDate
import java.time.YearMonth

/** 1ヶ月を表現する */
internal data class Month(
    val yearMonth: YearMonth,
    val today: LocalDate
) {
    companion object {

        /**
         * LocalDateからMonthを作成する
         */
        fun of(date: LocalDate): Month {
            return Month(YearMonth.of(date.year, date.month), date)
        }

        /**
         * monthIndexからMonthを作成する
         * monthIndex = year * 12 + month
         */
        fun of(monthIndex: Int): Month {
            return of(LocalDate.of(monthIndex / 12, monthIndex % 12, 1))
        }
    }

    /** ページャー用のインデックス */
    val monthIndex: Int = yearMonth.year * 12 + yearMonth.monthValue

    /** 週ごとの日付リスト */
    val weekList: List<Week>
        get() {
            val firstDayOfMonth = LocalDate.of(yearMonth.year, yearMonth.month, 1)
            val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value
            // 月初に足りない日数は、先月末の日付を足す
            val lackDate = if (firstDayOfWeek == 7) {
                0
            } else {
                firstDayOfWeek
            }
            val firstDayOfFirstWeek = firstDayOfMonth.minusDays(lackDate.toLong())
            // 1ヶ月は5週間にする
            return (0..4).toList().map {
                Week.of(firstDayOfFirstWeek.plusDays((it * 7).toLong()))
            }
        }
}
