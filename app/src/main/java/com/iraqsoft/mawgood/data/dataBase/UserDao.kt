package com.iraqsoft.mawgood.data.dataBase

import androidx.room.*
import com.iraqsoft.mawgood.data.model.LoginCompanyResponse

@Dao
interface UserDao {

    @Query("SELECT * FROM Response")
    fun findOne(): LoginCompanyResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLoginResponse(user: LoginCompanyResponse)


    @Query("DELETE FROM Response")
    fun delete()
}