package com.joserobertofilho.personia.domain.validation

interface Validator {

    fun isValid(relationships: Map<String, String>): Boolean
}