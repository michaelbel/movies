package org.michaelbel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import org.michaelbel.data.local.model.TrailerLocal

@Dao
abstract class TrailersDao {

    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(trailers: ArrayList<TrailerLocal>)

    /*@Query("select * from trailers where id = :id")
    protected abstract fun findById(id: Int): Deferred<Video>

    @Query("select * from trailers")
    protected abstract fun getAll(): List<Video>

    @Query("select * from trailers where movieId = :id")
    protected abstract fun getAll(id: Int): List<Video>

    @Query("delete from trailers")
    protected abstract fun deleteAll()*/

    /**
     * Аннотирование метода с помощью @Transaction гарантирует, что все операции базы данных,
     * которые вы выполняете в этом методе, будут выполняться внутри одной транзакции.
     * Транзакция не будет выполнена, если в теле метода возникнет исключение.
     */
    /*@Transaction
    open fun transaction(videos: List<Video>) {
        deleteAll()
        insert(videos)
    }*/

    /**
     * На некоторых экранах нам не нужно отображать всю эту информацию.
     * Таким образом, вместо этого мы можем создать объект VideoMinimal,
     * который содержит только необходимые данные.
     */
    /*@Query("select id, title from trailers")
    protected abstract fun videosMinimal(): List<Video>*/
}