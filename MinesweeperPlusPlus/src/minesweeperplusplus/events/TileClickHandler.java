package minesweeperplusplus.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import minesweeperplusplus.MinesweeperPlusPlusGame;
import minesweeperplusplus.MinesweeperPlusPlusGameDataModel;
import minesweeperplusplus.Tile;

public class TileClickHandler implements ActionListener
{
	private MinesweeperPlusPlusGameDataModel data;
	private int tileRowIndex;
	private int tileColIndex;
	
	public TileClickHandler(MinesweeperPlusPlusGameDataModel initData, int initTileRowIndex,
			int initTileColIndex)
	{
		data = initData;
		tileRowIndex = initTileRowIndex;
		tileColIndex = initTileColIndex;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		Tile[][] board = data.getGameBoard();
		Tile tile = board[tileRowIndex][tileColIndex];
		String buttonPressed = tile.getMouseButtonPressed();
		
		if (buttonPressed.equals("LEFT"))
		{
			if (tile.getState().equals(MinesweeperPlusPlusGame.VISIBLE_STATE))
			{
				if (tile.getCheese().getState() == MinesweeperPlusPlusGame.VISIBLE_STATE)
				{
					tile.getCheese().setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
				}
				tile.setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
				if (tile.getNumBorderingMines().equals("0")
						&& ((tile.getMine()).getState()).equals(MinesweeperPlusPlusGame.INVISIBLE_STATE))
					data.revealNeighboringBlanks(tile, tileRowIndex, tileColIndex);
				if ((tile.getMine()).getState().equals(MinesweeperPlusPlusGame.INTACT_STATE))
				{
					tile.getCheese().setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
					data.markForExplosion(tile.getMine());
				}
			}
		}
		else if (buttonPressed.equals("RIGHT"))
		{
			if (tile.getState().equals(MinesweeperPlusPlusGame.VISIBLE_STATE))
			{
				if(tile.getCheese().getState().equals(MinesweeperPlusPlusGame.INVISIBLE_STATE))
				{
					tile.getCheese().setState(MinesweeperPlusPlusGame.VISIBLE_STATE);
					data.addCheeseTile(tile);
					data.decrementMineCounter();
				}
				else
				{
					tile.getCheese().setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
					data.removeCheeseTile(tile);
					data.incrementMineCounter();
				}
			}
		}
	}
}
