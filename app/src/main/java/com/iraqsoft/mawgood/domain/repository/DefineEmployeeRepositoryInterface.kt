package com.iraqsoft.mawgood.domain.repository

import com.iraqsoft.mawgood.data.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.data.model.GetResponse
import com.iraqsoft.mawgood.data.model.GetResponseItem
import com.iraqsoft.mawgood.data.model.LoginCompanyResponse
import com.iraqsoft.mawgood.util.AppResult
import retrofit2.Response

interface DefineEmployeeRepositoryInterface {

    suspend fun getSelectedBranch() : String

    suspend fun getCompanyId() : String

    suspend fun requestEmployeesFromApi(companyId:String,branchId:String): AppResult<GetResponse>?

    suspend fun requestEmployeesFromDataBase() : List<GetResponseItem>


    suspend fun cacheEmployees(cache :List<GetResponseItem>)






}