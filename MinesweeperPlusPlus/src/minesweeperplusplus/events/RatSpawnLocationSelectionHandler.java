package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import minesweeperplusplus.LittleTile;
import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class RatSpawnLocationSelectionHandler implements ActionListener
{
	private MinesweeperPlusPlusLevelEditor editor;
	private int littleTileRowIndex;
	private int littleTileColIndex;
	
	public RatSpawnLocationSelectionHandler(MinesweeperPlusPlusLevelEditor initEditor, 
			int initLittleTileRowIndex, int initLittleTileColIndex)
	{
		editor = initEditor;
		littleTileRowIndex = initLittleTileRowIndex;
		littleTileColIndex = initLittleTileColIndex;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		editor.clearBoardForRatSpawnLocationSelection();
		LittleTile[][] gameBoard = editor.getBoardForRatSpawnLocationSelection();
		LittleTile littleTile = gameBoard[littleTileRowIndex][littleTileColIndex];
		// vvv this is done inside of setRatSpawnLocationSelectionPanel
		/*littleTile.setState(MinesweeperPlusPlusLevelEditor.RAT_SPAWN_STATE);*/
		editor.setRatSpawnLocation(littleTile);
		editor.setRatSpawnLocationSelectionPanel(littleTileRowIndex, littleTileColIndex);
	}
}
