package com.joserobertofilho.personia.domain.validation

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.joserobertofilho.personia.domain.exceptions.MultipleSeniorEmployeesFoundException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class MultipleSeniorsValidatorTest {

    private val responseBodyWithMultipleSeniors = """{
            "Pete": "Nick",
            "Barbara": "Nick",
            "Nick": "Sophie",
            "Sophie": "Jonas",
            "Chan": "Patrick"
         }"""

    private val responseBodyWithoutMultipleSeniors = """{
            "Pete": "Nick",
            "Barbara": "Nick",
            "Nick": "Sophie",
            "Sophie": "Jonas"
         }"""

    @Test
    fun given_payload_with_multiple_seniors_then_should_throw_exception() {
        val payload = ObjectMapper().readValue<MutableMap<String, String>>(responseBodyWithMultipleSeniors)
        assertThrows(MultipleSeniorEmployeesFoundException::class.java) {
            MultipleSeniorsValidator().validate(payload)
        }
    }

    @Test
    fun given_payload_without_multiple_seniors_then_should_pass() {
        val payload = ObjectMapper().readValue<MutableMap<String, String>>(responseBodyWithoutMultipleSeniors)
        MultipleSeniorsValidator().validate(payload)
    }
}
