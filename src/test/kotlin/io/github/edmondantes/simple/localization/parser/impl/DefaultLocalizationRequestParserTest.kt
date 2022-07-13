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

package io.github.edmondantes.simple.localization.parser.impl

import io.github.edmondantes.simple.localization.exception.NotEscapedCharacterException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DefaultLocalizationRequestParserTest {

    private val parser = DefaultLocalizationRequestParser()

    @Test
    fun testSimpleParser() {
        val request = parser.parse("Test@{test.test}{1}{2}Test1")

        assertEquals(3, request.keys.size)
        assertEquals("Test", request.keys[0].arguments.joinToString())

        request.keys[1].also {
            assertEquals("test.test", it.key)
            assertEquals(2, it.arguments.size)
            assertEquals("1", it.arguments[0])
            assertEquals("2", it.arguments[1])
        }

        assertEquals("Test1", request.keys[2].arguments.joinToString())
    }

    @Test
    fun testNotEscapedCharacters() {
        assertThrows<NotEscapedCharacterException> {
            parser.parse("Test@")
        }

        assertThrows<NotEscapedCharacterException> {
            parser.parse("Test{")
        }

        assertThrows<NotEscapedCharacterException> {
            parser.parse("Test}")
        }
    }

}