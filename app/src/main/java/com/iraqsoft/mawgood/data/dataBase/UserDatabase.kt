package com.iraqsoft.mawgood.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iraqsoft.mawgood.data.model.Branch
import com.iraqsoft.mawgood.data.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.data.model.GetResponseItem
import com.iraqsoft.mawgood.data.model.LoginCompanyResponse

@Database(
    entities = [LoginCompanyResponse::class, Branch::class, GetResponseItem::class, EmpNeedsToBeSynced::class],
    version = 10, exportSchema = false
)


abstract class UserDatabase : RoomDatabase() {
    abstract val userDao : UserDao
    abstract val branchesDao: SelectedBranchesDao
    abstract val employeesDao: EmployeesDao
    abstract val empNeedsToBeSynced: EmpNeedsToBeSyncedDao
}