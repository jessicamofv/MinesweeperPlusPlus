package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class BackHandler implements ActionListener
{
	private MinesweeperPlusPlusLevelEditor editor;
	
	public BackHandler(MinesweeperPlusPlusLevelEditor initEditor)
	{
		editor = initEditor;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		editor.backToCLLWindow();
	}

}
