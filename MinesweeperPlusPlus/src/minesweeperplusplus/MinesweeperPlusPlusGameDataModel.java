package minesweeperplusplus;

import java.awt.Point;
import java.util.Vector;

import javax.swing.JOptionPane;

import minesweeperplusplus.events.CustomLevelGameBoardPanelHandler;
import minesweeperplusplus.events.GamePanelHandler;
import minesweeperplusplus.events.TileClickHandler;
import mini_game.MiniGame;
import mini_game.MiniGameDataModel;
import mini_game.Sprite;

public class MinesweeperPlusPlusGameDataModel extends MiniGameDataModel
{
	private MinesweeperPlusPlusGame game;
	private MPPLevel currentLevel;
	private Tile[][] gameBoard;
	private int maxRows;
	private int maxCols;
	private int tileWidth;
	private int tileHeight;
	private Vector<Tile> playableTiles;
	private Vector<Sprite> minesToExplode;
	private Vector<Tile> mineTiles;
	private Vector<Tile> mineTilesToRemove;
	private Vector<Tile> mineCandidates;
	private Vector<Tile> cheeseTiles;
	private Vector<Tile> cheeseTilesToClear;
	private Vector<Rat> rats;
	private Vector<Rat> ratsToRemove;
	private Tile[][] cells;
	private Tile ratSpawnLocation;
	private int ratSpawnFrameCounter;
	private float secsSinceSpawn;
	private boolean allTilesRevealed;
	private int frameCounter;
	private int gameTimeInSecs;
	private int mineCounter;
	
	public MinesweeperPlusPlusGameDataModel(MinesweeperPlusPlusGame initGame)
	{
		game = initGame;
		maxRows = MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_ROWS;
		maxCols = MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_COLS;
		playableTiles = new Vector<Tile>();
		minesToExplode = new Vector<Sprite>();
		mineTiles = new Vector<Tile>();
		mineTilesToRemove = new Vector<Tile>();
		mineCandidates = new Vector<Tile>();
		cheeseTiles = new Vector<Tile>();
		cheeseTilesToClear = new Vector<Tile>();
		rats = new Vector<Rat>();
		ratsToRemove = new Vector<Rat>();
		ratSpawnFrameCounter = 0;
		secsSinceSpawn = 0;
		allTilesRevealed = false;
		frameCounter = 0;
		gameTimeInSecs = 0;
		mineCounter = 0;
	}
	
	public Tile[][] getGameBoard()
	{
		return gameBoard;
	}
	
	public Tile[][] getCells()
	{
		return cells;
	}
	
	public Vector<Rat> getRats()
	{
		return rats;
	}
	
	public int getMaxRows()
	{
		return maxRows;
	}
	
	public int getMaxCols()
	{
		return maxCols;
	}
	
	public int getTileHeight()
	{
		return tileHeight;
	}
	
	public int getTileWidth()
	{
		return tileWidth;
	}
	
	public Vector<Tile> getMineCandidates()
	{
		return mineCandidates;
	}
	
	public Vector<Tile> getPlayableTiles()
	{
		return playableTiles;
	}
	
	public Vector<Tile> getCheeseTiles()
	{
		return cheeseTiles;
	}
	
	public int getGameTimeInSecs()
	{
		return gameTimeInSecs;
	}
	
	public int getMineCounter()
	{
		return mineCounter;
	}
	
	public void decrementMineCounter()
	{
		mineCounter--;
	}
	
	public void incrementMineCounter()
	{
		mineCounter++;
	}
	
	public MinesweeperPlusPlusGame getGame()
	{
		return game;
	}
	
