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
import com.monet.library.model.Day
import com.monet.library.model.Event
import com.monet.library.model.Month
import java.time.LocalDate
import java.time.LocalDateTime


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    today: LocalDate = LocalDate.now(),
    selectedDate: LocalDate? = null,
//    events: List<Event> = testEventList,
    events: List<Event> = emptyList(),
    onSelectDay: (LocalDate) -> Unit = {}
) {
    val month = remember { mutableStateOf(Month.of(today, events)) }
    val rememberSelectedDate = remember { mutableStateOf(selectedDate) }
    val pagerState = rememberPagerState(
        pageCount = Int.MAX_VALUE,
        initialPage = month.value.monthIndex
    )

    CalendarLayout(
        modifier = modifier,
        month = month.value,
        pagerState = pagerState,
        today = today,
        selectedDate = rememberSelectedDate.value,
        onChangePage = { month.value = it },
        onSelectDay = {
            // 日付の選択
            rememberSelectedDate.value = it.day
            onSelectDay(it.day)
        })
}

@ExperimentalPagerApi
@Composable
private fun CalendarLayout(
    modifier: Modifier = Modifier,
    month: Month,
    pagerState: PagerState,
    today: LocalDate? = null,
    selectedDate: LocalDate? = null,
    onChangePage: (Month) -> Unit = {},
    onSelectDay: (Day) -> Unit = {}
) = Column(modifier = modifier) {
    CalendarHeader(month)
    WeekdayLabel()
    CalendarTable(
        pagerState,
        month,
        today,
        selectedDate,
        onChangePage = onChangePage,
        onSelectDay = onSelectDay
    )
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
private fun PreviewCalendarLayout() {
    val month = Month.of(
        LocalDate.now(), testEventList
    )
    val pagerState = rememberPagerState(
        pageCount = Int.MAX_VALUE,
        initialPage = 100
    )
    MaterialTheme {
        CalendarLayout(month = month, pagerState = pagerState)
    }
}

val testEventList = listOf(
    Event(LocalDateTime.now(), LocalDateTime.now().plusDays(1), "event1"),
    Event(LocalDateTime.now(), LocalDateTime.now().plusDays(1), "event2"),
    Event(LocalDateTime.now(), LocalDateTime.now().plusDays(1), "event3"),
    Event(
        LocalDateTime.now().plusMonths(1),
        LocalDateTime.now().plusMonths(1).plusDays(1),
        "event4"
    )
)
