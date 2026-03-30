package androidx.compose.ui.arkui.locale

import androidx.compose.ui.arkui.messenger.MessengerOwner
import androidx.compose.ui.arkui.messenger.send

/**
 * 语言区域管理器，通过 messenger 从 ArkTS 侧获取系统语言和区域。
 * 仿照 DensityManager 模式。
 */
internal class LocaleManager(
    messengerOwner: MessengerOwner
) : MessengerOwner by messengerOwner {

    private var localeChangedCallback: ((language: String, region: String) -> Unit)? = null

    init {
        messenger.onReceive(RECEIVE_LOCALE_CHANGED) { message ->
            val parts = message.split("|")
            val language = parts.getOrElse(0) { "zh" }
            val region = parts.getOrElse(1) { "CN" }
            localeChangedCallback?.invoke(language, region)
            ""
        }
    }

    fun getSystemLanguage(): String {
        return messenger.send(SEND_GET_LANGUAGE) ?: "zh"
    }

    fun getSystemRegion(): String {
        return messenger.send(SEND_GET_REGION) ?: "CN"
    }

    fun onLocaleChanged(callback: (language: String, region: String) -> Unit) {
        localeChangedCallback = callback
    }

    companion object {
        private const val SEND_GET_LANGUAGE = "compose.ui:Locale.getLanguage"
        private const val SEND_GET_REGION = "compose.ui:Locale.getRegion"
        private const val RECEIVE_LOCALE_CHANGED = "compose.ui:Locale.onLocaleChanged"
    }
}
