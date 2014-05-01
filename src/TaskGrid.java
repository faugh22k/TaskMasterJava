package taskMaster;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Created by kcf412 on 4/26/14.
 */ 
public class TaskGrid extends JPanel{

    // TODO after adding priority queues, might well not need all those linked lists
    private String[] texts = {"aaa", "bbb", "ccc", "ddd", "eee", "fff", "eee", "hhh", "iii"};
    // predictably, all the tasks
    private LinkedList<Task> allTasks;

    // grouped by importance
    private LinkedList<Task> low;
    private LinkedList<Task> normal;
    private LinkedList<Task> high;

    // grouped by time
    private LinkedList<Task> current;
    private LinkedList<Task> inFuture;

    private LinkedList<Task> selectedCategory;

    private HashSet<String> categories;
    private TaskMaster taskMaster;

    // these hold the order of the tasks according to different priorities

    // order: high priority current, then normal current, low current, then high future, normal future, low future.
    private TaskSorter orderedByRelaxedDate;

    // order: purely by due date
    private TaskSorter orderedByStrictDate;

    // order: high, normal, low (within individual categories, by due date)
    private TaskSorter orderedByPriority;

    private TaskSorter currentSorting;

    private DisplayState displayState;
    private SortState sortState; 
    
    private Color background;
    private Color taskColor;

