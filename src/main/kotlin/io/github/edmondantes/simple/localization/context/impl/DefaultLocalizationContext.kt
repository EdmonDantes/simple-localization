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

package io.github.edmondantes.simple.localization.context.impl

import io.github.edmondantes.simple.localization.LocalizationKey
import io.github.edmondantes.simple.localization.LocalizationRequest
import io.github.edmondantes.simple.localization.Localizer
import io.github.edmondantes.simple.localization.context.LocalizationContext

/**
 * Default implementation of [LocalizationContext]. Delegates call with specified [language] to [localizer]
 *
 * @param language specified language tag for context
 * @param localizer [Localizer] for delegate calls
 */
class DefaultLocalizationContext(
        override val language: String,
        private val localizer: Localizer
) : LocalizationContext {
    override fun localize(key: LocalizationKey): String =
            localizer.localize(language, key)

    override fun localizeOrNull(key: LocalizationKey): String? =
            localizer.localizeOrNull(language, key)

    override fun localizeOrDefault(key: LocalizationKey, default: String): String =
            localizer.localizeOrDefault(language, key, default)

    override fun localize(request: LocalizationRequest): String =
            localizer.localize(language, request)
}