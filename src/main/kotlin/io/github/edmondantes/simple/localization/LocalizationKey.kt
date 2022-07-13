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

/**
 * This interface describes an object which store information for localization
 *
 * @see Localizer
 * @see LocalizationRequest
 * @see LocalizationContext
 */
interface LocalizationKey {

    /**
     * Key which associated with localized string.
     *
     * If this field is null, [Localizer] should return arguments concatenation
     */
    val key: String?

    /**
     * Argument for localized string
     */
    val arguments: List<String>

}