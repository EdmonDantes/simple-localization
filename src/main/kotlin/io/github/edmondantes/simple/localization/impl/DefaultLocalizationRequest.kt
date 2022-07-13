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

/**
 * Default implementation of [LocalizationRequest]
 *
 * @param keys list of [LocalizationKey] for localization
 *
 * @see LocalizationKey
 * @see LocalizationRequest
 */
@Suppress("unused")
class DefaultLocalizationRequest(override val keys: List<LocalizationKey>) : LocalizationRequest {

    constructor(vararg keys: LocalizationKey) : this(keys.toList())

    operator fun plus(request: LocalizationRequest): LocalizationRequest =
        DefaultLocalizationRequest(keys + request.keys)

    override fun equals(other: Any?): Boolean =
        other === this
                || other != null
                && other.javaClass == this.javaClass
                && keys == (other as LocalizationRequest).keys


    override fun hashCode(): Int =
        keys.hashCode()

    override fun toString(): String =
        keys.toString()

    /**
     * Class for build [DefaultLocalizationRequest]
     */
    class Builder {
        private val keys = ArrayList<LocalizationKey>()

        /**
         * Add new [LocalizationKey] to builder
         *
         * @param key key which associated with localized string
         * @param arguments arguments for injection into localized string
         *
         * @return [Builder] instance
         */
        fun add(key: String, arguments: List<String>): Builder = this.apply {
            keys.add(DefaultLocalizationKey(key, arguments))
        }

        /**
         * Add new [LocalizationKey] to builder
         *
         * @param key key which associated with localized string
         * @param arguments arguments for injection into localized string
         *
         * @return [Builder] instance
         */
        fun add(key: String, vararg arguments: String): Builder = this.apply {
            keys.add(DefaultLocalizationKey(key, *arguments))
        }

        /**
         * Add to builder new string which will not be localized
         *
         * @param str string which will not be localized
         *
         * @return [Builder] instance
         */
        fun withoutLocalization(str: String): Builder = this.apply {
            keys.add(DefaultLocalizationKey(null, listOf(str)))
        }

        /**
         * Build [LocalizationRequest]
         */
        fun build(): LocalizationRequest =
            DefaultLocalizationRequest(keys.toList())
    }
}

/**
 * Construct new [LocalizationRequest]
 *
 * @param block lambda which get [DefaultLocalizationRequest.Builder] for construct [LocalizationRequest]
 *
 * @return [LocalizationRequest] which was constructed in block
 */
@Suppress("unused")
inline fun localizationRequest(block: DefaultLocalizationRequest.Builder.() -> Unit): LocalizationRequest =
    DefaultLocalizationRequest.Builder().apply(block).build()

/**
 * Create new [LocalizationRequest] with only one key
 *
 * @param key key which associated with localized string
 * @param arguments arguments for injection into localized string
 *
 * @return [LocalizationRequest] with only one key
 */
@Suppress("unused")
fun singleRequest(key: String, vararg arguments: String): LocalizationRequest =
    DefaultLocalizationRequest(listOf(DefaultLocalizationKey(key, arguments.toList())))

/**
 * Create new [LocalizationRequest] with only one key
 *
 * @param key key which associated with localized string
 * @param arguments arguments for injection into localized string
 *
 * @return [LocalizationRequest] with only one key
 */
@Suppress("unused")
fun singleRequest(key: String, arguments: List<String>): LocalizationRequest =
    DefaultLocalizationRequest(listOf(DefaultLocalizationKey(key, arguments.toList())))

/**
 * Create new [LocalizationRequest] with only one key which will not be localized
 *
 * @param str string which will not be localized
 *
 * @return [LocalizationRequest] with only one key which will not be localized
 */
@Suppress("unused")
fun stringRequest(str: String): LocalizationRequest =
    DefaultLocalizationRequest(listOf(DefaultLocalizationKey(null, listOf(str))))