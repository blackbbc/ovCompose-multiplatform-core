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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.tokens.DatePickerModalTokens
import androidx.compose.material3.tokens.DialogTokens
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
@ExperimentalMaterial3Api
actual fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier,
    dismissButton: @Composable (() -> Unit)?,
    shape: Shape,
    tonalElevation: Dp,
    colors: DatePickerColors,
    properties: DialogProperties,
    content: @Composable ColumnScope.() -> Unit
) {
    // Use Dialog directly instead of BasicAlertDialog, because BasicAlertDialog
    // on ohos is not yet implemented (empty body, never calls content).
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        val dialogPaneDescription = getString(Strings.Dialog)
        Box(
            modifier = modifier
                .wrapContentHeight()
                .sizeIn(minWidth = DialogMinWidth, maxWidth = DialogMaxWidth)
                .then(Modifier.semantics { paneTitle = dialogPaneDescription }),
            propagateMinConstraints = true
        ) {
            Surface(
                modifier = Modifier
                    .requiredWidth(DatePickerModalTokens.ContainerWidth)
                    .heightIn(max = DatePickerModalTokens.ContainerHeight),
                shape = shape,
                color = colors.containerColor,
                tonalElevation = tonalElevation,
            ) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    content()
                    // Buttons
                    Box(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(DialogButtonsPadding)
                    ) {
                        ProvideContentColorTextStyle(
                            contentColor = DialogTokens.ActionLabelTextColor.value,
                            textStyle =
                            MaterialTheme.typography.fromToken(DialogTokens.ActionLabelTextFont)
                        ) {
                            AlertDialogFlowRow(
                                mainAxisSpacing = DialogButtonsMainAxisSpacing,
                                crossAxisSpacing = DialogButtonsCrossAxisSpacing
                            ) {
                                dismissButton?.invoke()
                                confirmButton()
                            }
                        }
                    }
                }
            }
        }
    }
}

private val DialogButtonsPadding = PaddingValues(bottom = 8.dp, end = 6.dp)
private val DialogButtonsMainAxisSpacing = 8.dp
private val DialogButtonsCrossAxisSpacing = 12.dp
