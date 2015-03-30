package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class LevelScrollHandler implements ActionListener
{
	private MinesweeperPlusPlusLevelEditor editor;
	
	public LevelScrollHandler(MinesweeperPlusPlusLevelEditor initEditor)
	{
		editor = initEditor;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		JButton arrowButton = (JButton)ae.getSource();
		editor.scrollLevel(arrowButton);
	}
}
