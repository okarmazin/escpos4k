@file:Suppress("unused")

package cz.multiplatform.escpos4k.core

import arrow.core.Either
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

fun <A, B> Either<A, B>.shouldBeLeft(expected: A) {
  shouldBeInstanceOf<Either.Left<A>>().value shouldBe expected
}

fun <A, B> Either<A, B>.shouldBeLeft(): A {
  return shouldBeInstanceOf<Either.Left<A>>().value
}

fun <A, B> Either<A, B>.shouldBeRight(expected: B) {
  shouldBeInstanceOf<Either.Right<B>>().value shouldBe expected
}

fun <A, B> Either<A, B>.shouldBeRight(): B {
  return shouldBeInstanceOf<Either.Right<B>>().value
}

fun String.asciiToBytes(): ByteArray = toCharArray().map { it.code.toByte() }.toByteArray()
