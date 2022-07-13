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

import java.util.Locale
import java.util.MissingResourceException
import java.util.ResourceBundle

/**
 * Default implementation for using [ResourceBundle] for localization
 * @param supportLanguages list of supports languages. Can not be empty
 * @param defaultLanguage default language tag for localizer
 * @param resourceBundlePrefix path to resource bundle in resources (default: "localization").
 */
class ResourceBundleLocalizer(
        supportLanguages: List<String>,
        defaultLanguage: String,
        private val resourceBundlePrefix: String = "localization"
) : AbstractLocalizer(supportLanguages, defaultLanguage) {

    private val bundles: Map<String, ResourceBundle>

    init {
        bundles = this.supportLanguages.associateWith {
            loadLocalizationResourceBundle(it)
        }
    }

    override fun findByKey(language: String, key: String): String? =
            try {
                bundles[language]?.getString(key)
            } catch (e: MissingResourceException) {
                null
            }


    private fun loadLocalizationResourceBundle(language: String): ResourceBundle =
            try {
                ResourceBundle.getBundle(resourceBundlePrefix, Locale.forLanguageTag(language))
            } catch (e: Exception) {
                error("Can not load resource bundle with base name '$resourceBundlePrefix' for language tag '$language'")
            }
}