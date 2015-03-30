package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import minesweeperplusplus.MinesweeperPlusPlusApplication;
import minesweeperplusplus.MinesweeperPlusPlusGame;
import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class InGameLogOutHandler implements ActionListener
{
	private MinesweeperPlusPlusGame game;
	private MinesweeperPlusPlusLevelEditor editor;
	private MinesweeperPlusPlusApplication application;
	
	public InGameLogOutHandler(MinesweeperPlusPlusGame initGame, MinesweeperPlusPlusLevelEditor initEditor, MinesweeperPlusPlusApplication initApplication)
	{
		game = initGame;
		editor = initEditor;
		application = initApplication;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		game.closeInGameWindow();
		editor.clearCurrentUser();
		editor.closeLevelEditorWindow();
		application.clearCurrentUser();
	}
}