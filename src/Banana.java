import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

public class Banana extends Item {
	
	Image banana;
	URL url;
	int dyUp = 5;
	public Banana(int x, int dx, int rad) {
		super(x, dx, rad);
		/* Super refers to the superclass in which (x, dx, rad) are found - the Item class, not
		 * the Banana class. The parameters x, dx, and rad determine the x location on the applet,
		 * the delta x of the item (speed) on the applet, and its radius. These values 
		 */
		this.banana = Pictures.banana;
		// The banana object in this class is the banana image found in class Pictures
	}
	
	public void paint(Graphics g) { // paint method for banana
		g.drawImage(banana, getX()-getRadius(), getY()-getRadius(), Pictures.sp); 
	}
	
	public void performAction(Ball ball) { // Specific item action
		if (ball.getGameDy() > 0) {
			ball.setGameDy(ball.getGameDy()+dyUp); // more Y-speed if GameDy is greater than 0
		} 
		else if (ball.getGameDy() < 0) {
			ball.setGameDy(ball.getGameDy()-dyUp); // less Y-speed if GameDy is less than 0
		}
	}
}
