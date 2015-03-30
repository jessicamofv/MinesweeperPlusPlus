package minesweeperplusplus.events;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import minesweeperplusplus.LittleTile;
import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class RSLSelectionPanelHandler implements MouseListener
{
	private MinesweeperPlusPlusLevelEditor editor;
	private boolean selectionMade = false;
	
	public RSLSelectionPanelHandler(MinesweeperPlusPlusLevelEditor initEditor)
	{
		editor = initEditor;
	}
	
	public void mousePressed(MouseEvent me)
	{
		int x = me.getX();
		int y = me.getY();
		
		LittleTile[][] gameBoard = editor.getBoardForRatSpawnLocationSelection();
		
		for (int row = 0; row < MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_ROWS; row++) 
		{
			for (int col = 0; col < MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_COLS; col++) 
			{
				if(gameBoard[row][col].getState().equals(MinesweeperPlusPlusLevelEditor.INVISIBLE_STATE) && gameBoard[row][col].containsPoint(x, y))
				{
					gameBoard[row][col].fireEvent();
					//selectionMade = true;
					col = MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_COLS;
					row = MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_ROWS;		
				}
				
			}
		}
		
		/*if (!selectionMade)
		{
			LittleTile ratSpawnLocation = new LittleTile(editor.getLittleTileSpriteType(), (float)x, 
			(float)y, (float)0, (float)0, MinesweeperPlusPlusLevelEditor.RAT_SPAWN_STATE);
			
			Image littleTileImage = 
					editor.getLittleTileSpriteType().getStateImage(ratSpawnLocation.getState());
			
			editor.getRatSpawnLocationSelectionPanel().getGraphics().drawImage(littleTileImage, 
					(int)ratSpawnLocation.getX(), (int)ratSpawnLocation.getY(), 
					editor.getLittleTileWidth(), editor.getLittleTileHeight(), null);
			
			selectionMade = true;
		}*/
		
		/*for (int row = 0; row < MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_ROWS; row++) 
		{
			for (int col = 0; col < MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_COLS; col++) 
			{
				gameBoard[row][col].setActionListener(null);
				
			}
		}*/
	}
	
	// WE WILL NOT USE THESE METHODS
	public void mouseEntered(MouseEvent me) 	{}
	public void mouseExited(MouseEvent me) 		{}
	public void mouseClicked(MouseEvent me)		{}
	public void mouseReleased(MouseEvent me) 	{}	
}
