package taskMaster;

import java.util.Comparator;

/**
 * Interface for the Task Comparators
 */
public interface TaskOrderer extends Comparator<Task> {

    @Override
    public int compare(Task one, Task two);
}
