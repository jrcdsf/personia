package com.joserobertofilho.personia.domain.usecases

import com.joserobertofilho.personia.domain.boundaries.HierarchyUseCaseInterface
import com.joserobertofilho.personia.domain.entities.Employee
import com.joserobertofilho.personia.domain.exceptions.EmployeeNotFoundException
import com.joserobertofilho.personia.domain.exceptions.SeniorSupervisorNotFoundException
import com.joserobertofilho.personia.domain.validation.ValidatorInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HierarchyUseCase : HierarchyUseCaseInterface {

    @Autowired
    lateinit var employeeUseCase: EmployeeUseCase

    @Autowired
    lateinit var validatorInterfaces: Set<ValidatorInterface>

    object HierarchyStatus {
        var isHierarchyConfigured = false

    }

    override fun createHierarchy(relationships: Map<String, String>): MutableMap<String, Set<String>> {
        val validationStatus = validate(relationships)
        if (HierarchyStatus.isHierarchyConfigured)
            if (!employeeUseCase.resetEmployeesHierarchy()) return mutableMapOf()
            else
                HierarchyStatus.isHierarchyConfigured = false
        val hierarchy: MutableMap<String, Set<String>> = mutableMapOf()
        relationships.forEach { (emp, sup) ->
            if (hierarchy[sup] != null)
                hierarchy[sup] = hierarchy[sup]!!.plus(emp)
            else
                hierarchy[sup] = mutableSetOf(emp)
        }
        hierarchy.forEach { relation ->
            val supervisor =
                employeeUseCase.addEmployee(Employee(name = relation.key, supervisorId = null, isSupervisor = true))

            var newEmployee: Employee?
            relation.value.forEach {
                val foundEmployee = employeeUseCase.find(it)
                if (foundEmployee != null) {
                    foundEmployee.supervisorId = supervisor.id
                    newEmployee = employeeUseCase.addEmployee(foundEmployee)
                } else newEmployee = employeeUseCase.addEmployee(Employee(name = it, supervisorId = supervisor.id))

            }
        }
        HierarchyStatus.isHierarchyConfigured = true
        return hierarchy
    }

    override fun getSupervisorAndSeniorSupervisorByEmployee(name: String): Map<String, Any> {
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

    override fun getFullHierarchy(): Map<String, Any>? {
        val senior = employeeUseCase.findSeniorSupervisor()
        if (senior != null) {
            return getHierarchy(senior)
        } else {
            throw SeniorSupervisorNotFoundException("No senior supervisor found!")
        }
    }

    protected fun validate(relationships: Map<String, String>) {
        validatorInterfaces.forEach { it.isValid(relationships) }
    }

    protected fun getSupervisorAndSeniorSupervisorByEmployee(
        employees: Set<Employee>,
        employee: Employee
    ): Map<String, Any> {
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

    protected fun getHierarchy(employee: Employee): Map<String, Any> {

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

    protected fun resetHierarchy(): Boolean {
        return true
    }
}