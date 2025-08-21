package com.srilakshmikanthanp.clipbirdhub.common.annotation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

class NullOrNotBlankValidator: ConstraintValidator<NullOrNotBlank, String> {
  override fun isValid(field: String?, context: ConstraintValidatorContext?): Boolean {
    return field == null || field.isNotBlank()
  }
}

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = [NullOrNotBlankValidator::class])
annotation class NullOrNotBlank(
  val message: String = "Field should be either null or not blank",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = []
)