    public TaskGrid(TaskMaster taskMaster){  
        // TODO read in initial values from memory somewhere??

    	// infinite rows, five columns
    	GridLayout layout = new GridLayout(0, 5);  
    	layout.setHgap(10);
    	layout.setVgap(10); 
    	
    	this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));        
    	this.setLayout(layout);   
    	this.setFocusable(true);
    
        allTasks = new LinkedList<Task>();
        low = new LinkedList<Task>();
        normal = new LinkedList<Task>();
        high = new LinkedList<Task>();
        current = new LinkedList<Task>();
        inFuture = new LinkedList<Task>();


        orderedByStrictDate = new TaskSorter(new TaskOrderStrictDueDate());
        orderedByRelaxedDate = new TaskSorter(new TaskOrderRelaxedDueDate());
        orderedByPriority = new TaskSorter(new TaskOrderPriority()); 

        currentSorting = orderedByRelaxedDate;

        displayState = DisplayState.all;
        sortState = SortState.relaxedDate;
        
        background = new Color(32, 32, 32); 
        this.setBackground(background); 
        
        taskColor = new Color(191, 227, 74); 
        
        this.taskMaster = taskMaster;

    }

    // corrects the location of the given task (in the different lists)
    public void updateTaskLocation(Task task){
        // if is current, make sure isn't in future, is in current
        if(task.getIsCurrent()){
            if(!current.contains(task)){
                current.add(task);
            }

            if (inFuture.contains(task)){
                inFuture.remove(task);
            }
        }
        // if is in future, make sure isn't in current, is in inFuture
        else {
            if(current.contains(task)){
                current.remove(task);
            }

            if(!inFuture.contains(task)){
                inFuture.add(task);
            }
        }

        if(task.getImportance() == ImportanceLevel.low){
            if(!low.contains(task)){
                low.add(task);
            }
            if(normal.contains(task)){
                normal.remove(task);
            }
            if(high.contains(task)){
                high.remove(task);
            }
        } else if(task.getImportance() == ImportanceLevel.normal){
            if(!normal.contains(task)){
                low.add(task);
            }
            if(low.contains(task)){
                normal.remove(task);
            }
            if(high.contains(task)){
                high.remove(task);
            }
        } else if(task.getImportance() == ImportanceLevel.high){
            if(!high.contains(task)){
                low.add(task);
            }
            if(low.contains(task)){
                normal.remove(task);
            }
            if(normal.contains(task)){
                high.remove(task);
            }
        }

        if(!allTasks.contains(task)){
            allTasks.add(task);
        }

        orderedByPriority.remove(task);
        orderedByPriority.add(task);

        orderedByStrictDate.remove(task);
        orderedByStrictDate.add(task);

        orderedByRelaxedDate.remove(task);
        orderedByRelaxedDate.add(task);
         
    }

    // deletes the task
    public void removeTask(Task task, boolean updateDisplay){
        if(task == null){
        	return;
        }
    	
    	allTasks.remove(task);
        current.remove(task);
        inFuture.remove(task);
        low.remove(task);
        normal.remove(task);
        high.remove(task);
        orderedByPriority.remove(task);
        orderedByStrictDate.remove(task);
        orderedByRelaxedDate.remove(task);
        
        this.remove(task);
        
        if(updateDisplay){
        	//repaint();
        	//validate();
        	redisplayAll();
        }
    }

    // for debugging: checks to see if any tasks are in the wrong place
    public boolean checkLists(){
        boolean correct = true;

        for (Task current : low){
            if(current.getImportance() != ImportanceLevel.low){
                System.out.println("There is a task in the wrong list! (in low)");
                correct = false;
            }
        }

        for (Task current : normal){
            if(current.getImportance() != ImportanceLevel.normal){
                System.out.println("There is a task in the wrong list! (in normal)");
                correct = false;
            }
        }

        for (Task current : high){
            if(current.getImportance() != ImportanceLevel.high){
                System.out.println("There is a task in the wrong list! (in high)");
                correct = false;
            }
        }

        for (Task currentT : current){
            if(!currentT.getIsCurrent()){
                System.out.println("There is a task in the wrong list! (in current)");
                correct = false;
            }
        }

        for (Task currentT : inFuture){
            if(currentT.getIsCurrent()){
                System.out.println("There is a task in the wrong list! (in inFuture)");
                correct = false;
            }
        }


        System.out.println("returning " + correct + " from checkLists");
        return correct;
    }

    // creates a new task to add to the grid, and the lists
    public void createNewTask(String text, ImportanceLevel level, String due){
        Task newTask = new Task(text, level, due, taskMaster);

        	finishTaskCreateOrSwap(newTask);
    }
    
    public void addNewTask(Task newTask){
    	finishTaskCreateOrSwap(newTask);
    }
    
    public void upDateTask(Task original, Task newTask){
    	removeTask(original, false);
    	finishTaskCreateOrSwap(newTask); 
    }
    
    private void finishTaskCreateOrSwap(Task newTask){
    	allTasks.add(newTask);
        orderedByPriority.add(newTask);
        orderedByStrictDate.add(newTask);
        orderedByRelaxedDate.add(newTask);
         

        if(newTask.getImportance() == ImportanceLevel.low){
            low.add(newTask);
        } else if(newTask.getImportance() == ImportanceLevel.normal){
            normal.add(newTask);
        } else if(newTask.getImportance() == ImportanceLevel.high){
            high.add(newTask);
        }

        if(newTask.getIsCurrent() == true){
            current.add(newTask);
        } else {
            inFuture.add(newTask);
        }

        //this.add(newTask);
        redisplayAll();
    }

    // goes through the tasks, updates current and inFuture
    public void updateTimeGrouping(Date now){
        for(Task task: allTasks){
            boolean original = task.getIsCurrent();
            if(original != task.reevaluateCurrent(now)){
                if(original){
                    current.remove(task);
                    inFuture.add(task);
                } else {
                    inFuture.remove(task);
                    current.add(task);
                }
            }
        }
    }

    public void addCategory(String category){
        if(!categories.contains(category)){
            categories.add(category);
        }
    }

    public void getCategoryList(String category){
        if(!categories.contains(category)){
            return;
        }

        selectedCategory = new LinkedList<Task>();

        for (Task current : currentSorting){
            if(current.isCategory(category)){
                selectedCategory.add(current);
            }
        }
    }

    public void changeSort(SortState newSort){
    	if(newSort == sortState){
    		System.out.println("sort state unchanged");
    		return;
    	} 
    	
    	System.out.println("changing sort from " + sortState + " to " + newSort);
    	
    	if(newSort == SortState.priority){
    		currentSorting = orderedByPriority;
    	} 
    	else if (newSort == SortState.relaxedDate){
    		currentSorting = orderedByRelaxedDate;
    	} 
    	else {
    		System.out.println("now sorting by strict date!");
    		currentSorting = orderedByStrictDate;
    	}
    	
    	sortState = newSort;
    	
        redisplayAll();
    }
    
    public void printQueues(){
    	String relaxed = "Relaxed \n";
    	String strict = "Strict \n";
    	String priority = "Priority \n";
    	String currentSort = "Current \n";
    	
    	for(Task current : orderedByRelaxedDate){ 
    		relaxed += current.getText() + "***\n\n"; 
    	}
    	
    	for(Task current : orderedByStrictDate){ 
    		strict += current.getText() + "***\n\n"; 
    	}
    	
    	for(Task current : orderedByPriority){ 
    		priority += current.getText() + "***\n\n"; 
    	}
    	
    	for(Task current : currentSorting){ 
    		currentSort += current.getText() + "***\n\n"; 
    	}
    	 
    	System.out.println(relaxed +  "\n" + priority + "\n" + strict + "\n***" + currentSort); 
    }
    
    public void redisplayAll(){ 
    	System.out.println("removing everything from the grid");
    	
    	this.removeAll();   
    	
    	//repaint();
    	String addedOrder = "";
    	//this.setLayout(new GridLayout(0,5));
    	 
    	for(Task current : currentSorting){  
    		this.add(current);  
    		addedOrder += current.getText() + ", ";
    		//repaint();
    	}   
    	
    	/*LinkedList<Task> list = currentSorting.getList();
    	System.out.println("list = " + list);
    	Task current = list.getFirst();
		for(int i = 0; i < list.size(); i++){ 
			System.out.println("current = " + current);
			if(current == null){
				continue;
			}
			current = list.get(i);
    		this.add(current);  
    		addedOrder += current.getText() + ", ";
    	}*/
    	
    	//this.add(currentSorting.peek());
    	 
    	//this.setLayout(new GridLayout(0,5));
    	
    	/*Task[] tasks = new Task[currentSorting.size()];
    	currentSorting.toArray(tasks);
    	 
    	TaskOrderPriority checking = new TaskOrderPriority();
    	Task last = tasks[0];
    	
    	System.out.println("\n****\n"); 
    	for (int i = 0; i < 9 && i < tasks.length; i++){
    		this.add(tasks[i]);
    		
    		System.out.println("\ncompare(last, current) = " + checking.compare(last, tasks[i]));
    		System.out.println("last: " + last + ", current: " + tasks[i]);
    		last = tasks[i];
    	}*/
    	
    	System.out.println(addedOrder);
    	System.out.println(currentSorting.toString());
    	
    	//printQueues();
    	
    	//invalidate();
    	repaint();
    	validate();
    	 
    }

    public void setDisplayState(DisplayState category, String categoryName){
    	displayState = category;
    	// TODO finish implementing this method
    }
}

