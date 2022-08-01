package com.joserobertofilho.personia.helpers

import com.fasterxml.jackson.databind.ObjectMapper
import com.joserobertofilho.personia.domain.entities.Employee

open class BaseTest {
    protected val objectMapper: ObjectMapper = ObjectMapper()

    protected val employee = Employee(1, "test 1", 5)

    protected val anotherEmployee = Employee(2, "test 2", 5)

    protected val supervisor = Employee(5, "supervisor", 10, true)

    protected val employeesUnderSupervisor = mutableSetOf(employee, anotherEmployee)

    protected val senior = Employee(10, "supervisor", null, true)

    protected val allEmployees = mutableSetOf(employee, supervisor, senior)
}