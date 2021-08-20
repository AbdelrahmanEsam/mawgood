package com.iraqsoft.mawgood.repository

import com.iraqsoft.mawgood.db.model.LoginCompanyResponse
import com.iraqsoft.mawgood.util.AppResult

interface LoginRepositoryInterface {
    suspend fun login(phone : String , password : String)  : AppResult<LoginCompanyResponse>
    suspend fun getResponse() : LoginCompanyResponse

}