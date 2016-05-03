package miuyongjun.twentysix.utils;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import miuyongjun.twentysix.TwentySixApplication;
import miuyongjun.twentysix.bean.device.DeviceInfo;
import miuyongjun.twentysix.utils.cache.CacheFileUtils;
import miuyongjun.twentysix.utils.task.Task;
import miuyongjun.twentysix.utils.task.TaskManager;

public class CrashHandler implements UncaughtExceptionHandler {
	public static final String TAG = "CrashHandler";
	// CrashHandler实例
	private static CrashHandler INSTANCE = new CrashHandler();
	// 程序的Context对象
	private TwentySixApplication ztApplication;
	// 用于格式化日期,作为日志文件名的一部分
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
	// 系统默认的UncaughtException处理类
	private UncaughtExceptionHandler mDefaultHandler;
	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * 初始化
	 */
	public void init(Context context) {
		ztApplication = (TwentySixApplication) context.getApplicationContext();
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
			TaskManager.getInstance().submit(new SaveErrorTask(ztApplication, Task.TASK_PRIORITY_HEIGHT, ex));
			mDefaultHandler.uncaughtException(thread, ex);
	}
	private boolean handleException(Throwable ex) {
		return true;
	}
	class SaveErrorTask extends Task {
		private Context context;
		private Throwable ex;

		public SaveErrorTask(Context context, int priority, Throwable ex) {
			super(priority);
			this.context = context;
			this.ex = ex;
		}
		@Override
		public void doTask() {
			//发布前 关闭
			if(ConfigManager.isTest){
				Writer writer = new StringWriter();
				PrintWriter printWriter = new PrintWriter(writer);
				ex.printStackTrace(printWriter);
				Throwable cause = ex.getCause();
				while (cause != null) {
					cause.printStackTrace(printWriter);
					cause = cause.getCause();
				}
				printWriter.close();
				String result = writer.toString();
				String time = formatter.format(new Date());
				String fileName = time + ".txt";
				StringBuffer stringBuffer = new StringBuffer();
				DeviceInfo deviceInfo = Utils.getDeviceInfo(context);
				stringBuffer.append("\nsdkVersion：" + deviceInfo.sdkVersion);
				stringBuffer.append("\nmanufacturer：" + deviceInfo.manufacturer);
				stringBuffer.append("\nmodel：" + deviceInfo.model);
				stringBuffer.append("\nversion" +Utils.getVersionName(context));
				stringBuffer.append("\nerrorStr：" + result);
				stringBuffer.append("\ntime：" + time);
				String filePath = CacheFileUtils.getLogPath(fileName);
				CacheFileUtils.saveErrorStr(filePath, stringBuffer.toString());
			}
		}
	}
}
