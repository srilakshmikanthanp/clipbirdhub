package com.srilakshmikanthanp.clipbirdhub.device

import com.srilakshmikanthanp.clipbirdhub.common.annotation.CreateGroup
import com.srilakshmikanthanp.clipbirdhub.common.annotation.UpdateGroup
import com.srilakshmikanthanp.clipbirdhub.user.UserUtility
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RequestMapping("/devices")
@RestController
class DeviceController(
  private val conversionService: ConversionService,
  private val deviceService: DeviceService,
  private val deviceMapper: DeviceMapper
) {
  @PostMapping
  fun createDevice(@RequestBody @Validated(CreateGroup::class) req: DeviceRequestDto): DeviceResponseDto {
    val device = deviceService.save(deviceMapper.create(req, false, UserUtility.currentUser))
    return conversionService.convert(device, DeviceResponseDto::class.java)!!
  }

  @PreAuthorize("@deviceSecurity.isDeviceOwnedCurrentUser(#id)")
  @GetMapping("/{id}")
  fun getDeviceById(@PathVariable id: String): DeviceResponseDto {
    val device = deviceService.getById(id)
    return conversionService.convert(device, DeviceResponseDto::class.java)!!
  }

  @GetMapping
  fun getAllDevices(@RequestParam(required = false) search: String?, pageable: Pageable): Page<DeviceResponseDto> {
    val devices = deviceService.getAllUserDevices(UserUtility.currentUser.id!!, search, pageable)
    return devices.map { conversionService.convert(it, DeviceResponseDto::class.java)!! }
  }

  @PreAuthorize("@deviceSecurity.isDeviceOwnedCurrentUser(#id)")
  @PatchMapping("/{id}")
  fun updateDevice(
    @PathVariable id: String,
    @RequestBody @Validated(UpdateGroup ::class) req: DeviceRequestDto
  ): DeviceResponseDto {
    val device = deviceMapper.update(req, deviceService.getById(id))
    return conversionService.convert(device, DeviceResponseDto::class.java)!!
  }

  @PreAuthorize("@deviceSecurity.isDeviceOwnedCurrentUser(#id)")
  @DeleteMapping("/{id}")
  fun deleteDevice(@PathVariable id: String) {
    deviceService.deleteById(id)
  }

  @DeleteMapping("/all")
  fun deleteAllDevices() {
    deviceService.deleteAllByUserId(UserUtility.currentUser.id!!)
  }
}
