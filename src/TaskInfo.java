package taskMaster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    
    
   

    // primary constructor 
    public TaskInfo(ImportanceLevel level, String due){
        // TODO must decide how to deal with getting due date, decided whether it's current
        created = new Date();
        edited = created;
        
        importance = level;
        categories = new ArrayList<String>(5);
        completed = false;
        this.setOpaque(false);
       
        
        formatDate = new SimpleDateFormat("MM/dd"); //new SimpleDateFormat("MM/dd/yyyy  HH:mm"); 
        try {
			this.due = formatDate.parse(due);
		} catch (ParseException e) {}
        
        if(this.due != null){
        	displayDueDate = new JLabel(formatDate.format(this.due)); 
        } else {
        	displayDueDate = new JLabel(""); 
        }
        this.add(displayDueDate, BorderLayout.EAST);
        
        // automatically gives initialized to current date, time
        Date now = new Date();
        determineIsCurrent(now);
    }

    public boolean determineIsCurrent(Date now){ 
    	
    	Calendar calendar = GregorianCalendar.getInstance();
    	int month = calendar.get(Calendar.MONTH) + 1; // starts at 0 with January
    	int day = calendar.get(Calendar.DATE); // day of the month
    	
    	//int[] current = getMonthDay(now);
    	int[] dueBy = getMonthDay(due);
    	System.out.println("\n\nDetermining is Current!!! ");
    	
    	System.out.println("date = " + getFormattedDate()); 
    	
    	System.out.println("priority = " + importance.toString());  
    	
    	boolean updated = false;
    	boolean tmp = false; 
    	
    	if(month == dueBy[0] && (day >= dueBy[1] - 2 && day <= (dueBy[1]))){ 
    		System.out.println("I am a current task!");
    		tmp = true;
    	} else {
    		System.out.println("I am a future task!");
    	}
        
    	System.out.println("now: " + month + "/" + day);
    	System.out.println("due: " + dueBy[0] + "/" + dueBy[1]);
    	
    	if (isCurrent != tmp){
    		updated = true;
    	}
    	
        isCurrent = tmp;
        System.out.println("Returning from Determining is Current!!!  \n\n");
        return updated;
    }
    
    public int[] getMonthDay(Date date){
    	int[] data = new int[2];
    	
    	String format = formatDate.format(date);
    	System.out.println("formatted date: " + format);
    	
    	int tmpDay = date.getDate();
    	int tmpMonth = date.getMonth();
    	
    	String[] extracted = format.split("/"); 
    	try {
    		/*if(extracted[0].charAt(0) == '0'){
    			extracted[0] = extracted[0].substring(1, 2);
    		} 
    		if(extracted[1].charAt(0) == '0'){
    			extracted[1] = extracted[1].substring(1, 2);
    		}*/
    		
    		int month = Integer.parseInt(extracted[0]);
    		int day = Integer.parseInt(extracted[1]);
    		data[0] = month;
    		data[1] = day;
    	} catch (NumberFormatException e){
    		data = null;
    	}
    	
    	return data;
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
    
    public void parseDue(String due){
    	try {
    		Date tmp = formatDate.parse(due);
			this.due = tmp; 
		} catch (ParseException e) {}
    }
    
    public String getFormattedDate(){
    	if(due == null){
    		return "";
    	}
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

