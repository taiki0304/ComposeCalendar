package com.monet.library.model.holidayLogic

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * 日本の祝日判定ロジックのテスト
 */
class JapaneseHolidayLogicStrategyTest {

    private val logic = JapaneseHolidayStrategy()

    /**
     * 1955 ~ 2022年の祝日をテストする
     * 内閣府「国民の祝日について」のHPより、CSVをダウンロードし、テスト対象としている
     * @see https://www8.cao.go.jp/chosei/shukujitsu/gaiyou.html
     */
    @ParameterizedTest
    @CsvFileSource(resources = ["/japanese_holiday_list.csv"], numLinesToSkip = 1)
    fun testHoliday(date: String, name: String) {
        val targetDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy/M/d"))
        assertTrue(logic.isHoliday(targetDate))
    }
}
