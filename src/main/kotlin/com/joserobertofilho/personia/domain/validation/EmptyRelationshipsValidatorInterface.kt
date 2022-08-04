package com.joserobertofilho.personia.domain.validation

import com.joserobertofilho.personia.domain.exceptions.EmptyRelationshipsException

class EmptyRelationshipsValidatorInterface : ValidatorInterface {
    override fun isValid(relationships: Map<String, String>): Boolean {
        if (relationships.isEmpty())
            throw EmptyRelationshipsException("Empty relationships provided as input")
        return true
    }
}