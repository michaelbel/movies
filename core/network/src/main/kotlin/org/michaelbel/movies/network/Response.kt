package org.michaelbel.movies.network

suspend fun <T> response(
    request: suspend () -> T
): Either<T> {
    return try {
        Either.Success(request.invoke())
    } catch (e: Exception) {
        Either.Failure(e)
    }
}