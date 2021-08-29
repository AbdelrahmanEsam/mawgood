package com.iraqsoft.mawgood.db

import androidx.room.*
import com.iraqsoft.mawgood.db.model.GetResponseItem

@Dao
interface EmployeesDao {

    @Query("SELECT * FROM selectedEmployees")
    fun getSelectedEmployees(): List<GetResponseItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSelectedEmployees(selectedEmployees:List<GetResponseItem>)

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun addSingleEmp(emp : GetResponseItem?)

    @Update
    fun updateEmp(emp : GetResponseItem?)

    @Delete
    fun deleteEmp(emp : GetResponseItem?)

}