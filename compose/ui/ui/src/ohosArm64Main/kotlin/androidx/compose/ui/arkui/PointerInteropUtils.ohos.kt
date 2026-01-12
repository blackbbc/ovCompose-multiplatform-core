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

import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.napi.JsObject
import androidx.compose.ui.napi.asInt
import androidx.compose.ui.napi.asLong
import androidx.compose.ui.napi.nApiValue
import org.jetbrains.skiko.currentNanoTime
import platform.ohos.napi_value


class TouchEvent(nativeEvent: napi_value?) : JsObject(nativeEvent) {
    var type: Int?
        set(value) {
            this["type"] = value.nApiValue()
        }
        get() = get("type").asInt()

    var timestamp: Long
        set(value) {
            this["timestamp"] = value.nApiValue()
        }
        get() = requireNotNull(get("timestamp").asLong()) { "The timestamp of TouchEvent(ohos) was null!" }

    val nativeEvent: napi_value? get() = jsValue

    companion object {
        const val ACTION_DOWN = 0
        const val ACTION_UP = 1
        const val ACTION_MOVE = 2
        const val ACTION_CANCEL = 3
    }
}

class MouseEvent(nativeEvent: napi_value?) : JsObject(nativeEvent) {
    var action: Int
        set(value) {
            this["action"] = value.nApiValue()
        }
        get() = requireNotNull(get("action").asInt()) { "The type of MouseEvent(ohos) was null!" }

    var timestamp: Long
        set(value) {
            this["timestamp"] = value.nApiValue()
        }
        get() = requireNotNull(get("timestamp").asLong()) { "The timestamp of TouchEvent(ohos) was null!" }

    val nativeEvent: napi_value? get() = jsValue

    companion object {
        const val ACTION_CANCEL = 13
    }
}

class AxisEvent(nativeEvent: napi_value?) : JsObject(nativeEvent) {
    var action: Int
        set(value) {
            this["action"] = value.nApiValue()
        }
        get() = requireNotNull(get("action").asInt()) { "The type of AxisEvent(ohos) was null!" }

    var timestamp: Long
        set(value) {
            this["timestamp"] = value.nApiValue()
        }
        get() = requireNotNull(get("timestamp").asLong()) { "The timestamp of TouchEvent(ohos) was null!" }

    val nativeEvent: napi_value? get() = jsValue

    companion object {
        const val ACTION_CANCEL = 4
    }
}

class KeyEvent(nativeEvent: napi_value?) : JsObject(nativeEvent)

/**
 * Converts to a [TouchEvent] and runs [block] with it.
 *
 * @param block The block to be executed with the resulting [TouchEvent].
 */
internal fun PointerEvent.toTouchEventScope(
    block: (TouchEvent) -> Unit
) {
    toTouchEventScope(block, false)
}

/**
 * Converts to an [TouchEvent.ACTION_CANCEL] [TouchEvent] and runs [block] with it.
 *
 * @param block The block to be executed with the resulting [TouchEvent].
 */
internal fun PointerEvent.toCancelTouchEventScope(
    block: (TouchEvent) -> Unit
) {
    toTouchEventScope(block, true)
}

private fun PointerEvent.toTouchEventScope(
    block: (TouchEvent) -> Unit,
    cancel: Boolean
) {
    val mouseEvent = nativeEvent as? MouseEvent
    val axisEvent = nativeEvent as? AxisEvent
    var touchEvent = nativeEvent as? TouchEvent

    if (mouseEvent != null) {
        touchEvent = TouchEvent(mouseEvent.nativeEvent)
    } else if (axisEvent != null) {
        touchEvent = TouchEvent(axisEvent.nativeEvent)
    }

    if (touchEvent == null) return

    if (cancel) {
        if (mouseEvent != null) {
            val oldAction = mouseEvent.action
            val oldTimestamp = mouseEvent.timestamp

            mouseEvent.action = MouseEvent.ACTION_CANCEL
            mouseEvent.timestamp = currentNanoTime()

            touchEvent = TouchEvent(mouseEvent.nativeEvent)
            block(touchEvent)

            mouseEvent.timestamp = oldTimestamp
            mouseEvent.action = oldAction
        } else if (axisEvent != null) {
            val oldAction = axisEvent.action
            val oldTimestamp = axisEvent.timestamp

            axisEvent.action = AxisEvent.ACTION_CANCEL
            axisEvent.timestamp = currentNanoTime()

            touchEvent = TouchEvent(axisEvent.nativeEvent)
            block(touchEvent)

            axisEvent.timestamp = oldTimestamp
            axisEvent.action = oldAction
        } else {
            val oldType = touchEvent.type
            val oldTimestamp = touchEvent.timestamp

            touchEvent.type = TouchEvent.ACTION_CANCEL
            // The timestamp also need to be updated here to pass the validity check,
            // such as `PostEventManager::CheckPointValidity` in ohos.
            touchEvent.timestamp = currentNanoTime()

            block(touchEvent)

            touchEvent.timestamp = oldTimestamp
            touchEvent.type = oldType
        }
    } else {
        block(touchEvent)
    }
}
