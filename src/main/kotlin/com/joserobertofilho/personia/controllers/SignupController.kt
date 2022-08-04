package com.joserobertofilho.personia.controllers

import com.joserobertofilho.personia.domain.boundaries.UserServiceInterface
import com.joserobertofilho.personia.infra.entities.UserEntity
import com.joserobertofilho.personia.infra.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/signup")
class SignupController {

    @Autowired
    private lateinit var userService: UserServiceInterface

    @PostMapping
    fun signup(@RequestBody userEntity: UserEntity): ResponseEntity<UserEntity> {
        val userCreated = userService.create(userEntity)
        return ResponseEntity.created(URI("")).body(userCreated)
    }

}