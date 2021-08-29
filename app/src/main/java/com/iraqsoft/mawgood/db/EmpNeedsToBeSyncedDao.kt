package com.iraqsoft.mawgood.db

import androidx.room.*
import com.iraqsoft.mawgood.db.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.util.AppResult

@Dao
interface EmpNeedsToBeSyncedDao {

    @Query("SELECT * FROM EmpNeedsToBeSynced")
    fun getEmployeesNeedsToBeSynced(): List<EmpNeedsToBeSynced>

    @Insert
    fun addSingleEmp(emp : EmpNeedsToBeSynced?)

    @Delete
    fun deleteEmp(emp : EmpNeedsToBeSynced?)

}