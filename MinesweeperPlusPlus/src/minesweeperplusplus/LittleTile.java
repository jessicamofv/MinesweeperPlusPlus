package minesweeperplusplus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mini_game.Sprite;
import mini_game.SpriteType;


public class LittleTile extends Sprite
{
	private ActionListener littleTileListener;
	
	public LittleTile (SpriteType initSpriteType, float initX, 	float initY, float initVx, float initVy,
							String initState)
	{
		super(initSpriteType, initX, initY, initVx, initVy, initState);
	}
	
	public void setActionListener(ActionListener initListener)
	{
		littleTileListener = initListener;
	}
	
	public void fireEvent()
	{
		if (littleTileListener != null)
		{
			ActionEvent ae;
			ae = new ActionEvent(this, getID(), spriteType.getSpriteTypeID());
			littleTileListener.actionPerformed(ae);
		}
	}
}