	public void initGameBoard()
	{
		currentLevel = game.getEditor().getCurrentLevel();
		mineCounter = currentLevel.getNumMines();
		tileWidth = game.getEditor().getLittleTileWidth() + 12;
		tileHeight = game.getEditor().getLittleTileHeight() + 12;
		
		LittleTile[][] littleGameBoard = currentLevel.getCustomLittleGameBoard();
		
		gameBoard = new Tile[maxRows][maxCols];
		cells = new Tile[maxRows][maxCols];
		
		float xCoord;
		float yCoord;
		
		for (int row = 0; row < maxRows; row++) 
		{
			for (int col = 0; col < maxCols; col++) 
			{
				xCoord = col * tileWidth;
				yCoord = (row * tileHeight) + tileHeight;
				
				gameBoard[row][col] = new Tile(getSpriteType(MinesweeperPlusPlusGame.TILE_TYPE), 
						xCoord, yCoord, (float)0, (float)0, littleGameBoard[row][col].getState(), this);

				cells[row][col] = new Tile(getSpriteType(MinesweeperPlusPlusGame.TILE_TYPE), 
						xCoord, yCoord, (float)0, (float)0, MinesweeperPlusPlusGame.INVISIBLE_STATE, this);
				
				if (gameBoard[row][col].getState().equals("VISIBLE_STATE"))
				{
					playableTiles.add(gameBoard[row][col]);
					TileClickHandler tch = new TileClickHandler(this, row, col);
					(gameBoard[row][col]).setActionListener(tch);
					mineCandidates.add(gameBoard[row][col]);
					cells[row][col].setState(MinesweeperPlusPlusGame.INVISIBLE_OUTLINED_STATE);
				}
			}
		}
		
		/* This information is already gotten from littleGameBoard
		int ratSpawnRow = (int)(currentLevel.getRatSpawnLocation().getY() - tileHeight)/tileHeight;
		int ratSpawnCol = (int)currentLevel.getRatSpawnLocation().getX()/tileWidth;
		gameBoard[ratSpawnRow][ratSpawnCol] = new Tile(getSpriteType(MinesweeperPlusPlusGame.TILE_TYPE),
				currentLevel.getRatSpawnLocation().getX(), currentLevel.getRatSpawnLocation().getY(),
				(float)0, (float)0, MinesweeperPlusPlusGame.RAT_SPAWN_STATE, this);*/
		
		int i = 0;
		int m = 0;

		while (i < currentLevel.getNumMines())
		{
			m = (int)(Math.random() * mineCandidates.size());
			
			if (!mineTiles.contains(mineCandidates.elementAt(m)))
			{
				mineTiles.add(mineCandidates.elementAt(m));
				i++;
			}
		}
		
		for (Tile mineTile : mineTiles)
		{
			mineTile.getMine().setState(MinesweeperPlusPlusGame.INTACT_STATE);
		}
		
		Tile tileToTest;
		int prevRow;
		int nextRow;
		int prevCol;
		int nextCol;
		
		for (int row = 0; row < maxRows; row++) 
		{
			for (int col = 0; col < maxCols; col++) 
			{
				tileToTest = gameBoard[row][col];
				
				if (!mineTiles.contains(tileToTest))
				{
					prevRow = row - 1;
					nextRow = row + 1;
					prevCol = col - 1;
					nextCol = col + 1;
					
					if ((prevRow >= 0 && prevCol >= 0) && ((gameBoard[prevRow][prevCol].getMine()).getState().equals(MinesweeperPlusPlusGame.INTACT_STATE))) 
						tileToTest.incrementNumBorderingMines();
						
					if ((prevRow >= 0) && ((gameBoard[prevRow][col].getMine()).getState().equals(MinesweeperPlusPlusGame.INTACT_STATE)))
						tileToTest.incrementNumBorderingMines();
					
					if ((prevRow >= 0) && (nextCol <= (maxCols - 1)) && ((gameBoard[prevRow][nextCol].getMine()).getState().equals(MinesweeperPlusPlusGame.INTACT_STATE)))
						tileToTest.incrementNumBorderingMines();
					
					if ((prevCol >= 0) && ((gameBoard[row][prevCol].getMine()).getState().equals(MinesweeperPlusPlusGame.INTACT_STATE)))
						tileToTest.incrementNumBorderingMines();
					
					if ((nextCol <= (maxCols - 1)) && ((gameBoard[row][nextCol].getMine()).getState().equals(MinesweeperPlusPlusGame.INTACT_STATE)))
						tileToTest.incrementNumBorderingMines();
					
					if ((nextRow <= (maxRows - 1) && (prevCol >= 0)) && ((gameBoard[nextRow][prevCol].getMine()).getState().equals(MinesweeperPlusPlusGame.INTACT_STATE)))
						tileToTest.incrementNumBorderingMines();
					
					if ((nextRow <= (maxRows - 1)) && ((gameBoard[nextRow][col].getMine()).getState().equals(MinesweeperPlusPlusGame.INTACT_STATE)))
						tileToTest.incrementNumBorderingMines();
					
					if ((nextRow <= (maxRows - 1) && nextCol <= (maxCols - 1)) && ((gameBoard[nextRow][nextCol].getMine()).getState().equals(MinesweeperPlusPlusGame.INTACT_STATE)))
						tileToTest.incrementNumBorderingMines();
				}
			}
		}
		
		int ratSpawnRow = (int)(currentLevel.getRatSpawnLocation().getY()/game.getEditor().getLittleTileHeight());
		int ratSpawnCol = (int)(currentLevel.getRatSpawnLocation().getX()/game.getEditor().getLittleTileWidth());
		ratSpawnLocation = new Tile(getSpriteType(MinesweeperPlusPlusGame.TILE_TYPE), 
				ratSpawnCol * tileWidth, (ratSpawnRow * tileHeight) + tileHeight, (float)0, (float)0, MinesweeperPlusPlusGame.RAT_SPAWN_STATE, this); 
		
		initClickHandler();	
	}
	
