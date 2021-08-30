package com.iraqsoft.mawgood.repository

import com.iraqsoft.mawgood.db.model.Branch
import com.iraqsoft.mawgood.db.model.CheckInAndOutResponse
import com.iraqsoft.mawgood.db.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.db.model.LoginCompanyResponse
import retrofit2.Response

interface MainFragmentRepositoryInterface {

    suspend fun getBranches() : List<Branch>

    suspend fun getSelectedBranches() :MutableList<Branch>

    suspend fun addSelectedBranches(selectedBranches:MutableList<Branch>)

    suspend fun getEmployeesNeedsToBeSynced():List<EmpNeedsToBeSynced>

    suspend fun syncCachedEmployees(time:Long,empId:String): Response<CheckInAndOutResponse>

    suspend fun deleteEmp(emp:EmpNeedsToBeSynced)

}