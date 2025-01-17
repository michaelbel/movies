package org.michaelbel.movies.widget.work

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.io.OutputStream

internal object MoviesGlanceStateDefinition: GlanceStateDefinition<MoviesWidgetState> {

    private const val DATA_STORE_FILENAME = "moviesGlanceWidgetState"

    private val Context.datastore by dataStore(DATA_STORE_FILENAME, MoviesSerializer)

    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<MoviesWidgetState> {
        return context.datastore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(DATA_STORE_FILENAME)
    }

    object MoviesSerializer: Serializer<MoviesWidgetState> {

        override val defaultValue = MoviesWidgetState.Failure("No Movies Found")

        override suspend fun readFrom(input: InputStream): MoviesWidgetState {
            return try {
                Json.decodeFromString(MoviesWidgetState.serializer(), input.readBytes().decodeToString())
            } catch (e: SerializationException) {
                throw CorruptionException("Could not read movies data: ${e.message}")
            }
        }

        override suspend fun writeTo(t: MoviesWidgetState, output: OutputStream) {
            output.use {
                it.write(Json.encodeToString(MoviesWidgetState.serializer(), t).encodeToByteArray())
            }
        }
    }
}