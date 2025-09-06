package com.srilakshmikanthanp.clipbirdhub.common.config

import com.srilakshmikanthanp.clipbirdhub.auth.AppUserDetailsService
import com.srilakshmikanthanp.clipbirdhub.auth.AuthConstants
import com.srilakshmikanthanp.clipbirdhub.auth.JwtUserDetails
import com.srilakshmikanthanp.clipbirdhub.common.filters.FilterExceptionHandler
import com.srilakshmikanthanp.clipbirdhub.session.SessionFilter
import com.srilakshmikanthanp.clipbirdhub.session.SessionService
import com.srilakshmikanthanp.clipbirdhub.user.UserService
import com.srilakshmikanthanp.clipbirdhub.user.UserVerificationFilter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.handler.HandlerMappingIntrospector

@Configuration
class SecurityConfig(
  private val handlerMappingIntrospector: HandlerMappingIntrospector,
  private val sessionService: SessionService,
  private val userService: UserService,
  private val passwordEncoder: PasswordEncoder,
  @Qualifier("handlerExceptionResolver")
  private val resolver: HandlerExceptionResolver,
) {
  @Bean
  fun authenticationManager(): AuthenticationManager {
    val authenticationProvider = DaoAuthenticationProvider()
    authenticationProvider.setUserDetailsService(userDetailsService())
    authenticationProvider.setPasswordEncoder(passwordEncoder)
    return ProviderManager(authenticationProvider)
  }

  @Bean
  fun userDetailsService(): UserDetailsService {
    return AppUserDetailsService(userService)
  }

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .addFilterBefore(
        FilterExceptionHandler(resolver),
        UsernamePasswordAuthenticationFilter::class.java
      )
      .addFilterAfter(
        SessionFilter(sessionService),
        BearerTokenAuthenticationFilter::class.java
      )
      .addFilterAfter(
        UserVerificationFilter(handlerMappingIntrospector),
        SecurityContextHolderAwareRequestFilter::class.java
      )
      .cors { it.configurationSource(corsConfigurationSource()) }
      .csrf { it.disable() }
      .authorizeHttpRequests {
        AuthConstants.AUTH_FREE_URLS.forEach { (pathPattern, method) ->
          it.requestMatchers(HttpMethod.valueOf(method), pathPattern).permitAll()
        }
        it.anyRequest().authenticated()
      }
      .oauth2ResourceServer {
        it.jwt { config ->
          config.jwtAuthenticationConverter(
            JwtUserDetails.JwtUserDetailsConverter(userService)
          )
        }
      }
      .sessionManagement {
        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      }
      .exceptionHandling {
        it.authenticationEntryPoint(BearerTokenAuthenticationEntryPoint())
          .accessDeniedHandler(BearerTokenAccessDeniedHandler())
      }
    return http.build()
  }

  @Bean
  fun corsConfigurationSource(): CorsConfigurationSource {
    val configuration = CorsConfiguration().applyPermitDefaultValues()
    configuration.addAllowedOrigin("*");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", configuration)
    return source
  }
}
