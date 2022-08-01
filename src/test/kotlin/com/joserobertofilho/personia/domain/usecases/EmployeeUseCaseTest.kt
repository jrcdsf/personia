package com.joserobertofilho.personia.domain.usecases

import com.joserobertofilho.personia.helpers.BaseTest
import com.joserobertofilho.personia.infra.services.EmployeeService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class EmployeeUseCaseTest : BaseTest() {

    @MockK
    lateinit var mockedService: EmployeeService

    @InjectMockKs
    lateinit var employeeUseCase: EmployeeUseCase

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `add employee should return employee`() {
        every { mockedService.add(any()) } returns employee

        val actual = employeeUseCase.addEmployee(employee)

        verify(exactly = 1) { mockedService.add(any()) }
        assertEquals(employee, actual)
    }

    @Test
    fun `find employee should return employee`() {
        every { mockedService.find(employee.name) } returns employee

        val actual = employeeUseCase.find(employee.name)

        verify(exactly = 1) { mockedService.find(employee.name) }
        assertEquals(employee, actual)
    }

    @Test
    fun `find all employees should return list of employees`() {
        every { mockedService.findAll() } returns allEmployees

        val actual = employeeUseCase.findAll()

        verify(exactly = 1) { mockedService.findAll() }
        assertIterableEquals(allEmployees, actual)
    }

    @Test
    fun `find employees by supervisor id should return list of employees under that supervisor`() {
        every { mockedService.findEmployeesBySupervisorId(supervisor.id) } returns employeesUnderSupervisor

        val actual = employeeUseCase.findEmployeesBySupervisorId(supervisor.id)

        verify(exactly = 1) { mockedService.findEmployeesBySupervisorId(supervisor.id) }
        assertIterableEquals(employeesUnderSupervisor, actual)
    }

    @Test
    fun `find senior supervisor should return senior supervisor`() {
        every { mockedService.findSeniorSupervisor() } returns senior

        val actual = employeeUseCase.findSeniorSupervisor()

        verify(exactly = 1) { mockedService.findSeniorSupervisor() }
        assertEquals(senior, actual)
    }
}