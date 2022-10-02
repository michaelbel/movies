@file:Suppress("unused")

package org.michaelbel.movies.entities

/**
 * A class that encapsulates a successful result with a value of type T
 * or a failure result with an [Throwable] exception
 */

@Suppress("unchecked_cast")
sealed class Either<out R> {
    data class Success<out T>(val value: T): Either<T>()
    data class Failure(val exception: Throwable): Either<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$value]"
            is Failure -> "Failure[exception=$exception]"
        }
    }

    companion object {
        /**
         * Construct a safe Either from statement
         * ```kotlin
         * Either.on { "something" }
         * ```
         */
        inline fun <T> on(f: () -> T): Either<T> = try {
            val result = f()
            if (result is Either<*>) {
                result as Either<T>
            } else {
                Success(result)
            }
        } catch (ex: Exception) {
            Failure(ex)
        }
    }
}

val Either<*>.success: Boolean
    get() = this is Either.Success && value != null

fun <T> Either<T>.successOr(fallback: T): T {
    return (this as? Either.Success<T>)?.data ?: fallback
}

val Either<*>.failure: Boolean
    get() = this is Either.Failure

val <T> Either<T>.data: T?
    get() = (this as? Either.Success)?.value

val <T> Either<T>.throwable: Throwable?
    get() = (this as? Either.Failure)?.exception

/**
 * Unwrap and receive the success result data or do a function with *return*
 * ```kotlin
 * val data = useCase.getData()
 *     .takeOrReturn {
 *         Log.d("LOG", "it is an error again")
 *         return
 *     }
 * ```
 */
inline fun <T> Either<T>.takeOrReturn(f: (Throwable) -> Unit): T = when (this) {
    is Either.Success -> this.value
    is Either.Failure -> {
        f(this.exception)
        throw IllegalStateException("You must write 'return' in the failure lambda")
    }
}

/**
 * Unwrap and receive the success result data or receive the default value in failure case
 * ```kotlin
 * val data = useCase.getData()
 *     .takeOrDefault {
 *         "default data"
 *     }
 * ```
 */
inline fun <T> Either<T>.takeOrDefault(default: () -> T): T = when (this) {
    is Either.Success -> this.value
    is Either.Failure -> default()
}

/**
 * Unwrap and receive the success result data or receive '''null''' in failure case
 * ```kotlin
 * val data = useCase.getData()
 *     .takeOrNull()
 */
fun <T> Either<T>.takeOrNull(): T? = when (this) {
    is Either.Success -> this.data
    is Either.Failure -> null
}

/**
 * Transform the success result by applying a function to it to another Either
 * ```kotlin
 * useCase.getData()
 *     .flatMap { Reaction.of { "Flatmapped data" } }
 * ```
 */
inline fun <T, R> Either<T>.flatMap(f: (T) -> Either<R>) = try {
    when (this) {
        is Either.Success -> f(this.value)
        is Either.Failure -> this
    }
} catch (e: Exception) {
    Either.Failure(e)
}

/**
 * Transform the success result by applying a function to it
 * ```kotlin
 * useCase.getData()
 *     .map { "Convert to another string" }
 * ```
 */
inline fun <R, T> Either<T>.map(f: (T) -> R) = try {
    when (this) {
        is Either.Success -> Either.Success(f(this.value))
        is Either.Failure -> this
    }
} catch (e: Exception) {
    Either.Failure(e)
}

/**
 * Transform the error result by applying a function to it
 * ```kotlin
 * useCase.getData()
 *     .errorMap { IllegalStateException("something went wrong") }
 * ```
 */
inline fun <T> Either<T>.errorMap(f: (Throwable) -> Throwable) = try {
    when (this) {
        is Either.Success -> this
        is Either.Failure -> Either.Failure(f(this.exception))
    }
} catch (e: Exception) {
    Either.Failure(e)
}

/**
 * Transform the failure result by applying a function to it to another Either
 * ```kotlin
 * useCase.getData()
 *     .recover { "New reaction, much better then old" }
 * ```
 */
inline fun <T> Either<T>.recover(transform: (exception: Throwable) -> T): Either<T> = try {
    when (this) {
        is Either.Success -> this
        is Either.Failure -> Either.on { transform(this.exception) }
    }
} catch (e: Exception) {
    Either.Failure(e)
}

/**
 * Handle the Either result with one action with success and failure
 * ```kotlin
 * useCase.getData(data)
 *     .flatHandle { success, failure ->
 *         Log.d("LOG", "Let's combine results ${success.toString() + failure.toString()}")
 *     }
 * ```
 */
inline fun <T> Either<T>.flatHandle(f: (T?, Throwable?) -> Unit) {
    when (this) {
        is Either.Success -> f(this.data, null)
        is Either.Failure -> f(null, this.exception)
    }
}

/**
 * Register an action to take when Either is nevermind
 * ```kotlin
 * useCase.getData()
 *     .doOnComplete { Log.d("Let's dance in any case!") }
 * ```
 */
inline fun <T> Either<T>.doOnComplete(f: () -> Unit) {
    f()
}

/**
 * Handle the Either result with on success and on failure actions
 * ```kotlin
 * useCase.getData(data)
 *     .handle(
 *         success = { liveData.postValue(it) },
 *         failure = { Log.d("LOG", "Failure. That's a shame") }
 *     )
```
 */
inline fun <T> Either<T>.handle(success: (T) -> Unit, failure: (Throwable) -> Unit) {
    when (this) {
        is Either.Success -> success(this.value)
        is Either.Failure -> failure(this.exception)
    }
}

/**
 * Handle the Either result with on success and on failure actions and transform them to the new object
 * ```kotlin
 * useCase.getData()
 *     .zip(
 *         success = { State.Success(it) },
 *         failure = { State.Failure }
 *     )
 * ```
 */
inline fun <T, R> Either<T>.zip(success: (T) -> R, failure: (Throwable) -> R): R =
    when (this) {
        is Either.Success -> success(this.value)
        is Either.Failure -> failure(this.exception)
    }

/**
 * Register an action to take when Either is Success
 * ```kotlin
 * useCase.getData()
 *     .doOnSuccess { Log.d("Success! Let's dance!") }
 * ```
 */
inline fun <T> Either<T>.doOnSuccess(f: (T) -> Unit): Either<T> = try {
    when (this) {
        is Either.Success -> {
            f(this.value)
            this
        }
        is Either.Failure -> this
    }
} catch (e: Exception) {
    Either.Failure(e)
}

/**
 * Either an action to take when Either is Failure
 * ```kotlin
 * useCase.getData()
 *     .doOnError { Log.d("Error! Let's dance but sadly =(") }
 * ```
 */
inline fun <T> Either<T>.doOnFailure(f: (Throwable) -> Unit): Either<T> = try {
    when (this) {
        is Either.Success -> this
        is Either.Failure -> {
            f(this.exception)
            this
        }
    }
} catch (e: Exception) {
    Either.Failure(e)
}

/**
 * Check the success result by a function
 * ```kotlin
 * useCase.getData()
 *     .check { it.isNotEmpty() }
 * ```
 */
inline fun <T> Either<T>.check(message: String = "", f: (T) -> Boolean): Either<T> = try {
    when (this) {
        is Either.Success -> {
            check(f(this.value)) { message }
            this
        }
        is Either.Failure -> this
    }
} catch (e: Exception) {
    Either.Failure(e)
}