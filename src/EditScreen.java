package taskMaster;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import javax.swing.*;


/**
 * When given a Task to edit, fills a screen with all the information for 
 * the Task, and updates the fields upon pressing save.
 * 
 * For creating a task, sets fields to initial default values (for the text
 * fields, these values disappear on click and reappear when focus is lost,
 * if no text is present)
 * 
 * Allows user to set no Date. 
 * 
 * @author Kim, Jackie
 *
 */
public class EditScreen extends JPanel{
	
	private JPanel topToolbar;
	private JLabel category;
	private JComboBox categories;
	private JTextField date;
	
	
	private JPanel textPanel;
	//create text field for inputting search information
	private JTextArea newText;
	
	private JPanel bottomPanel;
	private JComboBox priority;
	private FlatButton cancel;
	private FlatButton save;
	
	//temp task and features
	private String textInput;
	private ImportanceLevel tempImp = null;
	private Date tempDate = null;
	private String tempCategory = null;
	private Task tempTask;
	
	private TaskMaster taskMaster;
	
	private String[] catS= {"Personal","Work","Other"};
	private String[] priorityS = {"Low","Normal","High"};
	
	private ArrayList<String> categoryNames;
	
	private Task editingTask; 
	private Color background;
	private Color textAreaShading;
	
	private String initialTaskText = "What do you need to do? :) ";
	private String initialDateText = "mm/dd";
	
	Color green = new Color(191, 227, 74);
	Color yellow = new Color(255, 255, 160);
	Color blue = new Color(99, 195, 210);
	
	EditScreen(TaskMaster taskMaster, Task toEdit, ArrayList<String> names){
		this.taskMaster = taskMaster;
		editingTask = toEdit;
		categoryNames = names;
		textAreaShading = new Color(5, 250, 250, 250); // was 10,20,20,20 // was 10, 200, 200, 200
		
		initBackground();
		this.setLayout(new BorderLayout());
		this.setBackground(background); //this might not be initializedset
		
		topToolbar = new JPanel();
		topToolbar.setBorder(BorderFactory.createEmptyBorder(5,5,5,5)); 
		topToolbar.setOpaque(false);
		topToolbar.setLayout(new BoxLayout(topToolbar, BoxLayout.X_AXIS));
		category = new JLabel("Choose type:");
		categories = new JComboBox();
		for(String name : categoryNames){
			if(!name.equals("All Tasks")){
				categories.addItem(name);
			}
		}
		date = new JTextField(10);
		topToolbar.add(category);
		topToolbar.add(categories);
		topToolbar.add(new JLabel("Due Date:"));
		topToolbar.add(date);
		
		
		textPanel = new JPanel();
		//textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));  
		textPanel.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));   
		textPanel.setLayout(new BorderLayout());  
		textPanel.setOpaque(false);
		//textPanel.setBackground(Color.CYAN);
		newText = new JTextArea();
		//newText.setOpaque(false);
		//newText.setBackground(textAreaShading); //** nice shading
		newText.setLineWrap(true);
		newText.setWrapStyleWord(true); 
		textPanel.add(BorderLayout.CENTER, newText);
		 
		
		bottomPanel = new JPanel();
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(5,5,2,0)); 
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		priority = new JComboBox(priorityS); 
		cancel = new FlatButton(blue, yellow, "Cancel");
		save = new FlatButton(blue, yellow, "Save");
		bottomPanel.add(new JLabel("Choose Priority:"));
		bottomPanel.add(priority);
		bottomPanel.add(cancel);
		bottomPanel.add(save);
		
		
		//set layout and size of frame
		this.add(topToolbar,BorderLayout.NORTH);
		this.add(textPanel, BorderLayout.CENTER); 
		//screenPanel.add(newText, BorderLayout.CENTER); 
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		initListeners();
		setInitialValues();
		this.setVisible(true);
		textPanel.setVisible(true);
		newText.setVisible(true);
	}
	
	/**
	 * Add the listeners to all the fields. 
	 */
	private void initListeners(){
		cancel.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	        	taskMaster.closeEditScreen(false, editingTask, null);
	        }
	    });
		save.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	        	//extract all the info into a task
	        	//get date
	        	String dateInput = date.getText(); 
	        	//tempDate = getInputtedDate(dateInput);//problems w deprecated date
	        	//get new task text
	        	textInput = newText.getText();
	        	//get importance level
	        	if((String)priority.getSelectedItem()=="Low"){
	        		tempImp = ImportanceLevel.low;
	        	}else if((String)priority.getSelectedItem()=="Normal"){
	        		tempImp = ImportanceLevel.normal;
	        	}else{
	        		tempImp = ImportanceLevel.high;
	        	}
      		    //get category   	
	        	tempCategory = (String)categories.getSelectedItem();
	        	//make task, how do we put it into taskgrid?
	        	 //fake color passed for now, category should be passed last
	        	tempTask = new Task(textInput, tempImp, dateInput, taskMaster);
	        	
	        	tempTask.addCategory(tempCategory);
	        	 
	        	//frame.dispose();
	        	//do i need to make this whole class null after?
	        	
	        	taskMaster.closeEditScreen(true, editingTask, tempTask);
	        }
	    });	
		
		priority.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				int selected = priority.getSelectedIndex();
				if(selected == 0){
					background = Task.low;
					save.changeColors(green, yellow);
					cancel.changeColors(green, yellow);
				} else if (selected == 1){
					background = Task.normal;
					save.changeColors(blue, yellow);
					cancel.changeColors(blue, yellow);
				} else {
					background = Task.high;
					save.changeColors(green, blue);
					cancel.changeColors(green, blue);
				}
				resetBackground();
			}
		});
	
		newText.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(newText.getText().equals(initialTaskText)){
					newText.setText("");
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(newText.getText().equals("")){
					newText.setText(initialTaskText);
				}
				
			}
			
		});
		
		date.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(date.getText().equals(initialDateText)){
					date.setText("");
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(date.getText().equals("")){
					date.setText(initialDateText);
				}
				
			}
			
		});
	}
	
	/**
	 * Update background (used for changing color with priority)
	 */
	public void resetBackground(){
		this.setBackground(background);
	}
	
	/**
	 * Fills the fields with their initial values (based on presence or 
	 * absence of a task to be editing)
	 */
	public void setInitialValues(){
		if(editingTask != null){
			newText.setText(editingTask.getText());
			priority.setSelectedIndex(editingTask.getImportance().ordinal()); 
			date.setText(editingTask.getFormattedDueDate());
			background = editingTask.getColor();
			
			int index = 0; 
			for(String name : categoryNames){  
				if(editingTask.isCategory(name)){
					categories.setSelectedItem(name); 
					break;
				} 	
			}
			
		} else {
			background = Task.normal;
			priority.setSelectedIndex(1);
			newText.setText(initialTaskText);
			date.setText(initialDateText);
		}
	}
	
	/**
	 * Determines initial value of background. 
	 */
	public void initBackground(){
		if(editingTask != null){
			background = editingTask.getColor();
		} else {
			background = Task.normal;
		}
	} 
	
	public JPanel getJPanel(){ 
		return this;
	}
	
}
