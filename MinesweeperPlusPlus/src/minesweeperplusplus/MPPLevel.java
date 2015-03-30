package minesweeperplusplus;

public class MPPLevel
{
	private String levelName;
	private int numMines;
	private float ratSpawnRate;
	private LittleTile ratSpawnLocation;
	private LittleTile[][] customLittleGameBoard;
	
	public MPPLevel(String initLevelName, int initNumMines, float initRatSpawnRate, 
			LittleTile initRatSpawnLocation, LittleTile[][] initCustomLittleGameBoard)
	{
		setLevelName(initLevelName);
		setNumMines(initNumMines);
		setRatSpawnRate(initRatSpawnRate);
		setRatSpawnLocation(initRatSpawnLocation);
		setCustomLittleGameBoard(initCustomLittleGameBoard);
	}
	
	public String getLevelName()                   { return levelName; }
	public void setLevelName(String initLevelName) { levelName = initLevelName; }
	
	public int getNumMines()                  { return numMines; }
	public void setNumMines(int initNumMines) { numMines = initNumMines; }
	
	public float getRatSpawnRate()                         { return ratSpawnRate; }
	public void setRatSpawnRate(float initRatSpawnRate) { ratSpawnRate = initRatSpawnRate; }
	
	public LittleTile getRatSpawnLocation()	                       { return ratSpawnLocation; }
	public void setRatSpawnLocation(LittleTile initRatSpawnLocation) { ratSpawnLocation = initRatSpawnLocation; }
	
	public LittleTile[][] getCustomLittleGameBoard()                               
	{ 
		return customLittleGameBoard; 
	}
	public void setCustomLittleGameBoard(LittleTile[][] initCustomLittleGameBoard)
	{ 
		customLittleGameBoard = initCustomLittleGameBoard; 
	}
}
