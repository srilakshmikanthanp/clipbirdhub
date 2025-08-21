package com.srilakshmikanthanp.clipbirdhub.common.annotation

import org.springframework.security.core.annotation.AuthenticationPrincipal

@AuthenticationPrincipal(expression = "user")
@Retention(AnnotationRetention.RUNTIME)
annotation class CurrentUser
