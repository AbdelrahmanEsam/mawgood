package com.iraqsoft.mawgood.domain.repository

import com.iraqsoft.mawgood.data.model.LoginCompanyResponse
import com.iraqsoft.mawgood.util.AppResult

interface LoginRepositoryInterface {
    suspend fun login(phone : String , password : String)  : AppResult<LoginCompanyResponse>
    suspend fun getResponse() : LoginCompanyResponse

}