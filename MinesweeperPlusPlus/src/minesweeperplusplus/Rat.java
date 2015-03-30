package minesweeperplusplus;

import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;
import minesweeperplusplus.MinesweeperPlusPlusGameDataModel;

public class Rat extends Sprite
{
	public Rat (SpriteType initSpriteType, float initX, float initY,
			float initVx, float initVy, String initState) {
		// INIT ALL THE Sprite STUFF
		super(initSpriteType, initX, initY, initVx, initVy, initState);
	}
	
	public void update (MiniGame game)
	{
		MinesweeperPlusPlusGameDataModel data = (MinesweeperPlusPlusGameDataModel)game.getDataModel();
		
		// In the MinesweeperPlusPlusGameDataModel class, have a vector of Rat Sprites and Cheese Sprites. Figure out which cheese is 
		// closest to each Rat Sprite, in the same way you did in the ZombiquariumDataModel's findClosestBrain method. Then determine the 
		// current distance (in tiles) in both the horizontal and vertical directions and randomly choose which direction the Rat will 
		// move with each tile-advance by using the Math class's getRandom method, making a result less than 0.5 be, say, horizontal and a 
		// result greater than or equal to 0.5 be vertical. Have all of a given board's Rats' vX and vY velocities be the magnitude of the 
		// user-input velocity (in tiles/frame) in the horizontal or vertical direction, positive for right and for down. 
		
		// Within this method, use the Sprite class's aabbsOverlap method for each Rat Sprite and the Cheese Sprite on the tile on which 
		// the Rat landed.
	}
}

