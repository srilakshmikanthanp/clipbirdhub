package com.srilakshmikanthanp.clipbirdhub.device

import com.srilakshmikanthanp.clipbirdhub.common.annotation.CreateGroup
import com.srilakshmikanthanp.clipbirdhub.common.annotation.NullOrNotBlank
import com.srilakshmikanthanp.clipbirdhub.common.annotation.UpdateGroup
import jakarta.validation.constraints.NotNull

data class DeviceRequestDto(
  @NullOrNotBlank(message = "publicKey cannot be blank", groups = [CreateGroup::class, UpdateGroup::class])
  @NotNull(message = "publicKey cannot be null", groups = [CreateGroup::class, UpdateGroup::class])
  var publicKey: String,
  @NullOrNotBlank(message = "name cannot be blank", groups = [CreateGroup::class, UpdateGroup::class])
  @NotNull(message = "name cannot be null", groups = [CreateGroup::class])
  var name: String,
  @NotNull(message = "type cannot be null", groups = [CreateGroup::class, UpdateGroup::class])
  var type: DeviceType,
)
