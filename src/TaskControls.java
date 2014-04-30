package taskMaster;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;   

/**
 * To hold all the buttons, display in linear view (horizontal)
 */
public class TaskControls extends JComponent{

	Color background; 

    public TaskControls() { 
        // TODO set horizontal
    	background = new Color(79, 129, 0);  
    	this.setBackground(background); 
    } 
    
    public void paint(Graphics g){
    	g.setColor(background);
    	g.fillRect(0, 0, 200, 200);
    }
}
