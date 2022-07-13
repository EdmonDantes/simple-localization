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

/**
 * Default implementation for adding prefix to keys before delegate call to [parent]
 * @param parent parent [Localizer] for delegating method's calls
 * @param prefix string which will be added to start to any key before trying to find localized string
 *
 * @see Localizer
 * @see AbstractLocalizer
 * @see AbstractParentLocalizer
 *
 * @see LocalizationKey
 * @see LocalizationRequest
 */
@Suppress("unused")
class DefaultParentLocalizerWithPrefix(
    parent: Localizer,
    prefix: String?
) : AbstractParentLocalizer(parent) {

    private val prefix: String = preparePrefix(prefix)

    override fun prepareKey(key: LocalizationKey): LocalizationKey {
        if (key.key.isNullOrBlank()) {
            return key
        }

        return DefaultLocalizationKey(prefix + key.key, key.arguments)
    }

    override fun prepareRequest(request: LocalizationRequest): LocalizationRequest {
        if (request.keys.isEmpty()) {
            return request
        }

        return DefaultLocalizationRequest(request.keys.map { prepareKey(it) })
    }

    /**
     * Prepare prefix for better using
     */
    private fun preparePrefix(prefix: String?): String =
        if (prefix.isNullOrBlank()) "" else if (prefix.endsWith('.')) prefix else "$prefix."
}

/**
 * Create new [Localizer] which add [prefix] to start before trying to find localized string
 *
 * @param prefix string which will be added to start to any key before trying to find localized string
 * @return [Localizer] which adds [prefix] to start to any key before trying to find localized string
 */
@Suppress("unused")
fun Localizer.withPrefix(prefix: String): Localizer =
    DefaultParentLocalizerWithPrefix(this, prefix)

/**
 * Create and use new [Localizer] which add [prefix] to start before trying to find localized string
 *
 * @param prefix which will be added to start to any key before trying to find localized string
 * @param block block of code which will use new [Localizer]
 */
@Suppress("unused")
fun Localizer.withPrefix(prefix: String, block: Localizer.() -> Unit): Unit =
    withPrefix(prefix).block()