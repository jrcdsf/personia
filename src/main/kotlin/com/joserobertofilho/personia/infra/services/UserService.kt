package com.joserobertofilho.personia.infra.services

import com.joserobertofilho.personia.domain.boundaries.UserServiceInterface
import com.joserobertofilho.personia.infra.entities.UserEntity
import com.joserobertofilho.personia.infra.security.UserDetailsImpl
import com.joserobertofilho.personia.infra.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService : UserServiceInterface {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    override fun create(userEntity: UserEntity): UserEntity {
        userEntity.password = bCryptPasswordEncoder.encode(userEntity.password)
        return userRepository.save(userEntity)
    }

}
