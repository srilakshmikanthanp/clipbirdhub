package com.srilakshmikanthanp.clipbirdhub.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class TaskSchedulerConfig {
  @Bean
  fun taskScheduler(): TaskScheduler {
    return ThreadPoolTaskScheduler().apply {
      poolSize = Runtime.getRuntime().availableProcessors() * 2
    }
  }
}
