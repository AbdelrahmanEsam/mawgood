package com.iraqsoft.mawgood.viewmodels

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fgtit.app.Fingerprint
import com.fgtit.fpcore.FPMatch
import com.iraqsoft.mawgood.repo.fingerPrint.FingerPrintRepo
import kotlinx.coroutines.launch

class FingerPrintViewModel(private val repo : FingerPrintRepo) : ViewModel() {

    val status =  MutableLiveData<String>()
    val fingerPrintData = MutableLiveData<String>()
    val fingerPrintErrorFrom = MutableLiveData<String>()
    val fingerPrintError = MutableLiveData<String>()
    val isGettingFingerPrint = ObservableBoolean() ;
    fun initFingerPrint() {
        try {
            FPMatch.getInstance().InitMatch()
            Fingerprint.getInstance().Open()
            Fingerprint.getInstance().FP_SetIREnable()
            Fingerprint.getInstance().SetUpImage(true)
        }catch (e : UnsatisfiedLinkError){
            fingerPrintData.postValue(e.toString())
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
        if(!isGettingFingerPrint.get())
            Fingerprint.getInstance().Process()
    }

    private val fingerprintHandler = @SuppressLint("HandlerLeak")
    object : Handler() {

        override fun handleMessage(msg: Message) {
            when(msg.what){
                 Fingerprint.STATE_PLACE -> {
                     isGettingFingerPrint.set(true);
                     status.postValue("please add your fingerPrint")
                }

                Fingerprint.STATE_GETIMAGE -> {
                    try{
                        fingerPrintErrorFrom.postValue("STATE_GETIMAGE")
                        status.postValue("get fingerPrint data")
                    }catch (e : Exception){
                        fingerPrintError.postValue(e.toString())
                    }
                }

                Fingerprint.STATE_UPIMAGE -> {
                    try{
                        fingerPrintErrorFrom.postValue("STATE_UPIMAGE")
                        val fingerPrintString = String((msg.obj as ByteArray)) ;
                        fingerPrintData.postValue(fingerPrintString)
                    }catch (e : Exception){
                        fingerPrintError.postValue(e.toString())
                    }
                }

                Fingerprint.STATE_GENDATA -> {
                    try{
                        fingerPrintErrorFrom.postValue("STATE_GENDATA")
                        val fingerPrintString = String((msg.obj as ByteArray))
                        fingerPrintData.postValue(fingerPrintString)
                    }catch (e : Exception){
                        fingerPrintError.postValue(e.toString())
                    }
                }
                Fingerprint.STATE_UPDATA ->{
                    try{
                        fingerPrintErrorFrom.postValue("STATE_UPDATA")
                        val fingerPrintString = String((msg.obj as ByteArray)) ;
                        fingerPrintData.postValue(fingerPrintString)
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