package miuyongjun.twentysix.utils.task;

public class TaskPriorityComparator
{
  private static boolean IS_BIG_PRIORITY_FIRST = true;

  public static int compareTo(Task src, Task o)
  {
    int result = -1;
    if (IS_BIG_PRIORITY_FIRST)
      result = o.getPriority() - src.getPriority();
    else {
      result = src.getPriority() - o.getPriority();
    }

    if (result == 0) {
      result = src.getSeqNum() - o.getSeqNum();
    }
    return result;
  }
}
