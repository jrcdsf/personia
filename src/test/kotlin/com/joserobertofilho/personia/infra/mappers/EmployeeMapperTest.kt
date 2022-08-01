package com.joserobertofilho.personia.infra.mappers

import com.joserobertofilho.personia.helpers.BaseTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class EmployeeMapperTest : BaseTest() {

    @Test
    fun `convert employee to employee entity`() {
        val actual = employeeMapper.convertToEmployeeEntity(employee)

        assertTrue(actual.id == employee.id)
        assertTrue(actual.name == employee.name)
        assertTrue(actual.supervisorId == employee.supervisorId)
        assertTrue(actual.isSupervisor == employee.isSupervisor)
    }

    @Test
    fun `convert employee entity to employee`() {
        val actual = employeeMapper.convertToEmployee(employeeEntity)

        assertTrue(actual.id == employee.id)
        assertTrue(actual.name == employee.name)
        assertTrue(actual.supervisorId == employee.supervisorId)
        assertTrue(actual.isSupervisor == employee.isSupervisor)
    }
}