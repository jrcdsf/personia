package com.joserobertofilho.personia.domain.validation

interface Validator {

    fun validate(relationships: Map<String, String>): Boolean
}