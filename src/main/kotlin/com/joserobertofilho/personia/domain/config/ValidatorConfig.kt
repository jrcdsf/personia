package com.joserobertofilho.personia.domain.config

import com.joserobertofilho.personia.domain.validation.EmptyRelationshipsValidatorInterface
import com.joserobertofilho.personia.domain.validation.LoopValidatorInterface
import com.joserobertofilho.personia.domain.validation.MultipleSeniorsValidatorInterface
import com.joserobertofilho.personia.domain.validation.ValidatorInterface
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ValidatorConfig {

    @Bean
    fun validators(): Set<ValidatorInterface> {
        return setOf(
            LoopValidatorInterface(),
            MultipleSeniorsValidatorInterface(),
            EmptyRelationshipsValidatorInterface()
        )
    }
}