package com.splanes.ktx_extensions_core.common.scope.default

import com.splanes.ktx_extensions_core.common.lambda.lambdaOf

// -------------------------------------------------------------------------------------------------
// Defaults
// -------------------------------------------------------------------------------------------------

infix fun <T> T?.default(block: () -> T): T =
    this ?: block()

suspend infix fun <T> T?.defaultSuspend(block: suspend () -> T): T =
    this ?: block()

infix fun <T> T?.default(default: T): T =
    this default lambdaOf(default)

infix fun <T> (() -> T)?.defaultLambda(other: () -> T): () -> T =
    this ?: other

// Or (Not deprecated but is recommended to use 'default') -----------------------------------------

fun <T> T?.or(block: () -> T): T =
    this default block

fun <T> T?.or(default: T): T =
    this default default

suspend fun <T> T?.orSuspendable(block: suspend () -> T): T =
    this defaultSuspend block

fun <T> T?.orNullable(default: T?): T? =
    this ?: default

// TODO: Revision(05/08/21) -> Uncomment when CustomThrowable had been implemented
/*
fun <T> T?.orThrow(throwable: Throwable? = null): T =
    this default { throw throwable.orDefault() }
    */
