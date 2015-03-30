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
	private Vector<Sprite> minesToExplode;
	private Vector<Tile> mineTiles;
	private Vector<Tile> mineCandidates;
	private Tile[][] cells;
	
	public MinesweeperPlusPlusGameDataModel(MinesweeperPlusPlusGame initGame)
	{
		game = initGame;
		maxRows = MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_ROWS;
		maxCols = MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_COLS;
		minesToExplode = new Vector<Sprite>();
		mineTiles = new Vector<Tile>();
		mineCandidates = new Vector<Tile>();
	}
	
	public Tile[][] getGameBoard()
	{
		return gameBoard;
	}
	
	public Tile[][] getCells()
	{
		return cells;
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
	
	public MinesweeperPlusPlusGame getGame()
	{
		return game;
	}
	
	public void initGameBoard()
	{
		currentLevel = game.getEditor().getCurrentLevel();
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
		
		for (int j = 0; j < mineTiles.size(); j++)
		{
			mineTiles.elementAt(j).getMine().setState(MinesweeperPlusPlusGame.INTACT_STATE);
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
	
	public void checkMousePressOnSprites(MiniGame game, int x, int y)
	{
		
	}
	
	public void reset(MiniGame game)
	{
		minesToExplode = new Vector<Sprite>();
		mineTiles = new Vector<Tile>();
		mineCandidates = new Vector<Tile>();
		initGameBoard();
		
		beginGame();
	}
	
	public void updateAll(MiniGame game)
	{
		if (this.inProgress() && minesToExplode.size() > 1 && 
				(minesToExplode.elementAt(1)).getState().equals(MinesweeperPlusPlusGame.EXPLODED_STATE))
		{	
			endGameAsLoss();
			
			for (int row = 0; row < maxRows; row++) 
			{
				for (int col = 0; col < maxCols; col++) 
				{
					if (gameBoard[row][col].getTileListener() != null)
						gameBoard[row][col].setActionListener(null);
				}
			}
			
			JOptionPane.showMessageDialog(((MinesweeperPlusPlusGame)game).getWindow(), "Sorry, you lost!");
		}
		
		if (minesToExplode.size() == 1)
		{
			minesToExplode.elementAt(0).setState(MinesweeperPlusPlusGame.INITIAL_MINE_EXPLODED_STATE);
		
			for (int row = 0; row < maxRows; row++) 
			{
				for (int col = 0; col < maxCols; col++) 
				{
					if ((gameBoard[row][col].getMine()).getState().equals(MinesweeperPlusPlusGame.INTACT_STATE))
					{
						markForExplosion(gameBoard[row][col].getMine());
					}	
				}
			}
		}
		else if (minesToExplode.size() > 1)
		{
			for (int i = 1; i < minesToExplode.size(); i++)
			{
				minesToExplode.elementAt(i).setState(MinesweeperPlusPlusGame.EXPLODED_STATE);
			}
		}
	}
	
	public void updateDebugText(MiniGame game)
	{
		
	}
}