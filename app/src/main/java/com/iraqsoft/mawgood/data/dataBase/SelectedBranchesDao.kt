package com.iraqsoft.mawgood.data.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iraqsoft.mawgood.data.model.Branch

@Dao
interface SelectedBranchesDao {

    @Query("SELECT * FROM selectedBranches")
    fun getSelectedBranches(): MutableList<Branch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSelectedBranches(selectedBranch:MutableList<Branch>)
}