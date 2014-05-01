package taskMaster;

/**
 * Order by due date: for tasks with the same due date, order by importance
 */
public class TaskOrderStrictDueDate implements TaskOrderer{

    public int compare(Task one, Task two){
    	/*System.out.println("\none.date = " + one.getFormattedDueDate());
    	System.out.println("two.date = " + two.getFormattedDueDate());
    	
    	System.out.println("one.priority = " + one.toString());
    	System.out.println("two.priority = " + two.toString());
    	
    	System.out.println("one.text = " + one.getText());
    	System.out.println("two.text = " + two.getText());*/
    	
    	if(one.getDue() == null){
        	return 1;
        }
        if(two.getDue() == null){
        	return -1;
        }
    	
        // the task with earlier due date comes first
        if(one.getDue().before(two.getDue())){
        	//System.out.println("one is due before two\n");
            return -1;
        } else if (one.getDue().after(two.getDue())){
        	//System.out.println("one is due after two\n");
            return 1;
        }

        //System.out.println("one and two due at same time");
        
        // if the dates are the same, ordered by priority
        if(one.getImportance().ordinal() > two.getImportance().ordinal()){
        	//System.out.println("one has a higher priority than two\n");
            return -1;
        } else if (one.getImportance().ordinal() < two.getImportance().ordinal()){
        	//System.out.println("one has a lower priority than two\n");
            return 1;
        }

        //System.out.println("one and two due at same time, have same importance\n");
        // if dates and priority level are the same, they are equal
        return 0;
    }
}
