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

import android.R.attr.name
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale

import android.R.attr.name
import android.R.attr.name








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

    fun initFingerPrint() {
        Log.e("fingerPrint" , "init fingerPrint");

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
           isGettingFingerPrint.set(false);
           Fingerprint.getInstance().setHandler(fingerprintHandler)
       }
        return "done";
    }

    fun getFingerPrint(){
        Log.e("fingerPrint" , "start getting fingerPrint");
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
                     fingerPrintError.value = "FingerPrint Sensor can't get Value"
                     val message = "رجاء ادخل البصمة"
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
                            viewModelScope.launch(IO){
                                val emps : List<GetResponseItem>  = fingerprintRepo.getEmps()
                                val matchedData = ByteArray(512)
                                System.arraycopy(fingerData , 0 , matchedData , 0 , 256) ;
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
                                        when(val response = fingerprintRepo.empCheck(empId = it._id)){
                                            is AppResult.Success -> {
                                                Log.e("check_emp" , "check emp success")
                                            }
                                            is AppResult.Error -> {
                                                val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                                                val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                                                fingerprintRepo.cacheCheck(EmpNeedsToBeSynced(it._id, it.displayName, currentDate, currentTime))
                                                Log.e("tag",response.exception.toString())
                                            }
                                        }
                                    }
                                }


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
                        fingerPrintErrorFrom.postValue("STATE_FAIL")
                        fingerPrintError.postValue("FingerPrint Sensor can't get Value")
                        Fingerprint.getInstance().Process()
                    }catch (e : Exception){
                        fingerPrintError.postValue(e.toString())
                    }
                }
            }
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
    fun counterWork(){
        Thread.sleep(1000L)
            timerOn.set(true)
            fingerStayCounter.postValue(fingerStayCounter.value?.minus(1))
            if(fingerStayCounter?.value!! >= 0){
                counterWork()
            }else{
                timerOn.set(false)
            }

    }
}