package com.eviden.ttsexample2.util

abstract class Callback<T> {
    abstract fun onDone(res: T)
    fun onError(e: Exception?) {}
}