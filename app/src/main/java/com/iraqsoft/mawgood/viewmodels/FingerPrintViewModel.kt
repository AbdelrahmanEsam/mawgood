package com.iraqsoft.mawgood.viewmodels

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fgtit.app.Fingerprint
import com.fgtit.fpcore.FPMatch
import com.iraqsoft.mawgood.db.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.db.model.GetResponseItem
import com.iraqsoft.mawgood.repository.FingerPrintRpoInterface
import com.iraqsoft.mawgood.util.AppResult
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FingerPrintViewModel(private val fingerprintRepo: FingerPrintRpoInterface) : ViewModel() {

    private   var enrollData:ByteArray = ByteArray(512)
    private  var enrollCount = 1
    val status =  MutableLiveData<String>()
    val fingerPrintData = MutableLiveData<String>()
    val fingerPrintErrorFrom = MutableLiveData<String>()
    val fingerPrintMatch = MutableLiveData<Int>()
    val fingerPrintError = MutableLiveData<String>()
    val isGettingFingerPrint = ObservableBoolean()
    val saveFingerprintSuccess = ObservableBoolean()
    var emp : GetResponseItem? = null
    fun initFingerPrint(empData : GetResponseItem?) {
        try {
            status.value = "رجاء ادخل البصمة"
            saveFingerprintSuccess.set(false)
            fingerPrintMatch.value = -1 ;
            this.emp = empData
            FPMatch.getInstance().InitMatch()
            Fingerprint.getInstance().Open()
            Fingerprint.getInstance().FP_SetIREnable()
            Fingerprint.getInstance().SetUpImage(true)
        }catch (e : UnsatisfiedLinkError){
        }

        setHandler()
    }

    fun cacheEmpNeedToBeSynced(emp:EmpNeedsToBeSynced)
    {
        // tested and it's OK

        viewModelScope.launch(IO) {
            fingerprintRepo.cacheCheck(emp)
        }
    }

    private fun setHandler(): String {
       viewModelScope.launch {
           isGettingFingerPrint.set(false);
           Fingerprint.getInstance().setHandler(fingerprintHandler)
       }
        return "done";
    }

    fun getFingerPrint(){
        Thread.sleep(500L)
        if(!isGettingFingerPrint.get())
            Fingerprint.getInstance().Process()
    }

    private val fingerprintHandler = @SuppressLint("HandlerLeak")
    object : Handler() {

        override fun handleMessage(msg: Message) {
            when(msg.what){
                 Fingerprint.STATE_PLACE -> {
                     isGettingFingerPrint.set(true)
                     fingerPrintMatch.value = -1 ;
                     val message = if(enrollCount == 1 )
                         "رجاء ادخل البصمة"
                     else
                         "رجاء ادخال البصمة مره اخري"

                     status.postValue(message )
                }

                Fingerprint.STATE_GETIMAGE -> {
                    try{
                        fingerPrintErrorFrom.postValue("STATE_GETIMAGE")
                        status.postValue("يتم قراءة البصمة")
                    }catch (e : Exception){
                        fingerPrintError.postValue(e.toString())
                    }
                }

                Fingerprint.STATE_UPIMAGE -> {
                }
                Fingerprint.STATE_GENDATA -> {
                }
                Fingerprint.STATE_UPDATA ->{
                    try{
                        val fingerData = msg.obj as ByteArray
                        fingerPrintErrorFrom.postValue("STATE_UPDATA")
                            if (enrollCount == 1){
                                System.arraycopy(fingerData,0,enrollData,0,256)
                                enrollCount++
                                Thread.sleep(500L)
                                Fingerprint.getInstance().Process()
                            }
                            else if(enrollCount == 2){
                                System.arraycopy(fingerData , 0 , enrollData , 256 , 256 )
                                emp?.fingerPrint = ByteArray(512);
                                System.arraycopy(enrollData , 0 , emp?.fingerPrint , 0 , 512) ;
                                viewModelScope.launch(IO) {
                                    fingerprintRepo.cashSingleEmp(emp)
                                }
                                enrollCount = 1 ;
                                enrollData = byteArrayOf()
                                saveFingerprintSuccess.set(true)
                            }


                        isGettingFingerPrint.set(false)
                    }catch (e : Exception){
                        fingerPrintError.postValue(e.toString())
                    }
                }
                Fingerprint.STATE_FAIL -> {
                    try{
                        fingerPrintErrorFrom.postValue("STATE_FAIL")
                        Fingerprint.getInstance().Process()
                    }catch (e : Exception){
                        fingerPrintError.postValue(e.toString())
                    }
                }
            }
        }
    }


}