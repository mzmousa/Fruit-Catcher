import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

public class Grapes extends Item{
	
	Image grapes;
	URL url;
	private StartingPoint appletInfo; // Private instance of the applet class within this class that can only be accessed here
	
	
	public Grapes(int x, int dx, int rad,  StartingPoint appletInfo) {
		super(x, dx, rad);
		this.appletInfo = appletInfo;
		this.grapes = Pictures.grapes;
		// The grapes object in this class is the grapes image found in class Pictures.
		// Refer to lines 15-18 in class Banana for details on super().
	}
	
	public void performAction(Ball ball) { // Specific item action - increase score by 5 to 15
		Random r = new Random();
		appletInfo.setScore(appletInfo.getScore() + 5 + r.nextInt(10));
	}
	
	public void paint(Graphics g) { // paint method for grapes
		g.drawImage(grapes, getX()-getRadius(), getY()-getRadius(), Pictures.sp);
	}
}
