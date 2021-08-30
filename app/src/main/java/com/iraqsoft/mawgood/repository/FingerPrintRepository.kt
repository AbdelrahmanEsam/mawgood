package com.iraqsoft.mawgood.repository

import android.util.Log
import com.iraqsoft.mawgood.ApiProvider
import com.iraqsoft.mawgood.db.EmpNeedsToBeSyncedDao
import com.iraqsoft.mawgood.db.EmployeesDao
import com.iraqsoft.mawgood.db.model.CheckInAndOutResponse
import com.iraqsoft.mawgood.db.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.db.model.GetResponseItem
import com.iraqsoft.mawgood.util.AppResult
import com.iraqsoft.mawgood.util.CheckConnection
import com.iraqsoft.mawgood.util.NetworkManager.isOnline
import com.iraqsoft.mawgood.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class FingerPrintRepository(private val api: ApiProvider,private val dao:EmployeesDao,private val empNeedToBeSynced:EmpNeedsToBeSyncedDao,private val checkConnection: CheckConnection) :FingerPrintRpoInterface {
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
        if (checkConnection.value!!){
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
            val response =   empNeedToBeSynced.addSingleEmp(EmpNeedsToBeSynced(emp._id,emp.displayName,null,time = System.currentTimeMillis()/1000))
            return   Utils.handleSuccess(Response.success(EmpNeedsToBeSynced(emp._id,emp.displayName,null,time = System.currentTimeMillis()/1000)))
        }




    }

    override suspend fun cacheCheck(emp: EmpNeedsToBeSynced) {
        empNeedToBeSynced.addSingleEmp(emp)
    }

}