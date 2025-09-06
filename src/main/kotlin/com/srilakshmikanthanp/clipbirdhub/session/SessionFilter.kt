package com.srilakshmikanthanp.clipbirdhub.session

import com.srilakshmikanthanp.clipbirdhub.auth.JwtUserDetails
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SessionFilter(
  private val sessionService: SessionService
): OncePerRequestFilter() {
  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {
    val authentication = SecurityContextHolder.getContext().authentication as? JwtUserDetails
    if (authentication == null) {
      return filterChain.doFilter(request, response)
    }
    val session = sessionService.findByToken(authentication.jwt.tokenValue)
    if (session.isEmpty) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired or invalid")
    } else {
      filterChain.doFilter(request, response)
    }
  }
}
