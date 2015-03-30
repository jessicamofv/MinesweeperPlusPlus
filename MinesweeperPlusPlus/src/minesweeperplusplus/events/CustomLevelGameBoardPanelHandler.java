package minesweeperplusplus.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import minesweeperplusplus.LittleTile;
import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class CustomLevelGameBoardPanelHandler implements MouseListener
{
	private MinesweeperPlusPlusLevelEditor editor;
	
	public CustomLevelGameBoardPanelHandler(MinesweeperPlusPlusLevelEditor initEditor)
	{
		editor = initEditor;
	}
	
	public void mousePressed(MouseEvent me)
	{
		int x = me.getX();
		int y = me.getY();
		
		LittleTile[][] gameBoard = editor.getCustomLevelGameBoard();
		
		for (int row = 0; row < MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_ROWS; row++) 
		{
			for (int col = 0; col < MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_COLS; col++) 
			{
				if(gameBoard[row][col].containsPoint(x, y))
				{
					gameBoard[row][col].fireEvent();
					col = MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_COLS;
					row = MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_ROWS;
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
