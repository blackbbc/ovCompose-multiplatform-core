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

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.toIntRect
import kotlin.math.max

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@ExperimentalMaterial3Api
actual fun ExposedDropdownMenuBox(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier,
    content: @Composable ExposedDropdownMenuBoxScope.() -> Unit
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    var anchorWidth by remember { mutableIntStateOf(0) }
    var menuMaxHeight by remember { mutableIntStateOf(0) }
    val verticalMarginInPx = with(density) { MenuVerticalMargin.roundToPx() }

    val focusRequester = remember { FocusRequester() }
    val expandedDescription = getString(Strings.MenuExpanded)
    val collapsedDescription = getString(Strings.MenuCollapsed)

    val scope = remember(expanded, onExpandedChange, windowInfo, density) {
        object : ExposedDropdownMenuBoxScope() {
            override fun Modifier.menuAnchor(): Modifier = this
                .onGloballyPositioned {
                    anchorWidth = it.size.width
                    val boundsInWindow = it.boundsInWindow()
                    val visibleWindowBounds = windowInfo.containerSize.toIntRect()
                    val heightAbove = boundsInWindow.top - visibleWindowBounds.top
                    val heightBelow = visibleWindowBounds.height - boundsInWindow.bottom
                    menuMaxHeight = max(heightAbove, heightBelow).toInt() - verticalMarginInPx
                }
                .expandable(
                    expanded = expanded,
                    onExpandedChange = { onExpandedChange(!expanded) },
                    expandedDescription = expandedDescription,
                    collapsedDescription = collapsedDescription,
                )
                .focusRequester(focusRequester)

            override fun Modifier.exposedDropdownSize(matchTextFieldWidth: Boolean): Modifier =
                layout { measurable, constraints ->
                    val menuWidth = constraints.constrainWidth(anchorWidth)
                    val menuConstraints = constraints.copy(
                        maxHeight = constraints.constrainHeight(menuMaxHeight),
                        minWidth = if (matchTextFieldWidth) menuWidth else constraints.minWidth,
                        maxWidth = if (matchTextFieldWidth) menuWidth else constraints.maxWidth,
                    )
                    val placeable = measurable.measure(menuConstraints)
                    layout(placeable.width, placeable.height) {
                        placeable.place(0, 0)
                    }
                }
        }
    }

    Box(modifier) {
        scope.content()
    }

    SideEffect {
        if (expanded) focusRequester.requestFocus()
    }
}

@Composable
internal actual fun ExposedDropdownMenuBoxScope.ExposedDropdownMenuDefaultImpl(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    scrollState: ScrollState,
    content: @Composable ColumnScope.() -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier.exposedDropdownSize(),
        scrollState = scrollState,
        content = content
    )
}
