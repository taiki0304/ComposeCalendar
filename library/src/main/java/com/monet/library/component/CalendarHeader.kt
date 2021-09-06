package com.monet.library.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.monet.library.CalendarManager
import com.monet.library.model.Month
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/** ヘッダー */
@Composable
internal fun CalendarHeader(month: Month) = Row(
    horizontalArrangement = Arrangement.Center,
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
) {
    Text(month.yearMonth.format(DateTimeFormatter.ofPattern(CalendarManager.Localizable.YEAR_MONTH_FORMAT)))
}


@Preview(showBackground = true)
@Composable
fun PreviewCalendarHeader() {
    CalendarHeader(Month.of(LocalDate.now()))
}
