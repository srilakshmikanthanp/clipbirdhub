package com.srilakshmikanthanp.clipbirdhub.common.config

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.core.io.Resource
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableTransactionManagement
@EnableMethodSecurity
@EnableAspectJAutoProxy
@Configuration
class ApplicationConfig {
  @Value("\${jwt.keys.private}")
  private lateinit var privateKey: RSAPrivateKey

  @Value("\${jwt.keys.public}")
  private lateinit var publicKey: RSAPublicKey

  @Bean
  fun transactionManager(emf: EntityManagerFactory?): PlatformTransactionManager? {
    return JpaTransactionManager(emf!!)
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  @Bean
  fun jwtDecoder(): JwtDecoder {
    return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
  }

  @Bean
  fun jwtEncoder(): JwtEncoder {
    val jwk: JWK = RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build()
    return NimbusJwtEncoder(ImmutableJWKSet(JWKSet(jwk)))
  }
}
