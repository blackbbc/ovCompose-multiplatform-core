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
 * Locales that typically use 12-hour format.
 */
private val twelveHourLocales = setOf("en-US", "en-AU", "en-PH", "en")

@ReadOnlyComposable
@Composable
internal actual fun is24HourFormat(): Boolean {
    val tag = ohosSystemLanguageTag
    // Most locales use 24-hour format; a few English-speaking locales default to 12-hour
    return tag !in twelveHourLocales && !tag.startsWith("en-US")
}
