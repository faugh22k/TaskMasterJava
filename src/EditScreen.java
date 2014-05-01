package taskMaster;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Date;

import javax.swing.*;



public class EditScreen extends JPanel{
	
	private JPanel topToolbar;
	private JLabel category;
	private JComboBox<String> categories;
	private JTextField date;
	
	
	private JPanel textPanel;
	//create text field for inputting search information
	private JTextArea newText;
	
	private JPanel bottomPanel;
	private JComboBox<String> priority;
	private JButton cancel;
	private JButton save;
	
	//temp task and features
	private String textInput;
	private ImportanceLevel tempImp = null;
	private Date tempDate = null;
	private String tempCategory = null;
	private Task tempTask;
	
	private TaskMaster taskMaster;
	
	private String[] catS= {"Personal","Work","Other"};
	private String[] priorityS = {"Low","Normal","High"};
	
	private Task editingTask; 
	private Color background;
	private Color textAreaShading;
	
	EditScreen(TaskMaster taskMaster, Task toEdit){
		this.taskMaster = taskMaster;
		editingTask = toEdit;
		textAreaShading = new Color(10, 20, 20, 20);
		initBackground();
		this.setLayout(new BorderLayout(500,500));
		this.setBackground(background); //this might not be initializedset
		
		topToolbar = new JPanel();
		topToolbar.setBorder(BorderFactory.createEmptyBorder(5,5,5,5)); 
		topToolbar.setOpaque(false);
		topToolbar.setLayout(new BoxLayout(topToolbar, BoxLayout.X_AXIS));
		category = new JLabel("Choose type:");
		categories = new JComboBox<String>(catS);
		date = new JTextField(10);
		topToolbar.add(category);
		topToolbar.add(categories);
		topToolbar.add(new JLabel("Due Date:"));
		topToolbar.add(date);
		
		
		textPanel = new JPanel();
		//textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));  
		textPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));   
		textPanel.setLayout(new BorderLayout());  
		//textPanel.setOpaque(false);
		textPanel.setBackground(Color.CYAN);
		newText = new JTextArea();
		newText.setOpaque(true);
		newText.setBackground(textAreaShading);
		newText.setLineWrap(true);
		newText.setWrapStyleWord(true); 
		textPanel.add(BorderLayout.CENTER, newText);
		
		
		bottomPanel = new JPanel();
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(5,5,2,0)); 
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		priority = new JComboBox<String>(priorityS); 
		cancel = new JButton("Cancel");
		save = new JButton("Save");
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
		initEditingTask();
		this.setVisible(true);
		textPanel.setVisible(true);
		newText.setVisible(true);
	}
	
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
	        	
	        	System.out.println("A task was made with text:"+ " " + tempTask.getText());
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
				} else if (selected == 1){
					background = Task.normal;
				} else {
					background = Task.high;
				}
				resetBackground();
			}
		});
	}
	
	public void resetBackground(){
		this.setBackground(background);
	}
	public void initEditingTask(){
		if(editingTask != null){
			newText.setText(editingTask.getText());
			priority.setSelectedIndex(editingTask.getImportance().ordinal()); 
		} else {
			priority.setSelectedIndex(1);
			newText.setText("Please input text here.");
		}
	}
	public void initBackground(){
		if(editingTask != null){
			background = editingTask.getColor();
		} else {
			background = Task.normal;
		}
	}
	private Date getInputtedDate(String s){
		if(s.equals("") || s.equals("MM/dd")){
			return null;
		}
		
		/*String d = s;
		String[] tokens = d.split("/");
		int[] dates= new int[tokens.length];
		for (int i=0; i<tokens.length;i++){
		  dates[i] = Integer.parseInt(tokens[i]);	
		}
		Date tempDate = new Date(dates[2],dates[1],dates[0]); //this is deprecated, is this correct order?
		return tempDate;*/
		
		//Date date = task.
		return null;
	}
	
	public JPanel getJPanel(){ return this;}
	
}
