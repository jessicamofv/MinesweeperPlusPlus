package minesweeperplusplus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mini_game.Sprite;
import mini_game.SpriteType;

public class Tile extends Sprite
{
	private ActionListener tileListener;
	
	private Sprite cheese;
	
	private Sprite mine;
	
	private String numBorderingMines;
	
	private String mouseButtonPressed;
	
	private MinesweeperPlusPlusGameDataModel data;
	
	public Tile(SpriteType initSpriteType, float initX, float initY, float initVx, float initVy,
			String initState, MinesweeperPlusPlusGameDataModel initData)
	{
		super(initSpriteType, initX, initY, initVx, initVy, initState);
		
		data = initData;
		
		cheese = new Sprite(data.getSpriteType(MinesweeperPlusPlusGame.CHEESE_TYPE), initX, initY, initVx,
						initVy, MinesweeperPlusPlusGame.INVISIBLE_STATE);
		
		mine = new Sprite(data.getSpriteType(MinesweeperPlusPlusGame.MINE_TYPE), initX, initY, initVx, 
						initVy, MinesweeperPlusPlusGame.INVISIBLE_STATE);
		
		numBorderingMines = "0";
		
		mouseButtonPressed = "";
	}
	
	public ActionListener getTileListener()
	{
		return tileListener;
	}
	
	public Sprite getCheese()
	{
		return cheese;
	}
	
	public Sprite getMine()
	{
		return mine;
	}
	
	public String getNumBorderingMines()
	{
		return numBorderingMines;
	}
	
	public String getMouseButtonPressed()
	{
		return mouseButtonPressed;
	}
	
	public void incrementNumBorderingMines()
	{
		int num = Integer.parseInt(numBorderingMines);
		num++;
		numBorderingMines = "" + num;
	}
	
	public void decrementNumBorderingMines()
	{
		int num = Integer.parseInt(numBorderingMines);
		num--;
		if (num < 0)
		{
			num = 0;
		}
		numBorderingMines = "" + num;
	}
	
	public void setActionListener(ActionListener initListener)
	{
		tileListener = initListener;
	}
	
	public void fireEvent(String initMouseButtonPressed)
	{
		mouseButtonPressed = initMouseButtonPressed;
		
		if (tileListener != null)
		{
			ActionEvent ae;
			ae = new ActionEvent(this, getID(), spriteType.getSpriteTypeID());
			tileListener.actionPerformed(ae);
		}
	}
}
