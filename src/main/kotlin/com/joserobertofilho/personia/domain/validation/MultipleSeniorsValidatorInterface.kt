package com.joserobertofilho.personia.domain.validation

import com.joserobertofilho.personia.domain.exceptions.MultipleSeniorEmployeesFoundException

class MultipleSeniorsValidatorInterface : ValidatorInterface {
    override fun isValid(relationships: Map<String, String>): Boolean {
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
        return true
    }
}