/*
 * Copyright 2025 Tencent. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.material3

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
internal class OhosCalendarModelImpl(locale: CalendarLocale) : CalendarModel(locale) {

    override val today: CalendarDate
        get() {
            val localDate = Clock.System.now().toLocalDateTime(systemTZ)
            return CalendarDate(
                year = localDate.year,
                month = localDate.monthNumber,
                dayOfMonth = localDate.dayOfMonth,
                utcTimeMillis = localDate.date
                    .atTime(Midnight)
                    .toInstant(TimeZone.UTC)
                    .toEpochMilliseconds()
            )
        }

    override val firstDayOfWeek: Int
        get() = if (locale.isChineseLocale) 1 /* Monday */ else 7 /* Sunday */

    override val weekdayNames: List<Pair<String, String>>
        get() = if (locale.isChineseLocale) ZH_WEEKDAY_NAMES else EN_WEEKDAY_NAMES

    private val systemTZ get() = TimeZone.currentSystemDefault()

    override fun getDateInputFormat(locale: CalendarLocale): DateInputFormat {
        return if (locale.isChineseLocale) {
            DateInputFormat(patternWithDelimiters = "yyyy/MM/dd", delimiter = '/')
        } else {
            DateInputFormat(patternWithDelimiters = "MM/dd/yyyy", delimiter = '/')
        }
    }

    override fun getCanonicalDate(timeInMillis: Long): CalendarDate {
        return Instant
            .fromEpochMilliseconds(timeInMillis)
            .toLocalDateTime(TimeZone.UTC)
            .date
            .atStartOfDayIn(TimeZone.UTC)
            .toCalendarDate(TimeZone.UTC)
    }

    override fun getMonth(timeInMillis: Long): CalendarMonth {
        return Instant
            .fromEpochMilliseconds(timeInMillis)
            .toCalendarMonth(TimeZone.UTC)
    }

    override fun getMonth(date: CalendarDate): CalendarMonth {
        return getMonth(date.utcTimeMillis)
    }

    override fun getMonth(year: Int, month: Int): CalendarMonth {
        val instant = LocalDate(
            year = year,
            monthNumber = month,
            dayOfMonth = 1,
        ).atTime(Midnight)
            .toInstant(TimeZone.UTC)

        return getMonth(instant.toEpochMilliseconds())
    }

    override fun getDayOfWeek(date: CalendarDate): Int {
        return LocalDate(
            year = date.year,
            monthNumber = date.month,
            dayOfMonth = date.dayOfMonth
        ).dayOfWeek.isoDayNumber
    }

    override fun plusMonths(from: CalendarMonth, addedMonthsCount: Int): CalendarMonth {
        return Instant
            .fromEpochMilliseconds(from.startUtcTimeMillis)
            .toLocalDateTime(TimeZone.UTC)
            .date
            .plus(DatePeriod(months = addedMonthsCount))
            .atTime(Midnight)
            .toInstant(TimeZone.UTC)
            .toCalendarMonth(TimeZone.UTC)
    }

    override fun minusMonths(from: CalendarMonth, subtractedMonthsCount: Int): CalendarMonth {
        return plusMonths(from, -subtractedMonthsCount)
    }

    override fun formatWithPattern(
        utcTimeMillis: Long,
        pattern: String,
        locale: CalendarLocale
    ): String {
        val dateTime = Instant
            .fromEpochMilliseconds(utcTimeMillis)
            .toLocalDateTime(TimeZone.UTC)

        val isChinese = locale.isChineseLocale

        return buildString {
            var i = 0
            while (i < pattern.length) {
                val ch = pattern[i]
                if (ch == '\'') {
                    // Handle quoted literal text
                    val end = pattern.indexOf('\'', i + 1)
                    if (end == -1) {
                        append(pattern.substring(i + 1))
                        break
                    }
                    append(pattern.substring(i + 1, end))
                    i = end + 1
                    continue
                }

                // Count consecutive same characters
                var count = 1
                while (i + count < pattern.length && pattern[i + count] == ch) count++

                when (ch) {
                    'y' -> {
                        val year = dateTime.year.toString()
                        append(if (count <= 2) year.takeLast(2) else year.padStart(4, '0'))
                    }
                    'M' -> when (count) {
                        1 -> append(dateTime.monthNumber)
                        2 -> append(dateTime.monthNumber.toString().padStart(2, '0'))
                        3 -> append(
                            if (isChinese) ZH_MONTH_ABBR[dateTime.monthNumber - 1]
                            else EN_MONTH_ABBR[dateTime.monthNumber - 1]
                        )
                        else -> append(
                            if (isChinese) ZH_MONTH_FULL[dateTime.monthNumber - 1]
                            else EN_MONTH_FULL[dateTime.monthNumber - 1]
                        )
                    }
                    'd' -> {
                        val day = dateTime.dayOfMonth.toString()
                        append(if (count >= 2) day.padStart(2, '0') else day)
                    }
                    'E' -> when {
                        count >= 4 -> append(
                            if (isChinese)
                                ZH_WEEKDAY_NAMES[dateTime.dayOfWeek.isoDayNumber - 1].first
                            else
                                EN_WEEKDAY_NAMES[dateTime.dayOfWeek.isoDayNumber - 1].first
                        )
                        else -> append(
                            if (isChinese)
                                ZH_WEEKDAY_NAMES[dateTime.dayOfWeek.isoDayNumber - 1].second
                            else
                                EN_WEEKDAY_NAMES[dateTime.dayOfWeek.isoDayNumber - 1].second
                        )
                    }
                    else -> {
                        // Pass through other characters (spaces, delimiters like '/', '-', '年', '月', '日')
                        repeat(count) { append(ch) }
                    }
                }
                i += count
            }
        }
    }

    override fun parse(date: String, pattern: String): CalendarDate? {
        return try {
            // Extract positions of y, M, d from the pattern
            val yearIndex = pattern.indexOf('y')
            val monthIndex = pattern.indexOf('M')
            val dayIndex = pattern.indexOf('d')

            if (yearIndex == -1 || monthIndex == -1 || dayIndex == -1) return null

            // Find the delimiter
            val delimiter = pattern.firstOrNull { it != 'y' && it != 'M' && it != 'd' }
                ?: return null

            val parts = date.split(delimiter)
            if (parts.size != 3) return null

            // Determine order from pattern
            val positions = listOf(
                yearIndex to 'y',
                monthIndex to 'M',
                dayIndex to 'd'
            ).sortedBy { it.first }

            var year = 0
            var month = 0
            var day = 0
            positions.forEachIndexed { index, (_, type) ->
                val value = parts[index].toIntOrNull() ?: return null
                when (type) {
                    'y' -> year = value
                    'M' -> month = value
                    'd' -> day = value
                }
            }

            if (year < 1 || month < 1 || month > 12 || day < 1 || day > 31) return null

            val localDate = LocalDate(year, month, day)
            CalendarDate(
                year = localDate.year,
                month = localDate.monthNumber,
                dayOfMonth = localDate.dayOfMonth,
                utcTimeMillis = localDate.atTime(Midnight)
                    .toInstant(TimeZone.UTC)
                    .toEpochMilliseconds()
            )
        } catch (_: Exception) {
            null
        }
    }

    private fun Instant.toCalendarMonth(timeZone: TimeZone): CalendarMonth {
        val dateTime = toLocalDateTime(timeZone)
        val monthStart = LocalDate(
            year = dateTime.year,
            month = dateTime.month,
            dayOfMonth = 1,
        )
        return CalendarMonth(
            year = dateTime.year,
            month = dateTime.monthNumber,
            numberOfDays = dateTime.month.numberOfDays(dateTime.year.isLeapYear()),
            daysFromStartOfWeekToFirstOfMonth = monthStart.daysFromStartOfWeekToFirstOfMonth(),
            startUtcTimeMillis = monthStart
                .atTime(Midnight)
                .toInstant(TimeZone.UTC)
                .toEpochMilliseconds()
        )
    }

    private fun LocalDate.daysFromStartOfWeekToFirstOfMonth() =
        (dayOfWeek.isoDayNumber - firstDayOfWeek).let { if (it >= 0) it else 7 + it }

    companion object {
        private val Midnight = LocalTime(0, 0)

        // Monday-indexed weekday names (ISO order: Mon=1 .. Sun=7)
        private val EN_WEEKDAY_NAMES = listOf(
            "Monday" to "M",
            "Tuesday" to "T",
            "Wednesday" to "W",
            "Thursday" to "T",
            "Friday" to "F",
            "Saturday" to "S",
            "Sunday" to "S"
        )

        private val ZH_WEEKDAY_NAMES = listOf(
            "星期一" to "一",
            "星期二" to "二",
            "星期三" to "三",
            "星期四" to "四",
            "星期五" to "五",
            "星期六" to "六",
            "星期日" to "日"
        )

        private val EN_MONTH_FULL = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

        private val EN_MONTH_ABBR = listOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        private val ZH_MONTH_FULL = listOf(
            "一月", "二月", "三月", "四月", "五月", "六月",
            "七月", "八月", "九月", "十月", "十一月", "十二月"
        )

        private val ZH_MONTH_ABBR = listOf(
            "1月", "2月", "3月", "4月", "5月", "6月",
            "7月", "8月", "9月", "10月", "11月", "12月"
        )

        private fun Int.isLeapYear() = this % 4 == 0 && (this % 100 != 0 || this % 400 == 0)

        private fun Month.numberOfDays(isLeap: Boolean): Int {
            return when (this) {
                Month.FEBRUARY -> if (isLeap) 29 else 28
                Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY,
                Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
                else -> 30
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
internal fun Instant.toCalendarDate(timeZone: TimeZone): CalendarDate {
    val dateTime = toLocalDateTime(timeZone)
    return CalendarDate(
        year = dateTime.year,
        month = dateTime.monthNumber,
        dayOfMonth = dateTime.dayOfMonth,
        utcTimeMillis = toEpochMilliseconds()
    )
}
