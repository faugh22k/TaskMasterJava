package com.example.myfirstapp.app;

/**
 * Order by due date: for tasks with the same due date, order by importance
 */
public class TaskOrderStrictDueDate implements TaskOrderer{

    public int compare(Task one, Task two){

        // the task with earlier due date comes first
        if(one.getDue().before(two.getDue())){
            return 1;
        } else if (one.getDue().after(two.getDue())){
            return -1;
        }

        // if the dates are the same, ordered by priority
        if(one.getImportance().ordinal() > two.getImportance().ordinal()){
            return 1;
        } else if (one.getImportance().ordinal() < two.getImportance().ordinal()){
            return -1;
        }

        // if dates and priority level are the same, they are equal
        return 0;
    }
}
