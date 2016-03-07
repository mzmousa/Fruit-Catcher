import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

public class StartingPoint extends Applet implements Runnable, KeyListener, MouseMotionListener, MouseListener, ActionListener {
	
	private Image image; // Used for double buffering (stops flicker)
	private Graphics doubleG; // Used for double buffering (stops flicker)
	Ball MarioBall; // Ball object, moveable character Mario
	Platform p[] = new Platform[7]; // Array of 7 platform objects
	Item item[] = new Item[8]; // Array of 8 item objects
	private double score; // Current player score
	private int itemSelecter; // Integer value that determines the next item to appear
	private int ItemFreq = 3; // Frequency of items that appear
	private int stItemSize = 10; // Standard item size
	double backGroundImageX = 0; // Top left x position of background
	double backGroundImageDx = .3; // Speed of background scrolling
	URL url; // Uniform resource locater, used for images found in source folder
	Image backgroundImage; // Background image
	private boolean leftKey = false; // left key pressed becomes true when pressed
	private boolean rightKey = false; // right key pressed becomes true when pressed
	int levelCheck = 0; // X distance between platforms
	boolean gameOver = false; // Boolean for game over. Starts out as false since game is not over
	boolean mouseIn = false; // Boolean for mouse location 
	Arrow arrow; // Arrow object that show's x-location of Ball object when it is above the applet screen
	Bomb bomb; // Bomb object that slows Mario down and decreases his energy
	Apple apple; // Apple object that decreases the pull of gravity on Mario, causing him to fall slower
	Banana banana; // Banana object that speeds up Mario and increases his energy 
	Grapes grapes; // Grapes object that increases the player's score
	private double energyPer; // Energy percentage of Mario - he dies when his energy is at 0.
	private int maxItemSpeed = 4; // Maximum speed at which items scroll to the left of the screen
	private int respawnDist = 350; // Respawn distance between items
	private boolean gameStarted = false; // Game has not been started yet
    private Choice cEnergy; // Drop down menu
    private Button bInitiate; // Button to initiate starting energy
    
