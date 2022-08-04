package com.joserobertofilho.personia.infra.security

import com.joserobertofilho.personia.infra.entities.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(private val userEntity: UserEntity) : UserDetails {

    override fun getAuthorities() = mutableListOf<GrantedAuthority>()

    override fun isEnabled() = true

    override fun getUsername() = userEntity.email

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = userEntity.password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}
