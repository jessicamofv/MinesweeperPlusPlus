package minesweeperplusplus.events;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import minesweeperplusplus.MinesweeperPlusPlusApplication;

public class LogInWindowHandler implements WindowListener
{
	private MinesweeperPlusPlusApplication application;

	public LogInWindowHandler(MinesweeperPlusPlusApplication initApplication)
	{
		application = initApplication;
	}

	public void windowClosing(WindowEvent we)	
	{
		application.killApplication();
	}
	
	// WE WILL NOT BE OVERRIDING THESE METHODS
	public void windowActivated(WindowEvent we)		{}
	public void windowClosed(WindowEvent we)		{}
	public void windowDeiconified(WindowEvent we) 	{}
	public void windowIconified(WindowEvent we) 	{}
	public void windowDeactivated(WindowEvent we) 	{}
	public void windowOpened(WindowEvent we)		{}
}