	public void initClickHandler()
	{
		GamePanelHandler gPH = new GamePanelHandler(this);
		((MinesweeperPlusPlusGamePanel)(game.getCanvas())).addMouseListener(gPH);
	}
	
	public void markForExplosion(Sprite initMineToExplode)
	{
		Sprite mineToExplode = initMineToExplode;
		mineToExplode.setState(MinesweeperPlusPlusGame.EXPLODING_STATE);
		minesToExplode.add(mineToExplode);
	}
	
	public void revealNeighboringBlanks(Tile centerTile, int centerTileRow, int centerTileCol)
	{
		Tile[][] board = getGameBoard();
		
		Tile tileToTest;
		
		// have to test to make sure these indexes don't
		// go out of the bounds of the game board (that
		// the corresponding tiles are visible?)
		int previousRow = centerTileRow - 1;
		int nextRow = centerTileRow + 1;
		int previousCol = centerTileCol - 1;
		int nextCol = centerTileCol + 1;
		
		if (previousCol >= 0)
		{
			tileToTest = board[centerTileRow][previousCol];
			if (tileToTest.getState().equals(MinesweeperPlusPlusGame.VISIBLE_STATE)
					&& ((tileToTest.getCheese()).getState()).equals(MinesweeperPlusPlusGame.INVISIBLE_STATE)
					&& ((tileToTest.getMine()).getState()).equals(MinesweeperPlusPlusGame.INVISIBLE_STATE))
			{
				tileToTest.setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
				if ((tileToTest.getNumBorderingMines()).equals("0"))
				{
					revealNeighboringBlanks(tileToTest, centerTileRow, previousCol);
					/*tilesToReveal.add(tileToTest);
					tilePositions.add(new Point(previousCol, centerTileRow));*/
				}
			}
		}
		
		if (previousRow >= 0)
		{
			tileToTest = board[previousRow][centerTileCol];
			if (tileToTest.getState().equals(MinesweeperPlusPlusGame.VISIBLE_STATE)
					&& ((tileToTest.getCheese()).getState()).equals(MinesweeperPlusPlusGame.INVISIBLE_STATE)
					&& ((tileToTest.getMine()).getState()).equals(MinesweeperPlusPlusGame.INVISIBLE_STATE))
			{
				tileToTest.setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
				if ((tileToTest.getNumBorderingMines()).equals("0"))
				{
					revealNeighboringBlanks(tileToTest, previousRow, centerTileCol);
					/*tilesToReveal.add(tileToTest);
					tilePositions.add(new Point(centerTileCol, previousRow));*/
				}
			}
		}
		
		if (nextCol < maxCols)
		{
			tileToTest = board[centerTileRow][nextCol];
			if (tileToTest.getState().equals(MinesweeperPlusPlusGame.VISIBLE_STATE)
					&& ((tileToTest.getCheese()).getState()).equals(MinesweeperPlusPlusGame.INVISIBLE_STATE)
					&& ((tileToTest.getMine()).getState()).equals(MinesweeperPlusPlusGame.INVISIBLE_STATE))
			{	
				tileToTest.setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
				if ((tileToTest.getNumBorderingMines()).equals("0"))
				{
					revealNeighboringBlanks(tileToTest, centerTileRow, nextCol);
					/*tilesToReveal.add(tileToTest);
					tilePositions.add(new Point(nextCol, centerTileRow));*/
				}
			}
		}
		
		if (nextRow < maxRows)
		{
			tileToTest = board[nextRow][centerTileCol];
			if (tileToTest.getState().equals(MinesweeperPlusPlusGame.VISIBLE_STATE)
					&& ((tileToTest.getCheese()).getState()).equals(MinesweeperPlusPlusGame.INVISIBLE_STATE)
					&& ((tileToTest.getMine()).getState()).equals(MinesweeperPlusPlusGame.INVISIBLE_STATE))
			{	
				tileToTest.setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
				if ((tileToTest.getNumBorderingMines()).equals("0"))
				{
					revealNeighboringBlanks(tileToTest, nextRow, centerTileCol);
					/*tilesToReveal.add(tileToTest);
					tilePositions.add(new Point(centerTileCol, nextRow));*/
				}
			}
		}
		
		/*for (int row = previousRow; row <= nextRow; row++)
		{
			for (int col = previousCol; col <= nextCol; col++)
			{
				Tile tileToTest = board[row][col];
				
				if ((tileToTest.getMine()).getState().equals(MinesweeperPlusPlusGame.INVISIBLE_STATE)
						&& tileToTest.getNumBorderingMines().equals("0"))
				{
					tileToTest.setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
					//revealNeighboringBlanks(tileToTest, row, col);
				}
			}
		}*/
	}
	public void addCheeseTile(Tile tileToAdd)
	{
		cheeseTiles.add(tileToAdd);
	}
	
