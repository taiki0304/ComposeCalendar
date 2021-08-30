package com.monet.library

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.monet.library.component.CalendarHeader
import com.monet.library.component.CalendarTable
import com.monet.library.component.WeekdayLabel
import com.monet.library.model.Month
import java.time.LocalDate
import java.time.YearMonth


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    today: LocalDate = LocalDate.now(),
    selectedDate: LocalDate? = null
) {
    val month = remember { mutableStateOf(Month.of(today)) }
    val pagerState = rememberPagerState(
        pageCount = Int.MAX_VALUE,
        initialPage = month.value.monthIndex
    )
    CalendarLayout(modifier = modifier, month.value, pagerState = pagerState) {
        month.value = it
    }
}

@ExperimentalPagerApi
@Composable
private fun CalendarLayout(
    modifier: Modifier = Modifier,
    month: Month,
    pagerState: PagerState,
    onPageChange: (Month) -> Unit = {}
) {

    Column(modifier = modifier) {
        CalendarHeader(month)
        WeekdayLabel()
        CalendarTable(pagerState, month, onPageChange)
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
private fun PreviewCalendarLayout() {
    val month = Month(YearMonth.now(), LocalDate.now())
    val pagerState = rememberPagerState(
        pageCount = Int.MAX_VALUE,
        initialPage = 100
    )
    MaterialTheme {
        CalendarLayout(month = month, pagerState = pagerState)
    }
}