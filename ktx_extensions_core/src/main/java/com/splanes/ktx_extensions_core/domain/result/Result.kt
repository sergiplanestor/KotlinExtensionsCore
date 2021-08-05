package com.splanes.ktx_extensions_core.domain.result

sealed class Result<in T, in R> {

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Failure
    val isUnknown: Boolean get() = this is Unknown
    val isFailureOrUnknown: Boolean get() = !isSuccess

    data class Success<T>(val data: T) : Result<T, Nothing>()

    data class Failure<R>(
        val data: R?,
        val isUnknownFailure: Boolean
    ) : Result<Nothing, R>()

    object Unknown : Result<Nothing, Nothing>()

}

fun <T, R, S> Result<T, R>.doWhen(
    onSuccess: T.() -> S,
    onFailure: (data: R?, isUnknownFailure: Boolean) -> S,
    onUnknown: (() -> S)? = null,
    manageUnknownAsFailure: Boolean = true
): S =
    when (this) {
        is Result.Failure -> onFailure(data, false)
        is Result.Success -> data.onSuccess()
        Result.Unknown -> when {
            manageUnknownAsFailure -> {
                onFailure(null, true)
            }
            onUnknown != null -> {
                onUnknown()
            }
            else -> {
                // TODO: Revision(05/08/21) -> Change to CustomThrowable when implemented
                throw RuntimeException()
            }
        }
    }