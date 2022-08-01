package com.joserobertofilho.personia.infra.services

import com.joserobertofilho.personia.domain.boundaries.EmployeeServiceInterface
import com.joserobertofilho.personia.domain.entities.Employee
import com.joserobertofilho.personia.infra.mappers.EmployeeMapper
import com.joserobertofilho.personia.infra.repositories.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeService : EmployeeServiceInterface {

    @Autowired
    lateinit var employeeMapper: EmployeeMapper

    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    override fun add(employee: Employee): Employee {
        return employeeMapper.convertToEmployee(employeeRepository.save(employeeMapper.convertToEmployeeEntity(employee)))
    }

    override fun find(name: String): Employee? {
        return employeeRepository.findByName(name)?.let { employeeMapper.convertToEmployee(it) }
    }

    override fun findAll(): Set<Employee>? {
        return employeeRepository.findAll().map { employeeMapper.convertToEmployee(it) }.toSet()
    }

    override fun findEmployeesBySupervisorId(supervisorId: Long): Set<Employee>? {
        return employeeRepository.findEmployeesBySupervisorId(supervisorId)
            ?.map { employeeMapper.convertToEmployee(it) }
            ?.toSet()
    }

    override fun findSeniorSupervisor(): Employee? {
        return employeeRepository.findSeniorSupervisor()?.let { employeeMapper.convertToEmployee(it) }
    }


}