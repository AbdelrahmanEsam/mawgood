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

    private val _searchedList= MutableLiveData<List<GetResponseItem>>()
    val searchedList: LiveData<List<GetResponseItem>> get()= _searchedList

    private val _cachedList= MutableLiveData<GetResponse>()
    val cachedList: LiveData<GetResponse> get()= _cachedList

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
        val branchId    = async { getBranchId() }


            when(val response = defineEmployeeRepo.requestEmployeesFromApi(companyId = companyId.await(),branchId = branchId.await())){
                is AppResult.Success -> {
                    withContext(Dispatchers.Main){
                       _employees.value = response.successData!!
                        _searchedList.value = _employees.value
                    }
                }
                is AppResult.Error -> {
                   Log.d("tag","response error")
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