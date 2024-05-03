package com.bsuirnethub.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.web.SecurityFilterChain

/**
 * Configures our application with Spring Security to restrict access to our API endpoints.
 */
@Configuration
class SecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        /*
        This is where we configure the security required for our endpoints and setup our app to serve as
        an OAuth2 Resource Server, using JWT validation.
        */
        return http
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/api/public/**").permitAll()
                    .requestMatchers("/api/private/**").authenticated()
            }
            .cors(Customizer.withDefaults())
            .oauth2ResourceServer { oauth2: OAuth2ResourceServerConfigurer<HttpSecurity?> ->
                oauth2
                    .jwt(Customizer.withDefaults())
            }
            .build()
    }
}
