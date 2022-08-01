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
import javax.transaction.Transactional


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
internal class EmployeeControllerTest : BaseTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    private val payload = """{
                "Pete": "Nick",
                "Barbara": "Nick",
                "Nick": "Sophie",
                "Sophie": "Jonas"
            }"""

    private val emptyPayload = """{}"""

    @Test
    fun `POST a proper body to endpoint employees should return 200 and successful message`() {
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
    fun `POST en empty body to endpoint employees should return 400 and error message`() {
        mockMvc.post("/employees") {
            contentType = MediaType.APPLICATION_JSON
            content = emptyPayload
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
            content {
                contentType(MediaType.APPLICATION_JSON)
                json(
                    """{
                    "status": 400,
                    "message": "Empty relationships provided as input"
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

    @Test
    fun `GET the endpoint employees without parameter should return the full hierarchy`() {
        mockMvc.post("/employees") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
            accept = MediaType.APPLICATION_JSON
        }.andDo {
            mockMvc.get("/employees") {
                accept = MediaType.APPLICATION_JSON
            }.andExpect {
                status { isOk() }
                content {
                    RequestPredicates.contentType(MediaType.APPLICATION_JSON)
                    json(
                        """{
                            "Jonas": {
                                "Sophie": {
                                    "Nick": [
                                        {
                                            "Pete": {}
                                        },
                                        {
                                            "Barbara": {}
                                        }
                                    ]
                                }
                            }
                        }"""
                    )
                }
            }
        }
    }
}