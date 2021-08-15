package android.fpi;

import android.os.Build;
import android.util.Log;
import android.zyapi.CommonApi;

/**
 * @hide
 * @author 1
 *
 */
public class MtGpio {

	private boolean mOpen = false;
	private CommonApi mCommonApi= new CommonApi();;
	private static MtGpio mMe = null;
	private MtGpio() {
	//	mOpen = openDev()>=0?true:false;
	//	Log.d("MtGpio","openDev->ret:"+mOpen);
	}
	
	public static MtGpio getInstance(){
		if (mMe == null){
			mMe = new MtGpio();
			mMe.InitGpio();
		}
		return mMe;
	}
	
	public void FPPowerSwitch(boolean bonoff){
		String devname = Build.MODEL;
		String version = Build.DISPLAY;
		if (devname.equals("FT-06")) {
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
			setGpio(bonoff,15);
		}
		if (devname.equals("HF-A5")) {
			setGpio(bonoff,15);
		}

	}

	public void setGpio(boolean bonoff,int pin) {
		if(bonoff){
			//FP Power
			mCommonApi.setGpioMode(pin,0);
			mCommonApi.setGpioDir(pin,1);
			mCommonApi.setGpioOut(pin,1);
		}else{
			mCommonApi.setGpioMode(pin,0);
			mCommonApi.setGpioDir(pin,1);
			mCommonApi.setGpioOut(pin,0);
		}
	}

	public void InitGpio(){
		mCommonApi.setGpioMode(21,0);
		mCommonApi.setGpioDir(21,1);
		
		mCommonApi.setGpioMode(34,0);
		mCommonApi.setGpioDir(34,1);
	}
	
	public void LockSwitch(boolean bonoff){
		if(bonoff){			
			mCommonApi.setGpioOut(21,1);
			mCommonApi.setGpioOut(34,1);
		}else{
			mCommonApi.setGpioOut(21,0);
			mCommonApi.setGpioOut(34,0);
		}
	}
	
	public void AlarmSwitch(boolean bonoff){
		if(bonoff){
			mCommonApi.setGpioOut(13,1);
		}else{
			mCommonApi.setGpioOut(13,0);
		}
	}

	public boolean ButtonIsPress(){
		if(mCommonApi.getGpioIn(0)==1) {
            Log.d("MtGpio", "高电平");
            return true;
        }else {
            Log.d("MtGpio", "低电平");
            return false;
        }
	}
	
	public boolean isOpen(){
		return mOpen;
	}
}
