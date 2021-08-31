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

import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale


class MatchFingerprintViewModel(private val fingerprintRepo: FingerPrintRpoInterface) : ViewModel() {

    private   var enrollData:ByteArray = ByteArray(512)
    val status =  MutableLiveData<String>()
    val fingerPrintData = MutableLiveData<String>()
    val fingerPrintErrorFrom = MutableLiveData<String>()
    val emp = MutableLiveData<GetResponseItem?>()
    val fingerPrintMatch = MutableLiveData<Int>()
    val fingerStayCounter = MutableLiveData<Int>()
    val fingerPrintError = MutableLiveData<String>()
    val isGettingFingerPrint = ObservableBoolean()
    val saveFingerprintSuccess = ObservableBoolean()
    val timerOn = ObservableBoolean()
    val readingGifVisibility = ObservableBoolean()

    fun initFingerPrint() {
        Log.e("fingerPrint" , "init fingerPrint")

        try {
            status.value = "رجاء ادخل البصمة"
            saveFingerprintSuccess.set(false)
            fingerPrintMatch.value = -1
            FPMatch.getInstance().InitMatch()
            Fingerprint.getInstance().Open()
            Fingerprint.getInstance().FP_SetIREnable()
            Fingerprint.getInstance().SetUpImage(true)
        }catch (e : UnsatisfiedLinkError){
            this.fingerPrintError.postValue(e.toString())
        }

        setHandler()
    }

    private fun setHandler(): String {
       viewModelScope.launch {
           isGettingFingerPrint.set(false)
           Fingerprint.getInstance().setHandler(fingerprintHandler)
       }
        return "done"
    }

    fun getFingerPrint(){
        Log.e("fingerPrint" , "start getting fingerPrint")
        Thread.sleep(500L)
        if(!isGettingFingerPrint.get())
            Fingerprint.getInstance().Process()
    }

    private val fingerprintHandler = @SuppressLint("HandlerLeak")
    object : Handler() {

        override fun handleMessage(msg: Message) {
            when(msg.what){
                 Fingerprint.STATE_PLACE -> {
                     readingGifVisibility.set(false)
                     isGettingFingerPrint.set(true)
                     fingerPrintError.value = "FingerPrint Sensor can't get Value"
                     val message = "رجاء ادخل البصمة"
                     status.postValue(message )
                }

                Fingerprint.STATE_GETIMAGE -> {
                    try{
                        fingerPrintErrorFrom.postValue("STATE_GETIMAGE")
                        status.postValue("يتم قراءة البصمة")
                        readingGifVisibility.set(true)
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
                            viewModelScope.launch(IO){
                                val emps : List<GetResponseItem>  = fingerprintRepo.getEmps()
                                val matchedData = ByteArray(512)
                                System.arraycopy(fingerData , 0 , matchedData , 0 , 256)
                               emps.forEach{
                                    val enroll1 = ByteArray(256)
                                    val enroll2 = ByteArray(256)
                                    if(it.fingerPrint != null ) {
                                        System.arraycopy(it.fingerPrint, 0, enroll1, 0, 256)
                                        System.arraycopy(it.fingerPrint,256 , enroll2, 0, 256)
                                    }
                                    if(FPMatch.getInstance().MatchTemplate(enroll1 ,matchedData) > 60 || FPMatch.getInstance().MatchTemplate(enroll2 , matchedData) > 60){
                                        saveFingerprintSuccess.set(true)
                                        fingerPrintMatch.postValue(1)
                                        status.postValue("تمت المعالجة بنجاح")
                                        emp.postValue(it)
                                        when(val response = fingerprintRepo.empCheck(emp = it)){
                                            is AppResult.Success -> {
                                                Log.e("check_emp" , "check emp success")
                                            }
                                            is AppResult.Error -> {

                                            }
                                        }
                                    }
                                }


                                readingGifVisibility.set(false)
                                result()
                                Fingerprint.getInstance().Process()

                            }


                        isGettingFingerPrint.set(false)
                    }catch (e : Exception){
                        fingerPrintError.postValue(e.toString())
                    }
                }
                Fingerprint.STATE_FAIL -> {
                    try{
                        isGettingFingerPrint.set(false)
                        fingerPrintErrorFrom.postValue("STATE_FAIL")
                        fingerPrintError.postValue("FingerPrint Sensor can't get Value")
                        Fingerprint.getInstance().Process()
                        resetFail()
                    }catch (e : Exception){
                        fingerPrintError.postValue(e.toString())
                    }
                }
            }
        }
    }

    private fun resetFail(){
        viewModelScope.launch(Main) {
            Thread.sleep(5000)
            fingerPrintError.postValue("")

        }
    }
    private fun result(){
        viewModelScope.launch(Main) {
            if(fingerPrintMatch.value == -1 ) {
                saveFingerprintSuccess.set(false)
                fingerPrintMatch.value = 0
            }

            Thread.sleep(3500L)
            emp.value = null
            fingerPrintMatch.value = -1
        }
    }
    fun test(){
        viewModelScope.launch(IO) {
            val emps : List<GetResponseItem>  = fingerprintRepo.getEmps()
            when(val response = fingerprintRepo.empCheck(emp =emps[0])){
                is AppResult.Success -> {
                    Log.e("check_emp" , "check emp success")
                }
                is AppResult.Error -> {
                    Log.e("check_emp" , "check emp error")
                }
            }
        }
    }
}