package taskMaster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Comparator;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Encompasses Task data, logic, and display
 */
public class Task extends JPanel{

    // the buttons area for this task
    private TaskControls controls;

    // the text stored in this task
    private TaskText text;
    
    //private JTextArea textArea;

    // the data: priority, dates, color
    private TaskInfo info;

    //private Paint background;
    private Color background; 
    
    private Color high;
    private Color normal;
    private Color low;


    public Task(String text, ImportanceLevel importance, Date due){ 
        this.text = new TaskText(text); 
        
         
        
        // TODO probably want more detailed constructor call
        info = new TaskInfo(importance, due);

        // TODO add some sort of listener or something?
        controls = new TaskControls();
         
        
        /*textArea = new JTextArea(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);*/
        
        JLabel testing = new JLabel("date area"); 
        //this.add(testing, BorderLayout.NORTH); 
         
        this.setLayout(new BorderLayout());
        
        this.add(info, BorderLayout.NORTH); 
        //this.add(textArea, BorderLayout.CENTER);
        this.add(this.text, BorderLayout.CENTER);
        this.add(controls, BorderLayout.SOUTH);
         
        normal = new Color(191, 227, 74); 
        high = new Color(255, 255, 160);
        low = new Color(99, 195, 210);
        
        if(importance == ImportanceLevel.high){
        	background = high;
        } else if(importance == ImportanceLevel.normal){
        	background = normal;
        } else {
        	background = low;
        }
        
        this.setBackground(background); 
        this.setSize(new Dimension(200, 200));
        this.setMaximumSize(new Dimension(200, 200));
        this.setMinimumSize(new Dimension(200, 200)); 

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

    public TaskText getTaskText() { 
    	return text;
    }

    public String getText() { 
    	return text.getText();
    	//return textArea.getText();
    } 
    
    public void setText(String text){
    	//textArea.setText(text);  
    	this.text.setText(text);
    }
 
    
    public String toString(){
    	//return textArea.getText();
    	//return text.getText();
    	return info.getImportance().toString();
    }
}

