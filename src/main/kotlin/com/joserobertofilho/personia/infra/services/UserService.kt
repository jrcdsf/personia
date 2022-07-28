package com.joserobertofilho.personia.infra.services

import com.joserobertofilho.personia.infra.entities.UserEntity
import com.joserobertofilho.personia.infra.models.UserDetailsImpl
import com.joserobertofilho.personia.infra.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    fun create(userEntity: UserEntity): UserEntity {
        userEntity.password = bCryptPasswordEncoder.encode(userEntity.password)
        return userRepository.save(userEntity)
    }

    fun myself(): String? {
        return userRepository.findByEmail(getCurrentUserEmail())?.fullName
    }

    private fun getCurrentUserEmail(): String? {
        val user = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
        return user.username
    }
}