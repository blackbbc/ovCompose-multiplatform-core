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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.intl.ohosSystemLanguageTag

@Composable
@ReadOnlyComposable
internal actual fun getString(string: Strings): String {
    val isChinese = ohosSystemLanguageTag.startsWith("zh")
    return if (isChinese) getStringZh(string) else getStringEn(string)
}

private fun getStringEn(string: Strings): String {
    return when (string) {
        Strings.NavigationMenu -> "Navigation menu"
        Strings.CloseDrawer -> "Close navigation menu"
        Strings.CloseSheet -> "Close sheet"
        Strings.DefaultErrorMessage -> "Invalid input"
        Strings.ExposedDropdownMenu -> "Dropdown menu"
        Strings.SliderRangeStart -> "Range Start"
        Strings.SliderRangeEnd -> "Range End"
        Strings.Dialog -> "Dialog"
        Strings.MenuExpanded -> "Expanded"
        Strings.MenuCollapsed -> "Collapsed"
        Strings.SnackbarDismiss -> "Dismiss"
        Strings.SearchBarSearch -> "Search"
        Strings.SuggestionsAvailable -> "Suggestions below"
        Strings.DatePickerTitle -> "Select date"
        Strings.DatePickerHeadline -> "Selected date"
        Strings.DatePickerYearPickerPaneTitle -> "Year picker visible"
        Strings.DatePickerSwitchToYearSelection -> "Switch to selecting a year"
        Strings.DatePickerSwitchToDaySelection ->
            "Swipe to select a year, or tap to switch back to selecting a day"
        Strings.DatePickerSwitchToNextMonth -> "Change to next month"
        Strings.DatePickerSwitchToPreviousMonth -> "Change to previous month"
        Strings.DatePickerNavigateToYearDescription -> "Navigate to year %1$"
        Strings.DatePickerHeadlineDescription -> "Current selection: %1$"
        Strings.DatePickerNoSelectionDescription -> "None"
        Strings.DatePickerTodayDescription -> "Today"
        Strings.DatePickerScrollToShowLaterYears -> "Scroll to show later years"
        Strings.DatePickerScrollToShowEarlierYears -> "Scroll to show earlier years"
        Strings.DateInputTitle -> "Select date"
        Strings.DateInputHeadline -> "Entered date"
        Strings.DateInputLabel -> "Date"
        Strings.DateInputHeadlineDescription -> "Entered date: %1$"
        Strings.DateInputNoInputDescription -> "None"
        Strings.DateInputInvalidNotAllowed -> "Date not allowed: %1$"
        Strings.DateInputInvalidForPattern -> "Date does not match expected pattern: %1$"
        Strings.DateInputInvalidYearRange -> "Date out of expected year range %1$ - %2$"
        Strings.DatePickerSwitchToCalendarMode -> "Switch to calendar input mode"
        Strings.DatePickerSwitchToInputMode -> "Switch to text input mode"
        Strings.DateRangePickerTitle -> "Select dates"
        Strings.DateRangePickerStartHeadline -> "Start date"
        Strings.DateRangePickerEndHeadline -> "End date"
        Strings.DateRangePickerScrollToShowNextMonth -> "Scroll to show the next month"
        Strings.DateRangePickerScrollToShowPreviousMonth -> "Scroll to show the previous month"
        Strings.DateRangePickerDayInRange -> "In range"
        Strings.DateRangeInputTitle -> "Enter dates"
        Strings.DateRangeInputInvalidRangeInput -> "Invalid date range input"
        Strings.BottomSheetPaneTitle -> "Bottom Sheet"
        Strings.BottomSheetDragHandleDescription -> "Drag Handle"
        Strings.BottomSheetPartialExpandDescription -> "Collapse bottom sheet"
        Strings.BottomSheetDismissDescription -> "Dismiss bottom sheet"
        Strings.BottomSheetExpandDescription -> "Expand bottom sheet"
        Strings.TooltipLongPressLabel -> "Show tooltip"
        Strings.TimePickerAM -> "AM"
        Strings.TimePickerPM -> "PM"
        Strings.TimePickerPeriodToggle -> "Select AM or PM"
        Strings.TimePickerMinuteSelection -> "Select minutes"
        Strings.TimePickerHourSelection -> "Select hour"
        Strings.TimePickerHourSuffix -> "%1$ o'clock"
        Strings.TimePicker24HourSuffix -> "%1$ hours"
        Strings.TimePickerMinuteSuffix -> "%1$ minutes"
        Strings.TimePickerMinute -> "Minute"
        Strings.TimePickerHour -> "Hour"
        Strings.TimePickerMinuteTextField -> "for minutes"
        Strings.TimePickerHourTextField -> "for hour"
        Strings.TooltipPaneDescription -> "Tooltip"
        else -> ""
    }
}

