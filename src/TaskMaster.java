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
	FlatButton defaultS;
	FlatButton dueDate;
	FlatButton priority;
	JLabel category;
	JComboBox<String> categories;
	
	JLabel manage;
	FlatButton create;
	FlatButton delete;
	FlatButton edit;
	 
	JPanel tasksPanel;
	 
	FlatButton addCategory;
	FlatButton deleteCategory;
	
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
		
		taskGrid.createNewTask("Hello world!", ImportanceLevel.low, "");   
		taskGrid.createNewTask("Second hi hi  ", ImportanceLevel.normal, "05/01");   
		taskGrid.createNewTask("Third ", ImportanceLevel.high, "05/02");
		taskGrid.createNewTask("Fourth ", ImportanceLevel.low, "05/03");
		taskGrid.createNewTask("Fifth :) ", ImportanceLevel.normal, "05/04"); // commented out from here
		taskGrid.createNewTask("Sixth ", ImportanceLevel.high, "05/09");
		taskGrid.createNewTask("Seventh !", ImportanceLevel.low, "05/08");
		taskGrid.createNewTask("Eighth  ", ImportanceLevel.normal, "05/06"); 
		taskGrid.createNewTask("Ninth :)  ", ImportanceLevel.high, "05/07"); 
		 
		Color green = new Color(191, 227, 74);
		Color yellow = new Color(255, 255, 160);
		Color blue = new Color(99, 195, 210);
		
		frame = new JFrame();
		
		toolbar = new JPanel();
		toolbar.setMaximumSize(new Dimension(50, 800));
		//toolbar.setMinimumSize(new Dimension(50, 200));
		//toolbar.setBackground(Color.darkGray);
		toolbar.setBackground(Color.darkGray);
		//toolbar.setBackground(new Color(253, 250, 199));
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
		//need to pin it to the left?
		
		sortBy = new JLabel ("Sort by:");
		sortBy.setForeground(Color.WHITE);
		defaultS = new FlatButton(blue, "Default");
		dueDate = new FlatButton(blue, "Due Date");
		priority = new FlatButton(blue, "Priority");
		
		category = new JLabel("Category");
		category.setForeground(Color.WHITE);
		categories = new JComboBox<String>(catS); //currently empty
		categories.setPrototypeDisplayValue("XXXXXXXXX"); 
		//categories.setPreferredSize(new Dimension(50, 20));
		categories.setSelectedIndex(3);
		addCategory = new FlatButton(blue, "Add");
		deleteCategory = new FlatButton(blue, "Remove"); 
		

		
		manage = new JLabel("Manage Tasks");
		manage.setForeground(Color.WHITE);
		create = new FlatButton(blue,"Create");
		edit = new FlatButton(blue,"Edit");
		delete = new FlatButton(blue,"Delete"); 
		
		JPanel manageTasks = new JPanel();
		JPanel manageTButtons = new JPanel(); 
		manageTButtons.setLayout(new BoxLayout(manageTButtons, BoxLayout.Y_AXIS));
		manageTButtons.setBorder(BorderFactory.createEmptyBorder(8,0,0,0));    
		manageTButtons.add(edit);
		manageTButtons.add(create);
		manageTButtons.add(delete);
		manageTButtons.setOpaque(false);
		manageTasks.setLayout(new BorderLayout());
		manageTasks.add(manage, BorderLayout.NORTH);
		manageTasks.add(manageTButtons, BorderLayout.CENTER);
		
		JPanel manageSort = new JPanel();
		JPanel manageSButtons = new JPanel(); 
		manageSButtons.setBorder(BorderFactory.createEmptyBorder(8,0,0,0));
		manageSButtons.setLayout(new BoxLayout(manageSButtons, BoxLayout.Y_AXIS));
		manageSButtons.add(defaultS);
		manageSButtons.add(dueDate);
		manageSButtons.add(priority);
		manageSort.setLayout(new BorderLayout());
		manageSort.add(sortBy, BorderLayout.NORTH);
		manageSort.add(manageSButtons, BorderLayout.CENTER); 
		manageSButtons.setOpaque(false);
		
		JPanel manageCategory = new JPanel(); 
		
		
		JPanel manageCButtons = new JPanel();
		
		manageCButtons.setLayout(new BoxLayout(manageCButtons, BoxLayout.Y_AXIS)); 
		manageCButtons.setBorder(BorderFactory.createEmptyBorder(2,0,0,0));  
		manageCButtons.add(addCategory);
		manageCButtons.add(deleteCategory);
		manageCButtons.setOpaque(false);
		
		JPanel manageCComponents = new JPanel();  
		manageCComponents.setLayout(new BorderLayout());
		manageCComponents.setBorder(BorderFactory.createEmptyBorder(8,0,0,0));  
		manageCComponents.add(categories, BorderLayout.NORTH);
		manageCComponents.add(manageCButtons, BorderLayout.CENTER);
		manageCComponents.setOpaque(false);
		
		manageCategory.setLayout(new BorderLayout());
		manageCategory.add(category, BorderLayout.NORTH);
		manageCategory.add(manageCComponents, BorderLayout.CENTER);
		
		manageCategory.setOpaque(false);
		manageSort.setOpaque(false);
		manageTasks.setOpaque(false);
		 
		toolbar.setBorder(BorderFactory.createEmptyBorder(5,20,5,5));    
		toolbar.add(manageSort);
		toolbar.add(manageCategory);
		toolbar.add(manageTasks);
		 
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


