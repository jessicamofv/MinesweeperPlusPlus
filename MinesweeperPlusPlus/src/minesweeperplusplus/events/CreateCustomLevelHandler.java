package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class CreateCustomLevelHandler implements ActionListener 
{
	private MinesweeperPlusPlusLevelEditor editor;
	
	public CreateCustomLevelHandler(MinesweeperPlusPlusLevelEditor initEditor)
	{
		editor = initEditor;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		editor.initCustomLevelLayoutWindow();
	}
}
