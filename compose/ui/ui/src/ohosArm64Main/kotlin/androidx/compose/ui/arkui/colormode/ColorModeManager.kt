package androidx.compose.ui.arkui.colormode

import androidx.compose.ui.arkui.messenger.MessengerOwner
import androidx.compose.ui.arkui.messenger.send

/**
 * 颜色模式管理器，通过 messenger 从 ArkTS 侧获取系统深色/亮色模式。
 * 仿照 DensityManager 模式。
 */
internal class ColorModeManager(
    messengerOwner: MessengerOwner
) : MessengerOwner by messengerOwner {

    private var colorModeChangedCallback: ((Int) -> Unit)? = null

    init {
        messenger.onReceive(RECEIVE_COLOR_MODE_CHANGED) { message ->
            val mode = message.toIntOrNull() ?: COLOR_MODE_LIGHT
            colorModeChangedCallback?.invoke(mode)
            ""
        }
    }

    fun getColorMode(): Int {
        val result = messenger.send(SEND_GET_COLOR_MODE)
        return result?.toIntOrNull() ?: COLOR_MODE_LIGHT
    }

    fun onColorModeChanged(callback: (Int) -> Unit) {
        colorModeChangedCallback = callback
    }

    companion object {
        private const val SEND_GET_COLOR_MODE = "compose.ui:ColorMode.getColorMode"
        private const val RECEIVE_COLOR_MODE_CHANGED = "compose.ui:ColorMode.onColorModeChanged"
        const val COLOR_MODE_DARK = 0
        const val COLOR_MODE_LIGHT = 1
    }
}
