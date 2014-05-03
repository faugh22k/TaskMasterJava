package taskMaster;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;   
import javax.swing.JPanel;

/**
 * Currently empty of buttons, but exists due to possibility of expansion.
 * To hold all the buttons, display in linear view (horizontal)
 */
public class TaskControls extends JPanel{

	Color background; 

    public TaskControls() {  
    	background = new Color(79, 129, 0);  
    	this.setBackground(background); 
    	this.setSize(new Dimension(200, 50)); 
    	this.setMaximumSize(new Dimension(200, 100));
        this.setMinimumSize(new Dimension(200, 100)); 
    } 
    
    public void paint(Graphics g){
    	g.setColor(background);
    	g.fillRect(0, 0, 200, 200);
    }
}
