package com.monet.library.model.holidayLogic

import java.time.*
import java.time.temporal.TemporalAdjusters

/** 日本の祝日判定ロジック */
class JapaneseHolidayStrategy : HolidayStrategy {

    /** 国民の祝日に関する法律（こくみんのしゅくじつにかんするほうりつ）は、
     *  1948年（昭和23年）7月20日に公布・即日施行された日本の法律である。通称祝日法。
     *  @see https://ja.wikipedia.org/wiki/国民の祝日に関する法律
     */
    private val publicHolidaysLawYear = 1948

    /** 「国民の祝日」が日曜日に当たるときは、その日後においてその日に最も近い「国民の祝日」でない日を休日とする
     *  1973年の国民の祝日に関する法律が改正されたことにより制定
     *  @see https://ja.wikipedia.org/wiki/振替休日
     */
    private val alternateHolidaysLawYear = 1973

    /** 「国民の祝日」の特例が2018年（平成30年6月20日）に公布・施行された。
     *  2020年（平成32年）の東京オリンピック・パラリンピック競技大会の円滑な準備及び運営に資するため
     *  同年に限り「海の日」は7月23日、「体育の日（スポーツの日）」は7月24日、「山の日」は8月10日となる。
     *   @see http://www8.cao.go.jp/chosei/shukujitsu/gaiyou.html
     */
    private val specialProvisionYear = 2020

    /** 「国民の祝日」の特例が2020年（平成32年12月2日）に公布・施行された。
     *  令和3年（2021年）に限り「海の日」は7月22日、「スポーツの日」は7月23日、「山の日」は8月8日となる。
     *  ※ 国民の祝日に関する法律第3条第2項の規定に基づき、8月9日は休日となります。
     *  @see http://www8.cao.go.jp/chosei/shukujitsu/gaiyou.html
     */
    private val specialExtraProvisionYear = 2021

    /** （注意）春分の日・秋分の日は1948年以前も祝祭日であったが、このカレンダーロジックの基準は1948年〜を基準とするので考慮しない
     *  @see https://ja.wikipedia.org/wiki/皇霊祭
     *  2019年天皇即位の年
     */
    private val emperorThroneYear = 2019

    /** 国民の祝日適用年
     *  元々、5月上旬における飛石連休の解消・改善を望む当時の世論に応える形で1985年12月27日に祝日法が改正され、
     *  導入されたものであるが、5月4日に限らず、祝日と祝日に挟まれた平日を全て休日にする制度であることから、
     *  後の祝日移動に伴い、5月以外の月にも国民の休日が現れることとなった。
     *  @see https://ja.wikipedia.org/wiki/国民の休日
     */
    private val nationalHolidaysStartYear = 1986

    /**
     * 日本の祝日を判定する
     * @param date 判定対象の日付
     * @return 祝日の場合、True
     */
    override fun isHoliday(date: LocalDate): Boolean {
        return if (isHolidayExtractNationalHoliday(date)) {
            true
        } else {
            // 対象日付が祝日ではなく前日と翌日が祝日の場合、国民の祝日とする
            val beforeDate = isHolidayExtractNationalHoliday(date.minusDays(1))
            val nextDate = isHolidayExtractNationalHoliday(date.plusDays(1))
            date.year >= nationalHolidaysStartYear && beforeDate && nextDate
        }
    }

    /**
     * 国民の祝日以外の、祝日を判定する
     *
     */
    private fun isHolidayExtractNationalHoliday(date: LocalDate): Boolean {
        val springDay = calcSpringAutumnDay(Year.of(date.year), SpringAutumn.SPRING)
        val autumnDay = calcSpringAutumnDay(Year.of(date.year), SpringAutumn.AUTUMN)
        return when {
            // 1月1日: 元旦
            date.monthDay() == MonthDay.of(1, 1) -> true

            // 1月2日: 振替休日
            date.year >= alternateHolidaysLawYear && date.monthDay() == MonthDay.of(1, 2) -> true

            // (1).1月15日(1999年まで)、(2).1月の第2月曜日(2000年から): 成人の日
            date.year <= 1999 && date.monthDay() == MonthDay.of(1, 15) -> true
            date.year > 1999 && date == LocalDate.of(date.year, Month.JANUARY, 1)
                .with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.MONDAY)) -> true

