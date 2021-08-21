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
import com.iraqsoft.mawgood.db.model.GetResponseItem
import com.iraqsoft.mawgood.repository.FingerPrintRpoInterface
import com.iraqsoft.mawgood.util.AppResult
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FingerPrintViewModel(private val fingerprintRepo: FingerPrintRpoInterface) : ViewModel() {

    private var direction = 0
    private lateinit  var enrollData:ByteArray
    private  var enrollCount = 1
    val status =  MutableLiveData<String>()
    val fingerPrintData = MutableLiveData<String>()
    val fingerPrintErrorFrom = MutableLiveData<String>()
    val fingerPrintError = MutableLiveData<String>()
    val isGettingFingerPrint = ObservableBoolean()
    val saveFingerprintSuccess = ObservableBoolean()
    var emp : GetResponseItem? = null
    fun initFingerPrint(direction:Int , empData : GetResponseItem?) {
        try {
            Log.e("fingerPrintViewModel" , "direction is :$direction")
            status.value = "رجاء ادخل البصمة"
            saveFingerprintSuccess.set(false)
            this.direction = direction
            this.emp = empData
            FPMatch.getInstance().InitMatch()
            Fingerprint.getInstance().Open()
            Fingerprint.getInstance().FP_SetIREnable()
            Fingerprint.getInstance().SetUpImage(true)
        }catch (e : UnsatisfiedLinkError){
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
                        val data = msg.obj as ByteArray
                        fingerPrintErrorFrom.postValue("STATE_UPDATA")
                        if(direction == 1 ){
                            if (enrollCount == 1){
                                System.arraycopy(data,0,enrollData,0,256)
                                enrollCount++
                                Thread.sleep(500L)
                                Fingerprint.getInstance().Process()
                            }
                            else if(enrollCount == 2){
                                System.arraycopy(data , 0 , enrollData , 256 , 256 )
                                emp?.fingerPrint = enrollData ;
                                viewModelScope.launch(IO) {
                                    fingerprintRepo.cashSingleEmp(emp)
                                }
                                enrollCount = 1 ;
                                enrollData = byteArrayOf()
                                saveFingerprintSuccess.set(true)
                            }
                        }else{
                            viewModelScope.launch(IO){
                                var emp : List<GetResponseItem>  = fingerprintRepo.getEmps();
                                emp.forEach{
                                    var enroll1 = byteArrayOf()
                                    var enroll2 = byteArrayOf()
                                    if(it.fingerPrint != null ) {
                                        System.arraycopy(it.fingerPrint, 0, enroll1, 0, 256)
                                        System.arraycopy(it.fingerPrint,256 , enroll2, 0, 256)
                                    }
                                    if(FPMatch.getInstance().MatchTemplate(enroll1 , it.fingerPrint) < 60 || FPMatch.getInstance().MatchTemplate(enroll2 , it.fingerPrint) > 60){
                                        when(val response = fingerprintRepo.empCheck(empId = it._id)){
                                            is AppResult.Success -> {
                                                withContext(Main){
                                                  saveFingerprintSuccess.set(true)
                                                }
                                            }
                                            is AppResult.Error -> {
                                                Log.e("tag",response.exception.toString())
                                            }
                                        }
                                    }
                                }
                            }
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