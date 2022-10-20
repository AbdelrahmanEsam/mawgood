package com.iraqsoft.mawgood.data.repository

import android.content.Context
import android.util.Log
import com.iraqsoft.mawgood.data.remote.ApiProvider
import com.iraqsoft.mawgood.data.dataBase.EmpNeedsToBeSyncedDao
import com.iraqsoft.mawgood.data.dataBase.EmployeesDao
import com.iraqsoft.mawgood.data.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.data.model.GetResponseItem
import com.iraqsoft.mawgood.domain.repository.FingerPrintRpoInterface
import com.iraqsoft.mawgood.util.AppResult
import com.iraqsoft.mawgood.util.NetworkManager.isOnline
import com.iraqsoft.mawgood.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class FingerPrintRepository(private val api: ApiProvider, private val dao: EmployeesDao, private val empNeedToBeSynced: EmpNeedsToBeSyncedDao, private val context: Context) :
    FingerPrintRpoInterface {
    override suspend fun cashSingleEmp(emp: GetResponseItem?) {
        Log.e("cashSingleItem" , "start Function")
        if(emp != null ) {
            Log.e("cashSingleItem" , "emp != null")
            dao.deleteEmp(emp)
            dao.addSingleEmp(emp)
        }else{
            Log.e("cashSingleItem" , "emp == null")
        }
    }

    override suspend fun getEmps(): List<GetResponseItem> {
        return withContext(Dispatchers.IO) {
            dao.getSelectedEmployees()
        }
    }

    override suspend fun empCheck(emp: GetResponseItem): AppResult<Any>? {
        Log.e("online" , isOnline(context).toString())
        if (isOnline(context)){
            return try {
                val response =  api.check(emp._id)
                if(response.isSuccessful){
                    response.body()?.let {
                        Log.e("checkEmpResponse" , response.body().toString())
                        Utils.handleSuccess(response)
                    }
                }else{

                    Utils.handleApiError(response)
                }
            }catch (e: Exception){
                return  AppResult.Error(e)
            }
        }else{
            empNeedToBeSynced.addSingleEmp(EmpNeedsToBeSynced(emp._id,emp.displayName,null,time = System.currentTimeMillis()/1000))
            return   Utils.handleSuccess(Response.success(EmpNeedsToBeSynced(emp._id,emp.displayName,null,time = System.currentTimeMillis()/1000)))
        }

    }

    override suspend fun cacheCheck(emp: EmpNeedsToBeSynced) {
        empNeedToBeSynced.addSingleEmp(emp)
    }

}