            // 1月16日: 成人の日の振替休日(1999年まで)
            date.year in alternateHolidaysLawYear..1999
                    && date.monthDay() == MonthDay.of(1, 16) -> true

            // 2月11日: 建国記念の日
            date.monthDay() == MonthDay.of(2, 11) -> true

            // 2月12日: 振替休日
            date.year >= alternateHolidaysLawYear && date.monthDay() == MonthDay.of(2, 12)
                    && date.isMonday() -> true

            // 2月23日(2020年から): 天皇誕生日
            date.year >= 2020 && date.monthDay() == MonthDay.of(2, 23) -> true

            // 2月24日(2020年から): 天皇誕生日の振替休日
            date.year >= 2020 && date.monthDay() == MonthDay.of(2, 24) && date.isMonday() -> true

            // 3月20日 or 21日: 春分の日(計算値によって算出)
            date.year >= publicHolidaysLawYear && springDay == date -> true

            // 春分の日の次が月曜日: 振替休日
            date.year >= publicHolidaysLawYear && springDay.isSunday()
                    && date == springDay.plusDays(1) -> true

            // 4月29日: 1949年から1989年までは天皇誕生日、1990年から2006年まではみどりの日、2007年以降は昭和の日
            date.year >= publicHolidaysLawYear && date.monthDay() == MonthDay.of(4, 29) -> true

            // 4月30日: 振替休日
            date.year >= publicHolidaysLawYear && date.monthDay() == MonthDay.of(4, 30)
                    && date.isMonday() -> true

            // 2019年5月1日： 2019年だけ天皇の即位の日
            date == LocalDate.of(2019, 5, 1) -> true

            // 5月3日: 1949年から憲法記念日
            date.year >= publicHolidaysLawYear && date.monthDay() == MonthDay.of(5, 3) -> true

            // 5月4日: (1)1988年以前は振替休日、(2).1988年から2006年まで国民の休日、2007年以降はみどりの日
            date.year >= publicHolidaysLawYear && date.monthDay() == MonthDay.of(5, 4) -> true

            // 5月5日: 1949年からこどもの日
            date.year >= publicHolidaysLawYear && date.monthDay() == MonthDay.of(5, 5) -> true

            // ゴールデンウィークの振替休日
            date.year >= publicHolidaysLawYear && isGoldenWeekTransferHoliday(date) -> true

            // 海の日
            isSeaDay(date) -> true

            // 7月21日: 海の日の振替休日
            date.year in 1996..2002 && date.monthDay() == MonthDay.of(7, 21)
                    && date.dayOfWeek == DayOfWeek.MONDAY -> true

            // 山の日
            isMountainDay(date) -> true

            // (1)2016年から8月12日、(2)2021年のみ8月9日: 山の日の振替休日
            date == LocalDate.of(2021, 8, 9) -> true
            date.year >= 2016 && date.monthDay() == MonthDay.of(8, 12) && date.isMonday() -> true

            // 敬老の日
            date == oldPeopleDay(date.year) -> true

            // 9月16日: 敬老の日の振替休日
            date.year in 1966..2002 && date.monthDay() == MonthDay.of(9, 16)
                    && date.isMonday() -> true

            // 9月22日 or 23日: 秋分の日(計算値によって算出)
            date.year >= publicHolidaysLawYear && autumnDay == date -> true

            // 秋分の日の次が月曜日: 振替休日
            date.year >= publicHolidaysLawYear && autumnDay.dayOfWeek == DayOfWeek.SUNDAY
                    && date == autumnDay.plusDays(1) -> true

            // 敬老の日と秋分の日に挟まれた日は休日: シルバーウィークの振替休日
            // (※現行の法律改正から変わらないと仮定した場合2009年から発生する)
            // @see https://ja.wikipedia.org/wiki/シルバーウィーク
            date.year >= 2009 && oldPeopleDay(date.year)?.isBefore(date) == true && date < autumnDay && autumnDay.dayOfWeek == DayOfWeek.TUESDAY -> true

