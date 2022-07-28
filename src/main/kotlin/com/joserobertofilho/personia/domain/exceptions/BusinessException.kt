package com.joserobertofilho.personia.domain.exceptions


data class BusinessException(
    val status: Int? = null,
    val message: String? = null
)