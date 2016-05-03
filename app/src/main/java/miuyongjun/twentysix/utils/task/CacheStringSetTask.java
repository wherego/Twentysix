package miuyongjun.twentysix.utils.task;

import miuyongjun.twentysix.utils.EncryptUtils;
import miuyongjun.twentysix.utils.MD5Utils;
import miuyongjun.twentysix.utils.cache.CacheFileUtils;

/**
 * 保存json缓存数据
 * @author Administrator
 */
public class CacheStringSetTask extends Task {
	private String cacheName;
	private String content;

	public CacheStringSetTask(int priority, String cacheName, String content) {
		super(priority);
		this.cacheName = cacheName;
		this.content = content;
	}
	@Override
	public void doTask() {
		if (content == null) {
			return;
		}
		String filePath = CacheFileUtils.getCacheStrPath(MD5Utils.md5(cacheName));
//		CacheFileUtils.saveErrorStr(filePath, content);
		CacheFileUtils.saveErrorStr(filePath, EncryptUtils.encrypt(content));
	}
}
