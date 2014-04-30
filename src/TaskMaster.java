package taskMaster;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
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
	
	public void go(){
		
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,500);
		frame.setVisible(true);
					
	}
	
	//Move this up when done--tasks panel
	JPanel tasksPanel;


}
