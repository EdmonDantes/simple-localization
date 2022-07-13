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

package io.github.edmondantes.simple.localization.context

import io.github.edmondantes.simple.localization.LocalizationKey
import io.github.edmondantes.simple.localization.LocalizationRequest
import io.github.edmondantes.simple.localization.Localizer

/**
 * This interface describes an object which implements interface [Localizer] for special language
 *
 * @see Localizer
 * @see LocalizationKey
 * @see LocalizationRequest
 */
interface LocalizationContext {

    /**
     * Specified language for localization
     */
    val language: String

    /**
     * Find localized [String] for [language] and [key]
     * and insert arguments to found [String]
     *
     * @param key key for localized string. If [key] have only arguments, method will return arguments concatenation
     *
     * @return If found localized string for [language] and [key], else key from [LocalizationKey],
     * if key is null, arguments concatenation from [LocalizationKey]
     */
    fun localize(key: LocalizationKey): String

    /**
     * Find localized [String] for [language] and [key]
     * and insert arguments to found [String]
     *
     * If not found returns null
     *
     * @param key key for localized string. If [key] have only arguments, method will return arguments concatenation
     *
     * @return If found localized string for [language] and [key], else null
     */
    fun localizeOrNull(key: LocalizationKey): String?

    /**
     * Find localized [String] for [language] and [key]
     * and insert arguments to found [String]
     *
     * If not found returns [default]
     *
     * @param key key for localized string. If [key] have only arguments, method will return arguments concatenation
     * @param default default value if localizer will not found localized string.
     *
     * @return If found localized string for [language] and [key], else [default]
     */
    fun localizeOrDefault(key: LocalizationKey, default: String): String

    /**
     * Transform [request] to localized [String] for [language]
     * For each key in [request] call method [localize], and concatenate parts after transformation
     *
     * @param request request for localization.
     *
     * @return if [request] has keys then method returns a string after transformation, else empty string
     */
    fun localize(request: LocalizationRequest): String
}