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

package io.github.edmondantes.simple.localization.parser

import io.github.edmondantes.simple.localization.LocalizationRequest
import io.github.edmondantes.simple.localization.exception.NotEscapedCharacterException

interface LocalizationRequestParser {

    /**
     * Parse [str] to [LocalizationRequest]
     *
     * Format:
     * "`not_localize@{key.for.localization}{arg1}{arg2}not_localize`"
     *
     * Please escape (use '\' before) characters '{','}','@'
     *
     * @throws NotEscapedCharacterException if fount not escaped character
     * @throws IllegalStateException if didn't find localization key or it is empty
     */
    @Throws(NotEscapedCharacterException::class, IllegalStateException::class)
    fun parse(str: String): LocalizationRequest

}