package com.joserobertofilho.personia.domain.validation

import com.fasterxml.jackson.module.kotlin.readValue
import com.joserobertofilho.personia.domain.exceptions.EmptyRelationshipsException
import com.joserobertofilho.personia.helpers.BaseTest
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class EmptyRelationshipsValidatorTest : BaseTest() {

    private val emptyBody = """{}"""

    private val normalBody = """{
            "Pete": "Nick",
            "Barbara": "Nick",
            "Nick": "Sophie",
            "Sophie": "Jonas",
            "Jonas": "Sophie"
         }"""

    @Test
    fun `given empty payload then should throw exception`() {
        val payload = objectMapper.readValue<MutableMap<String, String>>(emptyBody)
        assertThrows(EmptyRelationshipsException::class.java) {
            EmptyRelationshipsValidator().validate(payload)

        }
    }

    @Test
    fun `given normal payload then should return true`() {
        val payload = objectMapper.readValue<MutableMap<String, String>>(normalBody)
        assertTrue(EmptyRelationshipsValidator().validate(payload))
    }
}