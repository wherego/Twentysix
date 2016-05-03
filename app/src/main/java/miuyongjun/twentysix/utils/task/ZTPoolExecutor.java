package miuyongjun.twentysix.utils.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ZTPoolExecutor extends ThreadPoolExecutor
{
  private static int currentTaskIndex = 0;

  public ZTPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue)
  {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
  }

  public ZTPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory)
  {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
  }

  public ZTPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler)
  {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
  }

  public ZTPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)
  {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
  }

  protected void afterExecute(Runnable r, Throwable t)
  {
    super.afterExecute(r, t);
    if ((r instanceof Task)) {
      ((Task)r).setState(Task.TASK_STATUS_DOWN);
      ((Task)r).notifyFinished();
    }
  }

  protected void beforeExecute(Thread t, Runnable r)
  {
    if ((r instanceof Task)) {
      ((Task)r).setState(Task.TASK_STATUS_RUNNING);
    }
    super.beforeExecute(t, r);
  }

  public void execute(Runnable command)
  {
    if ((command instanceof Task)) {
      ((Task)command).setSeqNum(currentTaskIndex);
      currentTaskIndex += 1;
      if (currentTaskIndex >= 2147483647) {
        currentTaskIndex = 0;
      }
    }
    super.execute(command);
  }
}
