package taskMaster;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FlatButton extends JButton{

	Color background;
	Color border;
	Color highlight;
	String title;
	int borderWidth = 9;
	int borderHeight = 6;
	
	Stroke stroke = new BasicStroke(3);
	
	public FlatButton(){
		this(Color.CYAN, "");
	}
	
	public FlatButton(Color color, String text){
		this(color, color.darker(), text);
	}
	
	public FlatButton(Color main, Color border, String text){
		super(text);
		background = main;
		this.border = border;
		highlight = main.brighter();
		title = text;
		setRolloverEnabled(true);
		getModel().addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("state changed!");
				repaint();
				
			}
			
		});
	} 
	
	public void changeColors(Color main, Color outside){
		if(outside == null){
			border = main.darker();
		} else {
			border = outside;
		}
		
		background = main;
		highlight = main.brighter();
	}
	
	public void paint (Graphics g){
		int width = getWidth();
		int height = getHeight();
		
		Graphics2D g2 = (Graphics2D) g;
		 
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Determine how big the message is, in pixels.
		FontMetrics metrics = g.getFontMetrics();
		Rectangle2D bounds = metrics.getStringBounds(title, g);
		int textWidth = (int) bounds.getWidth();
		int textHeight = (int) bounds.getHeight();
				
		// Draw the message centered horizontally and with a fixed margin
		// from the top.
		int textLeft = (width - textWidth) / 2;
		int textTop = (height - textHeight) / 2;
		
		int left = textLeft - borderWidth;
		int rectWidth = textWidth + borderWidth*2;
		int top = textTop - borderHeight;
		int rectHeight = textTop + borderHeight*2;
		
		
		if(getModel().isArmed()){
			g2.setColor(highlight); 
			g2.fillRoundRect(left, top, rectWidth, rectHeight, 15, 15);
			System.out.println("is armed");
		} 
		
		else {
			System.out.println("not armed");
			g2.setColor(background);
			g2.fillRoundRect(left, top, rectWidth, rectHeight, 15, 15);
		}
		
		if(getModel().isRollover()){
			System.out.println("is rollover");
			g2.setStroke(stroke);
			g2.setColor(border);
			g2.drawRoundRect(left, top, rectWidth, rectHeight, 15, 15); 
		}
		
		System.out.println("about to draw string!!! " + title);
		g2.setColor(Color.BLACK);
		g2.drawString(title, textLeft, textTop + 8); 
		
		
	}
	
	public void paintComponent(Graphics g){
		System.out.println("paint component");
		paint(g);
	}
}
