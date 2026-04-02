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

/**
 * Creates a [CalendarModel] to be used by the date picker.
 *
 * @param locale a [CalendarLocale] that will be used by the created model
 */
@ExperimentalMaterial3Api
internal actual fun createCalendarModel(locale: CalendarLocale): CalendarModel {
    return OhosCalendarModelImpl(locale)
}

/**
 * Formats a UTC timestamp into a string with a given date format skeleton.
 *
 * Maps common ICU skeletons to locale-appropriate patterns, then delegates
 * to [OhosCalendarModelImpl.formatWithPattern] for the actual formatting.
 *
 * @param utcTimeMillis a UTC timestamp to format (milliseconds from epoch)
 * @param skeleton a date format skeleton
 * @param locale the [CalendarLocale] to use when formatting the given timestamp
 * @param cache a [MutableMap] for caching (unused in this implementation)
 */
@ExperimentalMaterial3Api
actual fun formatWithSkeleton(
    utcTimeMillis: Long,
    skeleton: String,
    locale: CalendarLocale,
    cache: MutableMap<String, Any>
): String {
    val pattern = skeletonToPattern(skeleton, locale)
    return OhosCalendarModelImpl(locale).formatWithPattern(utcTimeMillis, pattern, locale)
}

@ExperimentalMaterial3Api
private fun skeletonToPattern(skeleton: String, locale: CalendarLocale): String {
    if (locale.isChineseLocale) {
        return when (skeleton) {
            DatePickerDefaults.YearMonthSkeleton -> "yyyy\u5E74M\u6708" // yyyy年M月
            DatePickerDefaults.YearAbbrMonthDaySkeleton -> "yyyy\u5E74M\u6708d\u65E5" // yyyy年M月d日
            DatePickerDefaults.YearMonthWeekdayDaySkeleton -> "yyyy\u5E74M\u6708d\u65E5EEEE" // yyyy年M月d日EEEE
            else -> skeleton
        }
    }
    return when (skeleton) {
        DatePickerDefaults.YearMonthSkeleton -> "MMMM yyyy"
        DatePickerDefaults.YearAbbrMonthDaySkeleton -> "MMM d, yyyy"
        DatePickerDefaults.YearMonthWeekdayDaySkeleton -> "EEEE, MMMM d, yyyy"
        else -> skeleton
    }
}
