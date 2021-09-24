package com.shkitter.data.files

import com.shkitter.domain.files.FilesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.CoroutineContext

class FilesDataSourceImpl(
    private val storePath: String
) : FilesDataSource, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override suspend fun saveFile(fileName: String, extension: String, bytes: ByteArray): String =
        withContext(coroutineContext) {
            val file = File("$storePath/$fileName.$extension")
            file.writeBytes(bytes)
            file.path
        }
}