            // 体育の日(スポーツの日)
            isHealthAndSportsDay(date) -> true

            // 10月11日: 体育の日の振替休日
            date.year in 1966..1999 && date.monthDay() == MonthDay.of(10, 11)
                    && date.isMonday() -> true

            // 2019年10月22日： 2019年だけ即位礼正殿の儀
            date == LocalDate.of(emperorThroneYear, 10, 22) -> true

            // 11月3日: 1948年から文化の日
            date.year >= publicHolidaysLawYear && date.monthDay() == MonthDay.of(11, 3) -> true

            // 11月4日: 振替休日
            date.year >= publicHolidaysLawYear && date.monthDay() == MonthDay.of(11, 4)
                    && date.isMonday() -> true

            // 11月23日: 1948年から勤労感謝の日
            date.year >= publicHolidaysLawYear && date.monthDay() == MonthDay.of(11, 23) -> true
            // 11月24日: 振替休日
            date.year >= publicHolidaysLawYear && date.monthDay() == MonthDay.of(11, 24)
                    && date.isMonday() -> true

            // 12月23日(1989年から2018年まで): 天皇誕生日
            date.year in 1989..2018 && date.monthDay() == MonthDay.of(12, 23) -> true

            // 12月24日(1989年から2018年まで): 天皇誕生日の振替休日
            date.year in 1989..2018 && date.monthDay() == MonthDay.of(12, 24)
                    && date.isMonday() -> true

            // 1999以前の祝日でその年限りに施行された祝日はこちら
            // 4月10日: 1959年だけ皇太子明仁親王の結婚の儀
            date == LocalDate.of(1959, 4, 10) -> true

            // 2月24日: 1989年だけ昭和天皇の大喪の礼
            date == LocalDate.of(1989, 2, 24) -> true

            // 11月12日: 1990年だけ即位礼正殿の儀
            date == LocalDate.of(1990, 11, 12) -> true

