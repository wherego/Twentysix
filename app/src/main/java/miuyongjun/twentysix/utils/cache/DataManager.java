package miuyongjun.twentysix.utils.cache;

import android.content.Context;

import java.io.File;

import miuyongjun.twentysix.utils.UIUtils;
import miuyongjun.twentysix.utils.task.CacheStringGetTask;
import miuyongjun.twentysix.utils.task.CacheStringListener;
import miuyongjun.twentysix.utils.task.CacheStringSetTask;
import miuyongjun.twentysix.utils.task.Task;
import miuyongjun.twentysix.utils.task.TaskManager;

public class DataManager {
	public static int MIN_SDCARD_CACHE_SPACE = 50;// MB 最小的sd卡空间
	public static int MAX_VOICE_CACHE_COUNT = 500;// 最多缓存文件的数量
	public static int EVERY_TIME_CLEAN_VOICE_CACHE_COUNT = (MAX_VOICE_CACHE_COUNT * 2) / 10;// 每次清理20%
	public static DataManager INSTANCE;// 单例
	public TaskManager taskManager;
	private Context context;// 上下文

	private DataManager(Context context) {
		this.context = context;
		taskManager = TaskManager.getInstance();
	}

	/**
	 * 获取单例句柄
	 * 
	 * @return
	 */
	public static DataManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DataManager(UIUtils.getContext());
		}
		return INSTANCE;
	}

	/**
	 * 获取缓存字符串
	 *            - 字符串名称(唯一标识)
	 * @param listener
	 *            - 结果监听
	 */
	public void getCacheString(String cacheName, CacheStringListener listener) {
		CacheStringGetTask cacheStringGetTask = new CacheStringGetTask(Task.TASK_PRIORITY_NORMAL, cacheName, listener);
		taskManager.submit(cacheStringGetTask);
	}

	/**
	 * 缓存字符串
	 * @param cacheName   缓存名称 - 重名为替换
	 * @param content     缓存的内容
	 */
	public void saveCacheStr(String cacheName, String content) {
		if (!CacheFileUtils.isSDCardAvaiable()) {
			return;
		}
		CacheStringSetTask cacheStringGetTask = new CacheStringSetTask(Task.TASK_PRIORITY_LOW, cacheName, content);
		taskManager.submit(cacheStringGetTask);
	}
	/**
	 * 释放资源
	 */
	public void release() {
		taskManager = null;
	}
	/**
	 * 判断是否需要清理旧数据
	 */
	public boolean isNeedCleanCache() {
		boolean isNeedCleanCache = false;
		String cacheRootPath = CacheFileUtils.getCacheImageRootPath();
		File cacheFolder = new File(cacheRootPath);
		String[] files = cacheFolder.list();
		int fileCount = 0;
		if (files != null) {
			fileCount = files.length;
		}
		if (CacheFileUtils.freeSpaceOnSd() <= MIN_SDCARD_CACHE_SPACE || fileCount > MAX_VOICE_CACHE_COUNT) {
			isNeedCleanCache = true;
		}
		return isNeedCleanCache;
	}
}
