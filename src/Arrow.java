import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

public class Arrow {
	
	Image arrow; // arrow image 
	URL url;
	Ball ball;

	public Arrow(Ball ball) {
		arrow = Pictures.arrow;
		this.ball = ball;
	}
	// Arrow sub-class. The arrow object 
	
	public void paint(Graphics g) {
		if (ball.getY() <= 0){
			g.drawImage(arrow, ball.getX()-25, 20, Pictures.sp);
		}	
	}
}
