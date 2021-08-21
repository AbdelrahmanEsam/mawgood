package com.iraqsoft.mawgood.repository

import com.iraqsoft.mawgood.db.model.CheckInAndOutResponse
import com.iraqsoft.mawgood.db.model.GetResponse
import com.iraqsoft.mawgood.db.model.GetResponseItem
import com.iraqsoft.mawgood.util.AppResult

interface FingerPrintRpoInterface {
    suspend fun cashSingleEmp(emp : GetResponseItem?)
    suspend fun getEmps(): List<GetResponseItem>
    suspend fun empCheck(empId :String ): AppResult<CheckInAndOutResponse>?
}