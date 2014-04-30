package com.example.myfirstapp.app;

/**
 * Created by kcf412 on 4/27/14.
 */
public class TaskOrderPriority implements TaskOrderer {

    public int compare(Task one, Task two){

        // Order by priority first (high, then normal, then low)
        if(one.getImportance().ordinal() > two.getImportance().ordinal()){
            return 1;
        } else if (one.getImportance().ordinal() < two.getImportance().ordinal()){
            return -1;
        }

        // if same priority, order by due date
        if(one.getDue().before(two.getDue())){
            return 1;
        } else if (one.getDue().after(two.getDue())){
            return -1;
        }

        // the two tasks have same priority and same due date, so they're equal
        return 0;
    }

}
