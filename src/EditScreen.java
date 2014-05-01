package taskMaster;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Date;

import javax.swing.*;



public class EditScreen{
	
	private JPanel screenPanel;
	private JPanel topToolbar;
	private JLabel category;
	private JComboBox categories;
	private JTextField date;
	
	
	private JPanel textPanel;
	//create text field for inputting search information
	private JTextArea newText;
	
	private JPanel bottomPanel;
	private JComboBox priority;
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
	
	EditScreen(TaskMaster taskMaster, Task toEdit){
		this.taskMaster = taskMaster;
		editingTask = toEdit;
		
		if(editingTask != null){
			background = editingTask.getColor();
		} else {
			background = Task.normal;
		}
		
		go();
		initButtons();
	}
	public void go(){
		screenPanel = new JPanel();
		screenPanel.setLayout(new BorderLayout(500,500));
		screenPanel.setBackground(background);
		
		topToolbar = new JPanel();
		topToolbar.setBorder(BorderFactory.createEmptyBorder(5,5,5,5)); 
		topToolbar.setOpaque(false);
		topToolbar.setLayout(new BoxLayout(topToolbar, BoxLayout.X_AXIS));
		category = new JLabel("Choose type:");
		categories = new JComboBox(catS);
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
		newText.setOpaque(false);
		newText.setLineWrap(true);
		newText.setWrapStyleWord(true);
		textPanel.add(BorderLayout.CENTER, newText);
		newText.setVisible(true);
		textPanel.setVisible(true);
		
		bottomPanel = new JPanel();
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(5,5,2,0)); 
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		priority = new JComboBox(priorityS);
		cancel = new JButton("Cancel");
		save = new JButton("Save");
		bottomPanel.add(new JLabel("Choose Priority:"));
		bottomPanel.add(priority);
		bottomPanel.add(cancel);
		bottomPanel.add(save);
		
	
		//set layout and size of frame
		screenPanel.add(topToolbar,BorderLayout.NORTH);
		screenPanel.add(textPanel, BorderLayout.CENTER); 
		screenPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		//frame.getContentPane().add(BorderLayout.NORTH,topToolbar);
		//frame.getContentPane().add(BorderLayout.CENTER,textPanel);
		//frame.getContentPane().add(BorderLayout.SOUTH,bottomPanel);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(500,500);
		screenPanel.setVisible(true);
		
	}
	
	private void initButtons(){
		cancel.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	        	taskMaster.closeEditScreen(false, null, null);
	        }
	    });
		save.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	        	//extract all the info into a task
	        	//get date
	        	String dateInput = date.getText(); 
	        	tempDate = getInputtedDate(dateInput);//problems w deprecated date
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
	        	tempTask = new Task(textInput, tempImp, tempDate);
	        	
	        	System.out.println("A task was made with text:"+ " " + tempTask.getText());
	        	//frame.dispose();
	        	//do i need to make this whole class null after?
	        	
	        	taskMaster.closeEditScreen(true, editingTask, tempTask);
	        }
	    });	
	}
	private Date getInputtedDate(String s){
		if(s.equals("")){
			return null;
		}
		
		String d = s;
		String[] tokens = d.split("/");
		int[] dates= new int[tokens.length];
		for (int i=0; i<tokens.length;i++){
		  dates[i] = Integer.parseInt(tokens[i]);	
		}
		Date tempDate = new Date(dates[2],dates[1],dates[0]); //this is deprecated, is this correct order?
		return tempDate;
	}
	
	public JPanel getJPanel(){ return screenPanel;}
	
}
