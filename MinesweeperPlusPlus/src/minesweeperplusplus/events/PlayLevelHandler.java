package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class PlayLevelHandler implements ActionListener
{
	private MinesweeperPlusPlusLevelEditor editor;
	
	public PlayLevelHandler(MinesweeperPlusPlusLevelEditor initEditor)
	{
		editor = initEditor;	
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		editor.initInGameWindow();		
	}
}
