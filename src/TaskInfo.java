package taskMaster;

import java.awt.BorderLayout;
import java.awt.Color; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar; 
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Store all of the settings of the task
 * Display the important ones  
 */
public class TaskInfo extends JPanel{

    // time created, edited, and due
    private Date created;
    private Date edited;
    private Date due;
    
    private JLabel displayDueDate;

    // low, normal, or high importance possible
    private ImportanceLevel importance;
 
    private boolean isCurrent;
    private boolean completed;
    private Color color;
    private ArrayList<String> categories;
    private SimpleDateFormat formatDate;
    
    
   

    /**
     * Initializes the two primary fields.
     * This does not include Task Text, because there was almost a custom 
     * JTextArea for that, and it was kept separate to allow for that in the future. 
     */
    public TaskInfo(ImportanceLevel level, String due){ 
        created = new Date();
        edited = created;
        
        importance = level;
        categories = new ArrayList<String>(5);
        completed = false;
        this.setOpaque(false);
       
        
        formatDate = new SimpleDateFormat("MM/dd");  
        try {
			this.due = formatDate.parse(due);
		} catch (ParseException e) {}
        
        if(this.due != null){
        	displayDueDate = new JLabel(formatDate.format(this.due)); 
        } else {
        	displayDueDate = new JLabel("  "); 
        }
        this.add(displayDueDate, BorderLayout.EAST);
        
        // automatically gives initialized to current date, time
        Date now = new Date();
        determineIsCurrent(now);
    }

    /**
     * Analyze due date vs current date to determine whether should be current.  
     * @return true if isCurrent has been changed. 
     */
	public boolean determineIsCurrent(Date now) {
		boolean updated = false;
		boolean tmp = false;

		int distance = determineDistance(now);

		/*
		 * if distance is negative, the task is in the past currently, we let
		 * tasks from farther back than yesterday fall out of current current 
		 * so that they don't clutter up the immediate tasks.  
		 */
		if (distance <= 2 && distance >= -1) { 
			tmp = true;
		}

		if (isCurrent != tmp) {
			updated = true;
		}

		isCurrent = tmp;
		return updated;
	}
    
	/**
	 * Calculates number of days in between now and due date. 
	 */
    private int determineDistance(Date now){
    	if(due == null){
    		return 0;
    	}
    	
    	int distance = 40;
    	
    	Calendar calendar = GregorianCalendar.getInstance();
		int monthN = calendar.get(Calendar.MONTH); // starts at 0 with January 
		int dayN = calendar.get(Calendar.DATE); // day of the month 
		
		int monthD = due.getMonth();
		int dayD = due.getDate();
		 
		
		// the task was due the previous month (mod handles the case where it was due in December)
		if ((monthD + 1) % 12 == monthN){ 
			// average number of days in a month is 30.4167... 
			// erring on the side of letting an older task slip into current
			// rather than one dropping off too quickly
			distance = -((30 - dayD) + dayN); 
			//monthD++;
		}
		// the task is due next month (mod handles the case where it is currently December) 
		else if (monthD  == (monthN + 1) % 12){ 
			distance = (30 - dayN) + dayD; 
			monthD--;
		}
		// if the months match, compare only the days
		else if(monthN == monthD){ 
			//distance = Math.max(dayD - dayN, dayN - dayD);
			distance = dayD - dayN;
		} 
    	  
    	return distance;
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
    
    /**
     * Sets the Date from a string
     * @param due the string with the Date (mm/dd)
     */
    public void parseDue(String due){
    	try {
    		Date tmp = formatDate.parse(due);
			this.due = tmp; 
		} catch (ParseException e) {}
    }
    
    /**
     * Translates the Date into a readable string (mm/dd) 
     */
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

