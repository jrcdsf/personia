package com.joserobertofilho.personia.controllers

import com.joserobertofilho.personia.helpers.BaseTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.web.servlet.function.RequestPredicates


@SpringBootTest
@AutoConfigureMockMvc
internal class EmployeeControllerTest : BaseTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    private val payload = """{
                "Pete": "Nick",
                "Barbara": "Nick",
                "Nick": "Sophie",
                "Sophie": "Jonas"
            }"""


    @Test
    fun `POST a proper json to endpoint employees should return 200 and successful message`() {
        mockMvc.post("/employees") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content {
                contentType(MediaType.APPLICATION_JSON)
                json(
                    """{
                    "status": 201,
                    "message": "Employees hierarchy successfully created"
                }"""
                )
            }
        }
    }

    @Test
    fun `GET the endpoint employees with parameter name=Barbara should return the supervisor and senior supervisor of Barbara`() {
        mockMvc.post("/employees") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
            accept = MediaType.APPLICATION_JSON
        }.andDo {
            mockMvc.get("/employees") {
                param("name", "Barbara")
                accept = MediaType.APPLICATION_JSON
            }.andExpect {
                status { isOk() }
                content {
                    RequestPredicates.contentType(MediaType.APPLICATION_JSON)
                    json(
                        """{
                        "Sophie": {
                            "Nick": {
                                "Barbara": {}
                            }
                        }
                    }"""
                    )
                }
            }
        }
    }
}