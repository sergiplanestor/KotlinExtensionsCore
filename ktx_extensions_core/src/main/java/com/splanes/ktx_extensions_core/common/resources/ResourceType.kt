package com.splanes.ktx_extensions_core.common.resources

import android.graphics.drawable.Drawable
import com.splanes.ktx_extensions_core.common.primitive.EMPTY
import com.splanes.ktx_extensions_core.common.scope.default.or
import kotlin.reflect.KClass

private const val RESOURCE_TYPE_STRING = "string"
private const val RESOURCE_TYPE_COLOR = "color"
private const val RESOURCE_TYPE_DIMENSION = "dimen"
private const val RESOURCE_TYPE_DRAWABLE = "drawable"

enum class ResourceType(val value: String, val kClass: KClass<*>) {
    STRING(value = RESOURCE_TYPE_STRING, kClass = String::class),
    COLOR(value = RESOURCE_TYPE_COLOR, kClass = Int::class),
    DIMENSION(value = RESOURCE_TYPE_DIMENSION, kClass = Float::class),
    DRAWABLE(value = RESOURCE_TYPE_DRAWABLE, kClass = Drawable::class),
    UNKNOWN(value = EMPTY, kClass = Nothing::class)
}

inline fun <reified T> resourceTypeOf(): ResourceType =
    ResourceType.values().associateBy { it.kClass }[T::class].or(ResourceType.UNKNOWN)

fun resourceTypeOf(kClass: KClass<*>): ResourceType =
    ResourceType.values().associateBy { it.kClass }[kClass].or(ResourceType.UNKNOWN)