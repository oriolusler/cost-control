package com.oriolsoler.costcontroler.infrastructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic(withDefaults())
            .authorizeRequests {
                it
                    .antMatchers("/greeting").authenticated()
                    .anyRequest().permitAll()


            }

        return http.build()
    }
}