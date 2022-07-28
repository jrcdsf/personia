package com.joserobertofilho.personia.domain.usecases

import com.joserobertofilho.personia.domain.entities.Employee
import com.joserobertofilho.personia.domain.exceptions.EmployeeNotFoundException
import com.joserobertofilho.personia.domain.exceptions.SeniorEmployeeNotFoundException
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
            val supervisor = employeeUseCase.addEmployee(Employee(name = relation.key, supervisorId = null))

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

    fun getSupervisorsEmployee(name: String): Map<String, Any> {
        val aEmployee = employeeUseCase.find(name)
        return if (aEmployee != null) {
            val all = employeeUseCase.findAll()
            if (all != null ) {
                val supervisors = getSupervisors(all, aEmployee)
                supervisors
            } else {
                emptyMap()
            }

        } else {
            throw EmployeeNotFoundException("Employee $name not found")
        }
    }

    fun printHierarchy(employees: Set<Employee>) {
        val root = employees.first()
        //val worksforroot = employees.filter { it.supervisorId == root.id }
        val worksforroot = getChildren(employees, root)
        //println("root $root")
        println("worksforroot $worksforroot")

    }

    fun getChildren(employees: Set<Employee>, employee: Employee): Map<String, Any> {
        val emps = employees.filter { it.supervisorId == employee.id }.toSet()
        val result: MutableMap<String, Any> = mutableMapOf()
        if (emps.isEmpty()) {
            return mutableMapOf(employee.name to emptyList<String>())
        } else {
            result[employee.name] = emps.map { it.name }
        }
        return result
    }

    fun getSupervisors(employees: Set<Employee>, employee: Employee) : Map<String, Any> {
        val supervisor = employees.firstOrNull { it.id == employee.supervisorId }
        val senior = employees.firstOrNull { it.id == supervisor?.supervisorId }
        val result: MutableMap<String, Any> = mutableMapOf()
        val emptyList = mutableListOf<String>()
        val empEntry: Map<String, Any> = mutableMapOf(employee.name to emptyList)

        if (senior != null) {
            val supervisorEntry: Map<String?, Any> = mutableMapOf(supervisor?.name to empEntry)
            result[senior.name] = supervisorEntry
        }
        else if (supervisor != null)
            result[supervisor.name] = empEntry
        else
            result[employee.name] = emptyList
        return result
    }


    fun getFullHierarchy(): Map<String, Any> {
        val senior = employeeUseCase.findSeniorEmployee()
        if (senior != null) {
            val employees = getSupervisorsEmployee(senior.name)
            //printHierarchy(employees)
            return emptyMap()
        } else {
            throw SeniorEmployeeNotFoundException("No senior supervisor found!")
        }
    }
}