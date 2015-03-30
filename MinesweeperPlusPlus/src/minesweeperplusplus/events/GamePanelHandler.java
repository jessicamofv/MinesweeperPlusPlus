package minesweeperplusplus.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import minesweeperplusplus.MinesweeperPlusPlusGameDataModel;
import minesweeperplusplus.Tile;

public class GamePanelHandler implements MouseListener
{
	private MinesweeperPlusPlusGameDataModel data;
	
	public GamePanelHandler(MinesweeperPlusPlusGameDataModel initData)
	{
		data = initData;
	}
	
	public void mousePressed(MouseEvent me)
	{
		int x = me.getX();
		int y = me.getY();
		String buttonPressed = "";
		
		Tile[][] gameBoard = data.getGameBoard();
		
		for (int row = 0; row < data.getMaxRows(); row++) 
		{
			for (int col = 0; col < data.getMaxCols(); col++) 
			{
				if(gameBoard[row][col].containsPoint(x, y))
				{
					if (me.getButton() == MouseEvent.BUTTON1)
						buttonPressed = "LEFT";
					else if (me.getButton() == MouseEvent.BUTTON3)
						buttonPressed = "RIGHT";

					gameBoard[row][col].fireEvent(buttonPressed);
					
					col = data.getMaxCols();
					row = data.getMaxRows();
				}
				
			}
		}
		
	}
	
	// WE WILL NOT USE THESE METHODS
	public void mouseEntered(MouseEvent me) 	{}
	public void mouseExited(MouseEvent me) 		{}
	public void mouseClicked(MouseEvent me)		{}
	public void mouseReleased(MouseEvent me) 	{}	
}
