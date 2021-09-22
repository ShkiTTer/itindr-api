package com.shkitter.domain.common.utils

import java.security.MessageDigest
import java.security.SecureRandom

object HashUtil {
    private const val ALGORITHM_TYPE = "SHA-256"
    private const val HEX_CHARS = "0123456789ABCDEF"
    private const val SALT_SIZE = 16

    fun hash(input: String, salt: ByteArray): String {
        if (salt.isEmpty() || input == "") throw Exception("Password secure error")

        return hashString(hashString(input, salt), salt)
    }

    fun generateSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(SALT_SIZE)

        random.nextBytes(salt)

        return salt
    }

    private fun hashString(input: String, salt: ByteArray): String {
        val md = MessageDigest.getInstance(ALGORITHM_TYPE).apply {
            reset()
            update(salt)
        }

        val bytes = md.digest(input.toByteArray())

        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }
}