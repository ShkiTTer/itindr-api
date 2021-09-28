package com.shkitter.domain.attachment.model

data class NewAttachment(
    val origFileName: String,
    val bytes: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewAttachment

        if (origFileName != other.origFileName) return false
        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = origFileName.hashCode()
        result = 31 * result + bytes.contentHashCode()
        return result
    }
}
