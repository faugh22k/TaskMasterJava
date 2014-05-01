package taskMaster;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Comparator;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.*;



public class EditScreen extends JPanel {
	
	String dateInput;
	String textInput;
	
	JPanel topToolbar;
	JLabel category;
	JComboBox categories;
	JTextField date;
	
	//create textfield
	JPanel textPanel;
	//create text field for inputting search information
	JTextField newText;
	
	JPanel buttonPanel;
	JButton flag;
	JButton cancel;
	JButton save;


	public void go(){
		JFrame frame = new JFrame();
		
		topToolbar = new JPanel();
		topToolbar.setBackground(Color.darkGray);
		topToolbar.setLayout(new BoxLayout(topToolbar, BoxLayout.X_AXIS));
		category = new JLabel("Choose type:");
		categories = new JComboBox();
		date = new JTextField(10);
		topToolbar.add(category);
		topToolbar.add(categories);
		topToolbar.add(date);
		
		textPanel.setBackground(Color.darkGray);
		newText = new JTextField(25);
		textPanel.add(newText);
		
		buttonPanel.setLayout(new BoxLayout(topToolbar, BoxLayout.X_AXIS));
		flag = new JButton("Flag");
		cancel = new JButton("Cancel");
		save = new JButton("Save");
		buttonPanel.add(flag);
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
	
}