	public void init() { // Initialize method
		setSize(800, 600); // Setting default applet size
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		// Input Listeners
		bInitiate=new Button("Set Start Energy!"); // Button label
		bInitiate.setActionCommand("Set Start Energy!");
		bInitiate.addActionListener(this); // Button action listener
		cEnergy=new Choice();
		cEnergy.add("100%");
		cEnergy.add("90%");
		cEnergy.add("70%");
		cEnergy.add("50%");
		cEnergy.add("30%");
		cEnergy.add("10%"); 
		// All choices from drop down menu
		add(cEnergy);
		add(bInitiate);
		// Declaring and creating button and choice items 

		try{
			url = getDocumentBase(); 
		/* Document base of this workspace is where any url's are grabbed from.
		 * This will be from the "src" and "class" folders.
		 */
		} catch (Exception e) {
			
		}
		backgroundImage = getImage(url, "images/background.png");
		Pictures p = new Pictures(this); // An instance of the pictures class where all pictures are located in (this) class
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Set Start Energy!")) {
			getEnergyChoice();
			setEnergyPer(getEnergyChoice());
			System.out.println(energyPer);
			bInitiate.setFocusable(false);
			cEnergy.setFocusable(false);
			bInitiate.setVisible(false);
			cEnergy.setVisible(false);
			this.setFocusable(true);
		}
		/* When the button "Set Start Energy!" is pressed, get energy choice value after choosing drop down menu option.
		 * Then set the button and drop down menu to be invisible and not to be in focus, and switch the focus to the applet
		 * so it can receive information from other input listeners such as mouse, mouse motion and the keyboard.
		 */
	}
	
	public double getEnergyChoice() {
			if (cEnergy.getSelectedItem().equals("100%")) {
				return Math.abs((MarioBall.getGameDy()/(95.0))*100);
			}
			else if (cEnergy.getSelectedItem().equals("90%")) {
				return Math.abs((MarioBall.getGameDy()/(95.0))*90);
			}
			else if (cEnergy.getSelectedItem().equals("70%")) {
				return Math.abs((MarioBall.getGameDy()/(95.0))*70);
			}
			else if (cEnergy.getSelectedItem().equals("50%")) {
				return Math.abs((MarioBall.getGameDy()/(95.0))*50);
			}
			else if (cEnergy.getSelectedItem().equals("30%")) {
				return Math.abs((MarioBall.getGameDy()/(95.0))*30);
			}
			else {
				return Math.abs((MarioBall.getGameDy()/(95.0))*10);
			}
		}
	/* Method used for determining the starting energy value of the player according to their
	 * choice in the choice box in the start and game over screens.
	 */
	 
	public void start() {
		this.setFocusable(true);
		MarioBall = new Ball(150, 150); // Mario's x and y coordinates when he first appears on the screen
		score = 0; // Player's score starts at 0
		for (int i = 0; i < p.length; i++){
			p[i] = new Platform(i*120, 300); // for loop used to populate array of platforms
		}
		
		arrow = new Arrow(MarioBall); 
		/* Arrow object. (MarioBall) refers to the object it needs to refer to for some information, which, 
		 * in this case is its X - position on the screen if it is above the applet's top border.
		 */
		
		for (int i = 0; i < item.length; i++){
			Random r = new Random();
			if(r.nextInt(ItemFreq) == 0){ // If the random number generated between 1 and ItemFreq is 1, then itemselecter is 1 (bomb)
				itemSelecter = 0;
			} 
			else if (r.nextInt(ItemFreq)== 1){
				itemSelecter = 1;
			}
			else if (r.nextInt(ItemFreq)== 2) {
				itemSelecter = 2;
			}
			else {
				itemSelecter = 3;
			}
			switch(itemSelecter) {
			case 0:
				item[i] = new Banana(getWidth() + 10*r.nextInt(respawnDist), -r.nextInt(maxItemSpeed)-1, stItemSize);
				break;
			case 1:
				item[i] = new Bomb(getWidth() + 10*r.nextInt(respawnDist), -r.nextInt(maxItemSpeed)-1, stItemSize);
				break;
			case 2:
				item[i] = new Apple(getWidth() + 10*r.nextInt(respawnDist), -r.nextInt(maxItemSpeed)-1, stItemSize);
				break;
			case 3:
				item[i] = new Grapes(getWidth() + 10*r.nextInt(respawnDist), -r.nextInt(maxItemSpeed)-1, stItemSize, this);
				break;
			}
			/* The first paramter getWidth() refers to screen width, where the item is located + 10 x a random number between
			 * 0 and respawnDist to determine its new x-position on the screen once it is recycled off screen to the left.
			 * The next parameter is a negative number between 0 and maxItemSpeed that determines the speed of the object
			 * flying to the left. A -1 coefficient ensures that the item's speed is in the negative x direction, flying
			 * to the left. stItemSize uses the default size of the item's radius. These three parameters are defined in
			 * the Item class with the parameters (int x, int dx, int dx) and their arguements are in their respective
			 * item object (e.g., grapes, banana, apple).
			 */
		}
		
		Thread thread = new Thread(this); 
		thread.start(); // single thread required for the run method
		
	}

	public void run() {
		while(true){ // infinite loop required to run game
			
			if (backGroundImageX > -1538){
				backGroundImageX -= backGroundImageDx;
			} else {
				backGroundImageX = 0; 
			}
			/* If the background image's x coordinate is greater than its pixel value if completely off the screen
			 * then move it to the left by a set speed. If it is completely off screen (left), then restart image
			 * at an x-coordinate of 0 on the applet, thus recycling it.
			 */
			
			if(!gameOver) {
				for (int i = 0; i < p.length; i++){
					int locationX = p[i].getX(); // locationX is the horizontal separation between two platforms.
					if (locationX < 0 - p[i].getWidth()) {
						Random r = new Random();
						int i2 = i; // new array control variable i2 equals i.
						if (i == 0) {
							i2 = p.length; // This is 7 since there are 7 platforms.
						}
						p[i].setX(p[i2-1].getX()+p[i].getWidth()+Pictures.level* r.nextInt(25)+60);
					}
				/* This for loop recycles platforms on the screen while the game is running (!gameOver means not gameOver).
				 * The first 7 platforms' x-positions are defined in a for loop elsewhere in this code and they all start 
				 * out with the same y position so there's a nice wall of platforms to stop Mario from falling off. According
				 * to code elsewhere in this document, their y-positions becomes randomized, and, according to this loop, the 
				 * x-position of the next platform produced in run() for that array value [i] will have an added x-position 
				 * of the last platform plus its width plus the value of Pictures.level (which becomes greater as the game 
				 * goes on) plus a random number between 60 and 85. This ensures no two platforms are touching each other, are
				 * directly on top of each other, and that their distance from each other increases with time. The "if" statement
				 * is required so no array value of i2 is -1, which causes an error since it does not exist.
				 */
				}
				gameOver = MarioBall.getGameOver(); 
				/* every time program is run, check to see if gameOver is true from the ball class, since that's where
				 */
				
				if (levelCheck > 1000) {  // if levelCheck is greater than 600 (10 seconds)
					Pictures.level++; // useful for x separation
					levelCheck = 0;
				}
				levelCheck ++;
				/* Adds 1 to levelCheck every time repaint() is run, so every 1/60 a second or about 16.7 ms. When 
				 * levelCheck is greater than 600, or after 10 seconds has passed in the game, level is increased 
				 * by one (found in class Pictures). Picture.level is used to increase the distance between two
				 * platforms found in the applet class, which is why the platforms are farther apart and game is
				 * more difficult every 10 seconds.
				 */
				
				if(leftKey){ //
					MarioBall.moveLeft();
				}
				if(rightKey){
					MarioBall.moveRight();
				}
				if (!gameOver){
					score += (16.7/1000); // if not game over, add 1 to score every 60th of a second or 16.7 ms.
				}
				Random r = new Random();
				for (int i = 0; i < item.length; i++) {
					if (item[i].getCreateNew()) { // when new item is to be created
						item[i] = null;
						if(r.nextInt(ItemFreq) == 0) {
							itemSelecter = 0;
						}
						else if (r.nextInt(ItemFreq)== 1) {
							itemSelecter = 1;
						}
						else if (r.nextInt(ItemFreq)== 2) {
							itemSelecter = 2;
						}
						else {
							itemSelecter = 3;
						}
						switch(itemSelecter) {
							case 0:
								item[i] = new Banana(getWidth() + 10*r.nextInt(respawnDist), -r.nextInt(maxItemSpeed)-1, stItemSize);
								break;
							case 1:
								item[i] = new Bomb(getWidth() + 10*r.nextInt(respawnDist), -r.nextInt(maxItemSpeed)-1, stItemSize);
								break;
							case 2:
								item[i] = new Apple(getWidth() + 10*r.nextInt(respawnDist), -r.nextInt(maxItemSpeed)-1, stItemSize);
								break;
							case 3:
								item[i] = new Grapes(getWidth() + 10*r.nextInt(respawnDist), -r.nextInt(maxItemSpeed)-1, stItemSize, this);
								break;
						}
						item[i].setCreateNew(false);
						// Refer to lines 149 - to 156 for details on this code.
						
					}
				}
				
				MarioBall.update(this);
				for (int i = 0; i < p.length; i++) {
					p[i].update(this, MarioBall); // Update any item objects every time game thread runs.
				}
				
				for (int i = 0; i < item.length; i++) {
					item[i].update(this, MarioBall); // Update any platform object every time game thread runs.
				}
			}
			repaint();
			try {
				Thread.sleep(17);
			// Game thread: run one frame (repaint) of the game every 17 milliseconds, resulting in close to a 60 FPS rate.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void update(Graphics g) { // Double buffer method, stops flicker
		
		if(image == null){
			image = createImage(this.getSize().width, this.getSize().height);
			doubleG = image.getGraphics();
		} 
		
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		doubleG.setColor(getForeground());
		paint(doubleG);
		
		g.drawImage(image, 0, 0, this);
		
	}

	public void paint(Graphics g) {
		g.setColor(new Color(15,77,147)); // Blueish background colour
		g.fillRect(0, 0, getWidth(), getHeight()); // Paint blue background
		g.drawImage(backgroundImage, (int)backGroundImageX, 0, this); // Draw background image in applet screen
		g.drawImage(backgroundImage, (int)backGroundImageX + 1538, 0, this); // Draw background image
		String s = Integer.toString((int)score); // Score as a string displayed on screen
		Font font = new Font("Serif", Font.BOLD, 32); // Game font attributes
		g.setFont(font);
		
		if (!gameStarted){ // Start Menu
			
			g.setColor(Color.WHITE);
			g.drawString("Fruit Catcher!", 278, 250);
			
			if (!mouseIn){
				g.setColor(Color.WHITE);
			} 
			else {
				g.setColor(Color.RED);
			}
			
			g.drawString("Start Game", 300, 300);
			}
			/* If the game is not over, then draw Start Game and Fruit Catcher in white. If the mouse is in 
			 * the dimensions defined by mouseMoved/mouseClicked, then make Start Game red.
			 */
			 
			else {
				for (int i = 0; i < p.length; i++){ // Paint Platforms
				p[i].paint(g);
			}
			
			for (int i = 0; i < item.length; i++){ // Paint Items
				item[i].paint(g);
			}
		
			MarioBall.paint(g); //Paint MarioBall
			
			arrow.paint(g); //Paint Arrow
			
			g.setColor(Color.BLACK);
			g.drawString(s, getWidth() -78, 52); // Paint score
			g.setColor(new Color(198,226,255));
			g.drawString(s, getWidth() -80, 50);
			g.drawString("Energy:       %", 10, getHeight() - 10);
			energyPer = Math.abs((MarioBall.getGameDy()/(95.0))*getEnergyChoice()); // Energy percentage value
			if (energyPer < 1) {
				setGameOver(true); // If the player's energyPercentage is less than 1 (0 as an int) then the game is over.
			}
			g.drawString(Integer.toString((int)energyPer), 122, getHeight() - 10); 
			// Location of energy percentage value as a string on screen
			if (gameOver) {
				
				g.setColor(Color.WHITE);
				g.drawString("Game Over!", 300, 300);
				
				if (mouseIn){
					g.setColor(Color.RED);
					g.drawString("Play Again?", 300, 340);
				} 
				else {
					g.setColor(Color.WHITE);
					g.drawString("Play Again?", 300, 340);
				}
				/* Draw "Game Over!" and "Play Again?" strings if game is over, but only make Play Again red if
				 * the mouse is in the dimensions defined by mouseMoved/mouseClicked, otherwise keep it white.
				 */
			}
		}
	}

	public void keyTyped(KeyEvent e) { // Empty method needed as part of class KeyListener 
	}

	public void keyPressed(KeyEvent e) { 
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT: // left arrow key switch when pressed
			leftKey = true;
			break;
			// 
		case KeyEvent.VK_RIGHT: // right arrow key switch when pressed
			rightKey = true;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT: // left arrow key switch when released
			leftKey = false; 
			break;
		case KeyEvent.VK_RIGHT: // left arrow key switch when released
			rightKey = false;
			break;
		}
	}
	
	public int getScore() {
		return (int)score;
	}
	// "Getter" method that returns the value of score as an int when called

	public void setScore(int score) {
		this.score = score;
	}
	// "Setter" method that sets the value of score in the applet as the global score value.
	// This gives the variable score the ability to be accessed from any class.

	public void mouseDragged(MouseEvent e) { // Empty method needed as part of class MouseMotionListener
	}

	public void mouseMoved(MouseEvent e) {
		if (gameOver) {
			if (e.getX() > 290 && e.getX() < 470){
				if(e.getY() > 320 && e.getY() < 350){
					mouseIn = true;
				}
			}
			
			if (e.getX() < 290 || e.getX() > 470){
				mouseIn = false;
			}
			if(e.getY() < 320 || e.getY() > 350){
				mouseIn = false;
			}
			/* If the mouse is within the dimensions given above in the applet once the game is over, then boolean mouseIn
			 * will be true (if game is over), which makes the Try Again text red and if clicked, restarts the game. If not,
			 * mouseIn is not true and no action initiated with boolean mouseIn occurs.
			 */
			bInitiate.setFocusable(true);
			cEnergy.setFocusable(true);
			bInitiate.setVisible(true);
			cEnergy.setVisible(true);
			/* Resets focus and visibility of button and choice menus when the game is over so the player can choose
			 * a new energy value if he/she loses. 
			 */
		}
		
		if (!gameStarted) {
			if (e.getX() > 290 && e.getX() < 470){
				if(e.getY() > 273 && e.getY() < 308){
					mouseIn = true;
				}
			}
			if (e.getX() < 290 || e.getX() > 470){
				mouseIn = false;
			}
			if(e.getY() < 273 || e.getY() > 308){
				mouseIn = false;
			}
			/* If the mouse is within the dimensions given in the applet once the game is running, then boolean mouseIn
			 * will be true (if game is not over), which makes the Start Game red and if clicked, starts the game. If not,
			 * mouseIn is not true and no action initiated with boolean mouseIn occurs.
			 */
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (mouseIn) {
			bInitiate.setFocusable(false);
			cEnergy.setFocusable(false);
			bInitiate.setVisible(false);
			cEnergy.setVisible(false);
			gameStarted = true;
			gameOver = false;
			MarioBall = null;
			MarioBall = new Ball();
			Pictures.level = 1;
			score = 0;
			levelCheck = 0;
			
			for (int i = 0; i < p.length; i++){
				p[i] = new Platform(i*120, 300);
			}
			
			arrow = new Arrow(MarioBall);
			
			for (int i = 0; i < item.length; i++){
				Random r = new Random();
				if(r.nextInt(ItemFreq) == 0){
					itemSelecter = 0;
				}
				else if (r.nextInt(ItemFreq)== 1){
					itemSelecter = 1;
				}
				else if (r.nextInt(ItemFreq)== 2) {
					itemSelecter = 2;
				}
				else {
					itemSelecter = 3;
				}
				switch(itemSelecter){
				case 0:
					item[i] = new Banana(getWidth() + 10*r.nextInt(respawnDist), -r.nextInt(maxItemSpeed)-1, stItemSize);
					break;
				case 1:
					item[i] = new Bomb(getWidth() + 10*r.nextInt(respawnDist), -r.nextInt(maxItemSpeed)-1, stItemSize);
					break;
				case 2:
					item[i] = new Apple(getWidth() + 10*r.nextInt(respawnDist), -r.nextInt(maxItemSpeed)-1, stItemSize);
					break;
				case 3:
					item[i] = new Grapes(getWidth() + 10*r.nextInt(respawnDist), -r.nextInt(maxItemSpeed)-1, stItemSize, this);
					break;
				}
			}
			mouseIn = false;
		}
	}

	public void mousePressed(MouseEvent e) { // Empty method needed as part of class MouseListener
	}

	public void mouseReleased(MouseEvent e) { // Empty method needed as part of class MouseListener		
	}

	public void mouseEntered(MouseEvent e) { // Empty method needed as part of class MouseListener
	}

	public void mouseExited(MouseEvent e) { // Empty method needed as part of class MouseListener
	}
	
	public double getEnergyPer() { 
		return energyPer;
	}
	// "Getter" method used to return the value of energyPer as an int when called

	public void setEnergyPer(double energyPer) { 
		this.energyPer = energyPer;
	}
	// "Setter" method used to set the energy percentage value in the applet as the global percentage value.
	// This gives the variable energyPer the ability to be accessed from any class.
	
	public boolean getGameOver() {
		return gameOver;
	}
	// "Getter" method used to return the value of gameOver (boolean) when called

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	// "Setter" method used to set the game over value in the applet as global.
	// This gives the variable gameOver the ability to be accessed from any class.
	}