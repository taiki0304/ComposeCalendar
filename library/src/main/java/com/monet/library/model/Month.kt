package com.monet.library.model

import com.monet.library.CalendarManager
import com.monet.library.model.type.FirstDayOfWeek
import java.time.DayOfWeek
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
            val isDecember = monthIndex % 12 == 0
            val year = if (isDecember) {
                monthIndex / 12 - 1
            } else {
                monthIndex / 12
            }
            val month = if (isDecember) {
                12
            } else {
                monthIndex % 12
            }
            return of(LocalDate.of(year, month, 1))
        }
    }

    /** ページャー用のインデックス */
    val monthIndex: Int = yearMonth.year * 12 + yearMonth.monthValue

    /** 週ごとの日付リスト */
    val weeksOfMonth: List<Week>
        get() {
            val firstDayOfMonth = LocalDate.of(yearMonth.year, yearMonth.month, 1)
            val firstDayOfWeek = firstDayOfMonth.dayOfWeek
            // 月初に足りない日数は、先月末の日付を足す
            val lackDate = calcLackDateOfWeek(firstDayOfWeek, CalendarManager.firstDayOfWeek)
            val firstDayOfFirstWeek = firstDayOfMonth.minusDays(lackDate.toLong())
            // 1カ月内の週の数を計算する
            var weekRowCount = (lackDate + yearMonth.lengthOfMonth()) / 7
            if ((lackDate + yearMonth.lengthOfMonth()) % 7 == 0) {
                weekRowCount--
            }
            return (0..weekRowCount).map {
                Week.of(firstDayOfFirstWeek.plusDays((it * 7).toLong()))
            }
        }

    private fun calcLackDateOfWeek(dayOfWeek: DayOfWeek, firstDayOfWeek: FirstDayOfWeek): Int {
        return when (firstDayOfWeek) {
            FirstDayOfWeek.SUNDAY -> {
                if (dayOfWeek == DayOfWeek.SUNDAY) {
                    0
                } else {
                    dayOfWeek.value
                }
            }
            FirstDayOfWeek.MONDAY -> dayOfWeek.value - 1
        }
    }
}
