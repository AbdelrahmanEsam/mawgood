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

    private val _employees= MutableLiveData<List<GetResponseItem>>()
    val employees: LiveData<List<GetResponseItem>> get()= _employees

    private val _searchedList= MutableLiveData<List<GetResponseItem>>()
    val searchedList: LiveData<List<GetResponseItem>> get()= _searchedList


    val query= MutableLiveData<String>()




    private suspend fun getCompanyId(): String {

      return defineEmployeeRepo.getCompanyId()
    }

    private suspend fun getBranchId(): String {

       return defineEmployeeRepo.getSelectedBranch()

    }

    private fun getEmployeesRequest()
    {
        viewModelScope.launch(IO) {
            val companyId = async { getCompanyId() }
            val branchId = async { getBranchId() }
            var response = defineEmployeeRepo.requestEmployeesFromDataBase()
            Log.e("empResponse1" , response.size.toString())
            if (response.isEmpty()) {
                Log.e("empResponse1" , "response from db is empty")
                when (val response2 = defineEmployeeRepo.requestEmployeesFromApi(
                    companyId = companyId.await(),
                    branchId = branchId.await()
                )) {
                    is AppResult.Success -> {
                        withContext(Main) {
                            _employees.value = response2.successData!!
                            _searchedList.value = _employees.value
                        }
                    }
                    is AppResult.Error -> {
                        Log.d("tag", "response error")
                    }
                }
            }else{
                Log.e("empResponse1" , "response from db is not Empty")
                Log.e("empResponse1" , response[0].displayName)
                Log.e("empResponse1" , String(response[0].fingerPrint))
                withContext(Main){
                    _employees.value = response
                    _searchedList.value = _employees.value
                }
            }
        }
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

    fun setDefaultSearchedList(){
        _searchedList.value = _employees.value
    }

    init {
        getEmployeesRequest()
    }

}