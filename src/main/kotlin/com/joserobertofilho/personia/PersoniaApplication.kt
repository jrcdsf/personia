package com.joserobertofilho.personia

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
class PersoniaApplication

fun main(args: Array<String>) {
	runApplication<PersoniaApplication>(*args)
}
