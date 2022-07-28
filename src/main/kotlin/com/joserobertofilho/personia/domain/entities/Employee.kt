package com.joserobertofilho.personia.domain.entities

data class Employee(
    val id: Long = 0,
    val name: String,
    var supervisorId: Long?)
