package org.michaelbel.domain

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.data.local.dao.TrailersDao
import org.michaelbel.data.local.model.TrailerLocal
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.Video
import org.michaelbel.data.remote.model.base.Result
import retrofit2.Response

class TrailersRepository(private val api: Api, private val dao: TrailersDao): Repository() {

    //region Remote

    suspend fun trailers(movieId: Long, apiKey: String): Response<Result<Video>> {
        return api.trailers(movieId, apiKey).await()
    }

    //endregion

    // region Local

    suspend fun addAll(items: List<Video>) {
        val trailers = java.util.ArrayList<TrailerLocal>()
        items.forEach {
            val trailer = TrailerLocal(id = it.id, title = it.name)
            trailers.add(trailer)
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = dao.insert(trailers)
                withContext(Dispatchers.Main) {
                    Log.e("1488", "Вставка произошла успешно!")
                }
            } catch (e: Exception) {
                Log.e("1488", "Exception: $e")
            }
        }

        dao.insert(trailers)
    }

    //endregion
}