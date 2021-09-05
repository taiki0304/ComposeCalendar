package com.monet.library

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.monet.library.model.holidayLogic.DefaultHolidayStrategy
import com.monet.library.model.holidayLogic.HolidayStrategy
import com.monet.library.model.type.FirstDayOfWeek

object CalendarManager {

    /** 祝日判定ロジック */
    var holidayStrategy: HolidayStrategy = DefaultHolidayStrategy()

    /** 週始まり */
    var firstDayOfWeek = FirstDayOfWeek.SUNDAY

    /** 色定義 */
    object Colors {
        var Sunday = Color.Red
        var Saturday = Color.Blue
        var Holiday = Sunday
        var Weekday = Color.Gray
        var Today = Color.Magenta
        var Selected = Color.Blue
    }

    /** レイアウト */
    object Layout {
        var calendarHeight = 320.dp
        var selectedBackground: Shape = RoundedCornerShape(12.dp)
    }

    object Localizable {
        /** 日付フォーマット */
        var YEAR_MONTH_FORMAT = "yyyy年MM月"
        var DATE_FORMAT = "d"

        /** 曜日ラベル */
        var SUN_LABEL = "日"
        var MON_LABEL = "月"
        var THU_LABEL = "火"
        var WED_LABEL = "水"
        var TUE_LABEL = "木"
        var FRI_LABEL = "金"
        var SAT_LABEL = "土"
    }
}
