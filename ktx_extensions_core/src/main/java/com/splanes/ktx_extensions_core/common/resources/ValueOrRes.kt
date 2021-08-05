package com.splanes.ktx_extensions_core.common.resources

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.*
import com.splanes.ktx_extensions_core.common.primitive.EMPTY
import kotlin.reflect.KClass

sealed class ValueOrRes<T : Any>(private val kClassOfT: KClass<T>) {

    // Abstract attributes
    abstract var value: T?
    abstract var resource: Int?

    // Common properties
    val isEmpty: Boolean get() = value == null && resource == null

    val isNotEmpty: Boolean get() = !isEmpty

    val isValue: Boolean get() = isNotEmpty && value != null

    val isResource: Boolean get() = isNotEmpty && resource != null

    /*// Open common functions
    open fun resolveResource(context: Context): T? =
        resource?.resolve(context, resourceTypeOf(kClassOfT))

    open fun getOrNull(context: Context): T? = value ?: resolveResource(context)*/

    // Sealed children
    data class StringOrRes(
        override var value: String? = null,
        @StringRes override var resource: Int? = null
    ) : ValueOrRes<String>(String::class) {

        val valueOrEmpty: String get() = value.orEmpty()

        fun isBlankValue(): Boolean = value.isNullOrBlank()

        fun isBlank(): Boolean = isBlankValue() || resource == null

       // fun getOrEmpty(context: Context): String = getOrNull(context).orEmpty()

        companion object {
            //val empty: StringOrRes get() = EMPTY.toValueOrRes()
        }
    }

    data class DrawableOrRes(
        override var value: Drawable? = null,
        @DrawableRes override var resource: Int? = null
    ) : ValueOrRes<Drawable>(Drawable::class)

    data class DimenOrRes(
        override var value: Float? = null,
        @DimenRes override var resource: Int? = null
    ) : ValueOrRes<Float>(Float::class)

    data class ColorOrRes(
        @ColorInt override var value: Int? = null,
        @ColorRes override var resource: Int? = null,
        var hex: String? = null
    ) : ValueOrRes<Int>(Int::class) {

        // TODO: Revision(05/08/21) -> Uncomment when 'safe' had been implemented
        //fun resolveColorHex(): Int? = safe { Color.parseColor(hex) }

        /*override fun resolveResource(context: Context): Int? {
            return super.resolveResource(context) *//*?: resolveColorHex()*//*
        }

        override fun getOrNull(context: Context): Int? {
            return super.getOrNull(context) *//*?: resolveColorHex()*//*
        }*/
    }
}
