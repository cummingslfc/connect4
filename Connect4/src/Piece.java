import java.awt.Color;
import java.awt.Point;
import acm.graphics.*;

public class Piece extends GOval
{
	private boolean isRed;
	public Piece(int x, int y, boolean red)
	{
		super(50*y+5+20, 0, 40, 40);
		isRed = red;
		if(red)
		{
			this.setFillColor(Color.red);
			this.setColor(Color.red);
		}
		else
		{
			this.setFillColor(Color.yellow);
			this.setColor(Color.yellow);
		}
		this.setFilled(true);
		
	}	
	public String whichPlayer()
	{
		return (isRed ? "Red" : "Yellow");
	}
}
