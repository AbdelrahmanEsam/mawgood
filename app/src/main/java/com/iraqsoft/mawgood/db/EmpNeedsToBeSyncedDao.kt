package com.iraqsoft.mawgood.db

import androidx.room.*
import com.iraqsoft.mawgood.db.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.util.AppResult
import retrofit2.Response

@Dao
interface EmpNeedsToBeSyncedDao {

    @Query("SELECT * FROM EmpNeedsToBeSynced")
    fun getEmployeesNeedsToBeSynced(): List<EmpNeedsToBeSynced>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSingleEmp(emp : EmpNeedsToBeSynced?)

    @Delete
    fun deleteEmp(emp : EmpNeedsToBeSynced)

}