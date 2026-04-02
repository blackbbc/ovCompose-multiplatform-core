/*
 * Tencent is pleased to support the open source community by making ovCompose available.
 * Copyright (C) 2025 Tencent. All rights reserved.
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
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.intl.ohosSystemLanguageTag

/**
 * Represents a Locale for the calendar on HarmonyOS.
 *
 * @param language the language code (e.g. "zh", "en")
 * @param region the region code (e.g. "CN", "US")
 */
@ExperimentalMaterial3Api
actual class CalendarLocale(
    val language: String = "zh",
    val region: String = "CN"
) {
    fun toLanguageTag(): String = if (region.isNotEmpty()) "$language-$region" else language

    val isChineseLocale: Boolean
        get() = language.startsWith("zh")

    companion object {
        fun fromLanguageTag(tag: String): CalendarLocale {
            val parts = tag.split("-")
            val language = parts.firstOrNull() ?: "zh"
            val region = parts.lastOrNull()?.takeIf { it.length == 2 && it[0].isUpperCase() } ?: ""
            return CalendarLocale(language, region)
        }
    }
}

/**
 * Returns the default [CalendarLocale] by reading the system language tag
 * set by [ComposeArkUIViewContainer] via [LocaleManager].
 */
@ReadOnlyComposable
@Composable
internal actual fun defaultLocale(): CalendarLocale {
    return CalendarLocale.fromLanguageTag(ohosSystemLanguageTag)
}

/**
 * Returns a string representation of an integer for the current Locale.
 */
internal actual fun Int.toLocalString(minDigits: Int): String {
    return toString().padStart(minDigits, '0')
}
