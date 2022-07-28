package com.joserobertofilho.personia.domain.validation

import com.joserobertofilho.personia.domain.exceptions.HierarchyWithLoopException

class LoopValidator : Validator {
    override fun validate(relationships: Map<String, String>) {
        relationships.forEach { (key, value) ->
            if (relationships.filter { it.key == value && it.value == key }.isNotEmpty()) {
                throw HierarchyWithLoopException("Loop detected for employee $key")
            }
        }
    }
}