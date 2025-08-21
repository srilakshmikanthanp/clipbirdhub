package com.srilakshmikanthanp.clipbirdhub.image

import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository<T: Image>: JpaRepository<T, String>
