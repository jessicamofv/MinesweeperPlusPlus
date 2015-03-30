package minesweeperplusplus.events;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import minesweeperplusplus.MinesweeperPlusPlusLevelEditor;

public class CustomizationWindowsHandler implements WindowListener
{
	private MinesweeperPlusPlusLevelEditor editor;
	
	public CustomizationWindowsHandler(MinesweeperPlusPlusLevelEditor initEditor)
	{
		editor = initEditor;
	}
	
	public void windowClosing(WindowEvent we)
	{
		editor.closeCustomizationWindows();
	}
	
	// WE WILL NOT BE OVERRIDING THESE METHODS
	public void windowActivated(WindowEvent we)		{}
	public void windowClosed(WindowEvent we)		{}
	public void windowDeiconified(WindowEvent we) 	{}
	public void windowIconified(WindowEvent we) 	{}
	public void windowDeactivated(WindowEvent we) 	{}
	public void windowOpened(WindowEvent we)		{}
}