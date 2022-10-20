package com.iraqsoft.mawgood.domain.repository

import com.iraqsoft.mawgood.data.model.CheckInAndOutResponse
import com.iraqsoft.mawgood.data.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.data.model.GetResponseItem
import com.iraqsoft.mawgood.util.AppResult

interface FingerPrintRpoInterface {
    suspend fun cashSingleEmp(emp : GetResponseItem?)
    suspend fun getEmps(): List<GetResponseItem>
    suspend fun empCheck(emp : GetResponseItem): AppResult<Any>?

    suspend fun cacheCheck(emp: EmpNeedsToBeSynced)
}