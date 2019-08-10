package org.michaelbel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import org.michaelbel.data.local.model.UserLocal

@Dao
abstract class UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(users: ArrayList<UserLocal>)
}