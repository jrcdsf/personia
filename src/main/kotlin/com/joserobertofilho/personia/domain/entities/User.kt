package com.joserobertofilho.personia.domain.entities

data class User(
    val id: Long = 0,
    val fullname: String = "",
    val email: String = "",
    var password: String = ""
)
