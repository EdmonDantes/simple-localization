/*
 * Copyright (c) 2022. Ilia Loginov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.edmondantes.simple.localization.exception

import io.github.edmondantes.simple.localization.Localizer

/**
 * It is thrown if [Localizer] found not escaped characters in localized string
 */
class NotEscapedCharacterException(
        /**
         * String which constance not escaped character
         */
        val strForParsing: String,
        /**
         * Part which was scanned for not escaped characters
         */
        val processingPart: String,
        /**
         * Indexes of [processingPart] in [strForParsing]
         */
        @Suppress("unused")
        val processingPartRange: IntRange,
        /**
         * Indexes of not escaped characters
         */
        @Suppress("unused")
        val notEscapedRange: IntRange
) : RuntimeException(
        "Found not escaped characters. " +
                "Please check you string. Match string '$processingPart' in indexes '$processingPartRange'. " +
                "Not escaped characters in indexes '$notEscapedRange'"
) {
}