package com.monet.library.model.type

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
