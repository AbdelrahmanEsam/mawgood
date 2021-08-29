package com.iraqsoft.mawgood.repository

import com.iraqsoft.mawgood.db.model.Branch
import com.iraqsoft.mawgood.db.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.db.model.LoginCompanyResponse

interface MainFragmentRepositoryInterface {

    suspend fun getBranches() : List<Branch>

    suspend fun getSelectedBranches() :MutableList<Branch>

    suspend fun addSelectedBranches(selectedBranches:MutableList<Branch>)

    suspend fun getEmployeesNeedsToBeSynced():List<EmpNeedsToBeSynced>
}