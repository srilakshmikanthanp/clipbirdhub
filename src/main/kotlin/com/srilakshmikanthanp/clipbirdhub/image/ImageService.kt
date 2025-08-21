package com.srilakshmikanthanp.clipbirdhub.image

interface ImageService<T: Image> {
  fun saveImage(image: T): T
  fun getImage(id: String): T
  fun deleteImage(id: String)
}
