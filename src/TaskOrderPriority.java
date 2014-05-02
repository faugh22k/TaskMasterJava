package taskMaster;

/**
 * Created by kcf412 on 4/27/14.
 */
public class TaskOrderPriority implements TaskOrderer {

    public int compare(Task one, Task two){

    	//System.out.println("\n\none.priority: " + one.getImportance());
    	//System.out.println("two.priority: " + two.getImportance());
    	
        // Order by priority first (high, then normal, then low)
        if(one.getImportance().ordinal() > two.getImportance().ordinal()){
        	//System.out.println("one higher priority than two");
            return -1;
        } else if (one.getImportance().ordinal() < two.getImportance().ordinal()){
        	//System.out.println("one lower priority than two");
            return 1;
        }

        //System.out.println("the tasks have equal priority");
        
        if(one.getDue() == null){
        	return -1;
        }
        if(two.getDue() == null){
        	return 1;
        }
        
        // if same priority, order by due date
        if(one.getDue().before(two.getDue())){
        	//System.out.println("one due before two");
            return -1;
        } else if (one.getDue().after(two.getDue())){
        	//System.out.println("one due after two");
            return 1;
        }

        //System.out.println("the tasks have equal priority and due date");
        // the two tasks have same priority and same due date, so they're equal
        return 0;
    }

}
