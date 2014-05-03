package taskMaster;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class FlatScrollBar extends BasicScrollBarUI{
	
	private Color background; 
	 

	@Override
	public void paint(Graphics g, JComponent c){  
		
		Rectangle track = getTrackBounds();
		Rectangle thumb = getTrackBounds(); 
		
		int totalWidth = track.width;
		int totalHeight = track.height;
		int barWidth = thumb.width;
		int barHeight = thumb.height;
		
		int tX = track.x;
		int tY = track.y;
		int bX = thumb.x;
		int bY = thumb.y;
		
		g.setColor(background);
		g.fillRect(tX, tY, totalWidth, totalHeight);
		
		g.setColor(background.brighter().brighter());
		g.fillRect(bX, bY, barWidth, barHeight);
	}
	
	private ImageIcon downArrow, upArrow, leftArrow, rightArrow;

    public FlatScrollBar(Color background){
    	this.background = background;
    	
        try {
            upArrow = new ImageIcon(new java.net.URL("http://icons.iconarchive.com/icons/oxygen-icons.org/oxygen/16/Actions-arrow-up-icon.png"));
            downArrow = new ImageIcon(new java.net.URL("http://icons.iconarchive.com/icons/oxygen-icons.org/oxygen/16/Actions-arrow-down-icon.png"));
            rightArrow = new ImageIcon(new java.net.URL("http://icons.iconarchive.com/icons/oxygen-icons.org/oxygen/16/Actions-arrow-right-icon.png"));
            leftArrow = new ImageIcon(new java.net.URL("http://icons.iconarchive.com/icons/oxygen-icons.org/oxygen/16/Actions-arrow-left-icon.png"));
        } catch (java.net.MalformedURLException ex) {
            ex.printStackTrace();
        }        
    }

   /* @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton decreaseButton = new JButton(getAppropriateIcon(orientation)){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(22, 22);
            }
        };
        return decreaseButton;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton increaseButton = new JButton(getAppropriateIcon(orientation)){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(22, 22);
            }
        };
        return increaseButton;
    }

    private ImageIcon getAppropriateIcon(int orientation){
        switch(orientation){
            case SwingConstants.SOUTH: return downArrow;
            case SwingConstants.NORTH: return upArrow;
            case SwingConstants.EAST: return rightArrow;
                default: return leftArrow;
        }
    }*/
}
