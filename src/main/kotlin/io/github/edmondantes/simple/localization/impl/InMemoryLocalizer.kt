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

/**
 * Default implementation for store localized string in memory
 * @param supportLanguages list of supports languages. Can not be empty
 * @param localizations object with localized string. First map's keys - language,
 * second map's keys - keys which associated with localized string
 * @param defaultLanguage default language tag for localizer
 */
class InMemoryLocalizer(
        supportLanguages: List<String>,
        defaultLanguage: String,
        private val localizations: Map<String, Map<String, String>>
) : AbstractLocalizer(supportLanguages, defaultLanguage) {

    init {
        if (!localizations.keys.containsAll(supportLanguages)) {
            error("Can not initialize InMemoryLocalizationManager, because not all support languages have a localization")
        }
    }

    override fun findByKey(language: String, key: String): String? =
            localizations[language]?.get(key)
}