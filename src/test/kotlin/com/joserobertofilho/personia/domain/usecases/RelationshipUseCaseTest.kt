package com.joserobertofilho.personia.domain.usecases

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.joserobertofilho.personia.domain.exceptions.MultipleSeniorEmployeeFoundException
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class RelationshipUseCaseTest {
    @Test
    fun findLoopsTest() {

        val mapper = jacksonObjectMapper()

        val json = """{
            "Pete": "Nick",
            "Barbara": "Nick",
            "Nick": "Sophie",
            "Sophie": "Jonas",
            "Jonas": "Sophie"
         }"""

        val map = mapper.readValue(json, Map::class.java)
        map.forEach { (key, value) ->
            if (map.filter { it.key == value && it.value == key }.isNotEmpty()) {
                println(key)
            }
        }
        assertFalse(map.isEmpty())
    }

    @Test
    fun findMultipleSeniorSupervisorsTest() {

        val mapper = jacksonObjectMapper()

        val json = """{
            "Pete": "Nick",
            "Barbara": "Nick",
            "Nick": "Sophie",
            "Sophie": "Jonas",
            "Chan": "Patrick"
         }"""

        val relationships = mapper.readValue(json, Map::class.java)
        val supervisors = relationships.values
        var numberOfSupervisors = 0
        supervisors.forEach { supervisor ->
            if (relationships.filterKeys { it == supervisor }.isEmpty()) numberOfSupervisors++
        }
        assertThrows(MultipleSeniorEmployeeFoundException::class.java) {
            if (numberOfSupervisors > 1)
                throw MultipleSeniorEmployeeFoundException("Found more than 1 senior supervisor")
        }

    }
}