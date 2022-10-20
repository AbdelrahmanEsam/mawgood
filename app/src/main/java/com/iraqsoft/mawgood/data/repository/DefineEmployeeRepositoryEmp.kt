package com.iraqsoft.mawgood.data.repository

import android.util.Log
import com.iraqsoft.mawgood.data.remote.ApiProvider
import com.iraqsoft.mawgood.data.dataBase.EmployeesDao
import com.iraqsoft.mawgood.data.dataBase.SelectedBranchesDao
import com.iraqsoft.mawgood.data.dataBase.UserDao
import com.iraqsoft.mawgood.data.model.GetResponse
import com.iraqsoft.mawgood.data.model.GetResponseItem
import com.iraqsoft.mawgood.domain.repository.DefineEmployeeRepositoryInterface
import com.iraqsoft.mawgood.util.AppResult
import com.iraqsoft.mawgood.util.Utils.handleApiError
import com.iraqsoft.mawgood.util.Utils.handleSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefineEmployeeRepositoryEmp(private val api : ApiProvider, private val userDao : UserDao, private val branchesDao : SelectedBranchesDao
                                  , private val employeesDao: EmployeesDao
): DefineEmployeeRepositoryInterface {
    override suspend fun getSelectedBranch(): String {
        return branchesDao.getSelectedBranches()[0]._id
    }

    override suspend fun getCompanyId(): String {
     return  userDao.findOne().userId
    }

    override suspend fun requestEmployeesFromApi(companyId: String, branchId: String): AppResult<GetResponse>? {
       return try {
           val response =  api.getEmployees(companyId,branchId)
            if(response.isSuccessful){
                response.body()?.let {
                    Log.e("getItemsResponse" , response.body().toString())
                    withContext(Dispatchers.IO){
                        cacheEmployees(response.body()!!)
                    }
                    handleSuccess(response)
                }
            }else{
                handleApiError(response)
            }
        }catch (e: Exception){
            return  AppResult.Error(e)
        }


    }

    override suspend fun requestEmployeesFromDataBase(): List<GetResponseItem> {
        return withContext(Dispatchers.IO) {
            employeesDao.getSelectedEmployees()
        }
    }

    override suspend fun cacheEmployees(cache: List<GetResponseItem>) {
        employeesDao.addSelectedEmployees(cache)
    }



}