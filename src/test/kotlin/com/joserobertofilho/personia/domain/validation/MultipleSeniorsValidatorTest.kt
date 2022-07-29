package com.joserobertofilho.personia.domain.validation

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.joserobertofilho.personia.domain.exceptions.MultipleSeniorEmployeesFoundException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class MultipleSeniorsValidatorTest {

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
    fun given_payload_with_multiple_seniors_then_should_throw_exception() {
        val payload = ObjectMapper().readValue<MutableMap<String, String>>(bodyWithMultipleSeniors)
        assertThrows(MultipleSeniorEmployeesFoundException::class.java) {
            MultipleSeniorsValidator().validate(payload)
        }
    }

    @Test
    fun given_payload_without_multiple_seniors_then_should_pass() {
        val payload = ObjectMapper().readValue<MutableMap<String, String>>(bodyWithoutMultipleSeniors)
        MultipleSeniorsValidator().validate(payload)
    }
}
