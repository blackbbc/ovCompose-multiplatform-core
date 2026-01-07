/*
 * Copyright 2026 The Android Open Source Project
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

package androidx.compose.ui.input.key

import org.jetbrains.skiko.SkikoKey

fun Int.asKey(): SkikoKey = when (this) {
    // System keys
    1 -> SkikoKey.KEY_HOME
    
    // Numbers 0-9
    2000 -> SkikoKey.KEY_0
    2001 -> SkikoKey.KEY_1
    2002 -> SkikoKey.KEY_2
    2003 -> SkikoKey.KEY_3
    2004 -> SkikoKey.KEY_4
    2005 -> SkikoKey.KEY_5
    2006 -> SkikoKey.KEY_6
    2007 -> SkikoKey.KEY_7
    2008 -> SkikoKey.KEY_8
    2009 -> SkikoKey.KEY_9
    
    // Direction keys
    2012 -> SkikoKey.KEY_UP
    2013 -> SkikoKey.KEY_DOWN
    2014 -> SkikoKey.KEY_LEFT
    2015 -> SkikoKey.KEY_RIGHT
    
    // Letters A-Z
    2017 -> SkikoKey.KEY_A
    2018 -> SkikoKey.KEY_B
    2019 -> SkikoKey.KEY_C
    2020 -> SkikoKey.KEY_D
    2021 -> SkikoKey.KEY_E
    2022 -> SkikoKey.KEY_F
    2023 -> SkikoKey.KEY_G
    2024 -> SkikoKey.KEY_H
    2025 -> SkikoKey.KEY_I
    2026 -> SkikoKey.KEY_J
    2027 -> SkikoKey.KEY_K
    2028 -> SkikoKey.KEY_L
    2029 -> SkikoKey.KEY_M
    2030 -> SkikoKey.KEY_N
    2031 -> SkikoKey.KEY_O
    2032 -> SkikoKey.KEY_P
    2033 -> SkikoKey.KEY_Q
    2034 -> SkikoKey.KEY_R
    2035 -> SkikoKey.KEY_S
    2036 -> SkikoKey.KEY_T
    2037 -> SkikoKey.KEY_U
    2038 -> SkikoKey.KEY_V
    2039 -> SkikoKey.KEY_W
    2040 -> SkikoKey.KEY_X
    2041 -> SkikoKey.KEY_Y
    2042 -> SkikoKey.KEY_Z
    
    // Symbols and punctuation
    2043 -> SkikoKey.KEY_COMMA
    2044 -> SkikoKey.KEY_PERIOD
    2045 -> SkikoKey.KEY_LEFT_ALT
    2046 -> SkikoKey.KEY_RIGHT_ALT
    2047 -> SkikoKey.KEY_LEFT_SHIFT
    2048 -> SkikoKey.KEY_RIGHT_SHIFT
    2049 -> SkikoKey.KEY_TAB
    2050 -> SkikoKey.KEY_SPACE
    2054 -> SkikoKey.KEY_ENTER
    2055 -> SkikoKey.KEY_BACKSPACE
    2056 -> SkikoKey.KEY_BACK_QUOTE
    2057 -> SkikoKey.KEY_MINUS
    2058 -> SkikoKey.KEY_EQUALS
    2059 -> SkikoKey.KEY_OPEN_BRACKET
    2060 -> SkikoKey.KEY_CLOSE_BRACKET
    2061 -> SkikoKey.KEY_BACKSLASH
    2062 -> SkikoKey.KEY_SEMICOLON
    2063 -> SkikoKey.KEY_QUOTE
    2064 -> SkikoKey.KEY_SLASH
    
    // Special keys
    2067 -> SkikoKey.KEY_MENU
    2068 -> SkikoKey.KEY_PGUP
    2069 -> SkikoKey.KEY_PGDOWN
    2070 -> SkikoKey.KEY_ESCAPE
    2071 -> SkikoKey.KEY_DELETE
    2072 -> SkikoKey.KEY_LEFT_CONTROL
    2073 -> SkikoKey.KEY_RIGHT_CONTROL
    2074 -> SkikoKey.KEY_CAPSLOCK
    2075 -> SkikoKey.KEY_SCROLL_LOCK
    2076 -> SkikoKey.KEY_LEFT_META
    2077 -> SkikoKey.KEY_RIGHT_META
    2079 -> SkikoKey.KEY_PRINTSCEEN
    2080 -> SkikoKey.KEY_PAUSE
    2081 -> SkikoKey.KEY_HOME
    2082 -> SkikoKey.KEY_END
    2083 -> SkikoKey.KEY_INSERT
    
    // Function keys F1-F12
    2090 -> SkikoKey.KEY_F1
    2091 -> SkikoKey.KEY_F2
    2092 -> SkikoKey.KEY_F3
    2093 -> SkikoKey.KEY_F4
    2094 -> SkikoKey.KEY_F5
    2095 -> SkikoKey.KEY_F6
    2096 -> SkikoKey.KEY_F7
    2097 -> SkikoKey.KEY_F8
    2098 -> SkikoKey.KEY_F9
    2099 -> SkikoKey.KEY_F10
    2100 -> SkikoKey.KEY_F11
    2101 -> SkikoKey.KEY_F12
    
    // Numpad
    2102 -> SkikoKey.KEY_NUM_LOCK
    2103 -> SkikoKey.KEY_NUMPAD_0
    2104 -> SkikoKey.KEY_NUMPAD_1
    2105 -> SkikoKey.KEY_NUMPAD_2
    2106 -> SkikoKey.KEY_NUMPAD_3
    2107 -> SkikoKey.KEY_NUMPAD_4
    2108 -> SkikoKey.KEY_NUMPAD_5
    2109 -> SkikoKey.KEY_NUMPAD_6
    2110 -> SkikoKey.KEY_NUMPAD_7
    2111 -> SkikoKey.KEY_NUMPAD_8
    2112 -> SkikoKey.KEY_NUMPAD_9
    2113 -> SkikoKey.KEY_NUMPAD_DIVIDE
    2114 -> SkikoKey.KEY_NUMPAD_MULTIPLY
    2115 -> SkikoKey.KEY_NUMPAD_SUBTRACT
    2116 -> SkikoKey.KEY_NUMPAD_ADD
    2117 -> SkikoKey.KEY_NUMPAD_DECIMAL
    2119 -> SkikoKey.KEY_NUMPAD_ENTER
    
    // Unknown or unsupported keys
    else -> SkikoKey.KEY_UNKNOWN
}
