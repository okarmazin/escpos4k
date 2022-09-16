package cz.multiplatform.escpos4k.core

import arrow.core.Either
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

inline fun <A, B> Either<A, B>.shouldBeLeft(expected: A) {
  shouldBeInstanceOf<Either.Left<A>>().value shouldBe expected
}

inline fun <A, B> Either<A, B>.shouldBeLeft(): A {
  return shouldBeInstanceOf<Either.Left<A>>().value
}

inline fun <A, B> Either<A, B>.shouldBeRight(expected: B) {
  shouldBeInstanceOf<Either.Right<B>>().value shouldBe expected
}

inline fun <A, B> Either<A, B>.shouldBeRight(): B {
  return shouldBeInstanceOf<Either.Right<B>>().value
}
