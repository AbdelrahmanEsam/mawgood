package com.iraqsoft.mawgood.domain.repository

import com.iraqsoft.mawgood.data.model.Branch
import com.iraqsoft.mawgood.data.model.CheckInAndOutResponse
import com.iraqsoft.mawgood.data.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.data.model.LoginCompanyResponse
import retrofit2.Response

interface MainFragmentRepositoryInterface {

    suspend fun getBranches() : List<Branch>

    suspend fun getSelectedBranches() :MutableList<Branch>

    suspend fun addSelectedBranches(selectedBranches:MutableList<Branch>)

    suspend fun getEmployeesNeedsToBeSynced():List<EmpNeedsToBeSynced>

    suspend fun syncCachedEmployees(time:Long,empId:String): Response<CheckInAndOutResponse>

    suspend fun deleteEmp(emp: EmpNeedsToBeSynced)

}