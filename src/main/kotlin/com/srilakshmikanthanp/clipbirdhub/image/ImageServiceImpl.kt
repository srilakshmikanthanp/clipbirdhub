package com.srilakshmikanthanp.clipbirdhub.image

import com.srilakshmikanthanp.clipbirdhub.common.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class ImageServiceImpl<T: Image>(
  private val imageRepository: ImageRepository<T>
) : ImageService<T> {
  override fun saveImage(image: T): T {
    return imageRepository.save(image)
  }

  override fun getImage(id: String): T {
    return imageRepository.findById(id).orElseThrow { NotFoundException("Image with $id not found") }
  }

  override fun deleteImage(id: String) {
    imageRepository.deleteById(id)
  }
}
