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

/**
 * Default implementation of [LocalizationKey]
 * @param key key which associated with localized string
 * @param arguments arguments for injection into localized string
 *
 * @see LocalizationKey
 */
class DefaultLocalizationKey(override val key: String?, override val arguments: List<String>) : LocalizationKey {

    constructor(key: String?, vararg arguments: String) : this(key, arguments.toList())

    override fun equals(other: Any?): Boolean =
        other === this
                || other != null
                && other.javaClass == this.javaClass
                && key == (other as LocalizationKey).key
                && arguments == other.arguments

    override fun hashCode(): Int {
        var result = key?.hashCode() ?: 0
        result = 31 * result + arguments.hashCode()
        return result
    }

    override fun toString(): String =
        "(key=$key,arguments=$arguments)"
}

/**
 * Create [LocalizationKey] for [key] with [arguments]
 *
 * @param key key which associated with localized string
 * @param arguments arguments for injection into localized string
 *
 * @return [LocalizationKey] which describe information about [key] and [arguments]
 */
fun localizationKey(key: String, arguments: List<String>): LocalizationKey =
    DefaultLocalizationKey(key, arguments)

/**
 * Create [LocalizationKey] for [key] with [arguments]
 *
 * @param key key which associated with localized string
 * @param arguments arguments for injection into localized string
 *
 * @return [LocalizationKey] which describe information about [key] and [arguments]
 */
fun localizationKey(key: String, vararg arguments: String): LocalizationKey =
    DefaultLocalizationKey(key, arguments.toList())