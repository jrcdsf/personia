package com.joserobertofilho.personia.infra.repositories

import com.joserobertofilho.personia.infra.models.User

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String?): User?

}
