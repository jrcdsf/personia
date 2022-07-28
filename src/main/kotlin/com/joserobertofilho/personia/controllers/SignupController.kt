package com.joserobertofilho.personia.controllers

import com.joserobertofilho.personia.infra.entities.UserEntity
import com.joserobertofilho.personia.infra.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/signup")
class SignupController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping
    fun signup(@RequestBody user: UserEntity): ResponseEntity<UserEntity> {
        val userCreated = userService.create(user)
        return ResponseEntity.created(URI("")).body(userCreated)
    }

    @GetMapping
    fun get(): ResponseEntity<String> {
        return ResponseEntity("Hello", HttpStatus.OK)
    }

}