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

@file:Suppress("DEPRECATION")

package androidx.compose.foundation.gestures

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.util.fastFold
import kotlin.math.abs

internal actual fun CompositionLocalConsumerModifierNode.platformScrollConfig(): ScrollConfig = OHOSScrollConfig

@ExperimentalFoundationApi
fun optOutOfCupertinoOverscroll() {
    OHOSScrollConfig.isRubberBandingOverscrollEnabled = false
}

internal object OHOSScrollConfig : ScrollConfig {
    var isRubberBandingOverscrollEnabled: Boolean = true

    /**
     * OHOS scroll event handling.
     * 
     * In ComposeSceneMediator.ohos.kt, scrollDelta is set to event.scrollX/scrollY directly,
     * where the values are in vp (viewport) units representing the intended scroll distance.
     * 
     * This function converts vp to pixels by multiplying with density:
     * - scrollDelta (vp) * density = pixels
     * 
     * Unlike Android which uses abstract "tick" values, OHOS provides physical distance
     * in vp units, so we don't apply the standard -64.dp multiplication.
     */
    override fun Density.calculateMouseWheelScroll(event: PointerEvent, bounds: IntSize): Offset {
        // Convert vp to pixels: vp * density
        return event.changes.fastFold(Offset.Zero) { acc, c -> 
            acc + c.scrollDelta 
        } * -this.density
    }
    
    /**
     * Detect if this is a precise wheel scroll event (touchpad).
     * 
     * OHOS touchpad vs mouse wheel characteristics:
     * - Touchpad: scrollDelta range 0.5 ~ 30 vp (continuous, variable values)
     * - Mouse wheel: scrollDelta = 45 vp (discrete, fixed value)
     * 
     * If detected as touchpad, MouseWheelScrollable will apply scrolling immediately
     * for real-time response. Otherwise, it will use animation for smoother experience.
     */
    override fun isPreciseWheelScroll(event: PointerEvent): Boolean {
        println("OHOSScrollConfig, scrollDelta:${event.changes.firstOrNull()?.scrollDelta}")
        val scrollDelta = event.changes.firstOrNull()?.scrollDelta ?: return false
        val maxDelta = maxOf(abs(scrollDelta.x), abs(scrollDelta.y))

        // Touchpad values are typically < 40 vp, mouse wheel = 45 vp
        return maxDelta > 0f && maxDelta < 40f
    }

    override val isSmoothScrollingEnabled: Boolean = false
}