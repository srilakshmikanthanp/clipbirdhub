package com.srilakshmikanthanp.clipbirdhub.common.filters

import com.srilakshmikanthanp.clipbirdhub.common.exception.ApplicationException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

class FilterExceptionHandler(
  private val resolver: HandlerExceptionResolver
) : OncePerRequestFilter() {
  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    try {
      filterChain.doFilter(request, response)
    } catch (ex: ApplicationException) {
      resolver.resolveException(request, response, null, ex)
    }
  }
}
