package android.fpi

import android.zyapi.CommonApi
import android.os.Build
import android.fpi.MtGpio
import android.util.Log

/**
 * @hide
 * @author 1
 */
class MtGpio private constructor() {
    val isOpen = false
    private val mCommonApi = CommonApi()
    fun FPPowerSwitch(bonoff: Boolean) {
        val devname = Build.MODEL
        val version = Build.DISPLAY
        if (devname == "FT-06") {
            /*String versionStr = version.substring(6, 7) + version.substring(8, 9) + version
					.substring(10, 11);
			Log.d("SerialPortManager", versionStr);
			Pattern p = Pattern.compile("[0-9]*");
			Matcher m = p.matcher(versionStr);
			if (m.matches()) {
				int versionInt = Integer.parseInt(versionStr);
				if (versionInt >= 201) {
					setGpio(bonoff,15);
				}
			}*/
            setGpio(bonoff, 15)
        }
        if (devname == "HF-A5") {
            setGpio(bonoff, 15)
        }
    }

    fun setGpio(bonoff: Boolean, pin: Int) {
        if (bonoff) {
            //FP Power
            mCommonApi.setGpioMode(pin, 0)
            mCommonApi.setGpioDir(pin, 1)
            mCommonApi.setGpioOut(pin, 1)
        } else {
            mCommonApi.setGpioMode(pin, 0)
            mCommonApi.setGpioDir(pin, 1)
            mCommonApi.setGpioOut(pin, 0)
        }
    }

    fun InitGpio() {
        mCommonApi.setGpioMode(21, 0)
        mCommonApi.setGpioDir(21, 1)
        mCommonApi.setGpioMode(34, 0)
        mCommonApi.setGpioDir(34, 1)
    }

    fun LockSwitch(bonoff: Boolean) {
        if (bonoff) {
            mCommonApi.setGpioOut(21, 1)
            mCommonApi.setGpioOut(34, 1)
        } else {
            mCommonApi.setGpioOut(21, 0)
            mCommonApi.setGpioOut(34, 0)
        }
    }

    fun AlarmSwitch(bonoff: Boolean) {
        if (bonoff) {
            mCommonApi.setGpioOut(13, 1)
        } else {
            mCommonApi.setGpioOut(13, 0)
        }
    }

    fun ButtonIsPress(): Boolean {
        return if (mCommonApi.getGpioIn(0) == 1) {
            Log.d("MtGpio", "高电平")
            true
        } else {
            Log.d("MtGpio", "低电平")
            false
        }
    }

    companion object {
        private var mMe: MtGpio? = null
        @JvmStatic
		val instance: MtGpio?
            get() {
                if (mMe == null) {
                    mMe = MtGpio()
                    mMe!!.InitGpio()
                }
                return mMe
            }
    }
}