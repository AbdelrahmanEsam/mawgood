package com.iraqsoft.mawgood.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iraqsoft.mawgood.db.model.UserModel

@Database(
    entities = [UserModel::class],
    version = 1, exportSchema = false
)


abstract class UserDatabase : RoomDatabase() {
    abstract val userDeo : UserDeo
}