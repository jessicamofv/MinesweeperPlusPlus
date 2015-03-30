package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import minesweeperplusplus.LittleTile;
import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class LittleTileClickHandler implements ActionListener
{
	private MinesweeperPlusPlusLevelEditor editor;
	private int littleTileRowIndex;
	private int littleTileColIndex;
	
	public LittleTileClickHandler(MinesweeperPlusPlusLevelEditor initEditor, int initLittleTileRowIndex,
			int initLittleTileColIndex)
	{
		editor = initEditor;
		littleTileRowIndex = initLittleTileRowIndex;
		littleTileColIndex = initLittleTileColIndex;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		LittleTile[][] gameBoard = editor.getCustomLevelGameBoard();
		LittleTile littleTile = gameBoard[littleTileRowIndex][littleTileColIndex];
		
		if (littleTile.getState().equals(MinesweeperPlusPlusLevelEditor.INVISIBLE_OUTLINED_STATE))
		{
			littleTile.setState(MinesweeperPlusPlusLevelEditor.VISIBLE_STATE);
			editor.getVisibleLittleTiles().add(littleTile);
		}
		else
		{
			littleTile.setState(MinesweeperPlusPlusLevelEditor.INVISIBLE_OUTLINED_STATE);
			editor.getVisibleLittleTiles().remove(littleTile);
		}
		
		editor.getCustomLevelGameBoardPanel().repaint();
	}
}
