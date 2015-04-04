package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import minesweeperplusplus.MinesweeperPlusPlusGame;
import mini_game.MiniGameDataModel;

public class NewGameHandler implements ActionListener
{
	private MinesweeperPlusPlusGame game;
	private MiniGameDataModel data;
	
	public NewGameHandler(MinesweeperPlusPlusGame initGame, MiniGameDataModel initData)
	{
		game = initGame;
		data = initData;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if (data.inProgress())
		{
			data.endGameAsLoss();
		}
		data.reset(game);
	}
}
