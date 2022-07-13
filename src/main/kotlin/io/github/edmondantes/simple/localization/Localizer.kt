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

package io.github.edmondantes.simple.localization

import io.github.edmondantes.simple.localization.context.LocalizationContext
import io.github.edmondantes.simple.localization.exception.LanguageNotSupportedException

/**
 * This interface describes an object which transform [LocalizationKey] and [LocalizationRequest]
 * to [String] for special language
 *
 * @see LocalizationKey
 * @see LocalizationRequest
 * @see LocalizationContext
 */
interface Localizer {

    /**
     * List of languages' tags which localizer supports
     * This value is constant
     */
    val supportLanguages: List<String>

    /**
     * Default language tag for localizer
     * This value is constant
     */
    val defaultLanguage: String

    /**
     * Check localizer supports [language] or not
     *
     * @param language language tag (example: 'en' (English))
     * @return True if localizer supports [language], else false
     */
    fun isSupport(language: String): Boolean

    /**
     * Find localized [String] for [language] and [key]
     * and insert arguments to found [String]
     *
     * @param language language tag of localized string (example: 'en' (English)).
     * if [language] is null, localizer will use [defaultLanguage]
     * @param key key for localized string. If [key] have only arguments, method will return arguments concatenation
     *
     * @return If found localized string for [language] and [key], else key from [LocalizationKey],
     * if key is null, arguments concatenation from [LocalizationKey]
     *
     * @throws LanguageNotSupportedException if [language] is not support
     */
    @Throws(LanguageNotSupportedException::class)
    fun localize(language: String?, key: LocalizationKey): String

    /**
     * Transform [request] to localized [String] for [language]
     * For each key in [request] call method [localize], and concatenate parts after transformation
     *
     * @param language language tag of localized string (example: 'en' (English)).
     * if [language] is null, localizer will use [defaultLanguage]
     * @param request request for localization.
     *
     * @return if [request] has keys then method returns a string after transformation, else empty string
     *
     * @throws LanguageNotSupportedException if [language] is not support
     */
    @Throws(LanguageNotSupportedException::class)
    fun localize(language: String?, request: LocalizationRequest): String

    /**
     * Find localized [String] for [language] and [key]
     * and insert arguments to found [String]
     *
     * If not found returns null
     *
     * @param language language tag of localized string (example: 'en' (English)).
     * if [language] is null, localizer will use [defaultLanguage]
     * @param key key for localized string. If [key] have only arguments, method will return arguments concatenation
     *
     * @return If found localized string for [language] and [key], else null
     *
     * @throws LanguageNotSupportedException if [language] is not support
     */
    @Throws(LanguageNotSupportedException::class)
    fun localizeOrNull(language: String?, key: LocalizationKey): String?

    /**
     * Find localized [String] for [language] and [key]
     * and insert arguments to found [String]
     *
     * If not found returns [default]
     *
     * @param language language tag of localized string (example: 'en' (English)).
     * if [language] is null, localizer will use [defaultLanguage]
     * @param key key for localized string. If [key] have only arguments, method will return arguments concatenation
     * @param default default value if localizer will not found localized string.
     *
     * @return If found localized string for [language] and [key], else [default]
     *
     * @throws LanguageNotSupportedException if [language] is not support
     */
    @Throws(LanguageNotSupportedException::class)
    fun localizeOrDefault(language: String?, key: LocalizationKey, default: String): String

    /**
     * @return [LocalizationContext] for language
     */
    @Throws(LanguageNotSupportedException::class)
    fun getContext(language: String?): LocalizationContext
}