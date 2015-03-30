package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class MoreCustomLevelOptionsHandler implements ActionListener
{
	private MinesweeperPlusPlusLevelEditor editor;

	public MoreCustomLevelOptionsHandler( MinesweeperPlusPlusLevelEditor initEditor)
	{
		editor = initEditor;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if (editor.getLevelNameField().getText() == null
			|| editor.getLevelNameField().getText().equals("")
			|| editor.getVisibleLittleTiles().isEmpty())
		{
			JOptionPane.showMessageDialog(editor.getCustomLevelLayoutWindow(), 
					"Please complete all customizations.");
			editor.getCustomLevelGameBoardPanel().repaint();
		}
		
		else
			editor.initMoreCustomLevelOptionsWindow();
	}
}
