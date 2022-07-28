package com.joserobertofilho.personia.domain.usecases

import com.joserobertofilho.personia.domain.entities.Employee
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
            println(supervisor)
            var newEmployee: Employee?
            relation.value.forEach {
                val foundEmployee = employeeUseCase.find(it)
                if (foundEmployee != null) {
                    foundEmployee.supervisorId = supervisor.id
                    newEmployee = employeeUseCase.addEmployee(foundEmployee)
                } else newEmployee = employeeUseCase.addEmployee(Employee(name = it, supervisorId = supervisor.id))

                print(newEmployee)
            }
        }
        return hierarchy
    }

    private fun validate(relationships: Map<String, String>) {
        validators.forEach { it.validate(relationships) }
    }

    fun getHierarchyPerEmployee(name: String): Set<Employee> {
        val emps: MutableSet<Employee> = mutableSetOf()
        val children: Set<Employee>

        val aEmp = employeeUseCase.find(name)
        if (aEmp != null) {
            emps.add(aEmp)
            val supervisorId = aEmp.id
            children = supervisorId.let { employeeUseCase.findEmployeesBySupervisorId(it) }!!
            children.forEach {
                val list = getHierarchyPerEmployee(it.name)
                emps.addAll(list)
            }
        }
        //printHierarchy(emps)
        return emps
    }

    fun printHierarchy(employees: Set<Employee>) {
        val root = employees.first()
        val worksforroot = employees.filter { it.supervisorId == root.id }
        val nonSupervisors = employees.forEach { emp ->
            employees.filter { it.supervisorId == emp.id }
        }
        if (worksforroot.size > 1) {

        } else {
            val emp = worksforroot[0]
        }

    }

    fun getFullHierarchy(): Set<Employee> {
        val senior = employeeUseCase.findSeniorEmployee()
        if (senior != null) {
            return getHierarchyPerEmployee(senior.name)
        } else {
            throw SeniorEmployeeNotFoundException("No senior supervisor found!")
        }

    }
}