package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class CancelHandler implements ActionListener
{
	private MinesweeperPlusPlusLevelEditor editor;
	
	public CancelHandler(MinesweeperPlusPlusLevelEditor initEditor)
	{
		editor = initEditor;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		editor.closeCustomizationWindows();
	}

}
