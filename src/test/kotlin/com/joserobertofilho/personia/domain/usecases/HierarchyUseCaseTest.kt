package com.joserobertofilho.personia.domain.usecases

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.joserobertofilho.personia.domain.entities.Employee
import com.joserobertofilho.personia.domain.validation.Validator
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


internal class HierarchyUseCaseTest {

    class WrapperHierarchyUseCase(
        val employees: Set<Employee>,
        val employee: Employee
    ) : HierarchyUseCase() {
        fun testGetSupervisorAndSeniorSupervisorByEmployee(): Map<String, Any> {
            return super.getSupervisorAndSeniorSupervisorByEmployee(
                employees,
                employee
            )
        }
    }

    private lateinit var wrapper: WrapperHierarchyUseCase

    @MockK
    lateinit var mockedEmployeeUseCase: EmployeeUseCase

    @MockK
    lateinit var mockedValidators: Set<Validator>

    @InjectMockKs
    lateinit var hierarchyUseCase: HierarchyUseCase

    private val employee = Employee(1, "test 1", 5)

    private val anotherEmployee = Employee(2, "test 2", 5)

    private val supervisor = Employee(5, "supervisor", 10, true)

    private val employeesUnderSupervisor = mutableSetOf(employee, anotherEmployee)

    private val senior = Employee(10, "supervisor", null, true)

    private val allEmployees = mutableSetOf(employee, supervisor, senior)

    private val hierarchy: MutableMap<String, Set<String>> = mutableMapOf()

    private val payload = """{
                "Pete": "Nick",
                "Barbara": "Nick",
                "Nick": "Sophie",
                "Sophie": "Jonas"
            }"""

    private var supervisorAndSenior: Map<String, Any> = mutableMapOf()

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
        setupHierarchy()
        wrapper = WrapperHierarchyUseCase(allEmployees, employee)
        supervisorAndSenior = wrapper.testGetSupervisorAndSeniorSupervisorByEmployee()

    }

    private fun setupHierarchy() {
        val relationships = ObjectMapper().readValue<MutableMap<String, String>>(payload)
        relationships.forEach { (emp, sup) ->
            if (hierarchy[sup] != null)
                hierarchy[sup] = hierarchy[sup]!!.plus(emp)
            else
                hierarchy[sup] = mutableSetOf(emp)
        }
    }

    @Test
    fun createHierarchy() {


    }

    @Test
    fun getSupervisorAndSeniorSupervisorByEmployee() {
        every { mockedEmployeeUseCase.find(any()) } returns employee
        every { mockedEmployeeUseCase.findAll() } returns allEmployees

        val actual = hierarchyUseCase.getSupervisorAndSeniorSupervisorByEmployee(employee.name)

        verify(exactly = 1) { mockedEmployeeUseCase.find(any()) }
        verify(exactly = 1) { mockedEmployeeUseCase.findAll() }

        assertEquals(supervisorAndSenior, actual)
    }

    @Test
    fun getFullHierarchy() {

    }


}