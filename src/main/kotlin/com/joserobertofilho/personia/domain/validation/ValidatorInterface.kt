package com.joserobertofilho.personia.domain.validation

interface ValidatorInterface {

    fun isValid(relationships: Map<String, String>): Boolean
}