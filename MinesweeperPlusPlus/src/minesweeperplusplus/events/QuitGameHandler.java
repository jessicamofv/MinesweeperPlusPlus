package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import minesweeperplusplus.MinesweeperPlusPlusGame;

public class QuitGameHandler implements ActionListener
{
	private MinesweeperPlusPlusGame game;
	
	public QuitGameHandler(MinesweeperPlusPlusGame initGame)
	{
		game = initGame;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		game.closeInGameWindow();
	}
}
