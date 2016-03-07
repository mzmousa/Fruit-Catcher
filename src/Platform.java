import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;



public class Platform {
	
	private int x, y, width, height; // Private ints, can only be accessed in this class
	Image plat; // Platform image
	URL url;
	float frame = 2; // Float used to cycle through platform images in order to animate
	
	public Platform(int x, int y){
		this.x = x;
		this.y = y;
		width = 120;
		height = 40;
		plat = Pictures.platform; // where platform image is located in files
	}
	
	public void update(StartingPoint sp, Ball b) { // 
		x += -Pictures.level; // x position of platform increases, making platforms farther apart from each other each level++
		checkForCollision(b);
		if (x < 0 - width){
			Random r = new Random();
			y = sp.getHeight() - 40 - r.nextInt(400); 
			/* if platform goes off screen, then make y position of platform anywhere between the height of the platform
			 * and the bottom of the applet screen.
			 */
		}
	}

	public int getWidth() {
		return width;
	}
	private void checkForCollision(Ball b) {
		int ballX = b.getX();
		int ballY = b.getY();
		int radius = b.getRadius();
		
		if (ballY+radius > y && ballY+radius < y + height){
			if (ballX > x-(radius/2) && ballX < x+width+(radius/2)) {
				b.setY(y-radius);
				b.setDy(b.getGameDy());
			}
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) { // 
		this.x = x;
	}

	public void paint(Graphics g) {
		frame += 0.1;
		if (frame >= 3){
			frame = 0;
		} 
		g.drawImage(plat, x, y, x+width, y+height, 0, 40*(2-(int)frame), 120, 40*(2-(int)frame)+40, Pictures.sp);
		/* Frames of the platform image are cycled through, animating the image. When the paint method is run every 17 ms,
		 * 0.1 is added to frame which is a float. Once it reaches 1, 2, or 3 (integer values, e.g., 10 cycles increase
		 * int frame by 1) a new source value for x and y from the platform image are used. There are three different 
		 * platform panels in the platform image, which is why platform has three images it can cycle through in animation.
		 */
	}
}