private fun getStringZh(string: Strings): String {
    return when (string) {
        Strings.NavigationMenu -> "\u5bfc\u822a\u83dc\u5355" // 导航菜单
        Strings.CloseDrawer -> "\u5173\u95ed\u5bfc\u822a\u83dc\u5355" // 关闭导航菜单
        Strings.CloseSheet -> "\u5173\u95ed\u5de5\u4f5c\u8868" // 关闭工作表
        Strings.DefaultErrorMessage -> "\u8f93\u5165\u65e0\u6548" // 输入无效
        Strings.ExposedDropdownMenu -> "\u4e0b\u62c9\u83dc\u5355" // 下拉菜单
        Strings.SliderRangeStart -> "\u8303\u56f4\u8d77\u70b9" // 范围起点
        Strings.SliderRangeEnd -> "\u8303\u56f4\u7ec8\u70b9" // 范围终点
        Strings.Dialog -> "\u5bf9\u8bdd\u6846" // 对话框
        Strings.MenuExpanded -> "\u5df2\u5c55\u5f00" // 已展开
        Strings.MenuCollapsed -> "\u5df2\u6536\u8d77" // 已收起
        Strings.SnackbarDismiss -> "\u5173\u95ed" // 关闭
        Strings.SearchBarSearch -> "\u641c\u7d22" // 搜索
        Strings.SuggestionsAvailable -> "\u4ee5\u4e0b\u662f\u641c\u7d22\u5efa\u8bae" // 以下是搜索建议
        Strings.DatePickerTitle -> "\u9009\u62e9\u65e5\u671f" // 选择日期
        Strings.DatePickerHeadline -> "\u9009\u5b9a\u7684\u65e5\u671f" // 选定的日期
        Strings.DatePickerYearPickerPaneTitle -> "\u5e74\u4efd\u9009\u62e9\u5668\u53ef\u89c1" // 年份选择器可见
        Strings.DatePickerSwitchToYearSelection -> "\u5207\u6362\u4e3a\u9009\u62e9\u5e74\u4efd" // 切换为选择年份
        Strings.DatePickerSwitchToDaySelection ->
            "\u6ed1\u52a8\u53ef\u9009\u62e9\u5e74\u4efd\uff0c\u70b9\u6309\u53ef\u5207\u6362\u56de\u9009\u62e9\u65e5\u671f" // 滑动可选择年份，点按可切换回选择日期
        Strings.DatePickerSwitchToNextMonth -> "\u8f6c\u5230\u4e0b\u4e2a\u6708" // 转到下个月
        Strings.DatePickerSwitchToPreviousMonth -> "\u8f6c\u5230\u4e0a\u4e2a\u6708" // 转到上个月
        Strings.DatePickerNavigateToYearDescription -> "\u5207\u6362\u5230\u5e74\u4efd\uff1a%1$" // 切换到年份：%1$
        Strings.DatePickerHeadlineDescription -> "\u5f53\u524d\u7684\u9009\u62e9\uff1a%1$" // 当前的选择：%1$
        Strings.DatePickerNoSelectionDescription -> "\u65e0" // 无
        Strings.DatePickerTodayDescription -> "\u4eca\u5929" // 今天
        Strings.DatePickerScrollToShowLaterYears -> "\u6eda\u52a8\u5373\u53ef\u663e\u793a\u4e4b\u540e\u7684\u5e74\u4efd" // 滚动即可显示之后的年份
        Strings.DatePickerScrollToShowEarlierYears -> "\u6eda\u52a8\u5373\u53ef\u663e\u793a\u4e4b\u524d\u7684\u5e74\u4efd" // 滚动即可显示之前的年份
        Strings.DateInputTitle -> "\u9009\u62e9\u65e5\u671f" // 选择日期
        Strings.DateInputHeadline -> "\u8f93\u5165\u7684\u65e5\u671f" // 输入的日期
        Strings.DateInputLabel -> "\u65e5\u671f" // 日期
        Strings.DateInputHeadlineDescription -> "\u8f93\u5165\u7684\u65e5\u671f\uff1a%1$" // 输入的日期：%1$
        Strings.DateInputNoInputDescription -> "\u65e0" // 无
        Strings.DateInputInvalidNotAllowed -> "\u65e5\u671f\u65e0\u6548\uff1a%1$" // 日期无效：%1$
        Strings.DateInputInvalidForPattern -> "\u65e5\u671f\u4e0d\u7b26\u5408\u683c\u5f0f\u8981\u6c42\uff1a%1$" // 日期不符合格式要求：%1$
        Strings.DateInputInvalidYearRange -> "\u65e5\u671f\u8d85\u51fa\u9884\u671f\u5e74\u4efd\u8303\u56f4 %1$ - %2$" // 日期超出预期年份范围 %1$ - %2$
        Strings.DatePickerSwitchToCalendarMode -> "\u5207\u6362\u5230\u65e5\u5386\u8f93\u5165\u6a21\u5f0f" // 切换到日历输入模式
        Strings.DatePickerSwitchToInputMode -> "\u5207\u6362\u5230\u6587\u672c\u5b57\u6bb5\u8f93\u5165\u6a21\u5f0f" // 切换到文本字段输入模式
        Strings.DateRangePickerTitle -> "\u9009\u62e9\u65e5\u671f" // 选择日期
        Strings.DateRangePickerStartHeadline -> "\u5f00\u59cb\u65e5\u671f" // 开始日期
        Strings.DateRangePickerEndHeadline -> "\u7ed3\u675f\u65e5\u671f" // 结束日期
        Strings.DateRangePickerScrollToShowNextMonth -> "\u6eda\u52a8\u5373\u53ef\u663e\u793a\u4e0b\u4e2a\u6708" // 滚动即可显示下个月
        Strings.DateRangePickerScrollToShowPreviousMonth -> "\u6eda\u52a8\u5373\u53ef\u663e\u793a\u4e0a\u4e2a\u6708" // 滚动即可显示上个月
        Strings.DateRangePickerDayInRange -> "\u5728\u8303\u56f4\u5185" // 在范围内
        Strings.DateRangeInputTitle -> "\u8f93\u5165\u65e5\u671f" // 输入日期
        Strings.DateRangeInputInvalidRangeInput -> "\u8f93\u5165\u7684\u65e5\u671f\u8303\u56f4\u65e0\u6548" // 输入的日期范围无效
        Strings.BottomSheetPaneTitle -> "\u5e95\u90e8\u52a8\u4f5c\u6761" // 底部动作条
        Strings.BottomSheetDragHandleDescription -> "\u62d6\u52a8\u624b\u67c4" // 拖动手柄
        Strings.BottomSheetPartialExpandDescription -> "\u6536\u8d77\u5e95\u90e8\u52a8\u4f5c\u6761" // 收起底部动作条
        Strings.BottomSheetDismissDescription -> "\u5173\u95ed\u5e95\u90e8\u52a8\u4f5c\u6761" // 关闭底部动作条
        Strings.BottomSheetExpandDescription -> "\u5c55\u5f00\u5e95\u90e8\u52a8\u4f5c\u6761" // 展开底部动作条
        Strings.TooltipLongPressLabel -> "\u663e\u793a\u63d0\u793a" // 显示提示
        Strings.TimePickerAM -> "\u4e0a\u5348" // 上午
        Strings.TimePickerPM -> "\u4e0b\u5348" // 下午
        Strings.TimePickerPeriodToggle -> "\u9009\u62e9\u4e0a\u5348\u6216\u4e0b\u5348" // 选择上午或下午
        Strings.TimePickerMinuteSelection -> "\u9009\u62e9\u5206\u949f" // 选择分钟
        Strings.TimePickerHourSelection -> "\u9009\u62e9\u5c0f\u65f6" // 选择小时
        Strings.TimePickerHourSuffix -> "%1$ \u70b9" // %1$ 点
        Strings.TimePicker24HourSuffix -> "%1$ \u5c0f\u65f6" // %1$ 小时
        Strings.TimePickerMinuteSuffix -> "%1$ \u5206\u949f" // %1$ 分钟
        Strings.TimePickerMinute -> "\u5206\u949f" // 分钟
        Strings.TimePickerHour -> "\u5c0f\u65f6" // 小时
        Strings.TimePickerMinuteTextField -> "\u8f93\u5165\u5206\u949f" // 输入分钟
        Strings.TimePickerHourTextField -> "\u8f93\u5165\u5c0f\u65f6" // 输入小时
        Strings.TooltipPaneDescription -> "\u63d0\u793a" // 提示
        else -> ""
    }
}

