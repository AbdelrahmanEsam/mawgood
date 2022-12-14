package com.iraqsoft.mawgood.data.repository

import com.iraqsoft.mawgood.data.remote.ApiProvider
import com.iraqsoft.mawgood.data.dataBase.UserDao
import com.iraqsoft.mawgood.data.model.LoginCompanyResponse
import com.iraqsoft.mawgood.domain.repository.LoginRepositoryInterface
import com.iraqsoft.mawgood.util.AppResult
import com.iraqsoft.mawgood.util.Utils.handleApiError
import com.iraqsoft.mawgood.util.Utils.handleSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LoginRepositoryImp(

    private val api : ApiProvider,
    private val dao : UserDao
) : LoginRepositoryInterface {
    override suspend fun login(phone: String, password: String): AppResult<LoginCompanyResponse> {
        return try{
            val response = api.login(phone , password)
            if(response.isSuccessful){
                    response.body()?.let {
                        withContext(Dispatchers.IO){
                            dao.addLoginResponse(it)
                        }
                    }
                handleSuccess(response)
            }else{
                handleApiError(response)
            }
        }catch (e: Exception){
            return  AppResult.Error(e)
        }
    }

    override suspend fun getResponse(): LoginCompanyResponse {
        return withContext(Dispatchers.IO){
            dao.findOne()
        }
    }
}