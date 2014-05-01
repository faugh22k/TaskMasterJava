package taskMaster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JComponent; 
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Store all of the settings of the task
 * Display the important ones (title, if we have titles, and due date)
 */
public class TaskInfo extends JPanel{

    // time created, edited, and due
    private Date created;
    private Date edited;
    private Date due;
    
    private JLabel displayDueDate;

    // low, normal, or high importance possible
    private ImportanceLevel importance;

    // TODO set to true if due is within certain range of current date
    // TODO said determination (namely, retrieval of current date) not yet done
    private boolean isCurrent;
    private boolean completed;
    private Color color;
    private ArrayList<String> categories;
    private SimpleDateFormat formatDate;
    
    
    public TaskInfo(){
        this(ImportanceLevel.normal, true, null);
    }

    public TaskInfo(ImportanceLevel level){
        this(level, true, null);
    }

    public TaskInfo(ImportanceLevel level, Date due){
        this(level, true, due);
    }

    // primary constructor 
    public TaskInfo(ImportanceLevel level, boolean isCurrent, Date due){
        // TODO must decide how to deal with getting due date, decided whether it's current
        created = new Date();
        edited = created;
        this.due = due;
        this.isCurrent = isCurrent;
        importance = level;
        categories = new ArrayList<String>(5);
        completed = false;
        this.setOpaque(false);
        
        formatDate = new SimpleDateFormat("MM/dd/yyyy  HH:mm");
        if(due != null){
        	displayDueDate = new JLabel(formatDate.format(due)); 
        } else {
        	displayDueDate = new JLabel("hello!");
        }
        this.add(displayDueDate, BorderLayout.EAST);
    }

    public boolean determineIsCurrent(){
        // TODO figure out how to decide isCurrent
        boolean updated = true;
        isCurrent = updated;
        return updated;
    }

    public ImportanceLevel getImportance(){
        return importance;
    }

    public void setImportance(ImportanceLevel toStore){
        importance = toStore;
    }

    public void setImportance(int level){
        if(level == 0){
            importance = ImportanceLevel.low;
        } else if (level == 1){
            importance = ImportanceLevel.normal;
        } else {
            importance = ImportanceLevel.high;
        }
    }

    public void setCurrent(boolean isCurrent){
        this.isCurrent = isCurrent;
    }

    public boolean getCurrent(){
        return isCurrent;
    }

    public void setEdited(Date date){
        edited = date;
    }

    public void setDue(Date date){
        due = date;
        displayDueDate.setText(getFormattedDate());
        
    }
    
    public String getFormattedDate(){
    	return formatDate.format(due);
    }

    public Date getEdited(){
        return edited;
    }

    public Date getDue(){
        return due;
    }

    public Date getCreated(){
        return created;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void addCategory(String category){
        if(!categories.contains(category)){
            categories.add(category);
        }
    }

    public void removeCategory(String category){
        categories.remove(category);
    }

    public boolean isCategory(String category){
        return categories.contains(category);
    }
}

