package com.joserobertofilho.personia.domain.boundaries

import com.joserobertofilho.personia.infra.entities.UserEntity

interface UserServiceInterface {
    fun create(userEntity: UserEntity): UserEntity
}