	public void removeCheeseTile(Tile tileToRemove)
	{
		if (cheeseTiles.contains(tileToRemove))
		{
			cheeseTiles.remove(tileToRemove);
		}
	}
	
	public void spawnRat()
	{
		Rat rat;
		String initRatState;
		
		if (Math.random() < 0.5)
		{
			initRatState = MinesweeperPlusPlusGame.RAT_LEFT_STATE;
		}
		else
		{
			initRatState = MinesweeperPlusPlusGame.RAT_RIGHT_STATE;
		}
		
		rat = new Rat(getSpriteType(MinesweeperPlusPlusGame.RAT_TYPE), 
				ratSpawnLocation.getX(), ratSpawnLocation.getY(),
				(float)0, (float)0, initRatState);
		rats.add(rat);
		
		/* Don't need this --- have rat move randomly, not necessarily towards the 
		board, if there's no cheese
		float distanceToTile;
		float distanceToNearestTile;
		Tile nearestTile;
		
		// if there is no cheese on the board, figure out which playable tile is
		// nearest to spawned rat
		if (cheeseTiles.isEmpty())
		{
			distanceToNearestTile = ratSpawnLocation.calculateDistanceToSprite(playableTiles.get(0));
			nearestTile = playableTiles.get(0);
			for (int i = 1; i < playableTiles.size(); i++)
			{
				distanceToTile = ratSpawnLocation.calculateDistanceToSprite(playableTiles.get(i));
				if (distanceToTile < distanceToNearestTile)
				{
					distanceToNearestTile = distanceToTile;
					nearestTile = playableTiles.get(i);
				}
			}
		}
		// else figure out which cheese is nearest to spawned rat
		else
		{
			distanceToNearestTile = ratSpawnLocation.calculateDistanceToSprite(cheeseTiles.get(0));
			nearestTile = cheeseTiles.get(0);
			for (int i = 1; i < cheeseTiles.size(); i++)
			{
				distanceToTile = ratSpawnLocation.calculateDistanceToSprite(cheeseTiles.get(i));
				if (distanceToTile < distanceToNearestTile)
				{
					distanceToNearestTile = distanceToTile;
					nearestTile = cheeseTiles.get(i);
				}
			}
		}
		
		// if rat spawn location is on the left side of the nearest cheese or nearest
		// tile if there is no cheese, spawn rat facing right
		if (ratSpawnLocation.getX() <= nearestTile.getX())
		{
			rat = new Rat(getSpriteType(MinesweeperPlusPlusGame.RAT_TYPE), 
				ratSpawnLocation.getX(), ratSpawnLocation.getY() + tileHeight, 
				(float)0, (float)0, MinesweeperPlusPlusGame.RAT_RIGHT_STATE);
			if (ratSpawnLocation.getX() == nearestTile.getX())
			{
				rat.initTowardBoard(0, tileWidth);
			}
			else
			{
				rat.initTowardBoard(1, tileWidth);
			}
			
			if (!cheeseTiles.isEmpty())
			{
				rat.setTargetCheeseTile(nearestTile);
			}
			rats.add(rat);
		}
		// else spawn rat facing left
		else
		{
			rat = new Rat(getSpriteType(MinesweeperPlusPlusGame.RAT_TYPE), 
					ratSpawnLocation.getX(), ratSpawnLocation.getY() + tileHeight, 
					(float)0, (float)0, MinesweeperPlusPlusGame.RAT_LEFT_STATE);
			rat.initTowardBoard(-1, tileWidth);
			if (!cheeseTiles.isEmpty())
			{
				rat.setTargetCheeseTile(nearestTile);
			}
			rats.add(rat);
		}*/
	}
	
