package com.shkitter.domain.files

interface FilesDataSource {
    suspend fun saveFile(fileName: String, extension: String, bytes: ByteArray): String
}