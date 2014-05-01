package taskMaster;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
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
	JButton edit;
	 
	JPanel tasksPanel;
	 
	
	TaskGrid taskGrid;
	JFrame frame;
	
	JPanel editScreen;
	
	ArrayList<Task> selected;
	
	private String[] catS= {"Personal","Work","Other","All Tasks"};
	public TaskMaster(){
		selected = new ArrayList<Task>();
	}
	
	public void go(){
		taskGrid = new TaskGrid(this);
		/*taskGrid.createNewTask("Hello world!", ImportanceLevel.low, new Date(2014, 5, 12));
		taskGrid.createNewTask("Second hi hi  ", ImportanceLevel.normal, new Date(2014, 5, 11));
		taskGrid.createNewTask("Third ", ImportanceLevel.high, new Date(2014, 5, 10));
		taskGrid.createNewTask("Fourth ", ImportanceLevel.low, new Date(2014, 5, 9));
		taskGrid.createNewTask("Fifth :) ", ImportanceLevel.normal, new Date(2014, 5, 8));
		taskGrid.createNewTask("Sixth ", ImportanceLevel.high, new Date(2014, 5, 7));
		taskGrid.createNewTask("Seventh !", ImportanceLevel.low, new Date(2014, 5, 6));
		taskGrid.createNewTask("Eighth  ", ImportanceLevel.normal, new Date(2014, 5, 5));
		taskGrid.createNewTask("Ninth :)  ", ImportanceLevel.high, new Date(2014, 5, 4));*/
		
		taskGrid.createNewTask("Hello world!", ImportanceLevel.low, "05/01"); 
		taskGrid.createNewTask("Second hi hi  ", ImportanceLevel.normal, "05/01");   
		taskGrid.createNewTask("Third ", ImportanceLevel.high, "05/02");
		taskGrid.createNewTask("Fourth ", ImportanceLevel.low, "05/03");
		taskGrid.createNewTask("Fifth :) ", ImportanceLevel.normal, "05/04"); // commented out from here
		taskGrid.createNewTask("Sixth ", ImportanceLevel.high, "05/09");
		taskGrid.createNewTask("Seventh !", ImportanceLevel.low, "05/08");
		taskGrid.createNewTask("Eighth  ", ImportanceLevel.normal, "05/06"); 
		taskGrid.createNewTask("Ninth :)  ", ImportanceLevel.high, "05/07"); 
		 
		
		
		frame = new JFrame();
		
		toolbar = new JPanel();
		toolbar.setBackground(Color.darkGray);
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
		//need to pin it to the left?
		
		sortBy = new JLabel ("Sort by:");
		defaultS = new JButton("Default");
		dueDate = new JButton("Due Date");
		priority = new JButton("Priority");
		category = new JLabel("Category");
		categories = new JComboBox(catS); //currently empty
		categories.setSelectedIndex(3);

		
		manage = new JLabel("Manage Tasks");
		create = new JButton("Create");
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		
		
		toolbar.add(sortBy);
		toolbar.add(defaultS);
		toolbar.add(dueDate);
		toolbar.add(priority);
		toolbar.add(category);
		toolbar.add(categories);
		toolbar.add(manage);
		toolbar.add(create);
		toolbar.add(edit);
		toolbar.add(delete);
		
		
		initListeners();
		//set layout and size of frame

		frame.getContentPane().add(BorderLayout.WEST,toolbar);
		frame.getContentPane().add(BorderLayout.CENTER,taskGrid);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,500);
		frame.setVisible(true);
		frame.setFocusable(true); 
		frame.setLocationRelativeTo(null); 
					 
	}
	
	public void initListeners(){
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
		create.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {        
	        	openEditScreen(null);
	        }
	    });

		edit.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	        //edit selected task	
	        	if(selected.size() > 0){
	        		openEditScreen(selected.get(selected.size()-1));
	        	}
	        }
	    });
		delete.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	        //delete a selected task
	        	for(int i = 0; i < selected.size(); i++){
	        		Task current = selected.get(i);
	        		taskGrid.removeTask(current, i == selected.size()-1);
	        	}
	        	//frame.repaint();
	        }
	    });
		categories.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){

				int selected = categories.getSelectedIndex();
				if(selected == 0){//personal
					taskGrid.setDisplayState(DisplayState.category, catS[0]); 
				} else if (selected == 1){//work
				    taskGrid.setDisplayState(DisplayState.category, catS[1]); 
				} else if(selected == 1){//other
					taskGrid.setDisplayState(DisplayState.category, catS[2]); 
				}else{
					taskGrid.setDisplayState(DisplayState.all, catS[3]); 
				}

				/*int selected = categories.getSelectedIndex();
				if(selected == 0){//personal
					taskGrid.setDisplayState(catS[0]); 
				} else if (selected == 1){//work
				    taskGrid.setDisplayState(catS[1]); 
				} else if(selected == 1){//other
					taskGrid.setDisplayState(catS[2]); 
				}else{
					taskGrid.setDisplayState(catS[3]); 
				}*/ 
			
			}
		});
		
		
	}
	
	public void openEditScreen(Task toEdit){
		EditScreen ed = new EditScreen(this, toEdit);
    	editScreen = ed.getJPanel();
    	frame.remove(taskGrid);
    	frame.getContentPane().add(BorderLayout.CENTER,editScreen);
    	frame.repaint();
    	frame.validate();
	}
	
	public void closeEditScreen(boolean saved, Task original, Task newTask){
		if(original != null){
				System.out.println("deselecting the task that was selected");
				selected.remove(original);
				original.changeSelection();
			}
		
		if(!saved){
			switchEditToGrid(); 
			return;
		}
		
		if(original != null){
			System.out.println("updating the task!");
			taskGrid.upDateTask(original, newTask);
		} else {
			taskGrid.addNewTask(newTask);
		}
		
		newTask.changeSelection();
		selected.add(newTask);
		
		switchEditToGrid();
	}
	
	private void switchEditToGrid(){
		if(editScreen != null){
			frame.remove(editScreen);
			editScreen = null;
		}
		
		frame.getContentPane().add(BorderLayout.CENTER, taskGrid);
		frame.validate();
		frame.repaint();
	}
	
	public void addSelection(Task selected){
		this.selected.add(selected);
	}
	
	public void removeSelection(Task remove){
		selected.remove(remove);
	}
	
	public static void main(String[] args){
		TaskMaster taskMaster = new TaskMaster();
		taskMaster.go();
	}
}


