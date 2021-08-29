package com.iraqsoft.mawgood.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraqsoft.mawgood.db.model.GetResponse
import com.iraqsoft.mawgood.db.model.GetResponseItem
import com.iraqsoft.mawgood.repository.DefineEmployeeRepositoryInterface
import com.iraqsoft.mawgood.util.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefineEmployeeViewModel(private val defineEmployeeRepo:DefineEmployeeRepositoryInterface):ViewModel() {

    private val _employees= MutableLiveData<GetResponse>()
    val employees: LiveData<GetResponse> get()= _employees

    private val _oldEmployee= MutableLiveData<Int>()
    val oldEmployee: LiveData<Int> get()= _oldEmployee




    private val _searchedList= MutableLiveData<List<GetResponseItem>>()
    val searchedList: LiveData<List<GetResponseItem>> get()= _searchedList


    val query= MutableLiveData<String>()




    private suspend fun getCompanyId(): String {

      return defineEmployeeRepo.getCompanyId()
    }

    private suspend fun getBranchId(): String {

       return defineEmployeeRepo.getSelectedBranch()

    }

   private fun getEmployees()
    {
        viewModelScope.launch(IO) {
           val requestFromDataBase = getEmployeesFromDataBase()
           if (requestFromDataBase.isNotEmpty()){
               Log.e("abdo","cache")
               withContext(Main){
                   val response = GetResponse()
                   response.addAll(requestFromDataBase)
                   _employees.value = response
                   _searchedList.value = _employees.value
               }
           }else {
               getEmployeesFromApiRequest()
           }


        }

    }

    private fun getEmployeesFromApiRequest()
    {
        Log.e("getResponse" , "getting emps fron the Api ")
        viewModelScope.launch(IO) {
        val companyId = async { getCompanyId() }
        val branchId    = async { getBranchId() }
            when(val response = defineEmployeeRepo.requestEmployeesFromApi(companyId = companyId.await(),branchId = branchId.await())){
                is AppResult.Success -> {
                    withContext(Main){
                       _employees.value = response.successData!!
                        _searchedList.value = _employees.value
                    }
                }
                is AppResult.Error -> {
                   Log.e("tag",response.exception.toString())
                }
            }
        }

    }

    private suspend fun getEmployeesFromDataBase(): List<GetResponseItem> {
      return  defineEmployeeRepo.requestEmployeesFromDataBase()
    }


    fun searchEmployeeList(query:String) {
        viewModelScope.launch(Dispatchers.Default) {
           if (query.isBlank()){ return@launch }
          val result  = employees.value?.filter {
                 it.displayName.contains(query.trim(), ignoreCase = true)
             }
            withContext(Main){
                _searchedList.value =result!!
            }
        }
    }

    fun setOldEmployee(position:Int)
    {
        _employees.value?.get(position)?.selected = !_employees.value?.get(position)?.selected!!
        if(position != _oldEmployee.value ) {
            _oldEmployee.value = position
        }
    }

    fun setDefaultSearchedList(){
        _searchedList.value = _employees.value
    }



    init {
        _oldEmployee.value = -1
       getEmployees()
    }

}