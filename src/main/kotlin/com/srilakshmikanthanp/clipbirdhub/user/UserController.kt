package com.srilakshmikanthanp.clipbirdhub.user

import com.srilakshmikanthanp.clipbirdhub.common.annotation.*
import com.srilakshmikanthanp.clipbirdhub.common.dto.Message
import org.springframework.core.convert.ConversionService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class UserController(
  private val conversionService: ConversionService,
  private val userService: UserService,
  private val userMapper: UserMapper,
) {
  @PostMapping
  fun create(@Validated(CreateGroup::class) @RequestBody req: UserRequestDto): UserResponseDto {
    var user = conversionService.convert(req, User::class.java)!!
    user = userService.saveUser(user)
    userService.sendVerificationMail(user)
    return conversionService.convert(user, UserResponseDto::class.java)!!
  }

  @UnverifiedUser
  @PostMapping("/verify/resend")
  fun resendVerificationMail(@CurrentUser user: User) {
    userService.sendVerificationMail(user)
  }

  @UnverifiedUser
  @PostMapping("/verify/{token}")
  fun verify(@PathVariable token: String) {
    userService.verify(token)
  }

  @GetMapping("/me")
  fun get(@CurrentUser user: User): UserResponseDto {
    return conversionService.convert(user, UserResponseDto::class.java)!!
  }

  @PatchMapping
  fun update(
    @CurrentUser user: User,
    @Validated(UpdateGroup::class) @RequestBody req: UserRequestDto
  ): UserResponseDto {
    userMapper.update(req, user)
    return conversionService.convert(userService.saveUser(user), UserResponseDto::class.java)!!
  }

  @DeleteMapping
  fun delete(@CurrentUser user: User): Message {
    userService.deleteUserById(user.id!!)
    return Message("User with ${user.id} has been deleted")
  }
}
