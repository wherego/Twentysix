package miuyongjun.twentysix.utils.task;

import android.os.Handler;
import android.os.Looper;

public abstract class Task
  implements Runnable, Comparable<Task>
{
  public static byte TASK_STATUS_IDLE = 0;
  public static byte TASK_STATUS_RUNNING = 1;
  public static byte TASK_STATUS_DOWN = 3;
  public static byte TASK_PRIORITY_NORMAL = 10;
  public static byte TASK_PRIORITY_LOW = 5;
  public static byte TASK_PRIORITY_HEIGHT = 20;
  public Integer id;
  private int seqNumb;
  private int priority;
  private byte state;
  protected Handler sHandler;
  private TaskStateListener taskStateListener;

  public Task(int priority)
  {
    this.id = Integer.valueOf(-1);
    this.priority = priority;
    this.sHandler = new Handler(Looper.getMainLooper());
  }

  public Task(int priority, TaskStateListener taskStateListener) {
    this.id = Integer.valueOf(-1);
    this.taskStateListener = taskStateListener;
    this.priority = priority;
  }

  public Task(Integer id, TaskStateListener taskStateListener, int priority) {
    this.id = id;
    this.taskStateListener = taskStateListener;
    this.priority = priority;
  }

  public void setTaskListener(TaskStateListener taskStateListener)
  {
    this.taskStateListener = taskStateListener;
  }

  void setSeqNum(int seq)
  {
    this.seqNumb = seq;
  }

  int getSeqNum()
  {
    return this.seqNumb;
  }

  public int compareTo(Task o)
  {
    return TaskPriorityComparator.compareTo(this, o);
  }

  public int getPriority() {
    return this.priority;
  }

  public void setPriority(int newPriority) {
    this.priority = newPriority;
  }

  void notifyFinished()
  {
    if (this.taskStateListener != null)
      this.taskStateListener.onFinished();
  }

  void setState(byte newState)
  {
    this.state = newState;
  }

  public boolean isStart()
  {
    return this.state != TASK_STATUS_IDLE;
  }

  public final void run()
  {
    try {
      doTask();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  protected abstract void doTask();
}
