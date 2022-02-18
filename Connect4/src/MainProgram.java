import acm.program.*;
import acm.graphics.*;

@SuppressWarnings("serial")
public class MainProgram extends GraphicsProgram
{
	Connect4Game game;
	public void init()
	{
		resize(800, 600);
	}
	public void run()
	{
		game = new Connect4Game(7, 6, getGCanvas());
		addMouseListeners(game);
		
		
		while(true)
		{
			game.animtion();
			pause(100);
		}
		
	}
}

