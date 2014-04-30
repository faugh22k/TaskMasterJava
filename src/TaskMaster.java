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
		taskGrid.createNewTask("Second Task, task, task, \ntask, task!", ImportanceLevel.normal, new Date(2014, 5, 4));
		taskGrid.createNewTask("Third Task, task, task task, task!", ImportanceLevel.normal, new Date(2014, 5, 4));
		taskGrid.createNewTask("Fourth task, yay for tasks!", ImportanceLevel.normal, new Date(2014, 5, 4));
		taskGrid.createNewTask("Fifth task :) ", ImportanceLevel.normal, new Date(2014, 5, 4));
		taskGrid.createNewTask("Sixth task \nhello \nthere \neveryone", ImportanceLevel.normal, new Date(2014, 5, 4));
		taskGrid.createNewTask("Seventh Task, task, task, \ntask, task!", ImportanceLevel.normal, new Date(2014, 5, 4));
		taskGrid.createNewTask("Eigth Task, task, task, \ntask, task! ", ImportanceLevel.normal, new Date(2014, 5, 4));
		
		
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
		
		//set layout and size of frame

		frame.getContentPane().add(BorderLayout.WEST,toolbar);
		frame.getContentPane().add(BorderLayout.CENTER,taskGrid);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,500);
		frame.setVisible(true);
					
	}
	
	public void initButtons(){
		defaultS.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	            //Order by default queue; needs flush
	        	taskGrid.changeSort(SortState.relaxedDate); 
	        }
	    });
		dueDate.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	            //Order by strictdatequeue
	        	taskGrid.changeSort(SortState.strictDate);  	 	
	        }
	    });
		priority.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
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


