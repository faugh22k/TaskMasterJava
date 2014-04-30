package taskMaster;

import java.awt.Color;
import java.util.Comparator;
import java.util.Date;

/**
 * Encompasses Task data, logic, and display
 */
public class Task{

    // the buttons area for this task
    private TaskControls controls;

    // the text stored in this task
    private TaskText text;

    // the data: priority, dates, color
    private TaskInfo info;

    //private Paint background;


    public Task(String text, ImportanceLevel importance, Date due){ 
        this.text = new TaskText(text);
        // TODO probably want more detailed constructor call
        info = new TaskInfo(importance, due);

        // TODO add some sort of listener or something?
        controls = new TaskControls();

        //background = new Paint(Paint.ANTI_ALIAS_FLAG);
        // rgb 191, 227, 74
        //background.setColor(0xBFE34A);
        // darker version: 5F8100  (79, 129, 0)
    }

    public Task(String text, ImportanceLevel importance){
        this(text, importance, null);
    }

    public Task(String text){
        this(text, ImportanceLevel.normal);
    }

    public ImportanceLevel getImportance(){
        return info.getImportance();
    }

    public boolean getIsCurrent(){
        return info.getCurrent();
    }

    public boolean reevaluateCurrent(){
        return info.determineIsCurrent();
    }

    public Date getDue(){
        return info.getDue();
    }

    public Color getColor(){
        return info.getColor();
    }

    public void setImportance(ImportanceLevel level){
        info.setImportance(level);
    }

    public void setDue(Date date){
        info.setDue(date);
    }

    public void setColor(Color color){
        info.setColor(color);
    }

    public boolean isCategory(String category){
        return info.isCategory(category);
    }

    public void addCategory(String category){
        info.addCategory(category);
    }

    public void removeCategory(String category){
        info.removeCategory(category);
    }

    public TaskText getTaskText() { return text;}

    public String getText() { return text.getText();}
 
}

