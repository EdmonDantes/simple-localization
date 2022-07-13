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

package io.github.edmondantes.simple.localization.impl

import io.github.edmondantes.simple.localization.LocalizationKey
import io.github.edmondantes.simple.localization.LocalizationRequest
import io.github.edmondantes.simple.localization.Localizer
import io.github.edmondantes.simple.localization.context.LocalizationContext
import io.github.edmondantes.simple.localization.context.impl.DefaultLocalizationContext
import io.github.edmondantes.simple.localization.exception.LanguageNotSupportedException
import io.github.edmondantes.simple.localization.exception.NotEscapedCharacterException

/**
 * Common implementation of [Localizer]
 * @param supportLanguages list of supports languages. Can not be empty
 * @param defaultLanguage default language tag for localizer
 *
 * @see Localizer
 * @see LocalizationKey
 * @see LocalizationRequest
 * @see LocalizationContext
 */
abstract class AbstractLocalizer(
    final override val supportLanguages: List<String>,
    final override val defaultLanguage: String
) : Localizer {

    /**
     * Cache for [LocalizationContext]s
     */
    private val localizationContexts: Map<String, LocalizationContext>

    init {
        if (supportLanguages.isEmpty()) {
            error("List of support languages can not be empty")
        }

        localizationContexts = supportLanguages.associateWith(::createLocalizationContext)
    }

    override fun isSupport(language: String): Boolean = supportLanguages.contains(language)

    override fun localize(language: String?, key: LocalizationKey): String =
        localizeOrNull(language, key) ?: key.key ?: key.arguments.joinToString()

    override fun localizeOrNull(language: String?, key: LocalizationKey): String? {
        if (language != null && !isSupport(language)) {
            throw LanguageNotSupportedException(language)
        }

        if (key.key.isNullOrBlank()) {
            return null
        }

        val localized = findByKey(language ?: defaultLanguage, key.key!!) ?: return null

        return unescapeCharacters(processArguments(localized, key.arguments))
    }

    override fun localizeOrDefault(language: String?, key: LocalizationKey, default: String): String {
        if (language != null && !isSupport(language)) {
            throw LanguageNotSupportedException(language)
        }

        if (key.key.isNullOrBlank()) {
            return default
        }

        val localized = findByKey(language ?: defaultLanguage, key.key!!) ?: default

        return unescapeCharacters(processArguments(localized, key.arguments))
    }

    override fun localize(language: String?, request: LocalizationRequest): String {
        if (language != null && !isSupport(language)) {
            throw LanguageNotSupportedException(language)
        }

        val resultBuilder = StringBuilder()
        request.keys.forEach {
            resultBuilder.append(localize(language, it))
        }
        return resultBuilder.toString()
    }

    override fun getContext(language: String?): LocalizationContext {
        if (language != null && !isSupport(language)) {
            throw LanguageNotSupportedException(language)
        }

        return localizationContexts[language ?: defaultLanguage]
            ?: error("Can not find LocalizationContext for language '${language ?: defaultLanguage}'")
    }

    /**
     * Creates localization context for specified [language]
     * @param language language tag
     *
     * @return [LocalizationContext] for specified language
     */
    protected open fun createLocalizationContext(language: String): LocalizationContext =
        DefaultLocalizationContext(language, this)

    /**
     * Inject arguments to localized string
     *
     * Format for arguments in localized string: '{}', '{1}'
     *
     * Argument's indexes started from 1
     *
     * Please escape these characters in localized string: '{', '}'
     *
     * Example:
     * localized string: "Hello {}"; arguments: ["world"] -> result: "Hello world"
     *
     * @param localized localized string
     * @param arguments arguments for inject
     *
     * @return localized string with injected arguments
     */
    protected open fun processArguments(localized: String, arguments: List<String>): String {
        var index = 0
        return localized.replace(ARGUMENT_IN_LOCALIZATION) {
            val argumentIndex = it.groupValues[2].toIntOrNull()?.let { it - 1 } ?: index++
            if (argumentIndex < arguments.size) {
                arguments[argumentIndex]
            } else {
                "?"
            }
        }
    }

    /**
     * Try to unescape characters in [str], and find not escaped characters
     * @param str string for processing
     *
     * @return [str] with unescaped characters
     *
     * @throws NotEscapedCharacterException if method found unescaped characters before processing
     */
    @Throws(NotEscapedCharacterException::class)
    protected open fun unescapeCharacters(str: String): String {
        val match = NOT_ESCAPED_CHARACTERS.find(str)
        if (match != null) {
            throw NotEscapedCharacterException(
                str,
                str,
                str.indices,
                match.range
            )
        }

        return str.replace(ESCAPED_CHARACTERS) { it.value.substring(1) }
    }

    /**
     * Find localization string for [language] and [key]
     *
     * @param language language tag
     * @param key key which associated with localized string
     *
     * @return If found localized string with escaped characters and without injecting arguments, else returns null
     */
    protected abstract fun findByKey(language: String, key: String): String?

    companion object {
        /**
         * Regular expression for find places for inject arguments
         */
        protected val ARGUMENT_IN_LOCALIZATION = Regex("(?<!\\\\)(\\{([0-9]*)})")

        /**
         * Regular expression for find escaped characters
         */
        protected val ESCAPED_CHARACTERS = Regex("\\\\[{}]")

        /**
         * Regular expression for find not escaped characters
         */
        protected val NOT_ESCAPED_CHARACTERS = Regex("(?<!\\\\)[{}]")
    }
}
