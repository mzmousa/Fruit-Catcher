import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

public class Apple extends Item {
	
	int gravBonus = 2;
	Image apple;
	URL url;

	public Apple (int x, int dx, int rad) {
		super(x, dx, rad);
		this.apple=Pictures.apple;
		// The apple object in this class is the apple image found in class Pictures
	}

	public void paint(Graphics g) { // paint method for apple
		g.drawImage(apple, getX()-getRadius(), getY()-getRadius(), Pictures.sp); 
	}

	public void performAction(Ball ball) { // Specific item action
		if (ball.getGravity() > gravBonus){ // Less gravity added to if gravity is more than gravBonus
			ball.setGravity(ball.getGravity()-gravBonus);
		}
		if (ball.getGravity() < gravBonus){ // More gravity added to Mario if current gravity is less than gravBonus
			ball.setGravity(gravBonus);
		}
	}
}