	public void markCheeseToBeEaten(Tile tileToClear)
	{
		cheeseTilesToClear.add(tileToClear);
	}
	
	public boolean isCheeseMarkedToBeEaten(Tile tileToCheck)
	{
		return cheeseTilesToClear.contains(tileToCheck);
	}
	
	public boolean hasCheese(Tile tileToCheck)
	{
		return cheeseTiles.contains(tileToCheck);
	}
	
	public void markRatToBeRemoved(Rat ratToRemove)
	{
		ratsToRemove.add(ratToRemove);
	}
	
	public void markMineTileToBeRemoved(Tile mineTileToRemove)
	{
		mineTilesToRemove.add(mineTileToRemove);
	}
	
	public void clearCell(Tile cellToClear)
	{
		mineTiles.remove(cellToClear);
		cellToClear.setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
		cellToClear.getMine().setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
		int row = (int)cellToClear.getY()/tileHeight - 1;
		int col = (int)cellToClear.getX()/tileWidth;
		
		for (int i = row - 1; i <= row + 1; i++)
		{
			for (int j = col - 1; j <= col + 1; j++)
			{
				if (!(i == row && j == col))
				{
					for (Tile playableTile : playableTiles)
					{
						if (playableTile.getY()/tileHeight - 1 == i && playableTile.getX()/tileWidth == j)
						{
							gameBoard[i][j].decrementNumBorderingMines();
							break;
						}
					}
				}
			}
		}
	}
	
	public void checkMousePressOnSprites(MiniGame game, int x, int y)
	{
		
	}
	
	public void reset(MiniGame game)
	{
		playableTiles.clear();
		minesToExplode.clear();
		mineTiles.clear();
		mineTilesToRemove.clear();
		mineCandidates.clear();
		cheeseTiles.clear();
		cheeseTilesToClear.clear();
		rats.clear();
		ratsToRemove.clear();
		ratSpawnFrameCounter = 0;
		allTilesRevealed = false;
		frameCounter = 0;
		gameTimeInSecs = 0;
		mineCounter = 0;
		
		initGameBoard();
		
		beginGame();
	}
	
	public void endGame(boolean gameWon)
	{
		for (int row = 0; row < maxRows; row++) 
		{
			for (int col = 0; col < maxCols; col++) 
			{
				if (gameBoard[row][col].getTileListener() != null)
					gameBoard[row][col].setActionListener(null);
			}
		}
		
		String message = "";
		
		if (gameWon)
		{
			message = "Nice going! You won!";
		}
		else
		{
			message = "Sorry, you lost!";
		}
		
		JOptionPane.showMessageDialog(((MinesweeperPlusPlusGame)game).getWindow(), message);
	}
	
