package com.joserobertofilho.personia.infra.mappers

import com.joserobertofilho.personia.domain.entities.Employee
import com.joserobertofilho.personia.infra.entities.EmployeeEntity
import org.springframework.stereotype.Component

@Component
class EmployeeMapper {
    fun convertToEmployeeEntity(employee: Employee): EmployeeEntity {
        return EmployeeEntity(employee.id, employee.name, employee.supervisorId, employee.isSupervisor)

    }

    fun convertToEmployee(employeeEntity: EmployeeEntity): Employee {
        return Employee(
            employeeEntity.id,
            employeeEntity.name,
            employeeEntity.supervisorId,
            employeeEntity.isSupervisor
        )
    }

}