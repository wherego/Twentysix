package miuyongjun.twentysix.utils.task;

import miuyongjun.twentysix.utils.EncryptUtils;
import miuyongjun.twentysix.utils.MD5Utils;
import miuyongjun.twentysix.utils.cache.CacheFileUtils;

/**
 * 获取缓存json数据
 * 
 * @author Administrator
 */
public class CacheStringGetTask extends Task {
	private String cacheName;
	private CacheStringListener stringCacheListener;

	public CacheStringGetTask(int priority, String cacheName, CacheStringListener listener) {
		super(priority);
		this.cacheName = cacheName;
		stringCacheListener = listener;
	}

	/**
	 * 获取缓存的json
	 */
	@Override
	public void doTask() {
		// 字符串缓存,目前不在数据库中记录,直接存文件
		final String cacheStr;
		// 检测sd卡是否存在
		if (CacheFileUtils.isSDCardAvaiable()) {
			// 对文件名进行了md5加密
			String cachePath = CacheFileUtils.getCacheStrPath(MD5Utils.md5(cacheName));
			/**
			 * 通过文件名获取文件的内容
			 */
			cacheStr = CacheFileUtils.readLocalCacheStr(cachePath);
			if (stringCacheListener != null) {
				// EncryptUtils.decrypt(cacheStr)
				super.sHandler.post(() -> stringCacheListener.onCacheStringFinish(cacheName, EncryptUtils.decrypt(cacheStr)));
			}
		}
	}
}
