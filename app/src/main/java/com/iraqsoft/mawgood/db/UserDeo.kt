package com.iraqsoft.mawgood.db

import androidx.room.*
import com.iraqsoft.mawgood.db.model.UserModel

@Dao
interface UserDeo {

    @Query("SELECT * FROM Users")
    fun findOne(): UserModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(user: UserModel)

    @Query("DELETE FROM Users")
    fun delete()
}