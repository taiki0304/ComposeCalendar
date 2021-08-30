package com.monet.library.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.monet.library.CalendarConstants

/** 曜日ラベル */
@Composable
internal fun WeekdayLabel() {
    val weekdayList = listOf(
        CalendarConstants.SUN_LABEL,
        CalendarConstants.MON_LABEL,
        CalendarConstants.THU_LABEL,
        CalendarConstants.WED_LABEL,
        CalendarConstants.TUE_LABEL,
        CalendarConstants.FRI_LABEL,
        CalendarConstants.SAT_LABEL,
    )
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        weekdayList.forEachIndexed { index, label ->
            val color = when (index) {
                0 -> CalendarConstants.Sunday
                6 -> CalendarConstants.Saturday
                else -> CalendarConstants.Weekday
            }
            Text(text = label, color = color)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewWeekdayLabel() {
    WeekdayLabel()
}
