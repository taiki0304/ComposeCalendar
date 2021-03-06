package com.monet.composecalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.monet.composecalendar.ui.theme.ComposeCalendarTheme
import com.monet.library.Calendar
import com.monet.library.CalendarManager
import com.monet.library.model.holidayLogic.JapaneseHolidayStrategy

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCalendarTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CalendarLayout()
                }
            }
        }
    }
}

@Composable
fun CalendarLayout() {
    CalendarManager.holidayStrategy = JapaneseHolidayStrategy()
    Calendar()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeCalendarTheme {
        CalendarLayout()
    }
}
