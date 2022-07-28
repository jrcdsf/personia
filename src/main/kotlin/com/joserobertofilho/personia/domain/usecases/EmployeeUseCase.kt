package com.joserobertofilho.personia.domain.usecases

import com.joserobertofilho.personia.domain.boundaries.EmployeeServiceInterface
import com.joserobertofilho.personia.domain.entities.Employee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EmployeeUseCase {

    @Autowired
    lateinit var service: EmployeeServiceInterface

    fun addEmployee(employee: Employee) : Employee {
        return service.add(employee)
    }
    fun find(name: String) : Employee? {
        return service.find(name)
    }

    fun find(id: Long) : Employee? {
        return service.find(id)
    }

    fun findAll() : Set<Employee>? {
        return service.findAll()
    }

    fun findEmployeesBySupervisorId(supervisorId: Long): Set<Employee>? {
        return service.findEmployeesBySupervisorId(supervisorId)
    }

    fun findSeniorEmployee(): Employee? {
        return service.findSeniorSupervisor()
    }
}