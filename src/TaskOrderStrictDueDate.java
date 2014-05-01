package taskMaster;

/**
 * Order by due date: for tasks with the same due date, order by importance
 */
public class TaskOrderStrictDueDate implements TaskOrderer{

    public int compare(Task one, Task two){
    	System.out.println("\none.date = " + one.getFormattedDueDate());
    	System.out.println("two.date = " + two.getFormattedDueDate());
    	
    	System.out.println("one.priority = " + one.toString());
    	System.out.println("two.priority = " + two.toString());
        // the task with earlier due date comes first
        if(one.getDue().before(two.getDue())){
        	System.out.println("one is due before two");
            return -1;
        } else if (one.getDue().after(two.getDue())){
        	System.out.println("one is due after two");
            return 1;
        }

        System.out.println("one and two due at same time");
        
        // if the dates are the same, ordered by priority
        if(one.getImportance().ordinal() > two.getImportance().ordinal()){
        	System.out.println("one has a higher priority than two");
            return -1;
        } else if (one.getImportance().ordinal() < two.getImportance().ordinal()){
        	System.out.println("one has a lower priority than two");
            return 1;
        }

        System.out.println("one and two due at same time, have same importance");
        // if dates and priority level are the same, they are equal
        return 0;
    }
}
