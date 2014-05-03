package taskMaster;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
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

    private int gridWidth = 5;
     

    private LinkedList<Task> selectedCategory;

    private ArrayList<String> categories;
    private TaskMaster taskMaster;
    
    
    private String currentCategory;

    // these hold the order of the tasks according to different priorities

    // order: high priority current, then normal current, low current, then high future, normal future, low future.
    private TaskSorter orderedByRelaxedDate;

    // order: purely by due date
    private TaskSorter orderedByStrictDate;

    // order: high, normal, low (within individual categories, by due date)
    private TaskSorter orderedByPriority;

    // the currently displayed sort
    private TaskSorter currentSorting;

    // displaying category, or all tasks?
    private DisplayState displayState;
    // which sort is being displayed
    private SortState sortState; 
    
    private Color background; 

    public TaskGrid(TaskMaster taskMaster, ArrayList<String> names){  
        // TODO read in initial values from memory somewhere??

    	// infinite rows, five columns
    	GridLayout layout = new GridLayout(0, gridWidth);   
    	layout.setHgap(10);
    	layout.setVgap(10); 
    	
    	this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));        
    	this.setLayout(layout);   
    	this.setFocusable(true); 

        categories = names;

        orderedByStrictDate = new TaskSorter(new TaskOrderStrictDueDate());
        orderedByRelaxedDate = new TaskSorter(new TaskOrderRelaxedDueDate());
        orderedByPriority = new TaskSorter(new TaskOrderPriority()); 

        currentSorting = orderedByRelaxedDate;

        displayState = DisplayState.all;
        sortState = SortState.relaxedDate;
        
        background = new Color(32, 32, 32); 
        this.setBackground(background);  
        
        this.taskMaster = taskMaster;

    }

    public void changeSize(){
    	int width;
    	int height;
    	
    	int numberTasks = -1;
    	
    	if(displayState == DisplayState.all){
    		numberTasks += currentSorting.size();
    	} else {
    		getCategoryList(currentCategory);
    		if(selectedCategory == null || selectedCategory.size() == 0){
    			numberTasks += currentSorting.size();
    		} else {
    			numberTasks += selectedCategory.size();
    		}
    	}
    	 
    	int numberRows = (numberTasks/gridWidth) + 1; 
    	
    	height = numberRows*Task.height;
    	width = gridWidth*Task.width;
    	 
    	
    	setPreferredSize(new Dimension(width,height)); 
    	setMinimumSize(new Dimension(width, Task.height*2));
    }
     

    // deletes the task
    public void removeTask(Task task, boolean updateDisplay){
        if(task == null){
        	return;
        } 
        orderedByPriority.remove(task);
        orderedByStrictDate.remove(task);
        orderedByRelaxedDate.remove(task);
        
        this.remove(task);
         
        if(updateDisplay){ 
        	redisplayAll();
        }
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
        orderedByPriority.add(newTask);
        orderedByStrictDate.add(newTask);
        orderedByRelaxedDate.add(newTask);
         
 
        //this.add(newTask);
        redisplayAll();
    }
 

    public void addCategory(String category){
        if(!categories.contains(category)){
            categories.add(category);
        }
    }
    
    public void removeCategory(String category){
        categories.remove(category);
        
        if(displayState == DisplayState.category && currentCategory.equals(category)){
        	displayState = DisplayState.all;
        	redisplayAll();
        }
    }

    /**
     * Pulls Tasks with the given category into a list
     * by order of the current sorting. 
     */
    private void getCategoryList(String category){
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
    		return;
    	} 
    	 
    	if(newSort == SortState.priority){
    		currentSorting = orderedByPriority;
    	} 
    	else if (newSort == SortState.relaxedDate){
    		currentSorting = orderedByRelaxedDate;
    	} 
    	else { 
    		currentSorting = orderedByStrictDate;
    	}
    	
    	sortState = newSort;
    	
    	if(displayState == DisplayState.category){
    		getCategoryList(currentCategory);
    	}
    	
        redisplayAll();
    }
     
    
    public void redisplayAll(){  
    	changeSize();
    	
    	this.removeAll();   
    	
    	
    	 
    	if(displayState != DisplayState.all){
    		getCategoryList(currentCategory);
    	}
    	
    	if(displayState == DisplayState.all || selectedCategory == null || selectedCategory.size() == 0){
    		for(Task current : currentSorting){  
    			this.add(current);   
    		}   
    	} else {
    		for(Task current : selectedCategory){
    			this.add(current);
    		}
    	}
    	 
    	repaint();
    	validate();
    	 
    }

    public void setDisplayState(DisplayState category, String categoryName){
    	displayState = category;
    	currentCategory = categoryName; 
    	redisplayAll();
    }
}

