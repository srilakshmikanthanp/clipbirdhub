package com.srilakshmikanthanp.clipbirdhub.common.utility

import java.net.URLConnection

inline fun <reified T : Enum<T>> isKeyInEnum(name: String): Boolean {
  return T::class.java.enumConstants.any { it.name == name}
}

fun contentType(name: String): String {
  return URLConnection.guessContentTypeFromName(name)
}
