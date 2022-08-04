package com.joserobertofilho.personia.domain.validation

import com.fasterxml.jackson.module.kotlin.readValue
import com.joserobertofilho.personia.domain.exceptions.MultipleSeniorEmployeesFoundException
import com.joserobertofilho.personia.helpers.BaseTest
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MultipleSeniorsValidatorTestInterface : BaseTest() {

    private val bodyWithMultipleSeniors = """{
            "Pete": "Nick",
            "Barbara": "Nick",
            "Nick": "Sophie",
            "Sophie": "Jonas",
            "Chan": "Patrick"
         }"""

    private val bodyWithoutMultipleSeniors = """{
            "Pete": "Nick",
            "Barbara": "Nick",
            "Nick": "Sophie",
            "Sophie": "Jonas"
         }"""

    @Test
    fun `given payload with multiple seniors then should throw exception`() {
        val payload = objectMapper.readValue<MutableMap<String, String>>(bodyWithMultipleSeniors)
        assertThrows(MultipleSeniorEmployeesFoundException::class.java) {
            MultipleSeniorsValidatorInterface().isValid(payload)
        }
    }

    @Test
    fun `given payload without multiple seniors then should return true`() {
        val payload = objectMapper.readValue<MutableMap<String, String>>(bodyWithoutMultipleSeniors)
        assertTrue(MultipleSeniorsValidatorInterface().isValid(payload))
    }
}
