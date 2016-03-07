import java.awt.Color;
import java.awt.Graphics;


public class GravUp extends Item{
	
	private int gravPen = 4;

	public GravUp(int x, int dx, int rad) {
		super(x, dx, rad);
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.RED);
		super.paint(g);
	}
	
	@Override
	public void performAction(Ball ball) {
		if (ball.getGravity() < 40){
			ball.setGravity(ball.getGravity()+gravPen);
		}
	}
}
