package taskMaster;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Date;

import javax.swing.*;



public class EditScreen extends JPanel {
	
	private String dateInput;
	private String textInput;
	
	private JFrame frame; 
	private JPanel topToolbar;
	private JLabel category;
	private JComboBox categories;
	private JTextField date;
	
	//create textfield
	private JPanel textPanel;
	//create text field for inputting search information
	private JTextField newText;
	
	private JPanel buttonPanel;
	private JComboBox priority;
	private JButton cancel;
	private JButton save;
	
	private String[] catS= {"Personal","Work","Other"};
	private String[] priorityS = {"Low","Normal","High"};
	

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
		
		textPanel.setBackground(Color.darkGray);
		newText = new JTextField(25);
		textPanel.add(newText);
		
		buttonPanel.setLayout(new BoxLayout(topToolbar, BoxLayout.X_AXIS));
		priority = new JComboBox(priorityS);
		cancel = new JButton("Cancel");
		save = new JButton("Save");
		buttonPanel.add(priority);
		buttonPanel.add(cancel);
		buttonPanel.add(save);
		
	
		//set layout and size of frame

		frame.getContentPane().add(BorderLayout.NORTH,topToolbar);
		frame.getContentPane().add(BorderLayout.CENTER,textPanel);
		frame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	        	Date tempDate;
	        	dateInput = date.getText(); //the below method is not finished, doe snot parse from string
	        	tempDate = getInputtedDate(dateInput);
	        	textInput = newText.getText();
	        	//importanceLevel
	        	
	        	//category

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
		Date tempDate = new Date(dates[2],dates[1],dates[0]); //this is deprecated, fix
		return tempDate;
	}
	
}