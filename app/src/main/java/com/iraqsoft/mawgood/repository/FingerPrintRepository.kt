package com.iraqsoft.mawgood.repository

import com.iraqsoft.mawgood.ApiProvider
import com.iraqsoft.mawgood.db.EmployeesDao

class FingerPrintRepository(private val api: ApiProvider,private val dao:EmployeesDao) :FingerPrintRpoInterface {
}