import acm.graphics.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;

	public class Connect4Game implements MouseListener, MouseMotionListener {
	private Piece [][] board;
	private int totRows, totCols;
	private GCanvas canvas;
	
	private GLabel label;
	private GRect selector;
	
	private boolean redTurn;
	private boolean theEnd;

	
	public Connect4Game(int w, int h, GCanvas canv)
	{
	
		board = new Piece[h][w];
		totRows = h;
		totCols = w;
		redTurn = true;
		theEnd=false;
		
		canvas = canv;
		
		label = new GLabel("Red's turn",50,40);
		
		//background
		GRect back = new GRect(50*w,50*h);
		back.setFilled(true);
		back.setFillColor(Color.blue);
		back.setColor(Color.blue);
		canvas.add(back,20,70);
		canvas.add(label);
		
		for(int i=0; i<totRows; i++)
		{
			for(int j=0; j<totCols; j++)
			{
				GOval spot = new GOval(40,40);
				spot.setFilled(true);
				spot.setFillColor(Color.white);
				spot.setColor(Color.white);
				canvas.add(spot,50*j+5+20,50*i+5+70);
			}
		}
		
		
		//slector
		selector = new GRect(50,50*totRows+70);
		selector.setFilled(true);
		selector.setFillColor(new Color((float)79/255,(float)79/255,(float)79/255,(float)0.8));
		
		
	}
	
	private boolean isFull(int col)
	{
		// determine if a particular column is full
		for(int i=0; i<board.length;i++)
		{
			if(board[i][col]==null)
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean boardIsFull()
	{
		// determine if the whole board is full
		for(int i=0; i<board[0].length;i++)
		{
			if(!isFull(i))
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean playDisc(int col, boolean isRed)
	{
		// play a disc of the specified color in the specified column
		// return true/false to indicate if the play succeeded
		for(int i=board.length-1; i>=0; i--)
		{
			
			if(board[i][col]==null)
			{
				board[i][col]= new Piece(i,col,isRed);
				canvas.add(board[i][col]);
				redTurn = !isRed;
				if(redTurn)
				{
					label.setLabel("Red's turn");
				}
				else
				{
					label.setLabel("Yellow's turn");
				}
				checkWin(i,col,isRed);
				if(boardIsFull() && !theEnd)
				{
					label.setLabel("Game ended in a tie");
					theEnd = true;
				}
				return true;
			}
		}
		return false;
	}
	
	public void mouseClicked(MouseEvent e)
	{
		Point p = e.getPoint();
		// mouse was clicked as point p
		if(!theEnd)
		{
			for(int i=0; i<totCols;i++)
			{
				if(p.x<(i+1)*50+20 && p.y<totRows*50+70 && p.x>20)
				{
					playDisc(i,redTurn);
					break;
				}
			}
		}
	}
	

	public void mouseMoved(MouseEvent e)
	{
		
		Point p = e.getPoint();
		// mouse was moved to point p
		canvas.remove(selector);
		for(int i=0; i<totCols;i++)
		{
			if(p.x<(i+1)*50+20 && p.y<totRows*50+70 && p.x>20)
			{
				canvas.add(selector,i*50+20,0);
				break;
			}
		}
		
	}
	
	private void checkWin(int row,int col,boolean playerRed)
	{
		String player;
		if(playerRed)
		{
			player = "Red";
		}
		else
		{
			player = "Yellow";
		}
		
		
		ArrayList<Integer> winX = new ArrayList<Integer>();
		ArrayList<Integer> winY = new ArrayList<Integer>();
		
		int count = 1;
		//down
		for(int i=1;row+i<totRows && board[row+i][col]!=null && board[row+i][col].whichPlayer().equals(player); i++)
		{
			winY.add(row+i);
			winX.add(col);
			count++;
		}
		//up
		for(int i=1;row-i>=0 && board[row-i][col]!=null && board[row-i][col].whichPlayer().equals(player); i++)
		{
			winY.add(row-i);
			winX.add(col);
			count++;
		}
		if(count<4)
		{
			winX.clear();
			winY.clear();
		}
		
		
		
		count = 1;
		//right
		for(int i=1;col+i<totCols && board[row][col+i]!=null && board[row][col+i].whichPlayer().equals(player); i++)
		{
			winY.add(row);
			winX.add(col+i);
			count++;
		}
		//left
		for(int i=1;col-i>=0 && board[row][col-i]!=null && board[row][col-i].whichPlayer().equals(player); i++)
		{
			winY.add(row);
			winX.add(col-i);
			count++;
		}
		if(count<4)
		{
			for(int i=0; i<count-1;i++)
			{
				winX.remove(winX.size()-1);
				winY.remove(winY.size()-1);
			}
		}
		
		count = 1;
		//diagonal
		//right up
		for(int i=1;col+i<totCols && row-i>=0 && board[row-i][col+i]!=null && board[row-i][col+i].whichPlayer().equals(player); i++)
		{
			winY.add(row-i);
			winX.add(col+i);
			count++;
		}
		//left down
		for(int i=1;col-i>=0 && row+i<totRows && board[row+i][col-i]!=null && board[row+i][col-i].whichPlayer().equals(player); i++)
		{
			winY.add(row+i);
			winX.add(col-i);
			count++;
		}
		if(count<4)
		{
			for(int i=0; i<count-1;i++)
			{
				winX.remove(winX.size()-1);
				winY.remove(winY.size()-1);
			}
		}
		
		
		count = 1;
		//diagonal
		//left up
		for(int i=1;col-i>=0 && row-i>=0 && board[row-i][col-i]!=null && board[row-i][col-i].whichPlayer().equals(player); i++)
		{
			winY.add(row-i);
			winX.add(col-i);
			count++;
		}
		//right down
		for(int i=1;col+i<totCols && row+i<totRows && board[row+i][col+i]!=null && board[row+i][col+i].whichPlayer().equals(player); i++)
		{
			winY.add(row+i);
			winX.add(col+i);
			count++;
		}
		if(count<4)
		{
			for(int i=0; i<count-1;i++)
			{
				winX.remove(winX.size()-1);
				winY.remove(winY.size()-1);
			}
		}
		
		
			winY.add(row);
			winX.add(col);
		if(winX.size()>=4)
		{
			label.setLabel(player +" wins!");
			theEnd = true;
			for(int i=0; i<winX.size() ; i++)
			{
				GOval temp = new GOval(20,20);
				temp.setFilled(true);
				temp.setFillColor(Color.gray);
				temp.setColor(Color.gray);
				canvas.add(temp,winX.get(i)*50+15+20,winY.get(i)*50+15+70);
			}
		}
	}
	
	
	public void animtion()
	{
		for(int i=0; i<totRows; i++)
		{
			for(int j=0; j<totCols; j++)
			{
				if(board[i][j]!=null && board[i][j].getY()<50*i+5+70)
				{
					board[i][j].move(0, +5);
				}
			}
		}
	}
	
	
	// These are required to be here, 
	// but should remain empty.
	public void mouseDragged(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
}
