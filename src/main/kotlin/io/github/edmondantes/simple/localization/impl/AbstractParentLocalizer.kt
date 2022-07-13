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

/**
 * Common implementation for delegate calls to [parent] with modified arguments
 * @param parent parent [Localizer] for delegating method's calls
 *
 * @see Localizer
 * @see AbstractLocalizer
 *
 * @see LocalizationKey
 * @see LocalizationRequest
 * @see LocalizationContext
 */
abstract class AbstractParentLocalizer(
        protected val parent: Localizer
) : Localizer {

    override val supportLanguages: List<String>
        get() = parent.supportLanguages
    override val defaultLanguage: String
        get() = parent.defaultLanguage

    override fun isSupport(language: String): Boolean =
            parent.isSupport(language)

    override fun localize(language: String?, key: LocalizationKey): String =
            parent.localize(language, prepareKey(key))

    override fun localize(language: String?, request: LocalizationRequest): String =
            parent.localize(language, prepareRequest(request))

    override fun localizeOrNull(language: String?, key: LocalizationKey): String? =
            parent.localizeOrNull(language, prepareKey(key))

    override fun localizeOrDefault(language: String?, key: LocalizationKey, default: String): String =
            parent.localizeOrDefault(language, prepareKey(key), default)

    override fun getContext(language: String?): LocalizationContext =
            parent.getContext(language)

    /**
     * Modify [LocalizationKey] before delegate call to [parent]
     * @param key [LocalizationKey] for modify
     * @return [LocalizationKey] after modify
     */
    protected open fun prepareKey(key: LocalizationKey): LocalizationKey = key

    /**
     * Modify [LocalizationRequest] before delegate call to [parent]
     * @param request [LocalizationRequest] for modify
     * @return [LocalizationRequest] after modify
     */
    protected open fun prepareRequest(request: LocalizationRequest) = request
}