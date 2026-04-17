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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Density
import androidx.compose.ui.platform.WindowInfo

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal actual fun getCurrentConfiguration() = Configuration(LocalWindowInfo.current)

@OptIn(ExperimentalComposeUiApi::class)
internal actual fun Configuration.getScreenWidthPx(density: Density): Int {
    val windowInfo = delegate as WindowInfo
    return windowInfo.containerSize.width
}
