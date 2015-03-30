package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import minesweeperplusplus.MinesweeperPlusPlusApplication;
import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class LevelEditorLogOutHandler implements ActionListener
{
	private MinesweeperPlusPlusLevelEditor editor;
	private MinesweeperPlusPlusApplication application;
	
	public LevelEditorLogOutHandler(MinesweeperPlusPlusLevelEditor initEditor, MinesweeperPlusPlusApplication initApplication)
	{
		editor = initEditor;
		application = initApplication;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		editor.clearCurrentUser();
		editor.closeLevelEditorWindow();
		application.clearCurrentUser();
	}
}
