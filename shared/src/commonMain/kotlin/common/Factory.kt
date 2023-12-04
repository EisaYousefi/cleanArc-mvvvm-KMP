package common

internal interface Factory<T> {
    fun create(): T
}