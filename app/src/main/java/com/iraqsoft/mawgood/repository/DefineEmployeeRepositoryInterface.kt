package com.iraqsoft.mawgood.repository

import com.iraqsoft.mawgood.db.model.GetResponse
import com.iraqsoft.mawgood.db.model.GetResponseItem
import com.iraqsoft.mawgood.db.model.LoginCompanyResponse
import com.iraqsoft.mawgood.util.AppResult
import retrofit2.Response

interface DefineEmployeeRepositoryInterface {

    suspend fun getSelectedBranch() : String

    suspend fun getCompanyId() : String

    suspend fun requestEmployeesFromApi(companyId:String,branchId:String): AppResult<GetResponse>?

    suspend fun requestEmployeesFromDataBase() : List<GetResponseItem>


}