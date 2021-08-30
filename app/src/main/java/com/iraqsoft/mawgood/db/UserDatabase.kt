package com.iraqsoft.mawgood.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iraqsoft.mawgood.db.model.Branch
import com.iraqsoft.mawgood.db.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.db.model.GetResponseItem
import com.iraqsoft.mawgood.db.model.LoginCompanyResponse

@Database(
    entities = [LoginCompanyResponse::class, Branch::class,GetResponseItem::class,EmpNeedsToBeSynced::class],
    version = 10, exportSchema = false
)


abstract class UserDatabase : RoomDatabase() {
    abstract val userDao : UserDao
    abstract val branchesDao: SelectedBranchesDao
    abstract val employeesDao:EmployeesDao
    abstract val empNeedsToBeSynced:EmpNeedsToBeSyncedDao
}