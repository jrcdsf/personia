package com.joserobertofilho.personia.infra.services

import com.joserobertofilho.personia.helpers.BaseTest
import com.joserobertofilho.personia.infra.mappers.EmployeeMapper
import com.joserobertofilho.personia.infra.repositories.EmployeeRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class EmployeeServiceTest : BaseTest() {

    private val employeeMapper: EmployeeMapper = EmployeeMapper()

    @MockK
    lateinit var mockedRepository: EmployeeRepository

    @InjectMockKs
    lateinit var employeeService: EmployeeService

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `add an employee should return the employee created`() {
        every { mockedRepository.save(any()) } returns employeeMapper.convertToEmployeeEntity(employee)

        val actual = employeeService.add(employee)

        assertEquals(employee, actual)
    }

    @Test
    fun `find an employee by name should return the employee with that name`() {
        every { mockedRepository.findByName(employee.name) } returns employeeMapper.convertToEmployeeEntity(employee)

        val actual = employeeService.find(employee.name)

        assertEquals(employee, actual)
    }

    @Test
    fun `find all should return all employees`() {
        every { mockedRepository.findAll() } returns allEmployees.map { employeeMapper.convertToEmployeeEntity(it) }

        val actual = employeeService.findAll()

        assertIterableEquals(allEmployees, actual)
    }

    @Test
    fun `find employees by supervisor id should return all employees under that supervisor`() {
        every { mockedRepository.findEmployeesBySupervisorId(supervisor.id) } returns
                employeesUnderSupervisor.map { employeeMapper.convertToEmployeeEntity(it) }.toSet()

        val actual = employeeService.findEmployeesBySupervisorId(supervisor.id)

        assertIterableEquals(employeesUnderSupervisor, actual)
    }

    @Test
    fun `find senior supervisor should return the senior supervisor`() {
        every { mockedRepository.findSeniorSupervisor() } returns employeeMapper.convertToEmployeeEntity(senior)

        val actual = employeeService.findSeniorSupervisor()

        assertEquals(senior, actual)
    }
}