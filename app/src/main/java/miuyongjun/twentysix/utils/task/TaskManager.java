package miuyongjun.twentysix.utils.task;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TaskManager {
	private PriorityBlockingQueue<Runnable> priorityBlockingQueue;
	public static final byte[] syncKey = new byte[0];
	private ConcurrentLinkedQueue<Integer> queue;
	private static TaskManager instance;
	private ExecutorService service;
	private final static int DEFAULT_POOL_SIZE = 5;

	public static TaskManager getInstance() {
		synchronized (syncKey) {
			if (instance == null) {
				instance = new TaskManager();
			}
		}
		return instance;
	}

	private TaskManager() {
		this.queue = new ConcurrentLinkedQueue();
		this.priorityBlockingQueue = new PriorityBlockingQueue();
		this.service = new ZTPoolExecutor(2, 5, 60L, TimeUnit.SECONDS, this.priorityBlockingQueue);
	}

	public void submit(Task task) {
		this.service.execute(task);
	}

	public void showResult() {
		Log.e("TaskManager", "queue = " + this.queue);
	}

	public void shutdown() {
		this.service.shutdown();
	}

	public void release() {
		shutdown();
	}
}
