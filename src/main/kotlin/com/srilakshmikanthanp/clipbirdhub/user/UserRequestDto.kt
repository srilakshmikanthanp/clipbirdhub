package com.srilakshmikanthanp.clipbirdhub.user

import com.srilakshmikanthanp.clipbirdhub.common.annotation.CreateGroup
import com.srilakshmikanthanp.clipbirdhub.common.annotation.NullOrNotBlank
import com.srilakshmikanthanp.clipbirdhub.common.annotation.UpdateGroup
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null

data class UserRequestDto(
  @field:NullOrNotBlank(message = "firstName can't be empty", groups = [CreateGroup::class, UpdateGroup::class])
  @field:NotNull(message = "firstName can't be null", groups = [CreateGroup::class])
  var firstName: String?,
  @field:NullOrNotBlank(message = "lastName can't be empty", groups = [CreateGroup::class, UpdateGroup::class])
  @field:NotNull(message = "lastName can't be null", groups = [CreateGroup::class])
  var lastName: String?,
  @field:NullOrNotBlank(message = "userName can't be empty", groups = [CreateGroup::class, UpdateGroup::class])
  @field:NotNull(message = "userName can't be null", groups = [CreateGroup::class])
  var userName: String?,
  @field:NullOrNotBlank(message = "email can't be empty", groups = [CreateGroup::class])
  @field:NotNull(message = "email can't be null", groups = [CreateGroup::class])
  @field:Null(message = "email must be null on update", groups = [UpdateGroup::class])
  var email: String?,
  @field:NullOrNotBlank(message = "password can't be empty", groups = [CreateGroup::class])
  @field:NotNull(message = "password can't be null", groups = [CreateGroup::class])
  @field:Null(message = "password must be null on update", groups = [UpdateGroup::class])
  var password: String?,
  @field:NullOrNotBlank(message = "avatar can't be empty", groups = [CreateGroup::class, UpdateGroup::class])
  var avatar: String?,
  @field:NotNull(message = "isActive can't be null", groups = [CreateGroup::class])
  var isActive: Boolean?,
)
