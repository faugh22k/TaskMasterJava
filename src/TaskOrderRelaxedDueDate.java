package com.example.myfirstapp.app;

/**
 * Order:
 *   current high priority
 *   current normal priority
 *   current low priority
 *
 *   future high priority
 *   future normal priority
 *   future low priority
 *
 *   Within the 6 categories, order by due date
 */
public class TaskOrderRelaxedDueDate implements  TaskOrderer{

    @Override
    public int compare(Task one, Task two){

        // between two tasks of the same general time range (current vs future), order by importance (then by due date, below)
        if((one.getIsCurrent() && two.getIsCurrent()) || (!one.getIsCurrent() && !two.getIsCurrent())) {
            if (one.getImportance().ordinal() > two.getImportance().ordinal()) {
                return 1;
            } else if (one.getImportance().ordinal() < two.getImportance().ordinal()) {
                return -1;
            }
        }

        // if the two tasks are not in the same general time range, or are in the same
        // general time range but with equal importance, order by due date
        if(one.getDue().before(two.getDue())){
            return 1;
        } else if (one.getDue().after(two.getDue())){
            return -1;
        }

        // the tasks have the same importance and due date, and are equal
        return 0;
    }

}
