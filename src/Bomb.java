import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

public class Bomb extends Item{
	

	Image bomb;
	URL url;
	int dyDown = 10; // Speed/delta-y down.
	
	public Bomb(int x, int dx, int rad) {
		super(x, dx, rad);
		// x, dx, and rad (radius) 
		this.bomb = Pictures.bomb;
		// The bomb object in this class is the bomb image found in class Pictures.
		// Refer to lines 15-18 in class Banana for details on super().
	}

	public void paint(Graphics g) { // bomb paint method
		g.drawImage(bomb, getX()-getRadius(), getY()-getRadius(), Pictures.sp);
	}

	public void performAction(Ball ball) { // Item specific action
		if (ball.getGameDy() > 0) {
			ball.setGameDy(ball.getGameDy()-dyDown); // Reduces gameDy by dyDown if gameDy is less than 0
		} 
		else if (ball.getGameDy() < 0) {
			ball.setGameDy(ball.getGameDy()+dyDown); // Increases gameDy by dyDown if gameDy is greater than 0
		}
	}
}
