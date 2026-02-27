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

package androidx.compose.ui.arkui

import androidx.compose.ui.napi.JsFunction
import androidx.compose.ui.napi.call
import androidx.compose.ui.napi.jsFunction
import androidx.compose.ui.napi.nApiValue

/**
 * Kotlin bridge for ArkWebViewNode (ETS side).
 *
 * Layout management (setTranslation, setSize, measure, etc.) is handled
 * by the internal [ArkUIView] which shares the same NAPI reference.
 *
 * This class adds WebView-specific methods: [loadUrl] and [evaluateJavascript].
 */
class ArkWebView internal constructor(
    internal val view: ArkUIView
) {
    /**
     * Load a URL in the WebView.
     */
    fun loadUrl(url: String) {
        view.jsArkUIViewRef?.call("loadUrl", url.nApiValue())
    }

    /**
     * Evaluate JavaScript in the WebView and optionally receive the result.
     *
     * @param script The JavaScript code to evaluate.
     * @param callback Optional callback to receive the result string. Called with null on error.
     */
    fun evaluateJavascript(script: String, callback: ((String?) -> Unit)? = null) {
        if (callback != null) {
            val jsCallback = jsFunction<ArkWebView, String?, Unit>(
                this
            ) { result: String? ->
                callback(result)
            }
            view.jsArkUIViewRef?.call(
                "evaluateJavascript",
                script.nApiValue(),
                jsCallback.jsValue
            )
            // Note: jsCallback is captured by the Promise.then closure on ETS side.
            // It cannot be disposed here since the callback hasn't fired yet.
            // TODO: consider auto-cleanup after callback, or switch to suspend API.
        } else {
            view.jsArkUIViewRef?.call("evaluateJavascript", script.nApiValue())
        }
    }

    /**
     * Dispose the WebView and release all NAPI resources.
     */
    fun dispose() {
        view.dispose()
    }
}
