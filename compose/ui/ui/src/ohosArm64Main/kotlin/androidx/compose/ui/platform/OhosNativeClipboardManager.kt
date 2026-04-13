/*
 * Copyright 2025 The Android Open Source Project
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

package androidx.compose.ui.platform

import androidx.compose.ui.text.AnnotatedString
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toKString
import platform.framework.OH_Pasteboard_Create
import platform.framework.OH_Pasteboard_Destroy
import platform.framework.OH_Pasteboard_GetData
import platform.framework.OH_Pasteboard_HasType
import platform.framework.OH_Pasteboard_SetData
import platform.framework.OH_UdmfData_AddRecord
import platform.framework.OH_UdmfData_Create
import platform.framework.OH_UdmfData_Destroy
import platform.framework.OH_UdmfData_GetRecord
import platform.framework.OH_UdmfRecord_AddPlainText
import platform.framework.OH_UdmfRecord_Create
import platform.framework.OH_UdmfRecord_Destroy
import platform.framework.OH_UdmfRecord_GetPlainText
import platform.framework.OH_UdsPlainText_Create
import platform.framework.OH_UdsPlainText_Destroy
import platform.framework.OH_UdsPlainText_GetContent
import platform.framework.OH_UdsPlainText_SetContent

/**
 * OHOS clipboard implementation using OH_Pasteboard NDK API.
 */
@OptIn(ExperimentalForeignApi::class)
internal class OhosNativeClipboardManager : ClipboardManager {

    override fun setText(annotatedString: AnnotatedString) {
        val pasteboard = OH_Pasteboard_Create() ?: return
        val data = OH_UdmfData_Create() ?: run {
            OH_Pasteboard_Destroy(pasteboard)
            return
        }
        val record = OH_UdmfRecord_Create() ?: run {
            OH_UdmfData_Destroy(data)
            OH_Pasteboard_Destroy(pasteboard)
            return
        }
        val plainText = OH_UdsPlainText_Create() ?: run {
            OH_UdmfRecord_Destroy(record)
            OH_UdmfData_Destroy(data)
            OH_Pasteboard_Destroy(pasteboard)
            return
        }

        OH_UdsPlainText_SetContent(plainText, annotatedString.text)
        OH_UdmfRecord_AddPlainText(record, plainText)
        OH_UdmfData_AddRecord(data, record)
        OH_Pasteboard_SetData(pasteboard, data)

        OH_UdsPlainText_Destroy(plainText)
        OH_UdmfRecord_Destroy(record)
        OH_UdmfData_Destroy(data)
        OH_Pasteboard_Destroy(pasteboard)
    }

    override fun getText(): AnnotatedString? {
        val pasteboard = OH_Pasteboard_Create() ?: return null
        if (!OH_Pasteboard_HasType(pasteboard, "text/plain")) {
            OH_Pasteboard_Destroy(pasteboard)
            return null
        }

        val content = memScoped {
            val status = alloc<IntVar>()
            val udmfData = OH_Pasteboard_GetData(pasteboard, status.ptr) ?: run {
                OH_Pasteboard_Destroy(pasteboard)
                return null
            }

            // record is an internal reference from udmfData — do NOT destroy it separately
            val record = OH_UdmfData_GetRecord(udmfData, 0u) ?: run {
                OH_UdmfData_Destroy(udmfData)
                OH_Pasteboard_Destroy(pasteboard)
                return null
            }

            val plainText = OH_UdsPlainText_Create() ?: run {
                OH_UdmfData_Destroy(udmfData)
                OH_Pasteboard_Destroy(pasteboard)
                return null
            }

            OH_UdmfRecord_GetPlainText(record, plainText)
            val text = OH_UdsPlainText_GetContent(plainText)?.toKString()

            OH_UdsPlainText_Destroy(plainText)
            // udmfData owns its records, destroying it cleans up everything
            OH_UdmfData_Destroy(udmfData)
            text
        }

        OH_Pasteboard_Destroy(pasteboard)
        return content?.let { AnnotatedString(it) }
    }

    override fun hasText(): Boolean {
        val pasteboard = OH_Pasteboard_Create() ?: return false
        val result = OH_Pasteboard_HasType(pasteboard, "text/plain")
        OH_Pasteboard_Destroy(pasteboard)
        return result
    }
}
