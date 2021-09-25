package com.shkitter.domain.common.utils

object FileUtils {
    private val allowableImageExtensions = listOf("png", "jpg", "jpeg")

    fun isExtensionAllowed(extension: String) = allowableImageExtensions.contains(extension)
}