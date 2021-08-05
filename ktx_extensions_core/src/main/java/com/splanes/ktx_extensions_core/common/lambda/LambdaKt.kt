package com.splanes.ktx_extensions_core.common.lambda

import com.splanes.ktx_extensions_core.common.scope.default.or

// Constants empty lambdas -------------------------------------------------------------------------

fun emptyLambda(): () -> Unit = {}

fun emptyLambdaSuspendable(): suspend () -> Unit = {}

fun <R> emptyLambdaWithReceiver(): R.() -> Unit = {}

fun <R> emptyLambdaSuspendableWithReceiver(): suspend R.() -> Unit = {}

fun (() -> Unit)?.orEmptyLambda(): () -> Unit = this ?: emptyLambda()

fun (suspend () -> Unit)?.orEmptyLambdaSuspendable(): suspend () -> Unit =
    this.or(emptyLambdaSuspendable())

fun <R> (R.() -> Unit)?.orEmptyLambda(): R.() -> Unit = this.or { emptyLambdaWithReceiver() }

fun <R> (suspend R.() -> Unit)?.orEmptyLambdaSuspendable(): suspend R.() -> Unit =
    this.or { emptyLambdaSuspendableWithReceiver() }

// Parse T to lambda of () -> T --------------------------------------------------------------------

fun <T> lambdaOf(value: T): () -> T = { value }

fun <T> lambdaSuspendableOf(value: T): suspend () -> T = lambdaOf(value).asSuspend()

fun <T, R> lambdaWithReceiverOf(value: T): R.() -> T = lambdaOf(value).withReceiver()

fun <T, R> lambdaSuspendableWithReceiverOf(value: T): suspend R.() -> T =
    lambdaWithReceiverOf<T, R>(value).asSuspendWithReceiver()

// Parse lambda to suspend lambda ------------------------------------------------------------------

fun <T> (() -> T).asSuspend(): suspend () -> T = { invoke() }

fun <T, R> (R.() -> T).asSuspendWithReceiver(): suspend R.() -> T = { invoke(this) }

// Add receiver to lambdas -------------------------------------------------------------------------

fun <T, R> (() -> T).withReceiver(): R.() -> T = { invoke() }

fun <T, R> (suspend () -> T).withReceiverSuspendable(): suspend R.() -> T = { invoke() }

// Other lambdas -----------------------------------------------------------------------------------

fun <R> lambdaWithReceiverOfTrue(): R.() -> Boolean = lambdaWithReceiverOf(value = true)

fun <R> lambdaWithReceiverOfFalse(): R.() -> Boolean = lambdaWithReceiverOf(value = false)
