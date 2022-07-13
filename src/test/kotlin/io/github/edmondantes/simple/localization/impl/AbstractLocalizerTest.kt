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

import io.github.edmondantes.simple.localization.Localizer
import io.github.edmondantes.simple.localization.exception.LanguageNotSupportedException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

abstract class AbstractLocalizerTest {

    protected abstract val localizer: Localizer

    @Test
    fun testGetLocalizationFromDifferentLanguage() {
        val key = localizationKey("test.test")

        Assertions.assertEquals("Test USA", localizer.localize("en-us", key))
        Assertions.assertEquals("Test UK", localizer.localize("en-uk", key))
    }

    @Test
    fun testGetLocalizationWithoutValue() {
        val key = localizationKey("test.default")

        Assertions.assertEquals("Test USA", localizer.localize("en-us", key))
        Assertions.assertEquals("test.default", localizer.localize("en-uk", key))
    }

    @Test
    fun testGetDefaultLocalization() {
        val key = localizationKey("test.default")

        Assertions.assertEquals("Test USA", localizer.localize(null, key))
    }

    @Test
    fun testRequest() {
        val request = localizationRequest {
            add("test.test")
            withoutLocalization("Testing")
            add("test.default")
        }

        Assertions.assertEquals("Test USATestingTest USA", localizer.localize("en-us", request))
    }

    @Test
    fun testArguments() {
        val key = localizationKey("test.arguments", "1", "2", "3")

        Assertions.assertEquals("Test a:1, b:3, f:1, t:2, g:3 {}", localizer.localize("en-us", key))
    }

    @Test
    fun testLanguageNotSupportException() {
        val key = localizationKey("test.arguments", "1", "2", "3")
        assertThrows<LanguageNotSupportedException> {
            localizer.localize("test-language", key)
        }
    }

}