package com.joserobertofilho.personia.domain.usecases

import com.joserobertofilho.personia.domain.entities.Employee
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

internal class EmployeeUseCaseTest {

    @MockK
    lateinit var mockedService: EmployeeService

    @InjectMockKs
    lateinit var employeeUseCase: EmployeeUseCase

    private val employee = Employee(10, "test", 5)

    private val employees = mutableSetOf(Employee(1, "test 1", 2), Employee(2, "test 2", 5))

    private val supervisor = Employee(5, "supervisor", 10, true)

    private val employeesUnderSupervisor = mutableSetOf(Employee(1, "test 1", supervisor.id), Employee(2, "test 2", supervisor.id))

    private val senior = Employee(5, "supervisor", null, true)

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun add_employee_should_return_employee() {
        every { mockedService.add(any()) } returns employee

        val actual = employeeUseCase.addEmployee(employee)

        verify(exactly = 1) { mockedService.add(any()) }
        assertEquals(employee, actual)
    }

    @Test
    fun find_employee_should_return_employee() {
        every { mockedService.find(employee.name) } returns employee

        val actual = employeeUseCase.find(employee.name)

        verify(exactly = 1) { mockedService.find(employee.name) }
        assertEquals(employee, actual)
    }

    @Test
    fun find_all_employees_should_return_list_of_employees() {
        every { mockedService.findAll() } returns employees

        val actual = employeeUseCase.findAll()

        verify(exactly = 1) { mockedService.findAll() }
        assertIterableEquals(employees, actual)
    }

    @Test
    fun find_employees_by_supervisor_id_should_return_list_of_employees_under_that_supervisor() {
        every { mockedService.findEmployeesBySupervisorId(supervisor.id) } returns employeesUnderSupervisor

        val actual = employeeUseCase.findEmployeesBySupervisorId(supervisor.id)

        verify(exactly = 1) { mockedService.findEmployeesBySupervisorId(supervisor.id) }
        assertIterableEquals(employeesUnderSupervisor, actual)
    }

    @Test
    fun find_senior_supervisor_should_return_senior_supervisor() {
        every { mockedService.findSeniorSupervisor() } returns senior

        val actual = employeeUseCase.findSeniorSupervisor()

        verify(exactly = 1) { mockedService.findSeniorSupervisor() }
        assertEquals(senior, actual)
    }
}