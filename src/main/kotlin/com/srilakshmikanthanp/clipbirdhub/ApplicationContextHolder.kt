package com.srilakshmikanthanp.clipbirdhub

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
object ApplicationContextHolder : ApplicationContextAware {
  private lateinit var context: ApplicationContext

  override fun setApplicationContext(applicationContext: ApplicationContext) {
    context = applicationContext
  }

  val applicationContext: ApplicationContext get() = context
}
