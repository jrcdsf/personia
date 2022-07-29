package com.joserobertofilho.personia.domain.boundaries

import com.joserobertofilho.personia.domain.entities.Employee

interface EmployeeServiceInterface {

    fun add(employee: Employee): Employee
    fun find(id: Long): Employee?
    fun find(name: String): Employee?
    fun findAll(): Set<Employee>?
    fun findEmployeesBySupervisorId(supervisorId: Long): Set<Employee>?
    fun findSeniorSupervisor(): Employee?

}