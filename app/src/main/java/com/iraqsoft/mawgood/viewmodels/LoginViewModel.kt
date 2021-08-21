package com.iraqsoft.mawgood.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraqsoft.mawgood.db.model.LoginCompanyResponse
import com.iraqsoft.mawgood.repository.LoginRepositoryInterface
import com.iraqsoft.mawgood.util.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val authRepo:LoginRepositoryInterface):ViewModel() {


   val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginSuccess= MutableLiveData<String>()
    val loginSuccess:LiveData<String> get()= _loginSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage:LiveData<String> get()= _errorMessage

    private  val user = MutableLiveData<LoginCompanyResponse>()

   fun login(){
        viewModelScope.launch(Dispatchers.IO) {
        val cached = getCached()
            if (cached != null){

                withContext(Dispatchers.Main){
                    user.value = cached
                    _loginSuccess.value = "success"
                }
            }else{
               loginFromApi()
            }
        }
    }



    private suspend  fun loginFromApi(){


            if(!userName.value.isNullOrEmpty() && !password.value.isNullOrEmpty()){
                when(val result = authRepo.login(userName.value!! , password.value!!)){

                    is AppResult.Success -> {
                        withContext(Dispatchers.Main){
                            user.value = result.successData!!
                            _loginSuccess.value = "success"
                        }
                    }
                    is AppResult.Error -> {
                        Log.e("result is ",result.message)
                    }
                }
            }
    }

    private suspend fun getCached(): LoginCompanyResponse {
      val cached =  authRepo.getResponse()
        return  cached
    }

    init {
        login()
    }

}