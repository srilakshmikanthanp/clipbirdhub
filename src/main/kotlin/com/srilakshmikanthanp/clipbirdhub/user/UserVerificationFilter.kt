package com.srilakshmikanthanp.clipbirdhub.user

import com.srilakshmikanthanp.clipbirdhub.auth.UserPrincipal
import com.srilakshmikanthanp.clipbirdhub.common.exception.ForbiddenException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerMappingIntrospector

@Order(Ordered.LOWEST_PRECEDENCE)
class UserVerificationFilter(
  private val handlerMappingIntrospector: HandlerMappingIntrospector
) : OncePerRequestFilter() {

  override fun shouldNotFilter(request: HttpServletRequest): Boolean {
    val handler = handlerMappingIntrospector.getMatchableHandlerMapping(request)?.getHandler(request)?.handler as? HandlerMethod

    if (SecurityContextHolder.getContext().authentication == null || handler == null) {
      return true
    }

    val methodAnnotated = handler.hasMethodAnnotation(UnverifiedUser::class.java)
    val classAnnotated = handler.beanType.isAnnotationPresent(UnverifiedUser::class.java)

    return methodAnnotated || classAnnotated
  }

  override fun doFilterInternal(
    request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain
  ) {
    val authentication = SecurityContextHolder.getContext().authentication
    val principal = authentication.principal

    if (principal is UserPrincipal && !principal.user.isVerified) {
      throw ForbiddenException("User not verified", ForbiddenException.USER_NOT_VERIFIED)
    }

    chain.doFilter(request, response)
  }
}
