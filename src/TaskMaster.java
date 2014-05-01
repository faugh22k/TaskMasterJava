package taskMaster;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Date;

import javax.swing.JComponent.*;

public class TaskMaster {
	
	//create side toolbar
	JPanel toolbar;
	
	JLabel sortBy;
	JButton defaultS;
	JButton dueDate;
	JButton priority;
	JLabel category;
	JComboBox categories;
	
	JLabel manage;
	JButton create;
	JButton delete;
	 
	JPanel tasksPanel;
	
	TaskGrid taskGrid;
	
	public void go(){
		taskGrid = new TaskGrid();
		taskGrid.createNewTask("Hello world!", ImportanceLevel.normal, new Date(2014, 5, 3));
		taskGrid.createNewTask("Second hi hi  ", ImportanceLevel.high, new Date(2014, 5, 4));
		taskGrid.createNewTask("Third ", ImportanceLevel.low, new Date(2014, 5, 4));
		taskGrid.createNewTask("Fourth ", ImportanceLevel.high, new Date(2014, 5, 4));
		taskGrid.createNewTask("Fifth :) ", ImportanceLevel.normal, new Date(2014, 5, 4));
		taskGrid.createNewTask("Sixth ", ImportanceLevel.low, new Date(2014, 5, 4));
		taskGrid.createNewTask("Seventh !", ImportanceLevel.normal, new Date(2014, 5, 4));
		taskGrid.createNewTask("Eighth  ", ImportanceLevel.normal, new Date(2014, 5, 4));
		
		
		JFrame frame = new JFrame();
		
		toolbar = new JPanel();
		toolbar.setBackground(Color.darkGray);
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
		//need to pin it to the left?
		
		sortBy = new JLabel ("Sort by:");
		defaultS = new JButton("Default");
		dueDate = new JButton("Due Date");
		priority = new JButton("Priority");
		category = new JLabel("Category");
		categories = new JComboBox(); //currently empty
		
		manage = new JLabel("Manage Tasks");
		create = new JButton("Create");
		delete = new JButton("Delete");
		
		toolbar.add(sortBy);
		toolbar.add(defaultS);
		toolbar.add(dueDate);
		toolbar.add(priority);
		toolbar.add(category);
		toolbar.add(categories);
		toolbar.add(manage);
		toolbar.add(create);
		toolbar.add(delete);
		
		
		initButtons();
		//set layout and size of frame

		frame.getContentPane().add(BorderLayout.WEST,toolbar);
		frame.getContentPane().add(BorderLayout.CENTER,taskGrid);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,500);
		frame.setVisible(true);
		
		frame.setLocationRelativeTo(null);
					
	}
	
	public void initButtons(){
		defaultS.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	        	System.out.println("relaxed date sort");
	            //Order by default queue; needs flush
	        	taskGrid.changeSort(SortState.relaxedDate); 
	        }
	    });
		dueDate.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	        	System.out.println("strict date sort");
	            //Order by strictdatequeue
	        	taskGrid.changeSort(SortState.strictDate);  	 	
	        }
	    });
		priority.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	        	System.out.println("priority sort");
	            //still need to flush current task display
	        	taskGrid.changeSort(SortState.priority);
	        }
	    });
		
		
		
	}
	

	public static void main(String[] args){
		TaskMaster taskMaster = new TaskMaster();
		taskMaster.go();
	}
}


