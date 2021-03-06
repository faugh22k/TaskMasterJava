package taskMaster;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Store and display the text for the task.
 */
public class TaskText extends JPanel{ 

	JTextArea textArea;
	
    public String text;

    public TaskText(String text){
        this.text = text;
        textArea = new JTextArea(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        
        this.setOpaque(false);   
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));       
        this.add(textArea);
        
    }

    public TaskText(){
        this("");
    }

    public String getText(){
        return textArea.getText();
    }

    public void setText(String text){
        textArea.setText(text); 
    }
}
