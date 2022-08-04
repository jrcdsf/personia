package com.joserobertofilho.personia.domain.boundaries

import com.joserobertofilho.personia.domain.entities.Employee

interface EmployeeServiceInterface {

    fun add(employee: Employee): Employee
    fun find(name: String): Employee?
    fun findAll(): Set<Employee>?
    fun findEmployeesBySupervisorId(supervisorId: Long): Set<Employee>?
    fun findSeniorSupervisor(): Employee?
    fun resetEmployees(): Boolean

}