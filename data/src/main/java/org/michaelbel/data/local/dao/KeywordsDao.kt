package org.michaelbel.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import org.michaelbel.data.Keyword
import org.michaelbel.data.local.BaseDao

@Dao
abstract class KeywordsDao: BaseDao<Keyword>() {

    @Query("select * from keywords where id = :id")
    abstract fun findById(id: Int): Flowable<Keyword>

    @Query("select * from keywords")
    abstract fun getAll(): List<Keyword>

    @Query("select * from keywords where movieId = :id")
    abstract fun getAll(id: Int): List<Keyword>

    @Query("delete from keywords")
    abstract fun deleteAll()
}