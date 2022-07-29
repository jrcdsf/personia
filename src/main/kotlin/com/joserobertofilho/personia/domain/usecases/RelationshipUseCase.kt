package com.joserobertofilho.personia.domain.usecases

import com.joserobertofilho.personia.domain.entities.Employee
import com.joserobertofilho.personia.domain.exceptions.EmployeeNotFoundException
import com.joserobertofilho.personia.domain.exceptions.SeniorSupervisorNotFoundException
import com.joserobertofilho.personia.domain.validation.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RelationshipUseCase {

    @Autowired
    lateinit var employeeUseCase: EmployeeUseCase

    @Autowired
    lateinit var validators: Set<Validator>

    fun createRelationships(relationships: Map<String, String>): MutableMap<String, Set<String>> {
        validate(relationships)
        val hierarchy: MutableMap<String, Set<String>> = mutableMapOf()
        relationships.forEach { (emp, sup) ->
            if (hierarchy[sup] != null)
                hierarchy[sup] = hierarchy[sup]!!.plus(emp)
            else
                hierarchy[sup] = mutableSetOf(emp)
        }
        hierarchy.forEach { relation ->
            val supervisor = employeeUseCase.addEmployee(Employee(name = relation.key, supervisorId = null, isSupervisor = true))

            var newEmployee: Employee?
            relation.value.forEach {
                val foundEmployee = employeeUseCase.find(it)
                if (foundEmployee != null) {
                    foundEmployee.supervisorId = supervisor.id
                    newEmployee = employeeUseCase.addEmployee(foundEmployee)
                } else newEmployee = employeeUseCase.addEmployee(Employee(name = it, supervisorId = supervisor.id))

            }
        }
        return hierarchy
    }

    private fun validate(relationships: Map<String, String>) {
        validators.forEach { it.validate(relationships) }
    }

    fun getSupervisorAndSeniorSupervisorByEmployee(name: String): Map<String, Any> {
        val aEmployee = employeeUseCase.find(name)
        return if (aEmployee != null) {
            val all = employeeUseCase.findAll()
            if (all != null) {
                val supervisors = getSupervisorAndSeniorSupervisorByEmployee(all, aEmployee)
                supervisors
            } else {
                emptyMap()
            }
        } else {
            throw EmployeeNotFoundException("Employee $name not found")
        }
    }

    private fun getChildren(employees: Set<Employee>, employee: Employee): Map<String, Any> {
        val emps = employees.filter { it.supervisorId == employee.id }.toSet()
        val result: MutableMap<String, Any> = mutableMapOf()
        if (emps.isEmpty()) {
            return mutableMapOf(employee.name to emptyList<String>())
        } else {
            result[employee.name] = emps.map { it.name }
        }
        return result
    }

    private fun getSupervisorAndSeniorSupervisorByEmployee(employees: Set<Employee>, employee: Employee): Map<String, Any> {
        val supervisor = employees.firstOrNull { it.id == employee.supervisorId }
        val senior = employees.firstOrNull { it.id == supervisor?.supervisorId }
        val result: MutableMap<String, Any> = mutableMapOf()
        val emptyList = mutableMapOf<String, String>()
        val empEntry: Map<String, Any> = mutableMapOf(employee.name to emptyList)

        if (senior != null) {
            val supervisorEntry: Map<String?, Any> = mutableMapOf(supervisor?.name to empEntry)
            result[senior.name] = supervisorEntry
        } else if (supervisor != null)
            result[supervisor.name] = empEntry
        else
            result[employee.name] = emptyList
        return result
    }

    private fun getHierarchy(employee: Employee): Map<String, Any> {

        val children: Set<Employee>?
        val result: MutableMap<String, Any> = mutableMapOf()
        val emptyList = mutableMapOf<String, String>()

        val aEmp = employeeUseCase.find(employee.name)
        if (aEmp != null) {
            val supervisorId = aEmp.id
            children = supervisorId.let { employeeUseCase.findEmployeesBySupervisorId(it) }
            if (children?.isEmpty() == true) {
                return mutableMapOf(employee.name to emptyList)
            } else {
                children?.map {
                    val empl = getHierarchy(it)
                    if (result[employee.name] == null)
                        result[employee.name] = empl
                    else {
                        result[employee.name] = mutableSetOf(result[employee.name], empl)
                    }
                }

            }
        }
        return result
    }

    fun getFullHierarchy(): Map<String, Any>? {
        val senior = employeeUseCase.findSeniorSupervisor()
        if (senior != null) {
            val hierarcy = getHierarchy(senior)
            return hierarcy
        } else {
            throw SeniorSupervisorNotFoundException("No senior supervisor found!")
        }
    }
}