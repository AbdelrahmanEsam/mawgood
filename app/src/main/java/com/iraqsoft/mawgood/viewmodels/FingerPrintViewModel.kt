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
    }

    fun getFingerPrint(): String {
       viewModelScope.launch {
           isGettingFingerPrint.set(false);
           Fingerprint.getInstance().setHandler(fingerprintHandler)
       }
        return "done";
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
                    status.postValue("get fingerPrint data")
                }

                Fingerprint.STATE_UPIMAGE -> {
                    fingerPrintData.postValue((msg.obj as ByteArray).toString())
                }

                Fingerprint.STATE_GENDATA -> {
                    fingerPrintData.postValue((msg.obj as ByteArray).toString() + "\n" +"this from gendata")
                }
                Fingerprint.STATE_UPDATA ->{
                    fingerPrintData.postValue((msg.obj as ByteArray).toString() + "\n" +"this from state updated")
                    isGettingFingerPrint.set(false)
                }
                Fingerprint.STATE_FAIL -> {
                    Fingerprint.getInstance().Process()
                }
            }
        }
    }
}