package com.joserobertofilho.personia.infra.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val fullName: String = "",
    val email: String = "",
    var password: String = ""
)
