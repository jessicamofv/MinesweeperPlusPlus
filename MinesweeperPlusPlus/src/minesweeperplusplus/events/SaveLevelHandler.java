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
			|| editor.getNumMinesField().getText().equals("")
			|| editor.getRatSpawnRateRatsField().getText() == null
			|| editor.getRatSpawnRateRatsField().getText().equals("")
			|| editor.getRatSpawnRateSecsField().getText() == null
			|| editor.getRatSpawnRateSecsField().getText().equals("")
			|| editor.getRatSpawnLocation() == null)
		{
			JOptionPane.showMessageDialog(editor.getMoreCustomLevelOptionsWindow(), 
					"Please complete all customizations.");
			editor.getRatSpawnLocationSelectionPanel().repaint();
		}
		else
		{
			editor.addCustomLevel();
			editor.closeCustomizationWindows();
		}
	}
}
