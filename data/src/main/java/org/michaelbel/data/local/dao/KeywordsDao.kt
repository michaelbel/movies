package org.michaelbel.data.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import org.michaelbel.data.local.model.KeywordLocal

@Dao
abstract class KeywordsDao {

    @Insert(onConflict = REPLACE)
    abstract fun insert(keyword: KeywordLocal)

    @Insert(onConflict = REPLACE)
    abstract fun insert(vararg keywords: KeywordLocal)

    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(keywords: ArrayList<KeywordLocal>)

    @Update
    abstract fun update(keyword: KeywordLocal)

    @Delete
    abstract fun delete(keyword: KeywordLocal)

    /*@Query("select * from keywords where keyword_id = :id")
    abstract fun findById(id: Int): Deferred<KeywordLocal>*/

    @Query("select * from keywords")
    abstract fun getAll(): List<KeywordLocal>

    @Query("select * from keywords where keyword_id = :id")
    abstract fun getAll(id: Int): List<KeywordLocal>

    @Query("delete from keywords")
    abstract fun deleteAll()
}