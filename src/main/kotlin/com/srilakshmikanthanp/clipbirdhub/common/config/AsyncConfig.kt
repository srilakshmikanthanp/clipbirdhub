package com.srilakshmikanthanp.clipbirdhub.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@EnableAsync
@Configuration
class AsyncConfig: AsyncConfigurer {
  @Primary
  @Bean
  override fun getAsyncExecutor(): TaskExecutor {
    val availableProcessors = Runtime.getRuntime().availableProcessors()
    val executor = ThreadPoolTaskExecutor()
    executor.corePoolSize = availableProcessors * 2
    executor.maxPoolSize = availableProcessors * 4
    executor.queueCapacity = 500
    executor.initialize()
    return executor
  }
}
