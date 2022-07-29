package com.joserobertofilho.personia.domain.validation

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.joserobertofilho.personia.domain.exceptions.HierarchyWithLoopException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class LoopValidatorTest {

    private val responseBodyWithLoop = """{
            "Pete": "Nick",
            "Barbara": "Nick",
            "Nick": "Sophie",
            "Sophie": "Jonas",
            "Jonas": "Sophie"
         }"""

    private val responseBodyWithoutLoop = """{
            "Pete": "Nick",
            "Barbara": "Nick",
            "Nick": "Sophie",
            "Sophie": "Jonas"
         }"""

    @Test
    fun given_payload_with_loop_then_should_throw_exception() {
        val payload = ObjectMapper().readValue<MutableMap<String, String>>(responseBodyWithLoop)
        assertThrows(HierarchyWithLoopException::class.java) {
            LoopValidator().validate(payload)
        }
    }

    @Test
    fun given_payload_without_loop_then_should_pass() {
        val payload = ObjectMapper().readValue<MutableMap<String, String>>(responseBodyWithoutLoop)
        LoopValidator().validate(payload)
    }
}