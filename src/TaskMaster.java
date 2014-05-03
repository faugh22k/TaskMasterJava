package taskMaster;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

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
	
	JScrollPane scroll;
	
	JLabel manage;
	FlatButton create;
	FlatButton delete;
	FlatButton edit;
	
	JComboBox choicesRemove;
	JFrame frameRemovePrompt;
	JFrame frameCreatePrompt;
	JTextField createInput;
	 
	JPanel tasksPanel;
	 
	FlatButton addCategory;
	FlatButton deleteCategory;
	
	TaskGrid taskGrid;
	JFrame frame;
	
	JPanel editScreen;
	
	ArrayList<Task> selected;
	
	Color green = new Color(191, 227, 74);
	Color yellow = new Color(255, 255, 160);
	Color blue = new Color(99, 195, 210);
	
	private String[] catS= {"Personal","Work","Other","All Tasks"};
	
	private ArrayList<String> categoryNames;
	
	public TaskMaster(){
		selected = new ArrayList<Task>();
		categoryNames = new ArrayList<String>(6);
		categoryNames.add("All Tasks");
		categoryNames.add("Personal");
		categoryNames.add("Work");
		categoryNames.add("Other");
		
	}
	
	public void go(){
		taskGrid = new TaskGrid(this, categoryNames);  
		
		/*taskGrid.createNewTask("Hello world!", ImportanceLevel.low, "");   
		taskGrid.createNewTask("Second hi hi  ", ImportanceLevel.normal, "05/01");   
		taskGrid.createNewTask("Third ", ImportanceLevel.high, "05/02");
		taskGrid.createNewTask("Fourth ", ImportanceLevel.low, "05/03");
		taskGrid.createNewTask("Fifth :) ", ImportanceLevel.normal, "05/04"); // commented out from here
		taskGrid.createNewTask("Sixth ", ImportanceLevel.high, "05/09");
		taskGrid.createNewTask("Seventh !", ImportanceLevel.low, "05/08");
		taskGrid.createNewTask("Eighth  ", ImportanceLevel.normal, "05/06"); 
		taskGrid.createNewTask("Ninth :)  ", ImportanceLevel.high, "05/07"); 
		taskGrid.createNewTask("Hello world!", ImportanceLevel.low, ""); 
		taskGrid.createNewTask("Hello world!", ImportanceLevel.low, "");   
		taskGrid.createNewTask("Second hi hi  ", ImportanceLevel.normal, "05/01");   
		taskGrid.createNewTask("Third ", ImportanceLevel.high, "05/02");
		taskGrid.createNewTask("Fourth ", ImportanceLevel.low, "05/03");
		taskGrid.createNewTask("Fifth :) ", ImportanceLevel.normal, "05/04"); // commented out from here
		taskGrid.createNewTask("Sixth ", ImportanceLevel.high, "05/09");
		taskGrid.createNewTask("Seventh !", ImportanceLevel.low, "05/08");
		taskGrid.createNewTask("Eighth  ", ImportanceLevel.normal, "05/06"); 
		taskGrid.createNewTask("Ninth :)  ", ImportanceLevel.high, "05/07"); 
		taskGrid.createNewTask("Hello world!", ImportanceLevel.low, ""); */
		 
		 
		
		frame = new JFrame();
		
		toolbar = new JPanel();
		toolbar.setMaximumSize(new Dimension(50, 800)); 
		toolbar.setBackground(Color.darkGray); 
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
		  
		sortBy = new JLabel ("Sort by:");
		sortBy.setForeground(Color.WHITE);
		defaultS = new FlatButton(blue, "Default");
		dueDate = new FlatButton(blue, "Due Date");
		priority = new FlatButton(blue, "Priority");
		
		category = new JLabel("Category");
		category.setForeground(Color.WHITE);
		//categories = new JComboBox<String>(catS);  
		categories = new JComboBox<String>();
		//categories.addItem("All Tasks");  
		for(String name : categoryNames){
			categories.addItem(name);
		}
		categories.setPrototypeDisplayValue("XXXXXXXXX");  
		categories.setSelectedIndex(0);
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
		manageSort.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
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
		 
		
		scroll = new JScrollPane(taskGrid);
		scroll.setBackground(Color.DARK_GRAY);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JScrollBar bar = new JScrollBar(); 
		//bar.setBackground(blue);
		bar.setOpaque(false); 
		// **bar.setUI(new FlatScrollBar(blue));
		// **scroll.setVerticalScrollBar(bar);
		//scroll.setPreferredSize(new Dimension(900, 500));
		
		//set layout and size of frame 
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(BorderLayout.WEST,toolbar); 
		//frame.getContentPane().add(BorderLayout.CENTER,taskGrid);  
		frame.getContentPane().add(BorderLayout.CENTER,scroll);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,538);
		frame.setVisible(true);
		frame.setFocusable(true); 
		frame.setLocationRelativeTo(null); 
					 
	}
	
	public void initListeners(){
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
		create.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {        
	        	if (editScreen == null) {
	        		openEditScreen(null);
	        	}
	        }
	    });

		edit.addActionListener(new ActionListener() 
	    {           	
	        public void actionPerformed(ActionEvent e) 
	        {       
	        //edit selected task	
	        	if(selected.size() > 0 && editScreen == null){
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

				String selected = (String) categories.getSelectedItem();
				
				if(!selected.equals("All Tasks")){//personal
					taskGrid.setDisplayState(DisplayState.category, (String) categories.getSelectedItem()); 
				} else{
					taskGrid.setDisplayState(DisplayState.all, (String) categories.getSelectedItem()); 
				} 
			}
		});

		addCategory.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				frameCreatePrompt = new JFrame();
				JPanel add = new JPanel();
				JPanel addComponents = new JPanel();
				JPanel addButtons = new JPanel();
				addComponents.setLayout(new BoxLayout(addComponents, BoxLayout.Y_AXIS)); 
				add.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));  
				addComponents.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 
				
				Color background = new Color(32,32,32);

				createInput = new JTextField();
				FlatButton okay = new FlatButton(green, "okay");
				FlatButton cancel = new FlatButton(green, "cancel");
				JLabel prompt = new JLabel("What category would you like to create?");
				prompt.setForeground(Color.WHITE);
				
				addButtons.add(cancel);
				addButtons.add(okay);
				addComponents.add(createInput); 
				addComponents.add(addButtons);
				add.add(prompt);
				add.add(addComponents);
				
				add.setBackground(background);
				addButtons.setOpaque(false);
				addComponents.setOpaque(false);
				
				frameCreatePrompt.getContentPane().add(add); 
				frameCreatePrompt.setSize(330,150); 
				frameCreatePrompt.setVisible(true);
				frameCreatePrompt.setFocusable(true); 
				frameCreatePrompt.setLocationRelativeTo(frame);  
				
				okay.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e){
						String toAdd = createInput.getText(); 
						if(!toAdd.equals("")){
							categoryNames.add(toAdd);
							taskGrid.addCategory(toAdd);
							categories.addItem(toAdd); 
						}
						
						frameCreatePrompt.dispatchEvent(new WindowEvent(frameCreatePrompt, WindowEvent.WINDOW_CLOSING));
					}
				});

				cancel.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e){ 
						frameCreatePrompt.dispatchEvent(new WindowEvent(frameCreatePrompt, WindowEvent.WINDOW_CLOSING));
					}
				});
				
			}
		});

		deleteCategory.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				frameRemovePrompt = new JFrame(); 
				JPanel remove = new JPanel();
				JPanel removeComponents = new JPanel();
				JPanel removeButtons = new JPanel();
				removeComponents.setLayout(new BoxLayout(removeComponents, BoxLayout.Y_AXIS)); 
				remove.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));  
				removeComponents.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));  

				Color background = new Color(32,32,32);

				FlatButton okay = new FlatButton(green, "okay");
				FlatButton cancel = new FlatButton(green, "cancel");
				JLabel prompt = new JLabel("What category would you like to remove?");
				prompt.setForeground(Color.WHITE);

				choicesRemove = new JComboBox<String>();
				for(String name : categoryNames){
					if(!name.equals("All Tasks")){
						choicesRemove.addItem(name);
					}
				}  
  
				removeButtons.add(cancel); 
				removeButtons.add(okay); 
				removeComponents.add(choicesRemove); 
				removeComponents.add(removeButtons);
				remove.add(prompt);
				remove.add(removeComponents);

				remove.setBackground(background);
				removeButtons.setOpaque(false);
				removeComponents.setOpaque(false);

				frameRemovePrompt.getContentPane().add(remove);

				frameRemovePrompt.setSize(330,150);
				frameRemovePrompt.setVisible(true);
				frameRemovePrompt.setFocusable(true); 
				frameRemovePrompt.setLocationRelativeTo(frame); 
				//remove.add()

				okay.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e){
						String toRemove = (String) choicesRemove.getSelectedItem();
						categoryNames.remove(toRemove);
						taskGrid.removeCategory(toRemove);
						if(categories.getSelectedItem().equals(toRemove)){
							categories.setSelectedItem("All Tasks");
						} 
						categories.removeItem(toRemove);
						 
						frameRemovePrompt.dispatchEvent(new WindowEvent(frameRemovePrompt, WindowEvent.WINDOW_CLOSING));
					}
				});

				cancel.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e){ 
						frameRemovePrompt.dispatchEvent(new WindowEvent(frameRemovePrompt, WindowEvent.WINDOW_CLOSING));
					}
				});

			}
		});
		
		taskGrid.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					deselectAll();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
	}
	
	public void openEditScreen(Task toEdit){
		EditScreen ed = new EditScreen(this, toEdit, categoryNames);
    	editScreen = ed.getJPanel();
    	//frame.remove(taskGrid);
    	frame.remove(scroll);
    	frame.getContentPane().add(BorderLayout.CENTER,editScreen);
    	frame.repaint();
    	frame.validate();
	}
	
	public void closeEditScreen(boolean saved, Task original, Task newTask){
		if(original != null){ 
				selected.remove(original);
				original.changeSelection();
			}
		
		if(!saved){
			switchEditToGrid(); 
			return;
		}
		
		if(original != null){ 
			taskGrid.upDateTask(original, newTask);
		} else {
			taskGrid.addNewTask(newTask);
		}
		
		//newTask.changeSelection();
		//selected.add(newTask);
		
		switchEditToGrid();
	}
	
	private void switchEditToGrid(){
		if(editScreen != null){
			frame.remove(editScreen);
			editScreen = null;
		}
		
		//frame.getContentPane().add(BorderLayout.CENTER, taskGrid);
		frame.getContentPane().add(BorderLayout.CENTER, scroll);
		frame.validate();
		frame.repaint();
	}
	
	public void addSelection(Task selected){
		this.selected.add(selected);
	}
	
	public void removeSelection(Task remove){
		selected.remove(remove);
	}
	
	public void deselectAll(){
		while(selected.size() != 0){
			Task current = selected.remove(selected.size()-1);
			current.changeSelection();
		}
	}
	
	public static void main(String[] args){
		TaskMaster taskMaster = new TaskMaster();
		taskMaster.go();
	}
}


