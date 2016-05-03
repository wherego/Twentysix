package miuyongjun.twentysix.utils.cache;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import miuyongjun.twentysix.common.Constant;

public class CacheFileUtils {
	private static byte[] readDeleteSynckey = new byte[0];
	public static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;// MB
	private static int BYTE_TO_MIB = 1048576;
	private static final String KEY_SDCARD = "/sdcard";
	private static int seq = 0;
	private static final int MAX = 9999;
	private final static Format dateFormat = new SimpleDateFormat("MMddHHmmssS", Locale.US);
	private final static NumberFormat numberFormat = new DecimalFormat("0000");
	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
	/**
	 * 计算sdcard上的剩余空间 - 单位M
	 * 
	 * @return
	 */
	public static int freeSpaceOnSd() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()); // 可用总的bytes;
		return (int) (sdFreeMB) / BYTE_TO_MIB;// MIB单位
	}
	/**
	 * 判断sdcard是否可用
	 * 
	 * @return
	 */
	public static boolean isSDCardAvaiable() {
		try {
			return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		} catch (Exception e) {
		}
		return false;
	}
	/**
	 * 获取日志文件路径
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getLogPath(String fileName) {
		StringBuffer fileSB = new StringBuffer();
		fileSB.append(getLogRootPath()).append(File.separator).append(fileName);
		return fileSB.toString();
	}
	/**
	 * 获取日志文件夹地址
	 * 
	 * @return
	 */
	public static String getLogRootPath() {
		String sdcardPath = getSDPath();
		StringBuffer fileSB = new StringBuffer();
		fileSB.append(sdcardPath).append(File.separator).append(Constant.APP_FOLDER);
		fileSB.append(File.separator).append(Constant.APP_LOG_FILE);
		// 文件夹问null时构造
		String rootPath = fileSB.toString();
		File destDir = new File(rootPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		return rootPath;
	}

	/**
	 * 获取SD路径
	 */
        public static String getSDPath() {
		// 判断sd卡是否存在
		File sdDir = null;
		if (isSDCardAvaiable()) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		} else {
			sdDir = Environment.getRootDirectory();
		}
		if (sdDir != null && !TextUtils.isEmpty(sdDir.getPath())) {
			return sdDir.getPath();
		} else {
			return KEY_SDCARD;
		}
	}
	public static void saveErrorStr(String filePath, String content) {
		synchronized (readDeleteSynckey) {
			// 判断sdcard上的空间
			if (FREE_SD_SPACE_NEEDED_TO_CACHE > CacheFileUtils.freeSpaceOnSd()) {
				return;
			}
			/**
			 * 保存到sdcard
			 */
			if (CacheFileUtils.isSDCardAvaiable()) {
				
				File file = new File(filePath);
				try {
					if (!file.exists()) {
						file.createNewFile();
					}
					OutputStream outStream = new FileOutputStream(file);
					outStream.write(content.getBytes());
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
				}
			}
		}
	}
	/**
	 * 获取文件路径
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getCacheStrPath(String fileName) {
		StringBuffer fileSB = new StringBuffer();
		fileSB.append(getCacheStrRootPath()).append(File.separator).append(fileName);
		return fileSB.toString();
	}
	/**
	 * 获取cache文件夹地址
	 * 
	 * @return
	 */
	public static String getCacheStrRootPath() {
		String sdcardPath = getSDPath();
		StringBuffer fileSB = new StringBuffer();
		fileSB.append(sdcardPath).append(File.separator).append(Constant.APP_FOLDER);
		fileSB.append(File.separator).append(Constant.CACHE_JSON_FILES);
		// 文件夹问null时构造
		String rootPath = fileSB.toString();
		File destDir = new File(rootPath);
		if (!destDir.exists() || destDir.getAbsoluteFile() == null) {
			destDir.mkdirs();
		}
		return rootPath;
	}
	/**
	 * 获取cache图片文件夹地址
	 * 
	 * @return
	 */
	public static String getCacheImageRootPath() {
		String sdcardPath = getSDPath();
		StringBuffer fileSB = new StringBuffer();
		fileSB.append(sdcardPath).append(File.separator).append(Constant.APP_FOLDER);
		fileSB.append(File.separator).append(Constant.CACHE_IMAGE_FILES);
		// 文件夹问null时构造
		String rootPath = fileSB.toString();
		File destDir = new File(rootPath);
		if (!destDir.exists() || destDir.getAbsoluteFile() == null) {
			destDir.mkdirs();
		}
		return rootPath;
	}
	/**
	 * 从本地sd卡读文件
	 * @param fileLocalPath
	 * @return
	 */
	public static String readLocalCacheStr(String fileLocalPath) {
		String result = null;
		synchronized (readDeleteSynckey) {
			File file = new File(fileLocalPath);
			if (!file.exists()) {
				return result;
			}
			try {
				FileInputStream fin = new FileInputStream(fileLocalPath);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length = -1;
				while ((length = fin.read(buffer)) != -1) {
					stream.write(buffer, 0, length);
				}
				result = stream.toString();
				stream.close();
				fin.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	


	public static synchronized String generateSequenceNo() {
		Calendar rightNow = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
		numberFormat.format(seq, sb, HELPER_POSITION);
		if (seq == MAX) {
			seq = 0;
		} else {
			seq++;
		}
		return sb.toString();
	}
	public static boolean isExists(String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			return false;
		}
		File file = new File(fileName);
		return file.exists();
	}
}
