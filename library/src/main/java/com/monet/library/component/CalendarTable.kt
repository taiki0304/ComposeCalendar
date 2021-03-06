package com.monet.library.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import com.monet.library.CalendarManager
import com.monet.library.model.Day
import com.monet.library.model.Month
import com.monet.library.model.type.DayOfMonthType
import com.monet.library.model.type.SwipeDirection
import kotlinx.coroutines.flow.collect
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/** カレンダー */
@ExperimentalPagerApi
@Composable
internal fun CalendarTable(
    pagerState: PagerState,
    month: Month,
    today: LocalDate? = null,
    selectedDay: LocalDate? = null,
    onChangePage: (Month) -> Unit = {},
    onSelectDay: (Day) -> Unit = {}
) {
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            // スワイプでのページ切り替え
            onChangePage(Month.of(monthIndex = page, month.events))
        }
    }
    val modifier = Modifier
        .background(MaterialTheme.colors.background)
        .height(CalendarManager.Layout.calendarHeight)
    // Calendar content
    when (CalendarManager.Layout.swipeDirection) {
        SwipeDirection.HORIZONTAL -> HorizontalCalendar(state = pagerState, modifier = modifier) {
            CalendarContent(month, today, selectedDay, onSelectDay)
        }
        SwipeDirection.VERTICAL -> VerticalCalendar(state = pagerState, modifier = modifier) {
            CalendarContent(month, today, selectedDay, onSelectDay)
        }
    }
}

/** Horizontal pager calendar */
@ExperimentalPagerApi
@Composable
private fun HorizontalCalendar(
    state: PagerState,
    modifier: Modifier = Modifier,
    content: @Composable PagerScope.(page: Int) -> Unit
) = HorizontalPager(
    state = state,
    verticalAlignment = Alignment.Top,
    modifier = modifier,
    content = content
)
/** Vertical pager calendar */
@ExperimentalPagerApi
@Composable
private fun VerticalCalendar(
    state: PagerState,
    modifier: Modifier = Modifier,
    content: @Composable PagerScope.(page: Int) -> Unit
) = VerticalPager(
    state = state,
    verticalAlignment = Alignment.Top,
    modifier = modifier,
    content = content
)

@Composable
private fun CalendarContent(
    month: Month,
    today: LocalDate? = null,
    selectedDay: LocalDate? = null,
    onSelectDay: (Day) -> Unit = {}
) = Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxSize()) {
    month.weeksOfMonth.forEach { week ->
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            week.days.forEach { day ->
                DayCell(
                    day,
                    day.dayOfMonthType(month, CalendarManager.holidayStrategy),
                    isToday = day.day == today,
                    isSelected = day.day == selectedDay,
                    onSelect = { onSelectDay(it) }
                )
            }
        }
    }
}


/** Cell of 1 day */
@Composable
private fun DayCell(
    day: Day,
    dayOfMonthType: DayOfMonthType,
    isToday: Boolean = false,
    isSelected: Boolean = false,
    onSelect: (Day) -> Unit = {}
) {
    val textColor = when {
        isSelected -> MaterialTheme.colors.surface
        isToday -> CalendarManager.Colors.Today
        else -> when (dayOfMonthType) {
            DayOfMonthType.WEEKDAY -> CalendarManager.Colors.Weekday
            DayOfMonthType.SUNDAY -> CalendarManager.Colors.Sunday
            DayOfMonthType.SATURDAY -> CalendarManager.Colors.Saturday
            DayOfMonthType.HOLIDAY -> CalendarManager.Colors.Holiday
            DayOfMonthType.DAY_OF_OTHER_MONTH -> Color.LightGray
        }
    }

    val backgroundColor = if (isSelected) {
        CalendarManager.Colors.Selected
    } else {
        Color.Unspecified
    }

    Box(contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                day.day.format(DateTimeFormatter.ofPattern(CalendarManager.Localizable.DATE_FORMAT)),
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable(enabled = !isSelected, onClick = { onSelect(day) })
                    .background(backgroundColor, CalendarManager.Layout.selectedBackground)
                    .widthIn(40.dp)
                    .padding(8.dp)
            )
            EventDots(day)
        }
    }
}

/** Dots of events */
@Composable
private fun EventDots(day: Day) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(day.eventCount(3)) {
            Text(
                CalendarManager.Localizable.EVENT_DOT,
                fontSize = 4.sp,
                color = CalendarManager.Colors.EventDot,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp)
            )
        }
        // イベントが0の場合、空文字のViewで位置を揃える
        if (day.eventCount(3) == 0) {
            Text(
                "", fontSize = 4.sp,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp)
            )
        }
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
private fun PreviewCalendarTable() {
    CalendarTable(pagerState = rememberPagerState(pageCount = 10), Month.of(LocalDate.now()))
}
