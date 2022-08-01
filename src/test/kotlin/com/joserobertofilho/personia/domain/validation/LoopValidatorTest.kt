package com.joserobertofilho.personia.domain.validation

import com.fasterxml.jackson.module.kotlin.readValue
import com.joserobertofilho.personia.domain.exceptions.HierarchyWithLoopException
import com.joserobertofilho.personia.helpers.BaseTest
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class LoopValidatorTest : BaseTest() {

    private val bodyWithLoop = """{
            "Pete": "Nick",
            "Barbara": "Nick",
            "Nick": "Sophie",
            "Sophie": "Jonas",
            "Jonas": "Sophie"
         }"""

    private val bodyWithoutLoop = """{
            "Pete": "Nick",
            "Barbara": "Nick",
            "Nick": "Sophie",
            "Sophie": "Jonas"
         }"""

    @Test
    fun `given payload with loop then should throw exception`() {
        val payload = objectMapper.readValue<MutableMap<String, String>>(bodyWithLoop)
        assertThrows(HierarchyWithLoopException::class.java) {
            LoopValidator().isValid(payload)
        }
    }

    @Test
    fun `given payload without loop then should return true`() {
        val payload = objectMapper.readValue<MutableMap<String, String>>(bodyWithoutLoop)
        assertTrue(LoopValidator().isValid(payload))
    }
}