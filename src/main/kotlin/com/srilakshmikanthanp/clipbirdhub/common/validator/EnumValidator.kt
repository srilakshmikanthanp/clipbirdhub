package com.srilakshmikanthanp.clipbirdhub.common.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.util.*
import kotlin.reflect.KClass

class EnumTypeValidator : ConstraintValidator<EnumValidator, Enum<*>> {
  private var valueList: MutableList<String> = ArrayList()

  override fun initialize(annotation: EnumValidator) {
    for (value in annotation.type.java.enumConstants ?: emptyArray()) {
      valueList.add(value.name.uppercase(Locale.getDefault()))
    }
  }

  override fun isValid(value: Enum<*>, context: ConstraintValidatorContext): Boolean {
    return valueList.contains(value.name.uppercase())
  }
}

@MustBeDocumented
@Constraint(validatedBy = [EnumTypeValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class EnumValidator(
  val message: String = "Not a valid enum field",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Any>> = [],
  val type: KClass<out Enum<*>>
)
