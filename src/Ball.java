import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

public class Ball {

	private double gravity = 20; // Gravity acceleration constant used in acceleration formula
	private double energyloss = 0.8; // After hitting side wall
	private double dt = .2; // Time variable used in acceleration formula
	private int x; // X position of Mario
	private int y; // Y position of Mario
	private double dx = 0; // Delta x or velocity (change in x position) of Mario
	private double dy = 0; // Delta y or velocity (change in y position) of Mario
	private double gameDy = -95; // Game speed - controls speed at which platform objects move
	private int radius = 20; // Radius of the Mario, if he was a circle
	private double agility = .9; // Increase or decrease in x velocity of Mario, a.k.a x acceleration
	private int maxSpeed = 12; // Maximum x speed of Mario
	private boolean gameOver = false; // Game over boolean. Starts as false since game is not over yet
	private int counter = 0; // Int used to control platform distances from one another
	Image Mario; // Mario Image
	URL url; // Folder URL (used to grab images)
	
	public Ball() { // Mario sub-class with no parameters, sets image of Mario
		this.Mario= Pictures.Mario;
	}
	
	 
	public Ball(int xPos, int yPos) { // Mario sub-class with x and y parameters for position on the screen
		x = xPos;
		y = yPos;
	}
	
	public void moveRight() {
		if(dx+agility < maxSpeed){
			dx +=agility;
		}
	}
	// When moveRight (right arrow key) is pressed, add agility to dx to make Mario accelerate right.
	
	public void moveLeft() {
		if(dx+agility > -maxSpeed) {
			dx -=agility;
		}
	}
	// When moveLeft (left arrow key) is pressed, subtract agility to dx to make Mario accelerate left.

	public void update(StartingPoint sp) { // method used to update
		if (dx > 0){
			dx -=.1;
		}
		else if (dx < 0) {
			dx +=.1;
		}
		if (!getGameOver()) {
			if (counter >= 60 ) {
				
				if (gameDy < -130){
					gameDy = gameDy*0.96;
				}
				else if(gameDy < -115) {
					gameDy = gameDy*0.985;
				}
				else {
					gameDy = gameDy*0.995;
				}
				counter  = 0;
			}
		}
		counter++;
		/* While the game is running (game is not over), add 1 to counter. Since the 
		 * game runs once every 17 ms and 1 is added to counter every time, if counter
		 * is equal to 1000 (about 1 second) then the gameDy (character's y-speed) is
		 * multiplied by a decimal (less than 1) to reduce its speed. The greater Mario's
		 * speed already is, the slower it becomes every second (as per the three if
		 * statements. This is also what causes energyPer to go down every second.
		 */
		
		if (x > sp.getWidth() - radius) {
			x = sp.getWidth() - radius;
			dx *= -energyloss;
		}
		 /* if the x position of the Mario is greater than the width
		  * of the applet minus the radius, (when it is touching the right
		  * edge), it will not go past the edge and its speed will be
		  * multiplied by -energyloss, which reverses its direction and 
		  * lowers dx
		  */
		else if (x < 0 + radius) { // energy loss and reversal of x direction for LEFT edge of applet
			x = 0 + radius;
			dx *= -energyloss;
	    }
	    else { 
			x += dx;
		}
		/* If the ball is not touching either edge, x position increases by velocity. This is zero since
		 * agility is added to dx whenever the left or right arrow key booleans are true (leftKey or rightKey),
		 * which then increase dx in correspondance to maxSpeed (moveLeft and moveRight methods). When the keys
		 * are not pressed (when leftKey or rightKey are false), agility does not increase, thus causing dx to 
		 * remain constant  and causing Mario to stop accelerating.
		 */
		
		if(y - 100> sp.getHeight() - radius) {
			gameOver = true; 
		}
		/* If the y-coordinate of Mario is below the applet screen, then game over boolean is true
		 * and the game ends.
		 */
		 
		else {
			dy += gravity*dt; // velocity formula for Mario to increase speed as he reaches the ground
			y += dy*dt + 0.5*gravity*dt*dt; // position formula for mario to accelerate towards the ground
		}
	}
	
	public void paint(Graphics g) { // paint Mario
		g.drawImage(Mario, x-radius, y-radius, Pictures.sp);
	}
	
	public int getX() { // public getter method (can be changed by other classes) for variable x  in this class
		return x;
	}
	
	
	public int getY() { // Public getter method for variable y
		return y;
	}
	
	public void setY(int y) { // Public setter method for variable y
		this.y = y;
	}
	
	public double getDy() { // Public getter method for variable dy in this class
		return dy;
	}
	
	public void setDy(double dy) { // Public setter method for variable
		this.dy = dy;
	}
	
	public double getGravity() { // Public getter method for double gravity 
		return gravity;
	}
	
	public void setGravity(double gravity) { // Public setter method for double gravity
		this.gravity = gravity;
	}
	
	public int getRadius() { // Public getter method for int radius x
		return radius;
	}
	
	public void setRadius(int radius) { // Public setter method for int radius
		this.radius = radius;
	}
	
	public double getGameDy() { // Public getter method for double gameDy
		return gameDy;
	}
	
	public void setGameDy(double gameDy) { // Public setter method for double gameDy
		this.gameDy = gameDy;
	}

	public boolean getGameOver() { // Public getter method for boolean gameOver
		return gameOver;
	}
	
}
