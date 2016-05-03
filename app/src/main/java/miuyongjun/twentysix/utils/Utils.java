package miuyongjun.twentysix.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;

import miuyongjun.twentysix.bean.device.DeviceInfo;

public class Utils {

	private Utils() {
	}

	/**
	 * 获取手机信息
	 * 
	 * @return
	 */
	public static DeviceInfo getDeviceInfo(Context context) {
		DeviceInfo deviceInfo = new DeviceInfo();
		// sdk版本
		deviceInfo.sdkVersion = Build.VERSION.SDK_INT;
		deviceInfo.manufacturer = Build.MANUFACTURER;
		deviceInfo.model = Build.MODEL;
		return deviceInfo;
	}
    public static String getVersionName()
    {
        // 获取packagemanager的实例
        PackageManager packageManager = UIUtils.getContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(UIUtils.getContext().getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }
    public static String getVersionName(Context context) {
        // 获取packagemanager的实例
        String versionName = "";
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }




    public static int getRelativeTop(View myView) {
        // if (myView.getParent() == myView.getRootView())
        if (myView.getId() == android.R.id.content)
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }

    public static int getRelativeLeft(View myView) {
        // if (myView.getParent() == myView.getRootView())
        if (myView.getId() == android.R.id.content)
            return myView.getLeft();
        else
            return myView.getLeft()
                    + getRelativeLeft((View) myView.getParent());
    }
    /*
     * Convert Dp to Pixel
     */
    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                resources.getDisplayMetrics());
        return (int) px;
    }
}
