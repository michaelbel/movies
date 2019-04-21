package org.michaelbel.data.local

import androidx.room.Dao

@Dao
abstract class BaseDao<DATA> {

    /**
     * Insert an object in the database.
     *
     * @param item the object to be inserted.
     */
    /*@Insert(onConflict = REPLACE)
    abstract fun insert(item: DATA): Completable*/

    /**
     * Insert an array of objects in the database.
     *
     * @param items the objects to be inserted.
     */
    /*@Insert(onConflict = REPLACE)
    abstract fun insert(vararg items: DATA): Completable*/

    /**
     * Insert a list of objects in the database.
     *
     * @param items the objects to be inserted.
     */
    /*@Insert(onConflict = REPLACE)
    abstract fun insert(items: List<DATA>): Completable*/

    /**
     * Update an object from the database.
     *
     * @param item the object to be updated
     */
    /*@Update
    abstract fun update(item: DATA)*/

    /**
     * Delete an object from the database
     *
     * @param item the object to be deleted
     */
    /*@Delete
    abstract fun delete(item: DATA)*/
}