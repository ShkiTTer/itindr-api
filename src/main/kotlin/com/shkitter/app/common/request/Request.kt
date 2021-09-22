package com.shkitter.app.common.request

abstract class Request<out T> {
    protected abstract fun validateOrThrowError()
    protected abstract fun toVerified(): T
    fun validateAndConvertToVerified(): T {
        validateOrThrowError()
        return toVerified()
    }
}