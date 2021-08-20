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
            Log.e("result is ",userName.value + password.value)
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
            }else{
                withContext(Dispatchers.Main){
                    _errorMessage.value = "please enter both Username and Password"
                }

            }


        }
    }

}