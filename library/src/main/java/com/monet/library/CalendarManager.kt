package com.monet.library

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.monet.library.model.holidayLogic.DefaultHolidayStrategy
import com.monet.library.model.holidayLogic.HolidayStrategy
import com.monet.library.model.type.FirstDayOfWeek

/**
 * Manage calendar property
 * If you customize calendar property, you customize property of this class.
 * This class is singleton.
 */
object CalendarManager {

    /** Logic of holiday */
    var holidayStrategy: HolidayStrategy = DefaultHolidayStrategy()

    /** First day of week type
     * Sunday or Monday
     */
    var firstDayOfWeek = FirstDayOfWeek.SUNDAY

    /** Colors */
    object Colors {
        var Sunday = Color.Red
        var Saturday = Color.Blue
        var Holiday = Sunday
        var Weekday = Color.Gray
        var Today = Color.Magenta
        var Selected = Color.Blue
        var EventDot = Color.Blue
    }

    /** Layout */
    object Layout {
        var calendarHeight = 320.dp
        var selectedBackground: Shape = RoundedCornerShape(12.dp)
    }

    object Localizable {
        /** date format */
        var YEAR_MONTH_FORMAT = "yyyy年MM月"
        var DATE_FORMAT = "d"

        /** Label of week day */
        var SUN_LABEL = "日"
        var MON_LABEL = "月"
        var THU_LABEL = "火"
        var WED_LABEL = "水"
        var TUE_LABEL = "木"
        var FRI_LABEL = "金"
        var SAT_LABEL = "土"

        /** Dot icon of event */
        var EVENT_DOT = "●"
    }
}
