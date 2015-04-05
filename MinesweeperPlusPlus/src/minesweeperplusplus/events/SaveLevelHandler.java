package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class SaveLevelHandler implements ActionListener
{
	private MinesweeperPlusPlusLevelEditor editor;
	
	public SaveLevelHandler(MinesweeperPlusPlusLevelEditor initEditor)
	{
		editor = initEditor;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if (editor.getNumMinesField().getText() == null 
			|| editor.getNumMinesField().getText().trim().equals(""))
		{
			editor.showMoreCustomLevelOptionsErrorMessage("Please enter the number of mines for "
					+ "this level.");
			// is a repaint needed?
			// editor.getRatSpawnLocationSelectionPanel().repaint();
			editor.getNumMinesField().requestFocus();
		}
		else if (!editor.isPositiveInteger(editor.getNumMinesField().getText(), 10))
		{
			editor.showMoreCustomLevelOptionsErrorMessage("The number of mines must be a positive "
					+ "integer.");
			//editor.getRatSpawnLocationSelectionPanel().repaint();
			editor.getNumMinesField().requestFocus();
		}
		else if (Integer.parseInt(editor.getNumMinesField().getText().trim()) > editor.getVisibleLittleTiles().size()/2)
		{
			editor.showMoreCustomLevelOptionsErrorMessage("The number of mines must be no greater "
					+ "than half the number of tiles in the game board.");
			//editor.getRatSpawnLocationSelectionPanel().repaint();
			editor.getNumMinesField().requestFocus();
		}
		else if (editor.getRatSpawnRateRatsField().getText() == null
				|| editor.getRatSpawnRateRatsField().getText().trim().equals(""))
		{
			editor.showMoreCustomLevelOptionsErrorMessage("Please enter some number of rats.");
			//editor.getRatSpawnLocationSelectionPanel().repaint();
			editor.getRatSpawnRateRatsField().requestFocus();
		}
		else if (!editor.isPositiveInteger(editor.getRatSpawnRateRatsField().getText(), 10))
		{
			editor.showMoreCustomLevelOptionsErrorMessage("The number of rats must be a positive "
					+ "integer.");
			//editor.getRatSpawnLocationSelectionPanel().repaint();
			editor.getRatSpawnRateRatsField().requestFocus();
		}
		else if (editor.getRatSpawnRateSecsField().getText() == null
				|| editor.getRatSpawnRateSecsField().getText().trim().equals(""))
		{
			editor.showMoreCustomLevelOptionsErrorMessage("Please enter some number of seconds.");
			//editor.getRatSpawnLocationSelectionPanel().repaint();
			editor.getRatSpawnRateSecsField().requestFocus();
		}
		else if (!editor.isPositiveInteger(editor.getRatSpawnRateSecsField().getText(), 10))
		{
			editor.showMoreCustomLevelOptionsErrorMessage("The number of seconds must be a positive "
					+ "integer.");
			//editor.getRatSpawnLocationSelectionPanel().repaint();
			editor.getRatSpawnRateRatsField().requestFocus();
		}
		else if (editor.getRatSpawnLocation() == null)
		{
			editor.showMoreCustomLevelOptionsErrorMessage("Please select a spawn location for this "
					+ "level.");
			//editor.getRatSpawnLocationSelectionPanel().repaint();
		}
		else
		{
			editor.addCustomLevel();
			editor.closeCustomizationWindows();
		}
	}
}
