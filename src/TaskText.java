package taskMaster;

import javax.swing.JTextArea;

/**
 * Store and display the text for the task.
 */
public class TaskText extends JTextArea{
    // TODO text view object, or a different text display object?
    // TODO ...Or could simply put text storage in TaskInfo, add to a text view from task

    public String text;

    public TaskText(String text){
        this.text = text;
    }

    public TaskText(){
        this("");
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }
}
