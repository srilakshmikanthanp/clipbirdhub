package com.srilakshmikanthanp.clipbirdhub.common.exception

open class ApplicationException(override val message: String, override val cause: Throwable? = null) : RuntimeException()
