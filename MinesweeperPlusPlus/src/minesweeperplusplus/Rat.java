package minesweeperplusplus;

import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;
import minesweeperplusplus.MinesweeperPlusPlusGameDataModel;

public class Rat extends Sprite
{
	private Tile targetCheeseTile;
	private int moveCounter;
	private boolean goingToDie;
	private int dyingCounter;
	private boolean dead;
	private int deadCounter;
	
	public Rat (SpriteType initSpriteType, float initX, float initY,
			float initVx, float initVy, String initState) {
		// INIT ALL THE Sprite STUFF
		super(initSpriteType, initX, initY, initVx, initVy, initState);
		moveCounter = 0;
		goingToDie = false;
		dyingCounter = 60;
		dead = false;
		deadCounter = 60;
		aabbWidth = 13;
		aabbHeight = 17;
		correctAABB();
	}
	
	public void setTargetCheeseTile(Tile initTargetCheeseTile)
	{
		targetCheeseTile = initTargetCheeseTile;
	}
	
	public int getMoveCounter()
	{
		return moveCounter;
	}
	
	public void incrementMoveCounter()
	{
		moveCounter++;
	}
	
	public void resetMoveCounter()
	{
		moveCounter = 0;
	}
	
	public boolean isGoingToDie()
	{
		return goingToDie;
	}
	
	public int getDyingCounter()
	{
		return dyingCounter;
	}
	
	public void decrementDyingCounter()
	{
		dyingCounter--;
	}
	
	public void resetDyingCounter()
	{
		dyingCounter = 60;
		goingToDie = false;
	}
	
	public boolean isDead()
	{
		return dead;
	}
	
	public void markAsDead()
	{
		goingToDie = false;
		dead = true;
	}
	
	public int getDeadCounter()
	{
		return deadCounter;
	}
	
	public void decrementDeadCounter()
	{
		deadCounter--;
	}
	
	public void correctAABB()
	{
		if (state.equals("RAT_LEFT_STATE"))
		{
			aabbX = 9;
			aabbY = 8;
		}
		else
		{
			aabbX = 5;
			aabbY = 8;
		}
	}
	
	/*public void initTowardBoard(int xDirection, int tileWidth)
	{
		// have rat move one tile
		vX = xDirection * tileWidth;
	}*/
	
	public void goForCheese(MinesweeperPlusPlusGameDataModel data)
	{
		if (targetCheeseTile != null)
		{
			if (!data.hasCheese(targetCheeseTile)
					|| data.isCheeseMarkedToBeEaten(targetCheeseTile))
			{
				targetCheeseTile = null;
				// have rats move randomly around board
				initRandomWanderingOfBoard(data);
			}
			else if (aabbsOverlap(targetCheeseTile))
			{
				data.markCheeseToBeEaten(targetCheeseTile);
				targetCheeseTile = null;
				vX = 0;
				vY = 0;
				goingToDie = true;
			}
			else
			{
				float xDistance = targetCheeseTile.getX() - x;
				float yDistance = targetCheeseTile.getY() - y;
				int colDistance = Math.round(xDistance/data.getTileWidth());
				int rowDistance = Math.round(yDistance/data.getTileHeight());
				if (colDistance != 0)
				{
					if (colDistance > 0)
					{
						vX = data.getTileWidth();
						vY = 0;
						if (state.equals(MinesweeperPlusPlusGame.RAT_LEFT_STATE))
						{
							state = MinesweeperPlusPlusGame.RAT_RIGHT_STATE;
						}
					}
					else
					{
						vX = -1 * data.getTileWidth();
						vY = 0;
						if (state.equals(MinesweeperPlusPlusGame.RAT_RIGHT_STATE))
						{
							state = MinesweeperPlusPlusGame.RAT_LEFT_STATE;
						}
					}
				}
				else
				{
					vX = 0;
					if (rowDistance != 0)
					{
						if (rowDistance > 0)
						{
							vY = data.getTileHeight();
						}
						else
						{
							vY = -1 * data.getTileHeight();
						}
					}
					else
					{
						vY = 0;
					}
				}
			}
		}
		else
		{
			if (!data.getCheeseTiles().isEmpty())
			{
				updateTargetCheeseTile(data);
			}
			else
			{
				// have rats move randomly around board
				initRandomWanderingOfBoard(data);
			}			
		}
	}
	
	public void updateTargetCheeseTile(MinesweeperPlusPlusGameDataModel data)
	{
		float distanceToNearestCheeseTile = calculateDistanceToSprite(data.getCheeseTiles().get(0));
		targetCheeseTile = data.getCheeseTiles().get(0);
		float distanceToCheeseTile;
		for (int i = 1; i < data.getCheeseTiles().size(); i++)
		{
			distanceToCheeseTile = calculateDistanceToSprite(data.getCheeseTiles().get(i));
			if (distanceToCheeseTile < distanceToNearestCheeseTile)
			{
				distanceToNearestCheeseTile = distanceToCheeseTile;
				targetCheeseTile = data.getCheeseTiles().get(i);
			}
		}
	}
	
	public void initRandomWanderingOfBoard(MinesweeperPlusPlusGameDataModel data)
	{
		int randomDirection = (int)(Math.random() * 4);
		if (randomDirection == 0)
		{
			vX = 0;
			vY = -1 * data.getTileHeight();
		}
		else if (randomDirection == 1)
		{
			vX = data.getTileWidth();
			vY = 0;
			if (state.equals(MinesweeperPlusPlusGame.RAT_LEFT_STATE))
			{
				state = MinesweeperPlusPlusGame.RAT_RIGHT_STATE;
			}
		}
		else if (randomDirection == 2)
		{
			vX = 0;
			vY = data.getTileHeight();
		}
		else
		{
			vX = -1 * data.getTileWidth();
			vY = 0;
			if (state.equals(MinesweeperPlusPlusGame.RAT_RIGHT_STATE))
			{
				state = MinesweeperPlusPlusGame.RAT_LEFT_STATE;
			}
		}
	}
	
	public void update (MiniGame game)
	{
		MinesweeperPlusPlusGameDataModel data = (MinesweeperPlusPlusGameDataModel)game.getDataModel();
		
		super.update(game);
		
		// actually, don't need to know when the rat is on the game board to have
		// him stay on the game board, because he doesn't need to stay on the board
		// if there's no cheese
		/*if (!onGameBoard)
		{
			for (int i = 0; i < data.getPlayableTiles().size(); i++)
			{
				// check if rat's current location is inside the game board
				if (data.getPlayableTiles().get(i).containsPoint(x, y))
				{
					onGameBoard = true;
					break;
				}
			}
		}*/
		
		resetMoveCounter();
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

