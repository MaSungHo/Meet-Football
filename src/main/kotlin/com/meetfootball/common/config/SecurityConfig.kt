package com.meetfootball.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@EnableWebSecurity
@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.formLogin { formLogin ->
                formLogin.disable()
            }.csrf { csrf ->
                csrf.disable()
            }.authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests.requestMatchers(
                        AntPathRequestMatcher.antMatcher("/api/**"), AntPathRequestMatcher.antMatcher("/**/api-docs/**")
                    ).permitAll()
            }.build()
    }
}