	public void updateAll(MiniGame game)
	{
		frameCounter++;
		
		if (frameCounter == 30)
		{
			gameTimeInSecs++;
			frameCounter = 0;
		}
		
		
		if (allTilesRevealed)
		{
			endGameAsWin();
			
			endGame(true);
		}
		
		allTilesRevealed = true;
		
		for (Tile playableTile : playableTiles)
		{
			if (!mineTiles.contains(playableTile)
					&& !playableTile.getState().equals(MinesweeperPlusPlusGame.INVISIBLE_STATE))
			{
				allTilesRevealed = false;
			}
		}
		
		if ((minesToExplode.size() == 1 && minesToExplode.elementAt(0).getState().equals(MinesweeperPlusPlusGame.INITIAL_MINE_EXPLODED_STATE))
				|| (minesToExplode.size() > 1 && minesToExplode.elementAt(1).getState().equals(MinesweeperPlusPlusGame.EXPLODED_STATE)))
		{	
			endGameAsLoss();
			
			endGame(false);
		}
		
		if (minesToExplode.size() == 1)
		{
			minesToExplode.elementAt(0).setState(MinesweeperPlusPlusGame.INITIAL_MINE_EXPLODED_STATE);
		    
			for (Tile mineTile : mineTiles)
			{
				if (mineTile.getMine().getState().equals(MinesweeperPlusPlusGame.INTACT_STATE))
				{
					mineTile.getCheese().setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
					markForExplosion(mineTile.getMine());
				}
			}
			/*for (int row = 0; row < maxRows; row++) 
			{
				for (int col = 0; col < maxCols; col++) 
				{
					if ((gameBoard[row][col].getMine()).getState().equals(MinesweeperPlusPlusGame.INTACT_STATE))
					{
						gameBoard[row][col].getCheese().setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
						markForExplosion(gameBoard[row][col].getMine());
					}	
				}
			}*/
		}
		else if (minesToExplode.size() > 1)
		{
			for (int i = 1; i < minesToExplode.size(); i++)
			{
				minesToExplode.elementAt(i).setState(MinesweeperPlusPlusGame.EXPLODED_STATE);
			}
		}
		
		secsSinceSpawn = ratSpawnFrameCounter/MinesweeperPlusPlusGame.FRAME_RATE;
		
		if (1/secsSinceSpawn == currentLevel.getRatSpawnRate())
		{
			spawnRat();
			ratSpawnFrameCounter = -1;
			secsSinceSpawn = -1;
		}
		
		ratSpawnFrameCounter++;
		
		for (Tile cheeseTileToClear : cheeseTilesToClear)
		{
			cheeseTileToClear.getCheese().setState(MinesweeperPlusPlusGame.INVISIBLE_STATE);
			cheeseTiles.remove(cheeseTileToClear);
		}
		
		cheeseTilesToClear.clear();
		
		for (Rat ratToRemove : ratsToRemove)
		{
			rats.remove(ratToRemove);
		}
		
		ratsToRemove.clear();
		
		for (Tile mineTileToRemove : mineTilesToRemove)
		{
			clearCell(mineTileToRemove);
		}
		
		mineTilesToRemove.clear();
		
		updateRats();
	}
	
	public void updateRats()
	{
		for (Rat rat : rats)
		{
			if (rat.isDead())
			{
				rat.decrementDeadCounter();
				
				if (rat.getDeadCounter() == 0)
				{
					markRatToBeRemoved(rat);
					for (Tile mineTile : mineTiles)
					{
						if (mineTile.aabbsOverlap(rat))
						{
							markMineTileToBeRemoved(mineTile);
							break;
						}
					}
				}
			}
			else if (rat.isGoingToDie())
			{
				rat.decrementDyingCounter();
				
				if (rat.getState().equals(MinesweeperPlusPlusGame.RAT_LEFT_STATE))
				{
					rat.setState(MinesweeperPlusPlusGame.EXPLODING_RAT_LEFT_STATE);
				}
				else
				{
					rat.setState(MinesweeperPlusPlusGame.EXPLODING_RAT_RIGHT_STATE);
				}
				
				if (rat.getDyingCounter() == 0)
				{
					for (Tile playableTile : playableTiles)
					{
						if (playableTile.aabbsOverlap(rat))
						{
							if (mineTiles.contains(playableTile))
							{
								rat.setState(MinesweeperPlusPlusGame.EXPLODED_RAT_STATE);
								rat.markAsDead();
								break;
							}
							else
							{
								if (rat.getState().equals(MinesweeperPlusPlusGame.EXPLODING_RAT_LEFT_STATE))
								{
									rat.setState(MinesweeperPlusPlusGame.RAT_LEFT_STATE);
								}
								else
								{
									rat.setState(MinesweeperPlusPlusGame.RAT_RIGHT_STATE);
								}
								
								rat.resetDyingCounter();
								mineCounter++;
							}
						}
					}
				}
			}
			else
			{
				rat.incrementMoveCounter();
				
				// change this back to 150 when you're done testing?
				if (rat.getMoveCounter() == 30)
				{
					if (!cheeseTiles.isEmpty())
					{
						rat.updateTargetCheeseTile(this);
					}
					rat.goForCheese(this);
					rat.update(game);
					
					for (Tile mineTile : mineTiles)
					{
						if (mineTile.aabbsOverlap(rat) 
							&& mineTile.getCheese().getState().equals(MinesweeperPlusPlusGame.INVISIBLE_STATE))
						{
							markForExplosion(mineTile.getMine());
							markRatToBeRemoved(rat);
						}
					}
				}
			}
		}
	}
	
	public void updateDebugText(MiniGame game)
	{
		
	}
}