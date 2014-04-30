package com.example.myfirstapp.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Created by kcf412 on 4/26/14.
 */
// TODO should change minimum api version to ...14? (ice cream sandwich)
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class TaskGrid extends BaseAdapter {

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

    // these hold the order of the tasks according to different priorities

    // order: high priority current, then normal current, low current, then high future, normal future, low future.
    private PriorityQueue<Task> orderedByRelaxedDate;

    // order: purely by due date
    private PriorityQueue<Task> orderedByStrictDate;

    // order: high, normal, low (within individual categories, by due date)
    private PriorityQueue<Task> orderedByPriority;

    private PriorityQueue<Task> currentSorting;

    private DisplayState displayState;
    private SortState sortState;

    private Context context;

    public TaskGrid(Context context){
        this.context = context;
        // TODO read in initial values from memory somewhere??

        allTasks = new LinkedList<Task>();
        low = new LinkedList<Task>();
        normal = new LinkedList<Task>();
        high = new LinkedList<Task>();
        current = new LinkedList<Task>();
        inFuture = new LinkedList<Task>();


        orderedByStrictDate = new PriorityQueue<Task>(10, new TaskOrderRelaxedDueDate());
        orderedByRelaxedDate = new PriorityQueue<Task>(10, new TaskOrderStrictDueDate());
        orderedByPriority = new PriorityQueue<Task>(10, new TaskOrderPriority());

        currentSorting = orderedByRelaxedDate;

        displayState = DisplayState.all;
        sortState = SortState.relaxedDate;

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
    public void removeTask(Task task){
        allTasks.remove(task);
        current.remove(task);
        inFuture.remove(task);
        low.remove(task);
        normal.remove(task);
        high.remove(task);
        orderedByPriority.remove(task);
        orderedByStrictDate.remove(task);
        orderedByRelaxedDate.remove(task);
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
    public void createNewTask(String text, ImportanceLevel level, Date due){
        Task newTask = new Task(text, level, due, context);

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

    }

    // goes through the tasks, updates current and inFuture
    public void updateTimeGrouping(){
        for(Task task: allTasks){
            boolean original = task.getIsCurrent();
            if(original != task.reevaluateCurrent()){
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




    /* (non-Javadoc)
 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
 */
    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        //this probably needs to be moved for efficiency
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);
            //get layout form main screen
            gridView = inflater.inflate(R.layout.activity_main, null);

            // set task text into textview
            TextView textView = (TextView) gridView.findViewById(R.id.grid_item_text);
            textView.setText(texts[index]);

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }


    @Override
    public int getCount() {
        return currentSorting.size();
    }

    @Override
    public Task getItem(int index) {
        Task[] tasks = (Task[])currentSorting.toArray();
        return tasks[index];
    }

    @Override
    public long getItemId(int index) {
        return index;
    }
}
