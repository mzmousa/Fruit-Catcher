import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

public class Pictures {
	
	static Image platform, ball, arrow, Mario, banana, bomb, apple, grapes; // static images for each object. Unchangeable.
	URL url; // Uniform Resource Locater, used for grabbing files/images from folders
	static StartingPoint sp; // Instance of the applet class StartingPoint in this class
	static int level = 1; // Variable used to increase speed of platforms

	public Pictures(StartingPoint sp) {
		
		try {
			url = sp.getDocumentBase(); // method to get path folder names for URL's
		} catch (Exception e){ // catch any errors
			
		}
		this.sp = sp;		
		platform = sp.getImage(url, "images/panelSprite.png");
		arrow = sp.getImage(url, "images/arrow.png");
		Mario = sp.getImage(url, "images/Mario.png");
		banana = sp.getImage(url, "images/banana.png");
		bomb = sp.getImage(url, "images/bomb.png");
		apple = sp.getImage(url, "images/apple.png");
		grapes = sp.getImage(url, "images/grapes.png");
		/* sp is an instance of the StartingPoint class in this class. It is useful so we can 
		 * import images from the defined url and place them as objects that we can use in our
		 * StartingPoint class, our applet.
		 */
	}
}