            // 6月9日: 1993年だけ皇太子徳仁親王の結婚の儀
            date == LocalDate.of(1993, 6, 9) -> true
            else -> false
        }
    }

    /**
     * 春分の日・秋分の日を計算する
     * @see http://koyomi8.com/reki_doc/doc_0330.htm
     */
    private fun calcSpringAutumnDay(year: Year, type: SpringAutumn): LocalDate = when {
        // 1999年以降の計算式
        year.value > 1999 -> SpringAutumn.calcDay(
            year.value,
            type,
            SpringAutumn.CalculationType.AFTER_1999
        )
        // 1980年から1999年までの計算式
        year.value in 1980..1999 -> SpringAutumn.calcDay(
            year.value,
            type,
            SpringAutumn.CalculationType.BETWEEN_1980_AND_1999
        )
        // 1980以前の計算式
        // （参考）http://www.asahi-net.or.jp/~ci5m-nmr/misc/equinox.html
        else -> when (type) {
            SpringAutumn.SPRING -> if (year.value >= 1960 && year.value % 4 == 0) {
                LocalDate.of(year.value, type.month, 20)
            } else {
                LocalDate.of(year.value, type.month, 21)
            }
            SpringAutumn.AUTUMN -> if ((year.value - 3) % 4 == 0) {
                LocalDate.of(year.value, type.month, 24)
            } else {
                LocalDate.of(year.value, type.month, 23)
            }
        }
    }

    /**
     * ゴールデンウィークの振替休日を判定する
     * (1)2006年以前は5/6が月曜日の場合、祝日
     * (2)2007年以降は5/6が月・火・水(5/3 or 5/4 or 5/5が日曜日)なら5/6を祝日とする
     */
    private fun isGoldenWeekTransferHoliday(date: LocalDate): Boolean {
        if (date.monthDay() != MonthDay.of(5, 6)) {
            return false
        }
        return when {
            date.year < 2007 && date.isMonday() -> true
            date.year >= 2007 && listOf(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY
            ).contains(LocalDate.of(date.year, 5, 6).dayOfWeek) -> true
            else -> false
        }
    }

    /**
     * 海の日の判定をする
     * (1).7月20日(1996年から2002年まで)
     * (2).7月の第3月曜日(2003年から)
     * (3).7月23日(2020年のみ)
     * (4).7月22日(2021年のみ)
     */
    private fun isSeaDay(date: LocalDate): Boolean = when (date.year) {
        in 1996..2002 -> date.monthDay() == MonthDay.of(7, 20)
        specialProvisionYear -> date.monthDay() == MonthDay.of(7, 23)
        specialExtraProvisionYear -> date.monthDay() == MonthDay.of(7, 22)
        else -> date == LocalDate.of(date.year, Month.JULY, 1)
            .with(TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.MONDAY))
    }

    /**
     * 山の日の判定をする
     * (1).8月11日(2016年から)
     * (2).8月10日(2020年のみ)
     * (3).8月8日(2021年のみ ※8月9日振替休日)
     */
    private fun isMountainDay(date: LocalDate): Boolean = when {
        date.year == specialProvisionYear -> date.monthDay() == MonthDay.of(8, 10)
        date.year == specialExtraProvisionYear -> date.monthDay() == MonthDay.of(8, 8)
        date.year in 2016..2019 || date.year >= 2022 -> date.monthDay() == MonthDay.of(8, 11)
        else -> false
    }

    /**
     * 敬老の日を返却する
     * (1).9月15日(1966年から2002年まで)
     * (2).9月の第3月曜日(2003年から)
     */
    private fun oldPeopleDay(year: Int): LocalDate? = when {
        year in 1966..2002 -> LocalDate.of(year, 9, 15)
        year >= 2003 -> LocalDate.of(year, Month.SEPTEMBER, 1)
            .with(TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.MONDAY))
        else -> null
    }

    /**
     * 体育の日(スポーツの日)を判定する
     * (1).10月10日(1966年から1999年まで)
     * (2).10月の第2月曜日(2000年からと2022年以降)
     * (3).7月24日(2020年のみ)
     * (4).7月23日(2021年のみ)
     */
    private fun isHealthAndSportsDay(date: LocalDate): Boolean = when {
        date.year in 1966..1999 -> date.monthDay() == MonthDay.of(10, 10)
        date.year in 2000..2019 || date.year >= 2022 ->
            date == LocalDate.of(date.year, Month.OCTOBER, 1)
                .with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.MONDAY))
        date.year == specialProvisionYear -> date.monthDay() == MonthDay.of(7, 24)
        date.year == specialExtraProvisionYear -> date.monthDay() == MonthDay.of(7, 23)
        else -> false
    }
}

/**
 * 春分の日・秋分の日
 */
private enum class SpringAutumn(val month: Int) {
    SPRING(3),
    AUTUMN(9);

    enum class CalculationType(
        val thresholdYear: Int,
        val springSunPassesConstant: Double,
        val autumnSunPassesConstant: Double
    ) {
        AFTER_1999(2000, 20.69115, 23.09000),
        BETWEEN_1980_AND_1999(1980, 20.8431, 23.2488),
        BEFORE_1980(1960, 0.0, 0.0);

        fun sunPassesConstant(springAutumn: SpringAutumn): Double = when (springAutumn) {
            SPRING -> springSunPassesConstant
            AUTUMN -> autumnSunPassesConstant
        }
    }

    companion object {

        /**
         * 春分の日・秋分の日を計算する
         */
        fun calcDay(year: Int, type: SpringAutumn, calcType: CalculationType): LocalDate {
            // １年ごとの春分点通過日の移動量
            val x1 = (year - calcType.thresholdYear) * 0.242194  // １年 = 365.242194日
            // 閏年によるリセット量
            val x2 = (year - calcType.thresholdYear) / 4
            val day = (calcType.sunPassesConstant(type) + x1 - x2).toInt()
            return LocalDate.of(year, type.month, day)
        }
    }
}

// extension
private fun LocalDate.monthDay(): MonthDay = MonthDay.of(month, dayOfMonth)

private fun LocalDate.isSunday(): Boolean = dayOfWeek == DayOfWeek.SUNDAY

private fun LocalDate.isMonday(): Boolean = dayOfWeek == DayOfWeek.MONDAY
