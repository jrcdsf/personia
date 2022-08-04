package com.joserobertofilho.personia.infra.repositories

import com.joserobertofilho.personia.infra.entities.UserEntity

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String?): UserEntity?

}
