package com.monet.library.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.monet.library.model.Day
import com.monet.library.model.Month
import kotlinx.coroutines.flow.collect
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/** カレンダー */
@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun CalendarTable(
    pagerState: PagerState,
    month: Month,
    onChangePage: (Month) -> Unit = {}
) {
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            // スワイプでのページ切り替え
            onChangePage(Month.of(monthIndex = page))
        }
    }

    HorizontalPager(
        pagerState,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            month.weekList.forEach { week ->
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    week.days.forEach { day ->
                        DayCell(day)
                    }
                }
            }
        }
    }
}

@Composable
private fun DayCell(day: Day) {
    Box(contentAlignment = Alignment.Center) {
        Text(
            day.day.format(DateTimeFormatter.ofPattern("d")),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .height(48.dp)
                .widthIn(min = 24.dp)
                .padding(top = 8.dp)
        )
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
private fun PreviewCalendarTable() {
    CalendarTable(pagerState = rememberPagerState(pageCount = 10), Month.of(LocalDate.now()))
}
