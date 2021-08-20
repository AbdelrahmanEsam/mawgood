package com.iraqsoft.mawgood.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iraqsoft.mawgood.db.model.GetResponseItem

@Dao
interface EmployeesDao {

    @Query("SELECT * FROM selectedEmployees")
    fun getSelectedEmployees(): List<GetResponseItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSelectedEmployees(selectedEmployees:List<GetResponseItem>)

}