package com.shkitter.domain.validation

sealed class ValidationRule<T> {
    abstract val value: T
    abstract fun validate(): Boolean
}

class EmailValidationRule(override val value: String) : ValidationRule<String>() {

    private companion object {
        const val EMAIL_REGEX = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")
    }

    override fun validate(): Boolean = value.matches(EMAIL_REGEX.toRegex())
}

class PasswordValidationRule(override val value: String) : ValidationRule<String>() {

    private companion object {
        const val PASSWORD_MIN_LENGTH = 8
    }

    override fun validate(): Boolean = value.length >= PASSWORD_MIN_LENGTH
}