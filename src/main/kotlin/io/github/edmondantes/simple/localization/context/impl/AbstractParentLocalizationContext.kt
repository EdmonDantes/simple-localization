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
import io.github.edmondantes.simple.localization.impl.AbstractLocalizer

/**
 * Common implementation for delegate calls to [parent] with modified arguments
 * @param parent parent [Localizer] for delegating method's calls
 *
 * @see LocalizationContext
 * @see AbstractLocalizer
 *
 * @see LocalizationKey
 * @see LocalizationRequest
 * @see LocalizationContext
 */
abstract class AbstractParentLocalizationContext(protected val parent: LocalizationContext) : LocalizationContext {

    override val language: String
        get() = parent.language

    override fun localize(key: LocalizationKey): String =
            parent.localize(prepareKey(key))

    override fun localizeOrNull(key: LocalizationKey): String? =
            parent.localizeOrNull(prepareKey(key))

    override fun localizeOrDefault(key: LocalizationKey, default: String): String =
            parent.localizeOrDefault(prepareKey(key), default)

    override fun localize(request: LocalizationRequest): String =
            parent.localize(prepareRequest(request))

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
    protected open fun prepareRequest(request: LocalizationRequest): LocalizationRequest = request
}