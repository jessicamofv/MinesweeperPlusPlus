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
		String levelFileName = "./setup/" 
				+ editor.getLevelNameField().getText().replaceAll("\\s", "") + ".txt";
		if (editor.getLevelNameField().getText() == null
			|| editor.getLevelNameField().getText().trim().equals(""))
		{
			editor.showCustomLevelLayoutErrorMessage("Please enter a name for this level.");
			// is a repaint needed?
			// editor.getCustomLevelGameBoardPanel().repaint();
			editor.getLevelNameField().requestFocus();
		}
		else if (editor.getLevelFileNames().contains(levelFileName))
		{
			editor.showCustomLevelLayoutErrorMessage("There is already a level with that name.");
			//editor.getCustomLevelGameBoardPanel().repaint();
			editor.getLevelNameField().requestFocus();
		}
		else if (editor.getVisibleLittleTiles().size() < 2)
		{
			editor.showCustomLevelLayoutErrorMessage("The game board must contain at least two "
					+ "tiles.");
			//editor.getCustomLevelGameBoardPanel().repaint();
		}
		else
			editor.initMoreCustomLevelOptionsWindow();
	}
}
