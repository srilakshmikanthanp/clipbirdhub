package com.srilakshmikanthanp.clipbirdhub.common.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException
import kotlin.reflect.KClass

class StringRegexValidator : ConstraintValidator<Regex, String?> {
  private lateinit var pattern: Pattern

  override fun initialize(annotation: Regex) {
    try {
      pattern = Pattern.compile(annotation.regexp)
    } catch (e: PatternSyntaxException) {
      throw IllegalArgumentException("Given regex is invalid", e)
    }
  }

  override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
    if (value == null) {
      return true
    }
    val m: Matcher = pattern.matcher(value)
    return m.matches()
  }
}

@MustBeDocumented
@Constraint(validatedBy = [StringRegexValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Regex(
  val message: String = "must match \"{regexp}\"",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Any>> = [],
  val regexp: String,
)
