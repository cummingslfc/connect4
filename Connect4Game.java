
public class Connect4Game {
	private char [][] board;
	private int posFilled;

	public Connect4Game(int width, int height)
	{
		board = new char[height][width];
		for(int i=0; i<board.length; i++)
		{
			for(int j=0; j<board[i].length; j++)
			{
				board[i][j] = ' ';
			}
		}
		
		posFilled=0;
	}
	public boolean ColumnIsFull(int c)
	{
		for(int i=0; i<board.length;i++)
		{
			if(board[i][c]==' ')
			{
				return false;
			}
		}
		return true;
	}
	public boolean playDisc(int column, boolean isRed)
	{
		if(column<board[0].length && column>=0)
		{
			for(int i=board.length-1; i>=0;i--)
			{
				if(board[i][column]==' ')
				{
					if(isRed)
					{
						board[i][column]='R';
					}
					else
					{
						board[i][column]='Y';
					}
					posFilled++;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean boardIsFull()
	{
		if(posFilled>=board.length*board[0].length)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String toString()
	{
		String str = "";
		for(char [] row: board)
		{
			for(char piece: row)
			{
				str+=(piece+" ");
			}
			str+="\n";
		}
		for(int i=0; i<board[0].length;i++)
		{
			str+="- ";
		}
		return str;
	}
}
