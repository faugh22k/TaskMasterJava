package taskMaster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Comparator;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Encompasses Task data, logic, and display
 */
public class Task extends JPanel implements MouseListener/*FocusListener*/ {  

    // the buttons area for this task
    private TaskControls controls;

    // the text stored in this task
    private TaskText text;
    
    //private JTextArea textArea;

    // the data: priority, dates, color
    private TaskInfo info;
    
    private boolean selected;

    //private Paint background;
    private Color background; 
    
    public static Color high;
    public static Color normal;
    public static Color low;
    
   
    
    private TaskMaster taskMaster;


    public Task(String text, ImportanceLevel importance, Date due, TaskMaster taskMaster) {  
        this.text = new TaskText(text); 
        this.taskMaster = taskMaster;
         
        selected = false;
        
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
        this.setFocusable(true);
        this.addMouseListener(this);
       // this.addFocusListener(this);
        
        //this.text.addFocusListener(this);
        //info.addFocusListener(this);
        //controls.addFocusListener(this);
        this.text.addMouseListener(this);
        info.addMouseListener(this);
        controls.addMouseListener(this);

        //background = new Paint(Paint.ANTI_ALIAS_FLAG);
        // rgb 191, 227, 74
        //background.setColor(0xBFE34A);
        // darker version: 5F8100  (79, 129, 0) 
    }

    public Task(String text, ImportanceLevel importance, TaskMaster taskMaster){ 
        this(text, importance, null, taskMaster);
    }

    public Task(String text, TaskMaster taskMaster){
        this(text, ImportanceLevel.normal, taskMaster); 
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
    
    public String getFormattedDueDate(){
    	return info.getFormattedDate();
    }

    public Color getColor(){
        return background;
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
    
    public Color brighter(){
    	int r,g,b;
    	
    	r = background.getRed() + 32;
    	g = background.getGreen() + 32;
    	b = background.getBlue() + 32; 
    	
    	if(r > 255){
    		r = 255;
    	} 
    	if(g > 255){
    		g = 255;
    	}
    	if(b > 255){
    		b = 255;
    	}
    	
    	return new Color(r,g,b);
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		changeSelection();
	}

	public void changeSelection(){
		selected = !selected;
		
		if(selected){
			System.out.println("I'm selected!");
			setBackground(brighter());
			taskMaster.addSelection(this); 
		} else {
			System.out.println("I'm not selected now");
			setBackground(background);
			taskMaster.removeSelection(this); 
		}
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
    
    /*public void focusLost(FocusEvent e){
    	System.out.println("don't have focus now.");
		taskMaster.setSelected(null); 
		this.setBackground(background.darker());
	}
	
	public void focusGained(FocusEvent e){
		System.out.println("have focus!");
		taskMaster.setSelected(this);
		this.setBackground(background);
	}*/
}

