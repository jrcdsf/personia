package com.joserobertofilho.personia.domain.config

import com.joserobertofilho.personia.domain.validation.LoopValidator
import com.joserobertofilho.personia.domain.validation.MultipleSeniorsValidator
import com.joserobertofilho.personia.domain.validation.Validator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ValidatorConfig {

    @Bean
    fun validators(): Set<Validator> {
        return setOf(LoopValidator(), MultipleSeniorsValidator())
    }
}