package com.joserobertofilho.personia.domain.validation

import com.joserobertofilho.personia.domain.exceptions.MultipleSeniorEmployeesFoundException

class MultipleSeniorsValidator : Validator {
    override fun validate(relationships: Map<String, String>) {
        val supervisors = relationships.values
        var numberOfSupervisors = 0
        val seniorSupervisorList: MutableSet<String> = mutableSetOf()
        supervisors.forEach { supervisor ->
            if (relationships.filterKeys { it == supervisor }.isEmpty()) {
                seniorSupervisorList.add(supervisor)
                numberOfSupervisors++
            }
        }
        if (numberOfSupervisors > 1)
            throw MultipleSeniorEmployeesFoundException("Found more than 1 senior supervisor - $seniorSupervisorList")
    }
}