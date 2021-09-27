package com.shkitter.domain.validation

import com.shkitter.domain.common.extensions.toUUID

sealed class ValidationRule<in T> {
    abstract fun validate(value: T): ValidationResult
}

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}

object EmailValidationRule : ValidationRule<String?>() {

    private const val EMAIL_REGEX = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")

    private const val EMAIL_MAX_LENGTH = 255

    override fun validate(value: String?): ValidationResult = when {
        value.isNullOrBlank() -> ValidationResult.Error("Email is required")
        value.length > EMAIL_MAX_LENGTH -> ValidationResult.Error("Email must be no longer than 255 characters")
        !value.matches(EMAIL_REGEX.toRegex()) -> ValidationResult.Error("Email is not correct")
        else -> ValidationResult.Valid
    }
}

object PasswordValidationRule : ValidationRule<String?>() {

    private const val PASSWORD_MIN_LENGTH = 8
    private const val PASSWORD_MAX_LENGTH = 255

    override fun validate(value: String?): ValidationResult = when {
        value.isNullOrBlank() -> ValidationResult.Error("Password is required")
        value.length < PASSWORD_MIN_LENGTH -> ValidationResult.Error("Password is too short")
        value.length > PASSWORD_MAX_LENGTH -> ValidationResult.Error("Password is too long")
        else -> ValidationResult.Valid
    }
}

object UserNameValidationRule : ValidationRule<String?>() {

    private const val USER_NAME_MAX_LENGTH = 100

    override fun validate(value: String?): ValidationResult = when {
        value.isNullOrBlank() -> ValidationResult.Error("User name is required")
        value.length > USER_NAME_MAX_LENGTH -> ValidationResult.Error("User name is too long")
        else -> ValidationResult.Valid
    }
}

object AboutMyselfValidationRule : ValidationRule<String?>() {

    private const val ABOUT_MYSELF_MAX_LENGTH = 250

    override fun validate(value: String?): ValidationResult = when {
        (value?.length ?: 0) > ABOUT_MYSELF_MAX_LENGTH -> ValidationResult.Error("About myself field is too long")
        else -> ValidationResult.Valid
    }
}

object TopicIdsValidationRule : ValidationRule<List<String>>() {
    override fun validate(value: List<String>): ValidationResult = when {
        value.any { it.toUUID() == null } -> ValidationResult.Error("Invalid some topic ids")
        else -> ValidationResult.Valid
    }
}

object LimitValidationRule : ValidationRule<Int?>() {
    override fun validate(value: Int?): ValidationResult = when {
        value == null -> ValidationResult.Error("Limit is required")
        value <= 0 -> ValidationResult.Error("Limit must be greater than 0")
        else -> ValidationResult.Valid
    }
}

object OffsetValidationRule : ValidationRule<Int?>() {
    override fun validate(value: Int?): ValidationResult = when {
        value == null -> ValidationResult.Error("Offset is required")
        value <= 0 -> ValidationResult.Error("Offset must be greater than 0")
        else -> ValidationResult.Valid
    }
}

object IdValidationRule : ValidationRule<String?>() {
    override fun validate(value: String?): ValidationResult = when {
        value.isNullOrBlank() -> ValidationResult.Error("ID is required")
        value.toUUID() == null -> ValidationResult.Error("Invalid ID")
        else -> ValidationResult.Valid
    }
}