import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

public class Item {
	
	private int x, y, dx, radius; // X pos
	private StartingPoint sp;
	private Boolean createNew = false;
	
	public Boolean getCreateNew() {
		return createNew;
	}

	public void setCreateNew(Boolean createNew) {
		this.createNew = createNew;
	}
	
	public Item (int x, int dx, int rad) {
		this.x = x;
		Random r = new Random(); // object used to generate a random number
		this.radius = rad;
		y = r.nextInt(400) + radius;
		this.dx = dx;
		/* X (location of item) and dx values are randomized in the StartingPoint class so the speed for each
		 * item will be different each time it appears, and rad values are passed in from the image size in the
		 * pictures class. When the Item class is called in StartingPoint, the values of this.x, this.radius and 
		 * this.dx become the global variables x, rad and dx, which can be accessed globally.
		 */
		
	}
	
	public void update(StartingPoint sp, Ball b) {
		x += dx;
		this.sp = sp;
		checkForCollision(b);
		if (x < 0 - radius){
			createNew = true;
			/* This boolean is needed to create a new item once it is completely off the screen to the left, as
			 * x refers to the item's x-position and 0 - radius is the width of the screen minus the item's radius.
			 */
		}
	}

	private void checkForCollision(Ball ball) {
		int ballX = ball.getX();
		int ballY = ball.getY();
		int ballR = ball.getRadius();
		
		int aSide = x - ballX;
		int bSide = y - ballY;
		int collide = radius + ballR;
		double cSide = Math.sqrt((double)(aSide*aSide) + (double)(bSide*bSide));
		/* collision detection using pythagorean theroem: a collision occurs if the square root of
		 * the aSide squared (Ball minus item's x-coordinates) plus the bSide squared (Ball minus item'
		 * y-coordinates) which is defined as cSide is less than collide. Collide is defined as the the
		 * distance between the two balls or the sum of their radii.
		 */
		
		if(cSide < collide) {
			performAction(ball);
			createNew = true;
		}
		/* If a collision occurs then perform action on the object ball, Mario. This is specific to each
		 * item in the game.
		 */
	}

	public void performAction(Ball ball) {
	}
	/* Method used to perform the action from a specific item. Another performAction method is in each item
	 * class and has its own specific arguements in which to do to the ball, the score, the energy percentage,
	 * etc., but this one is needed
	 * the ball 
	 */
	

	public void paint(Graphics g) {
		g.fillOval(x - radius, y - radius, radius*2, radius*2);
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getRadius() {
		return radius;
	}
}
