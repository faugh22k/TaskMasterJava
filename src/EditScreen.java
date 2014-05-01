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
	
	private JFrame frame;
	private JPanel topToolbar;
	private JLabel category;
	private JComboBox categories;
	private JTextField date;
	
	
	private JPanel textPanel;
	//create text field for inputting search information
	private JTextField newText;
	
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
	
	private String[] catS= {"Personal","Work","Other"};
	private String[] priorityS = {"Low","Normal","High"};
	
	EditScreen(){
		go();
		initButtons();
	}
	public void go(){
		frame = new JFrame();
		topToolbar = new JPanel();
		topToolbar.setBackground(Color.darkGray);
		topToolbar.setLayout(new BoxLayout(topToolbar, BoxLayout.X_AXIS));
		category = new JLabel("Choose type:");
		categories = new JComboBox(catS);
		date = new JTextField(10);
		topToolbar.add(category);
		topToolbar.add(categories);
		topToolbar.add(date);
		
		
		textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		System.out.println("*******************text panel = " + textPanel);
		textPanel.setBackground(Color.darkGray);
		newText = new JTextField(25);
		textPanel.add(newText);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		priority = new JComboBox(priorityS);
		cancel = new JButton("Cancel");
		save = new JButton("Save");
		bottomPanel.add(priority);
		bottomPanel.add(cancel);
		bottomPanel.add(save);
		
	
		//set layout and size of frame

		frame.getContentPane().add(BorderLayout.NORTH,topToolbar);
		frame.getContentPane().add(BorderLayout.CENTER,textPanel);
		frame.getContentPane().add(BorderLayout.SOUTH,bottomPanel);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setVisible(true);
					
	}
	
	private void initButtons(){
		cancel.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	        	//throw away the task being made
	        	frame.setVisible(false); 
	        	frame.dispose();
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
	        }
	    });	
	}
	private Date getInputtedDate(String s){
		String d = s;
		String[] tokens = d.split("/");
		int[] dates= new int[tokens.length];
		for (int i=0; i<tokens.length;i++){
		  dates[i] = Integer.parseInt(tokens[i]);	
		}
		Date tempDate = new Date(dates[2],dates[1],dates[0]); //this is deprecated, is this correct order?
		return tempDate;
	}
	
	public JFrame getJFrame(){ return frame;}
	
}
