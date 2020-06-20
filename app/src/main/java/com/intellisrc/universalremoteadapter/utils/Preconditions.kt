package com.intellisrc.universalremoteadapter.utils

object Preconditions {
    fun <T> checkNotNull(`object`: T?): T {
        if (`object` == null) {
            throw NullPointerException("object == null")
        }
        return `object`
    }

    fun checkArgument(isTrue: Boolean, errorMessage: String?) {
        if (!isTrue) {
            throw RuntimeException(errorMessage)
        }
    }
}