package miuyongjun.twentysix.utils;


import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 双击退出工具类
 * 
 * @author wxc
 * 
 */
public class DoubleClickExitHelper {

	private final Activity mActivity;

	private boolean isOnKeyBacking;
	private Handler mHandler;
	private Toast mBackToast;

	public DoubleClickExitHelper(Activity activity) {
		mActivity = activity;
		mHandler = new Handler(Looper.getMainLooper());
		// TODO 为什么需要加入，之前的app都是不用的。
		AppManager.getAppManager().addActivity(mActivity);
	}

	/**
	 * Activity onKeyDown事件
	 * */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode != KeyEvent.KEYCODE_BACK) {
			return false;
		}
		if (isOnKeyBacking) {
			mHandler.removeCallbacks(onBackTimeRunnable);
			if (mBackToast != null) {
				mBackToast.cancel();
			}
			AppManager.getAppManager().AppExit(mActivity);
			return true;
		} else {
			isOnKeyBacking = true;
			if (mBackToast == null) {
				mBackToast = Toast.makeText(mActivity, "再次点击退出应用",
						2000);
			}
			mBackToast.show();
			mHandler.postDelayed(onBackTimeRunnable, 2000);
			return true;
		}
	}

	private Runnable onBackTimeRunnable = new Runnable() {
		@Override
		public void run() {
			isOnKeyBacking = false;
			if (mBackToast != null) {
				mBackToast.cancel();
			}
		}
	};
}
