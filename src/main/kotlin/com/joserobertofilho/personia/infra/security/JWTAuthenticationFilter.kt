package com.joserobertofilho.personia.infra.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.joserobertofilho.personia.infra.models.Credentials
import com.joserobertofilho.personia.infra.models.UserDetailsImpl
import com.joserobertofilho.personia.infra.util.JWTUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter: UsernamePasswordAuthenticationFilter {

    private var jwtUtil: JWTUtil
    private val objectMapper: ObjectMapper

    constructor(authenticationManager: AuthenticationManager, jwtUtil: JWTUtil, objectMapper: ObjectMapper) : super() {
        this.authenticationManager = authenticationManager
        this.jwtUtil = jwtUtil
        this.objectMapper = objectMapper
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication? {
        try {
            val (email, password) = objectMapper.readValue(request.inputStream, Credentials::class.java)

            val token = UsernamePasswordAuthenticationToken(email, password)

            return authenticationManager.authenticate(token)
        } catch (e: Exception) {
            throw UsernameNotFoundException("User not found!")
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse, chain: FilterChain?, authResult: Authentication) {
        val username = (authResult.principal as UserDetailsImpl).username
        val token = jwtUtil.generateToken(username)
        response.addHeader("Authorization", "Bearer $token")
    }

}