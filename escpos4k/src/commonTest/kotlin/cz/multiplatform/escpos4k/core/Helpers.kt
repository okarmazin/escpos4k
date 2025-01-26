@file:Suppress("unused")

package cz.multiplatform.escpos4k.core

import arrow.core.Either

fun String.asciiToBytes(): ByteArray = toCharArray().map { it.code.toByte() }.toByteArray()

// inline fun <T> assertLeft(message: String? = null, block: () -> Either<T, *>): T = when (val value = block()) {
//    is Either.Left -> value.value
//    is Either.Right -> throw AssertionError(message ?: "Expected Either.Left but found Either.Right")
// }

fun <T> assertLeft(value: Either<T, *>, message: String? = null): T =
    when (value) {
      is Either.Left -> value.value
      is Either.Right ->
          throw AssertionError(message ?: "Expected Either.Left but found Either.Right with value ${value.value}")
    }

// inline fun <T> assertRight(message: String? = null, block: () -> Either<*, T>): T = when (val value = block()) {
//    is Either.Left -> throw AssertionError(message ?: "Expected Either.Right but found Either.Left")
//    is Either.Right -> value.value
// }

fun <T> assertRight(value: Either<*, T>, message: String? = null): T =
    when (value) {
      is Either.Left ->
          throw AssertionError(message ?: "Expected Either.Right but found Either.Left with value ${value.value}")
      is Either.Right -> value.value
    }
