package com.srilakshmikanthanp.clipbirdhub.image

import com.srilakshmikanthanp.clipbirdhub.common.annotation.CurrentUser
import com.srilakshmikanthanp.clipbirdhub.common.utility.contentType
import com.srilakshmikanthanp.clipbirdhub.user.User
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/images")
@RestController
class ImageController(
  private val conversionService: ConversionService,
  private val imageService: ImageService<Image>,
) {
  @PostMapping("/public")
  fun createPublicImage(@RequestParam("image") file: MultipartFile, @CurrentUser user: User): ImageResponseDto {
    var image = Image(name = file.originalFilename ?: file.name, blob = file.bytes, uploadedBy = user)
    image = imageService.saveImage(image)
    return conversionService.convert(image, ImageResponseDto::class.java)!!
  }

  @GetMapping("/public/{id}")
  fun getPubLicImage(@PathVariable id: String): ResponseEntity<ByteArray> {
    val image = imageService.getImage(id)
    return ResponseEntity.ok().header("Content-Type", contentType(image.name)).body(image.blob)
  }
}
