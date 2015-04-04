package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class DeleteLevelHandler implements ActionListener
{
	private MinesweeperPlusPlusLevelEditor editor;
	
	public DeleteLevelHandler(MinesweeperPlusPlusLevelEditor initEditor)
	{
		editor = initEditor;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		editor.deleteCustomLevel();
	}
}