@Immutable
internal actual value class Strings actual constructor(actual val value: Int) {
    actual companion object {
        actual val NavigationMenu = Strings(0)
        actual val CloseDrawer = Strings(1)
        actual val CloseSheet = Strings(2)
        actual val DefaultErrorMessage = Strings(3)
        actual val SliderRangeStart = Strings(4)
        actual val SliderRangeEnd = Strings(5)
        actual val Dialog = Strings(6)
        actual val MenuExpanded = Strings(7)
        actual val MenuCollapsed = Strings(8)
        actual val SnackbarDismiss = Strings(9)
        actual val SearchBarSearch = Strings(10)
        actual val SuggestionsAvailable = Strings(11)
        actual val DatePickerTitle = Strings(12)
        actual val DatePickerHeadline = Strings(13)
        actual val DatePickerYearPickerPaneTitle = Strings(14)
        actual val DatePickerSwitchToYearSelection = Strings(15)
        actual val DatePickerSwitchToDaySelection = Strings(16)
        actual val DatePickerSwitchToNextMonth = Strings(17)
        actual val DatePickerSwitchToPreviousMonth = Strings(18)
        actual val DatePickerNavigateToYearDescription = Strings(19)
        actual val DatePickerHeadlineDescription = Strings(20)
        actual val DatePickerNoSelectionDescription = Strings(21)
        actual val DatePickerTodayDescription = Strings(22)
        actual val DatePickerScrollToShowLaterYears = Strings(23)
        actual val DatePickerScrollToShowEarlierYears = Strings(24)
        actual val DateInputTitle = Strings(25)
        actual val DateInputHeadline = Strings(26)
        actual val DateInputLabel = Strings(27)
        actual val DateInputHeadlineDescription = Strings(28)
        actual val DateInputNoInputDescription = Strings(29)
        actual val DateInputInvalidNotAllowed = Strings(30)
        actual val DateInputInvalidForPattern = Strings(31)
        actual val DateInputInvalidYearRange = Strings(32)
        actual val DatePickerSwitchToCalendarMode = Strings(33)
        actual val DatePickerSwitchToInputMode = Strings(34)
        actual val DateRangePickerTitle = Strings(35)
        actual val DateRangePickerStartHeadline = Strings(36)
        actual val DateRangePickerEndHeadline = Strings(37)
        actual val DateRangePickerScrollToShowNextMonth = Strings(38)
        actual val DateRangePickerScrollToShowPreviousMonth = Strings(39)
        actual val DateRangePickerDayInRange = Strings(40)
        actual val DateRangeInputTitle = Strings(41)
        actual val DateRangeInputInvalidRangeInput = Strings(42)
        actual val BottomSheetPaneTitle = Strings(43)
        actual val BottomSheetDragHandleDescription = Strings(44)
        actual val BottomSheetPartialExpandDescription = Strings(45)
        actual val BottomSheetDismissDescription = Strings(46)
        actual val BottomSheetExpandDescription = Strings(47)
        actual val TooltipLongPressLabel = Strings(48)
        actual val TimePickerAM = Strings(49)
        actual val TimePickerPM = Strings(50)
        actual val TimePickerPeriodToggle = Strings(51)
        actual val TimePickerHourSelection = Strings(52)
        actual val TimePickerMinuteSelection = Strings(53)
        actual val TimePickerHourSuffix = Strings(54)
        actual val TimePicker24HourSuffix = Strings(55)
        actual val TimePickerMinuteSuffix = Strings(56)
        actual val TimePickerHour = Strings(57)
        actual val TimePickerMinute = Strings(58)
        actual val TimePickerHourTextField = Strings(59)
        actual val TimePickerMinuteTextField = Strings(60)
        actual val TooltipPaneDescription = Strings(61)
        actual val ExposedDropdownMenu = Strings(62)
    }
}

internal actual fun String.format(vararg formatArgs: Any?): String {
    var result = this
    formatArgs.forEachIndexed { index, arg ->
        result = result.replace("%${index + 1}$", arg.toString())
    }
    return result
}
