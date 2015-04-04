package minesweeperplusplus;

import java.util.Vector;

import javax.swing.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import minesweeperplusplus.events.BackHandler;
import minesweeperplusplus.events.CancelHandler;
import minesweeperplusplus.events.CreateCustomLevelHandler;
import minesweeperplusplus.events.CustomLevelGameBoardPanelHandler;
import minesweeperplusplus.events.CustomizationWindowsHandler;
import minesweeperplusplus.events.DeleteLevelHandler;
import minesweeperplusplus.events.LevelEditorLogOutHandler;
import minesweeperplusplus.events.LevelScrollHandler;
import minesweeperplusplus.events.LittleTileClickHandler;
import minesweeperplusplus.events.MoreCustomLevelOptionsHandler;
import minesweeperplusplus.events.PlayLevelHandler;
import minesweeperplusplus.events.SaveLevelHandler;
import minesweeperplusplus.events.RSLSelectionPanelHandler;
import minesweeperplusplus.events.RatSpawnLocationSelectionHandler;
import mini_game.SpriteType;

public class MinesweeperPlusPlusLevelEditor
{
	public static final String LEVEL_FILENAMES_FILE = "./setup/Minesweeper++LevelFilenames.txt";
	public static final String FILE_DELIMITER = "-";
	public static String LITTLE_TILE_TYPE = "LITTLE_TILE_TYPE";
	public static String VISIBLE_STATE = "VISIBLE_STATE";
	public static String INVISIBLE_STATE = "INVISIBLE_STATE";
	public static String INVISIBLE_OUTLINED_STATE = "INVISIBLE_OUTLINED_STATE";
	public static String RAT_SPAWN_STATE = "RAT_SPAWN_STATE";
	private JFrame levelEditorWindow;
	private Vector<MPPLevel> levels;
	private Vector<String> levelFileNames;
	private String[] defaultLevelNames = { "Default Beginner", "Default Intermediate", 
			"Default Advanced"};
	private Vector<Vector<LittleTile>> defaultVisibleLittleTiles;
	private int levelIndex;
	private LittleTile[][] littleGameBoardDefaultBeginner;
	private LittleTile[][] littleGameBoardDefaultIntermediate;
	private LittleTile[][] littleGameBoardDefaultAdvanced;
	private LittleTile ratSpawnLocationDefaultBeginner;
	private LittleTile ratSpawnLocationDefaultIntermediate;
	private LittleTile ratSpawnLocationDefaultAdvanced;
	private MinesweeperPlusPlusLevelNamePanel levelNamePanel;
	private MinesweeperPlusPlusLevelEditorPanel littleGameBoardPanel;
	private MinesweeperPlusPlusLevelEditorPanel customLevelGameBoardPanel;
	private MinesweeperPlusPlusLevelEditorPanel ratSpawnLocationSelectionPanel;
	private LittleTile[][] customLevelGameBoard;
	private LittleTile[][] boardForRatSpawnLocationSelection;
	private Vector<LittleTile> visibleLittleTiles;
	private LittleTile ratSpawnLocation;
	public static int MAX_GAME_BOARD_ROWS = 24;
	public static int MAX_GAME_BOARD_COLS = 44;
	private JButton leftArrowButton;
	private JButton rightArrowButton;
	private ImageIcon leftArrowIcon;
	private ImageIcon rightArrowIcon;
	private JButton playLevelButton;
	private JButton deleteLevelButton;
	private JButton viewLevelStatsButton;
	private JFrame levelStatsWindow;
	private JButton backButton;
	private JButton createCustomLevelButton;
	private JFrame customLevelLayoutWindow;
	private JButton moreCustomLevelOptionsButton;
	private JFrame moreCustomLevelOptionsWindow;
	private JTextField levelNameField;
	private JTextField numMinesField;
	private JTextField ratSpawnRateRatsField;
	private JTextField ratSpawnRateSecsField;
	private JButton cancelButton;
	private JButton cancelButton2;
	private JButton saveLevelButton;
	private JButton logOutButton;
	private MinesweeperPlusPlusApplication application;
	private String currentUser;
	private SpriteType littleTileSpriteType;
	private int littleTileWidth;
	private int littleTileHeight;
	private MPPLevel levelToAdd;
	private MinesweeperPlusPlusGame game;
	
	// INITIALIZE LEVEL EDITOR WINDOW AND ITS COMPONENTS, STORING FILENAME OF FILE
	// FROM WHICH WILL COME INFORMATION FOR RENDERING LITTLE GAME BOARD VISUALS 
	// REPRESENTING LEVEL OPTIONS
	public MinesweeperPlusPlusLevelEditor(MinesweeperPlusPlusApplication initApplication)
	{
		application = initApplication;
		currentUser = application.getCurrentUser();
		initLevelEditor();
	}
	
	public MinesweeperPlusPlusApplication getApplication() { return application; }
	
	public String getCurrentUser() { return currentUser; }
	public void clearCurrentUser() { currentUser = null; }
	
	public void setLittleTileSpriteType(SpriteType initLittleTileSpriteType)
	{ 
		littleTileSpriteType = initLittleTileSpriteType; 
	}
	public SpriteType getLittleTileSpriteType()
	{ 
		return littleTileSpriteType;
	}

	public int getLittleTileWidth()  { return littleTileWidth;  }
	public int getLittleTileHeight() { return littleTileHeight; }
	
	public LittleTile[][] getCustomLevelGameBoard()  { return customLevelGameBoard;      }
	public JPanel getCustomLevelGameBoardPanel() 	 { return customLevelGameBoardPanel; }
	
	public Vector<LittleTile> getVisibleLittleTiles() { return visibleLittleTiles; }
	
	public LittleTile[][] getBoardForRatSpawnLocationSelection()
	{ 
		return boardForRatSpawnLocationSelection;
	}
	
	public MinesweeperPlusPlusLevelEditorPanel getRatSpawnLocationSelectionPanel()
	{ 
		return ratSpawnLocationSelectionPanel;
	}
	
	public void setRatSpawnLocation(LittleTile initRatSpawnLocation)
	{ 
		ratSpawnLocation = initRatSpawnLocation;
	}
	
	public JFrame getLevelEditorWindow()
	{
		return levelEditorWindow;
	}
	
	public JFrame getCustomLevelLayoutWindow()
	{
		return customLevelLayoutWindow;
	}
	
	public JFrame getMoreCustomLevelOptionsWindow()
	{
		return moreCustomLevelOptionsWindow;
	}
	
	public JTextField getLevelNameField()
	{
		return levelNameField;
	}
	
	public JTextField getNumMinesField()
	{
		return numMinesField;
	}
	
	public JTextField getRatSpawnRateRatsField()
	{
		return ratSpawnRateRatsField;
	}
	
	public JTextField getRatSpawnRateSecsField()
	{
		return ratSpawnRateSecsField;
	}
	
	public LittleTile getRatSpawnLocation()
	{
		return ratSpawnLocation;
	}
	
	public MPPLevel getCurrentLevel()
	{
		return levels.elementAt(levelIndex);
	}
	
	public void initLevelEditor()
	{
		customLevelLayoutWindow = new JFrame("Custom Level Layout");
		levels = new Vector<MPPLevel>();
		levelFileNames = new Vector<String>();
		levelIndex = 0;
		cancelButton = new JButton("CANCEL");
		cancelButton2 = new JButton("CANCEL");
		moreCustomLevelOptionsButton = new JButton("MORE CUSTOM LEVEL OPTIONS");
		visibleLittleTiles = new Vector<LittleTile>();
		defaultVisibleLittleTiles = new Vector<Vector<LittleTile>>();
		moreCustomLevelOptionsWindow = new JFrame("More Custom Level Options");
		saveLevelButton = new JButton("SAVE LEVEL");
		backButton = new JButton("BACK");
		levelNameField = new JTextField(15);
		numMinesField = new JTextField(15);
		ratSpawnRateRatsField = new JTextField(15);
		ratSpawnRateSecsField = new JTextField(15);
		initWindow();
		initLittleTileSpriteType();
		initDefaultLevels();
		initCustomLevels();
		layoutGUI();
		initHandlers();
	}
	
	// INITIALIZE LEVEL EDITOR WINDOW
	public void initWindow()
	{
		levelEditorWindow = new JFrame("Minesweeper++ Level Editor");
		levelEditorWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		levelEditorWindow.setResizable(false);
		levelEditorWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		levelEditorWindow.setLocation((int)(screen.getWidth() - levelEditorWindow.getWidth())/9, (int)(screen.getHeight() - levelEditorWindow.getHeight())/45);
	}
	
	// INITIALIZE THE ONLY SPRITE TYPE USED IN LEVEL EDITOR WINDOW, THE SPRITE TYPE
	// OF THE LITTLE TILE SPRITES THAT WILL BE USED IN RENDERING THE LITTLE GAME
	// BOARD VISUALS REPRESENTING LEVEL OPTIONS
	public void initLittleTileSpriteType()
	{
		littleTileSpriteType = new SpriteType(LITTLE_TILE_TYPE);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Image littleTileImage = tk.createImage("./setup/images/LittleTile.png");
		Image invisibleLittleTileImage = tk.createImage("./setup/images/InvisibleLittleTile.png");
		Image invisibleOutlinedLittleTileImage = 
				tk.createImage("./setup/images/InvisibleOutlinedLittleTile.png");
		Image littleRatSpawnLocationImage = tk.createImage("./setup/images/LittleRatSpawnLocation.png");
		MediaTracker tracker = new MediaTracker(levelEditorWindow);
		tracker.addImage(littleTileImage, 0);
		tracker.addImage(invisibleLittleTileImage, 1);
		tracker.addImage(invisibleOutlinedLittleTileImage, 2);
		tracker.addImage(littleRatSpawnLocationImage, 3);
		try { tracker.waitForAll(); }
		catch(InterruptedException ie) {}
		littleTileWidth = littleTileImage.getWidth(null);
		littleTileHeight = littleTileImage.getHeight(null);
		
		// IF A USER CHOOSES TO INCLUDE A LITTLE TILE AS PART OF THE CONFIGURATION
		// OF A LEVEL'S GAME BOARD, IT WILL BE MADE VISIBLE
		// IT WILL BE MADE INVISIBLE IF THE USER CHOOSES TO EXCLUDE IT
		littleTileSpriteType.addState(VISIBLE_STATE, littleTileImage);
		littleTileSpriteType.addState(INVISIBLE_STATE, invisibleLittleTileImage);
		littleTileSpriteType.addState(INVISIBLE_OUTLINED_STATE, invisibleOutlinedLittleTileImage);
		littleTileSpriteType.addState(RAT_SPAWN_STATE, littleRatSpawnLocationImage);
	}
	
	public void initDefaultLevels()
	{
		// DECLARE AND INITILIAZE EACH 2D ARRAY REPRESENTING ONE OF THREE DEFAULT GAME BOARDS
		littleGameBoardDefaultBeginner = initDefaultGameBoardTiles(9, 9, 0);
		littleGameBoardDefaultIntermediate = initDefaultGameBoardTiles(16, 16, 1);
		littleGameBoardDefaultAdvanced = initDefaultGameBoardTiles(16, 30, 2);
		
		// SET EACH DEFAULT LEVEL'S RAT SPAWN LOCATION TO TILE HALFWAY DOWN THE GAME SPACE AND HALF OF 
		// THE DISTANCE BETWEEN THE OUTER BOUNDARY OF THE GAME SPACE AND THE LEFT EDGE OF THE GAME 
		// BOARD
		ratSpawnLocationDefaultBeginner = new LittleTile(littleTileSpriteType, 
				(float)((int)((MAX_GAME_BOARD_COLS - 9)/4) * littleTileWidth), 
					(float)(((int)(MAX_GAME_BOARD_ROWS/2) - 1) * littleTileHeight), 0, 0, 
						RAT_SPAWN_STATE);
		ratSpawnLocationDefaultIntermediate = new LittleTile(littleTileSpriteType, 
				(float)((int)((MAX_GAME_BOARD_COLS - 16)/4) * littleTileWidth),  
					(float)(((int)(MAX_GAME_BOARD_ROWS/2) - 1) * littleTileHeight), 0, 0, 
						RAT_SPAWN_STATE);
		ratSpawnLocationDefaultAdvanced = new LittleTile(littleTileSpriteType, 
				(float)((int)((MAX_GAME_BOARD_COLS - 30)/4) * littleTileWidth),  
					(float)(((int)(MAX_GAME_BOARD_ROWS/2) - 1) * littleTileHeight), 0, 0, 
						RAT_SPAWN_STATE);
		
		String fileName;
		
		try
		{
			Reader reader = new FileReader(LEVEL_FILENAMES_FILE);
			BufferedReader in = new BufferedReader(reader);
			
			String line = in.readLine();
			
			// IF FILENAMES FOR DEFAULT LEVELS HAVE NOT BEEN WRITTEN TO LEVEL NAMES FILE, THEN DEFAULT 
			// LEVELS HAVE NOT YET BEEN WRITTEN TO THEIR OWN TEXT FILES
			if (line == null || line.equals(""))
			{
				for(int i = 0; i < defaultLevelNames.length; i++)
				{
					levelFileNames.add("./setup/" + defaultLevelNames[i].replaceAll("\\s", "") 
							+ ".txt");
					
					updateLevelFileNamesFile(/*"./setup/" + defaultLevelNames[i].replaceAll("\\s", "") 
							+ ".txt"*/);
				}
				
				// CONSTRUCT EACH DEFAULT LEVEL TO BE WRITTEN TO ITS OWN TEXT FILE
				levelToAdd = new MPPLevel(defaultLevelNames[0], 10, (float)1/60, ratSpawnLocationDefaultBeginner, 
												littleGameBoardDefaultBeginner);
				addDefaultLevel(levelFileNames.elementAt(0), 0);
				levelToAdd = new MPPLevel(defaultLevelNames[1], 40, (float)1/30, ratSpawnLocationDefaultIntermediate,
												littleGameBoardDefaultIntermediate);
				addDefaultLevel(levelFileNames.elementAt(1), 1);
				levelToAdd = new MPPLevel(defaultLevelNames[2], 99, (float)1/15, ratSpawnLocationDefaultAdvanced, 
												littleGameBoardDefaultAdvanced);
				addDefaultLevel(levelFileNames.elementAt(2), 2);
			}
			// IF FILENAMES FOR DEFAULT LEVELS HAVE BEEN WRITTEN TO LEVEL NAMES FILE, USE THEM TO READ
			// IN DATA FROM EACH DEFAULT LEVEL TEXT FILE TO LOAD IT INTO GAME
			else
			{
				if (levelFileNames.isEmpty())
				{
					levelFileNames.add(line);
					
					for(int i = 0; i < defaultLevelNames.length - 1; i++)
					{
						line = in.readLine();
						levelFileNames.add(line);
					}
				}
			}
			
			// CLOSE BUFFEREDREADER WHEN DONE READING IN FILE NAMES IN LEVEL NAMES FILE
			in.close();
			
			if (levels.isEmpty())
			{
				MPPLevel initLevel;
				String levelName;
				int numMines;
				float ratSpawnRate;
				int numVisibleTiles;
				float x;
				float y;
				String xyCoords;
				String[] xyCoordsData;
				LittleTile visibleLittleTile;
				float rX;
				float rY;
				String rXYCoords;
				String[] rXYCoordsData;
				LittleTile rSpawnLocation;
			
				for (int i = 0; i < 3; i++)
				{
					fileName = levelFileNames.elementAt(i);
					reader = new FileReader(fileName);
					in = new BufferedReader(reader);
				
					// READ IN THIS LEVEL'S NAME
					levelName = in.readLine();
				
					// AND NUMBER OF MINES
					numMines = Integer.parseInt(in.readLine());
				
					// AND ITS RAT SPAWN RATE
					ratSpawnRate = Float.parseFloat(in.readLine());
				
					// READ IN THE NUMBER OF VISIBLE TILES THIS LEVEL'S GAME BOARD HAS
					numVisibleTiles = Integer.parseInt(in.readLine());
				
					// READ IN EACH VISIBLE TILE'S COORDINATES AND ADD A VISIBLE LITTLE TILE WITH 
					// THOSE COORDINATES TO visibleLittleTilesVector
					for (int j = 0; j < numVisibleTiles; j++)
					{
						xyCoords = in.readLine();
						xyCoordsData = xyCoords.split(FILE_DELIMITER);
						x = Float.parseFloat(xyCoordsData[0]);
						y = Float.parseFloat(xyCoordsData[1]);
						visibleLittleTile = new LittleTile(littleTileSpriteType, x, y, 
																(float)0, (float)0, VISIBLE_STATE);
						visibleLittleTiles.add(visibleLittleTile);
					}
				
					// CREATE EMPTY GAME BOARD
					LittleTile[][] boardToLoad = new LittleTile[MAX_GAME_BOARD_ROWS][MAX_GAME_BOARD_COLS];
				
					float xCoord;
					float yCoord;
				
					// FILL IT WITH INVISIBLE LITTLE TILES, CHANGING TO VISIBLE THE STATE OF THOSE TILES 
					// WHOSE X AND Y COORDINATES CORRESPOND TO THOSE OF AN ELEMENT IN visibleLittleTiles 
					// VECTOR
					for (int row = 0; row < MAX_GAME_BOARD_ROWS; row++) 
					{
						for (int col = 0; col < MAX_GAME_BOARD_COLS; col++) 
						{
							xCoord = col * littleTileWidth;
							yCoord = row * littleTileHeight;
						
							boardToLoad[row][col] = new LittleTile(littleTileSpriteType, xCoord, 
														yCoord, (float)0, (float)0, INVISIBLE_STATE);
						
							for (int k = 0; k < visibleLittleTiles.size(); k++)
							{
								if (xCoord == visibleLittleTiles.elementAt(k).getX() && 
										yCoord == visibleLittleTiles.elementAt(k).getY())
									boardToLoad[row][col] = new LittleTile(littleTileSpriteType, 
											xCoord, yCoord, (float)0, (float)0, VISIBLE_STATE);
							}
						}
					}
				
					visibleLittleTiles.removeAllElements();
				
					rXYCoords = in.readLine();
					rXYCoordsData = rXYCoords.split(FILE_DELIMITER);
					rX = Float.parseFloat(rXYCoordsData[0]);
					rY = Float.parseFloat(rXYCoordsData[1]);
				
					in.close();
				
					rSpawnLocation = new LittleTile(littleTileSpriteType, rX, rY, (float)0, 
													(float)0, RAT_SPAWN_STATE);
					boardToLoad[(int)rY/littleTileHeight][(int)rX/littleTileWidth] = rSpawnLocation;
				
					initLevel = new MPPLevel(levelName, numMines, ratSpawnRate, rSpawnLocation, boardToLoad);
				
					levels.add(initLevel);
				}
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(levelEditorWindow, "Error loading default level");
			System.exit(0);
        }
	}
		
	// INITIALIZE EACH GAME BOARD BY INITIALIZING EACH TILE SPRITE MAKING UP GAME BOARD WITH
	// VISIBLE STATE AND INITILIAZING ALL OTHER TILE SPRITES IN MAX-SIZED GAME BOARD 
	// TEMPLATE WITH INVISIBLE STATE
	private LittleTile[][] initDefaultGameBoardTiles(int initRows, int initCols, int initDefaultIndex)
	{
		float xCoord;
		float yCoord;
		int startingRow = (int)((MAX_GAME_BOARD_ROWS - initRows)/2);
		int startingCol = (int)((MAX_GAME_BOARD_COLS - initCols)/2);
		int endRow = startingRow + initRows - 1;
		int endCol = startingCol + initCols - 1;
	
		LittleTile[][] littleGameBoard = new LittleTile[MAX_GAME_BOARD_ROWS][MAX_GAME_BOARD_COLS];
		
		defaultVisibleLittleTiles.add(initDefaultIndex, new Vector<LittleTile>());
		
		for (int row = 0; row < MAX_GAME_BOARD_ROWS; row++) 
		{
			for (int col = 0; col < MAX_GAME_BOARD_COLS; col++) 
			{
				xCoord = col * littleTileWidth;
				yCoord = row * littleTileHeight;
				
				if ((row < startingRow) || (col < startingCol)
						||
					(row > endRow) || (col > endCol))
				{

					littleGameBoard[row][col] = new LittleTile(littleTileSpriteType, xCoord, 
							yCoord, (float)0, (float)0, INVISIBLE_STATE);
				}
				else
				{
					littleGameBoard[row][col] = new LittleTile(littleTileSpriteType, xCoord, 
							yCoord, (float)0, (float)0, VISIBLE_STATE);
					defaultVisibleLittleTiles.elementAt(initDefaultIndex).add(littleGameBoard[row][col]);
				}
			}
		}
		
		return littleGameBoard;
	}
	
	public void initCustomLevels()
	{
		String fileName;
		
		try
		{
			Reader reader = new FileReader(LEVEL_FILENAMES_FILE);
			BufferedReader in = new BufferedReader(reader);
			
			int counter = 0;
			String line = "";
			
			int currentNumLevels = levelFileNames.size();
			
			// IF ANY CUSTOM LEVELS HAVE BEEN CREATED, THEIR FILENAMES WOULD BE LISTED AFTER THE THREE
			// DEFAULT LEVEL FILENAMES
			while(counter < currentNumLevels)
			{
				line = in.readLine();
				counter++;
			}
			
			line = in.readLine();
			
			// IF FILENAMES FOR CUSTOM LEVELS HAVE BEEN WRITTEN TO LEVEL NAMES FILE, READ EACH IN
			// TO BE USED IN CONSTRUCTING TEXT FILES FOR EACH LEVEL USING THOSE NAMES
			while (!(line == null))
			{
				levelFileNames.add(line);
				line = in.readLine();
			}
			
			// CLOSE BUFFEREDREADER WHEN DONE READING IN FILE NAMES IN LEVEL NAMES FILE
			in.close();
				
			MPPLevel initLevel;
			String levelName;
			int numMines;
			float ratSpawnRate;
			int numVisibleTiles;
			float x;
			float y;
			String xyCoords;
			String[] xyCoordsData;
			LittleTile visibleLittleTile;
			float rX;
			float rY;
			String rXYCoords;
			String[] rXYCoordsData;
			LittleTile rSpawnLocation;
				
			// IF FILENAMES FOR CUSTOM LEVELS HAVE BEEN WRITTEN TO LEVEL NAMES FILE, USE THEM TO READ
			// IN DATA FROM EACH CUSTOM LEVEL TEXT FILE TO LOAD IT INTO GAME
			for (int i = currentNumLevels; i < levelFileNames.size(); i++)
			{
				fileName = levelFileNames.elementAt(i);
				reader = new FileReader(fileName);
				in = new BufferedReader(reader);
					
				// READ IN THIS LEVEL'S NAME
				levelName = in.readLine();
					
				// AND NUMBER OF MINES
				numMines = Integer.parseInt(in.readLine());
					
				// AND ITS RAT SPAWN RATE
				ratSpawnRate = Float.parseFloat(in.readLine());
					
				// READ IN THE NUMBER OF VISIBLE TILES THIS LEVEL'S GAME BOARD HAS
				numVisibleTiles = Integer.parseInt(in.readLine());
					
				// READ IN EACH VISIBLE TILE'S COORDINATES AND ADD A VISIBLE LITTLE TILE WITH 
				// THOSE COORDINATES TO visibleLittleTilesVector
				for (int j = 0; j < numVisibleTiles; j++)
				{
					xyCoords = in.readLine();
					xyCoordsData = xyCoords.split(FILE_DELIMITER);
					x = Float.parseFloat(xyCoordsData[0]);
					y = Float.parseFloat(xyCoordsData[1]);
					visibleLittleTile = new LittleTile(littleTileSpriteType, x, y, 
											(float)0, (float)0, VISIBLE_STATE);
					visibleLittleTiles.add(visibleLittleTile);
				}
					
				// CREATE EMPTY GAME BOARD
				LittleTile[][] boardToLoad = new LittleTile[MAX_GAME_BOARD_ROWS][MAX_GAME_BOARD_COLS];
					
				float xCoord;
				float yCoord;
					
				// FILL IT WITH INVISIBLE LITTLE TILES, CHANGING TO VISIBLE THE STATE OF THOSE TILES 
				// WHOSE X AND Y COORDINATES CORRESPOND TO THOSE OF AN ELEMENT IN visibleLittleTiles 
				// VECTOR
				for (int row = 0; row < MAX_GAME_BOARD_ROWS; row++) 
				{
					for (int col = 0; col < MAX_GAME_BOARD_COLS; col++) 
					{
						xCoord = col * littleTileWidth;
						yCoord = row * littleTileHeight;
							
						boardToLoad[row][col] = new LittleTile(littleTileSpriteType, xCoord, yCoord,
								(float)0, (float)0, INVISIBLE_STATE);
							
						for (int k = 0; k < visibleLittleTiles.size(); k++)
						{
							if (xCoord == visibleLittleTiles.elementAt(k).getX() && 
									yCoord == visibleLittleTiles.elementAt(k).getY())
								boardToLoad[row][col] = new LittleTile(littleTileSpriteType, 
										xCoord, yCoord, (float)0, (float)0, VISIBLE_STATE);
						}
					}
				}
					
				visibleLittleTiles.removeAllElements();
					
				rXYCoords = in.readLine();
				rXYCoordsData = rXYCoords.split(FILE_DELIMITER);
				rX = Float.parseFloat(rXYCoordsData[0]);
				rY = Float.parseFloat(rXYCoordsData[1]);
					
				in.close();
					
				rSpawnLocation = new LittleTile(littleTileSpriteType, rX, rY, (float)0, 
								(float)0, RAT_SPAWN_STATE);
				boardToLoad[(int)rY/littleTileHeight][(int)rX/littleTileWidth] = rSpawnLocation;
					
				initLevel = new MPPLevel(levelName, numMines, ratSpawnRate, rSpawnLocation, boardToLoad);
					
				levels.add(initLevel);
			}
		}
		catch(Exception e)
        {
			JOptionPane.showMessageDialog(levelEditorWindow, "Error loading custom level");
			System.exit(0);
        }
	}
	
	// CHANGE LEVEL OPTION BEING DISPLAYED TO NEXT OR PREVIOUS OPTION, DEPENDING ON
	// WHETHER USER PRESSED RIGHT OR LEFT ARROW BUTTON, RESPECTIVELY
	public void changePanel()
	{
		if (levelIndex >= 0 && levelIndex < levels.size())
		{
			String nameToUse = levels.elementAt(levelIndex).getLevelName();
			levelNamePanel.setLevelName(nameToUse);
		
			LittleTile[][] boardToUse = levels.elementAt(levelIndex).getCustomLittleGameBoard();
			littleGameBoardPanel.setLittleGameBoard(boardToUse);
			
			levelNamePanel.repaint();
			littleGameBoardPanel.repaint();
		}
		else if(levelIndex < 0)
		{
			levelIndex = 0;
		}
		else
		{
			levelIndex = levels.size() - 1;
		}
	}
	
	// INITIALIZE AND LAY OUT COMPONENTS OF LEVEL EDITOR WINDOW
	public void layoutGUI()
	{
		
		JPanel leftArrowPanel = new JPanel();
		leftArrowPanel.setLayout(new GridBagLayout());
		leftArrowIcon = new ImageIcon("./setup/images/LeftArrow.png");
		leftArrowButton = new JButton(leftArrowIcon);
		placeComponentInContainer(leftArrowPanel, leftArrowButton, 0, 0, 1, 1);
		
		levelEditorWindow.add(leftArrowPanel, BorderLayout.WEST);
		
		JPanel levelViewPanel = new JPanel();
		Insets lvpInsets = levelViewPanel.getInsets();
		levelViewPanel.setSize(600 + lvpInsets.left + lvpInsets.right, 1000 + lvpInsets.top + lvpInsets.bottom);
		levelViewPanel.setLayout(new BorderLayout());
		
		levelNamePanel = new MinesweeperPlusPlusLevelNamePanel(this,
				     levels.elementAt(levelIndex).getLevelName());
		littleGameBoardPanel = new MinesweeperPlusPlusLevelEditorPanel(this, 
				levels.elementAt(levelIndex).getCustomLittleGameBoard());
		
		levelViewPanel.add(levelNamePanel, BorderLayout.NORTH);
		levelViewPanel.add(littleGameBoardPanel, BorderLayout.CENTER);
		/*placeComponentInContainer(levelViewPanel, levelNamePanel, 0, 0, 1, 1);
		placeComponentInContainer(levelViewPanel, littleGameBoardPanel, 0, 1, 1, 1);*/
		
		//levelViewPanel.setPreferredSize(new Dimension(750, 500));
		/*Dimension panelSize = levelNamePanel.getPreferredSize();
		levelNamePanel.setBounds(100 + lvpInsets.left, 25 + lvpInsets.top, panelSize.width, panelSize.height);
		panelSize = littleGameBoardPanel.getPreferredSize();
		littleGameBoardPanel.setBounds(10 + lvpInsets.left, 50 + lvpInsets.top, panelSize.width, panelSize.height);*/
		
		levelEditorWindow.add(levelViewPanel, BorderLayout.CENTER);
		
		JPanel eastPanel = new JPanel(new GridBagLayout());
		
		JPanel rightArrowPanel = new JPanel();
		rightArrowPanel.setLayout(new GridBagLayout());
		rightArrowIcon = new ImageIcon("./setup/images/RightArrow.png");
		rightArrowButton = new JButton(rightArrowIcon);
		placeComponentInContainer(rightArrowPanel, rightArrowButton, 0, 0, 1, 1);
		placeComponentInContainer(eastPanel, rightArrowPanel, 0, 0, 1, 1);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		playLevelButton = new JButton("Play Level");
		placeComponentInContainer(buttonPanel, playLevelButton, 1, 0, 3, 1);
		deleteLevelButton = new JButton("Delete Level");
		placeComponentInContainer(buttonPanel, deleteLevelButton, 1, 1, 3, 1);
		deleteLevelButton.setEnabled(false);
		viewLevelStatsButton = new JButton("View Level Stats");
		placeComponentInContainer(buttonPanel, viewLevelStatsButton, 1, 2, 3, 1);
		createCustomLevelButton = new JButton("Create Custom Level");
		placeComponentInContainer(buttonPanel, createCustomLevelButton, 1, 3, 3, 1);
		logOutButton = new JButton("Log Out");
		placeComponentInContainer(buttonPanel, logOutButton, 1, 4, 3, 1);
		placeComponentInContainer(eastPanel, buttonPanel, 1, 0, 1, 1);
		
		levelEditorWindow.add(eastPanel, BorderLayout.EAST);
		
		levelEditorWindow.setSize(1075, 450);
	}
	
	// HELPER METHOD FOR ARRANGING COMPONENTS IN A CONTAINER THAT UTILIZES GridBagLayout
	private void placeComponentInContainer(Container c, JComponent jc, int col, int row, int colSpan, 
			int rowSpan)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = col;
		gbc.gridy = row;
		gbc.gridwidth = colSpan;
		gbc.gridheight = rowSpan;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		c.add(jc, gbc);
	}
	
	// INITIALIZE HANDLERS FOR PRESSING LEVEL EDITOR ACTIONS BUTTONS AND EXING-OUT
	// OF LEVEL EDITOR WINDOW
	public void initHandlers()
	{
		/*LevelEditorWindowHandler levelEditorWH = new LevelEditorWindowHandler(this);
		levelEditorWindow.addWindowListener(levelEditorWH);*/
		
		LevelScrollHandler lsh = new LevelScrollHandler(this);
		leftArrowButton.addActionListener(lsh);
		rightArrowButton.addActionListener(lsh);
		
		CreateCustomLevelHandler cclh = new CreateCustomLevelHandler(this);
		createCustomLevelButton.addActionListener(cclh);
		
		CustomizationWindowsHandler cWH = new CustomizationWindowsHandler(this);
		customLevelLayoutWindow.addWindowListener(cWH);
		
		CustomizationWindowsHandler c2WH = new CustomizationWindowsHandler(this);
		moreCustomLevelOptionsWindow.addWindowListener(c2WH);
		
		CancelHandler ch = new CancelHandler(this);
		cancelButton.addActionListener(ch);
		
		CancelHandler c2h = new CancelHandler(this);
		cancelButton2.addActionListener(c2h);
		
		SaveLevelHandler slh = new SaveLevelHandler(this);
		saveLevelButton.addActionListener(slh);
		
		MoreCustomLevelOptionsHandler mcloh = new MoreCustomLevelOptionsHandler(this);
		moreCustomLevelOptionsButton.addActionListener(mcloh);
		
		BackHandler bh = new BackHandler(this);
		backButton.addActionListener(bh);
		
		PlayLevelHandler plh = new PlayLevelHandler(this);
		playLevelButton.addActionListener(plh);
		
		DeleteLevelHandler dlh = new DeleteLevelHandler(this);
		deleteLevelButton.addActionListener(dlh);
		
		LevelEditorLogOutHandler leloh = new LevelEditorLogOutHandler(this, application);
		logOutButton.addActionListener(leloh);
	}
	
	// OPEN WINDOW IN WHICH USER CAN CONFIGURE CUSTOM LEVEL LAYOUT
	public void initCustomLevelLayoutWindow()
	{
		levelEditorWindow.setVisible(false);
		initCLLWindow();
		initCLLWLittleTiles();
		layoutCLLWGUI();
		customLevelGameBoardPanel.repaint();
		customLevelLayoutWindow.setVisible(true);
	}
	
	public void initMoreCustomLevelOptionsWindow()
	{
		customLevelLayoutWindow.setVisible(false);
		initMCLOWindow();
		initMCLOWLittleTiles();
		layoutMCLOWGUI();
		ratSpawnLocationSelectionPanel.repaint();
		moreCustomLevelOptionsWindow.setVisible(true);
	}
	
	// INITIALIZE customLevelLayoutWindow
	public void initCLLWindow()
	{
		customLevelLayoutWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		customLevelLayoutWindow.setResizable(false);
		customLevelLayoutWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		customLevelLayoutWindow.setLocation((int)(screen.getWidth() - customLevelLayoutWindow.getWidth())/2, (int)(screen.getHeight() - customLevelLayoutWindow.getHeight())/2);
	}
	
	public void initMCLOWindow()
	{
		moreCustomLevelOptionsWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		moreCustomLevelOptionsWindow.setResizable(false);
		moreCustomLevelOptionsWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		moreCustomLevelOptionsWindow.setLocation((int)(screen.getWidth() - moreCustomLevelOptionsWindow.getWidth())/2, (int)(screen.getHeight() - moreCustomLevelOptionsWindow.getHeight())/2);
	}
	
	// INITIALIZE BLANK GAME BOARD TO BE CUSTOMLY MANIPULATED BY USER
	public void initCLLWLittleTiles()
	{
		customLevelGameBoard = new LittleTile[MAX_GAME_BOARD_ROWS][MAX_GAME_BOARD_COLS];
			
		float xCoord;
		float yCoord;
			
		for (int row = 4; row < MAX_GAME_BOARD_ROWS - 4; row++) 
		{
			for (int col = 4; col < MAX_GAME_BOARD_COLS - 4; col++) 
			{
				xCoord = col * littleTileWidth;
				yCoord = row * littleTileHeight;
					
				customLevelGameBoard[row][col] = new LittleTile(littleTileSpriteType, xCoord, 
								yCoord, (float)0, (float)0, INVISIBLE_OUTLINED_STATE);
				
				LittleTileClickHandler ltch = new LittleTileClickHandler(this, row, col);
				(customLevelGameBoard[row][col]).setActionListener(ltch);
			}
		}
		
		// INITIALIZE BORDER OF GAME BOARD
		
		for (int row = 0; row < 4; row++) 
		{
			for (int col = 0; col < MAX_GAME_BOARD_COLS; col++) 
			{
				xCoord = col * littleTileWidth;
				yCoord = row * littleTileHeight;
					
				customLevelGameBoard[row][col] = new LittleTile(littleTileSpriteType, xCoord, 
								yCoord, (float)0, (float)0, INVISIBLE_STATE);
			}
		}
		
		for (int row = MAX_GAME_BOARD_ROWS - 4; row < MAX_GAME_BOARD_ROWS; row++) 
		{
			for (int col = 0; col < MAX_GAME_BOARD_COLS; col++) 
			{
				xCoord = col * littleTileWidth;
				yCoord = row * littleTileHeight;
					
				customLevelGameBoard[row][col] = new LittleTile(littleTileSpriteType, xCoord, 
								yCoord, (float)0, (float)0, INVISIBLE_STATE);
			}
		}
		
		for (int row = 0; row < MAX_GAME_BOARD_ROWS; row++) 
		{
			for (int col = 0; col < 4; col++) 
			{
				xCoord = col * littleTileWidth;
				yCoord = row * littleTileHeight;
					
				customLevelGameBoard[row][col] = new LittleTile(littleTileSpriteType, xCoord, 
								yCoord, (float)0, (float)0, INVISIBLE_STATE);
			}
		}
		
		for (int row = 0; row < MAX_GAME_BOARD_ROWS; row++) 
		{
			for (int col = MAX_GAME_BOARD_COLS - 4; col < MAX_GAME_BOARD_COLS; col++) 
			{
				xCoord = col * littleTileWidth;
				yCoord = row * littleTileHeight;
					
				customLevelGameBoard[row][col] = new LittleTile(littleTileSpriteType, xCoord, 
								yCoord, (float)0, (float)0, INVISIBLE_STATE);
			}
		}
	}
	
	public void initMCLOWLittleTiles()
	{
		boardForRatSpawnLocationSelection = new LittleTile[MAX_GAME_BOARD_ROWS][MAX_GAME_BOARD_COLS];
		
		float xCoord;
		float yCoord;
		
		for (int row = 0; row < MAX_GAME_BOARD_ROWS; row++) 
		{
			for (int col = 0; col < MAX_GAME_BOARD_COLS; col++) 
			{
				xCoord = col * littleTileWidth;
				yCoord = row * littleTileHeight;
				
				boardForRatSpawnLocationSelection[row][col] = new LittleTile(littleTileSpriteType, 
						xCoord, yCoord, (float)0, (float)0, INVISIBLE_STATE);
				
				for (int i = 0; i < visibleLittleTiles.size(); i++)
				{
					if (xCoord == visibleLittleTiles.elementAt(i).getX() && 
							yCoord == visibleLittleTiles.elementAt(i).getY())
						boardForRatSpawnLocationSelection[row][col] = 
							new LittleTile(littleTileSpriteType, xCoord, yCoord, (float)0, (float)0, 
									VISIBLE_STATE);
				}
				
				if (boardForRatSpawnLocationSelection[row][col].getState().equals(INVISIBLE_STATE))
				{
					RatSpawnLocationSelectionHandler rslsh = 
							new RatSpawnLocationSelectionHandler(this, row, col);
					(boardForRatSpawnLocationSelection[row][col]).setActionListener(rslsh);
				}
			}
		}
	}
	
	// INITIALIZE AND LAYOUT COMPONENTS OF customLevelLayoutWindow
	public void layoutCLLWGUI()
	{
		JPanel cllwNorthPanel = new JPanel();
		cllwNorthPanel.setLayout(new BorderLayout());
		
		JPanel levelNamePanel = new JPanel();
		JLabel levelName = new JLabel("Level Name: ");
		((FlowLayout)levelNamePanel.getLayout()).setAlignment(FlowLayout.LEFT);
		levelNamePanel.add(levelName);
		levelNamePanel.add(levelNameField);
		cllwNorthPanel.add(levelNamePanel, BorderLayout.NORTH);
		
		JPanel messageToUserPanel = new JPanel();
		JLabel messageToUser = new JLabel("CLICK ON WHICH TILES TO BE PLAYABLE IN MINESWEEPER++ GAME " +
				"BOARD: ");
		((FlowLayout)messageToUserPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		messageToUserPanel.add(messageToUser);
		cllwNorthPanel.add(messageToUserPanel, BorderLayout.SOUTH);
		
		customLevelLayoutWindow.add(cllwNorthPanel, BorderLayout.NORTH);
		
		customLevelGameBoardPanel = new MinesweeperPlusPlusLevelEditorPanel(this, customLevelGameBoard);
		CustomLevelGameBoardPanelHandler cPH = new CustomLevelGameBoardPanelHandler(this);
		customLevelGameBoardPanel.addMouseListener(cPH);
		customLevelLayoutWindow.add(customLevelGameBoardPanel, BorderLayout.CENTER);
		
		JPanel cllwEastPanel = new JPanel();
		cllwEastPanel.setLayout(new GridBagLayout());
		
		placeComponentInContainer(cllwEastPanel, moreCustomLevelOptionsButton, 0, 0, 2, 1);
		placeComponentInContainer(cllwEastPanel, cancelButton, 0, 1, 2, 1);
		
		customLevelLayoutWindow.add(cllwEastPanel, BorderLayout.EAST);
		
		customLevelLayoutWindow.setSize(1000, 1000);
	}
	
	public void layoutMCLOWGUI()
	{
		JPanel mcloNorthPanel = new JPanel();
		mcloNorthPanel.setLayout(new BorderLayout());
		
		JPanel numMinesPanel = new JPanel();
		JLabel numMines = new JLabel("Number of Mines: ");
		((FlowLayout)numMinesPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		numMinesPanel.add(numMines);
		numMinesPanel.add(numMinesField);
		mcloNorthPanel.add(numMinesPanel, BorderLayout.NORTH);
		
		JPanel ratSpawnRatePanel = new JPanel();
		JLabel ratSpawnRate = new JLabel("Rat Spawn Rate: ");
		JLabel rats = new JLabel("Rats / ");
		JLabel secs = new JLabel("Secs");
		((FlowLayout)ratSpawnRatePanel.getLayout()).setAlignment(FlowLayout.LEFT);
		ratSpawnRatePanel.add(ratSpawnRate);
		ratSpawnRatePanel.add(ratSpawnRateRatsField);
		ratSpawnRatePanel.add(rats);
		ratSpawnRatePanel.add(ratSpawnRateSecsField);
		ratSpawnRatePanel.add(secs);
		mcloNorthPanel.add(ratSpawnRatePanel, BorderLayout.CENTER);
		
		JPanel messageToUserPanel = new JPanel();
		JLabel messageToUser = new JLabel("CLICK ON LOCATION FROM WHICH RATS WILL SPAWN: ");
		((FlowLayout)messageToUserPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		messageToUserPanel.add(messageToUser);
		mcloNorthPanel.add(messageToUserPanel, BorderLayout.SOUTH);
		
		moreCustomLevelOptionsWindow.add(mcloNorthPanel, BorderLayout.NORTH);
		
		ratSpawnLocationSelectionPanel = new MinesweeperPlusPlusLevelEditorPanel(this, 
				boardForRatSpawnLocationSelection);
		RSLSelectionPanelHandler rPH = new RSLSelectionPanelHandler(this);
		ratSpawnLocationSelectionPanel.addMouseListener(rPH);
		moreCustomLevelOptionsWindow.add(ratSpawnLocationSelectionPanel, BorderLayout.CENTER);
		
		JPanel mcloEastPanel = new JPanel();
		mcloEastPanel.setLayout(new GridBagLayout());
		
		placeComponentInContainer(mcloEastPanel, saveLevelButton, 0, 0, 2, 1);
		placeComponentInContainer(mcloEastPanel, backButton,      0, 1, 2, 1);
		placeComponentInContainer(mcloEastPanel, cancelButton2,    0, 2, 2, 1);
		
		moreCustomLevelOptionsWindow.add(mcloEastPanel, BorderLayout.EAST);
		
		moreCustomLevelOptionsWindow.setSize(1000, 1000);
	}
	
	// SCROLL TO NEXT OR PREVIOUS LEVEL OPTION, DEPENDING ON WHETHER USER PRESSED RIGHT OR LEFT
	// ARROW BUTTON, RESPECTIVELY
	public void scrollLevel(JButton initArrowButton)
	{
		if (initArrowButton.equals(rightArrowButton) && levelIndex != levels.size())
		{
			levelIndex++;
		}
		
		else if (initArrowButton.equals(leftArrowButton) && levelIndex != 0)
		{
			levelIndex--;
		}
		
		if (levelIndex < 3)
		{
			deleteLevelButton.setEnabled(false);
		}
		else
		{
			deleteLevelButton.setEnabled(true);
		}
	
		this.changePanel();
	}
	
	public void closeLevelEditorWindow()
	{
		application.getLogInWindow().setVisible(true);
		levelEditorWindow.setVisible(false);
	}
	
	public void closeCustomizationWindows()
	{
		moreCustomLevelOptionsWindow.dispose();
		customLevelLayoutWindow.dispose();
		initLevelEditor();
		initDefaultLevels();
		initCustomLevels();
		startLevelEditor();
	}
	
	public void backToCLLWindow()
	{
		customLevelLayoutWindow.setVisible(true);
		moreCustomLevelOptionsWindow.setVisible(false);
	}
	
	public void addDefaultLevel(String initFileName, int initDefaultIndex)
	{
		visibleLittleTiles = defaultVisibleLittleTiles.elementAt(initDefaultIndex);
		
		LittleTile visibleLittleTile;
		
		try
		{
			Writer writer = new FileWriter(initFileName);
			PrintWriter out = new PrintWriter(writer);
			
			// WRITE TO TEXT FILE REPRESENTING LEVEL THE LEVEL'S NAME
			out.println("" + levelToAdd.getLevelName());
			// AND NUMBER OF MINES IT HAS
			out.println("" + levelToAdd.getNumMines());
			// AND ITS RAT SPAWN RATE
			out.println("" + levelToAdd.getRatSpawnRate());
						
			// WRITE TO FILE NUMBER OF VISIBLE GAME BOARD TILES LEVEL HAS TO USE LATER FOR READING 
			// TILES BACK IN
			out.println("" + visibleLittleTiles.size());
						
			// WRITE TO FILE COORDINATES OF LEVEL'S VISIBLE GAME BOARD TILES
			for (int i = 0; i < visibleLittleTiles.size(); i++)
			{
				visibleLittleTile = visibleLittleTiles.elementAt(i);
				out.println(visibleLittleTile.getX() + FILE_DELIMITER + visibleLittleTile.getY());
			}
						
			// WRITE TO FILE COORDINATES OF LEVEL'S RAT SPAWN LOCATION
			out.println(levelToAdd.getRatSpawnLocation().getX() + FILE_DELIMITER 
							+ levelToAdd.getRatSpawnLocation().getY());
			
			// CLEAR AND CLOSE PRINTWRITER
			out.flush();
			out.close();
		}
		catch(Exception e)
        {
			JOptionPane.showMessageDialog(levelEditorWindow, "Error saving default level");
			System.exit(0);
        }
		
		// CLEAR visibleLittleTiles VECTOR SO THAT IT CAN BE USED FOR NEXT LEVEL
		visibleLittleTiles.removeAllElements();
	}
	
	public void addCustomLevel()
	{
		float xCoord;
		float yCoord;
		
		// INITIALIZE BLANK GAME BOARD TO BE USED FOR CUSTOM GAME BOARD
		LittleTile[][] customLittleGameBoard = new LittleTile[MAX_GAME_BOARD_ROWS][MAX_GAME_BOARD_COLS];
		
		// INITIALIZE EACH TILE WITH INVISIBLE STATE, SETTING STATE OF THOSE THAT WERE SELECTED BY USER
		// AND ADDED TO visibleLittleTiles VECTOR TO VISIBLE
		for (int row = 0; row < MAX_GAME_BOARD_ROWS; row++) 
		{
			for (int col = 0; col < MAX_GAME_BOARD_COLS; col++) 
			{
				xCoord = col * littleTileWidth;
				yCoord = row * littleTileHeight;
				
				customLittleGameBoard[row][col] = new LittleTile(littleTileSpriteType, xCoord, yCoord, 
						(float)0, (float)0, INVISIBLE_STATE);
				
				for (int i = 0; i < visibleLittleTiles.size(); i++)
				{
					if (xCoord == visibleLittleTiles.elementAt(i).getX() && 
							yCoord == visibleLittleTiles.elementAt(i).getY())
						customLittleGameBoard[row][col] = new LittleTile(littleTileSpriteType, xCoord, 
								yCoord, (float)0, (float)0, VISIBLE_STATE);
				}
			}
		}
		
		// CONSTRUCT NEW LEVEL USING INFO ENTERED IN CUSTOMIZATION WINDOWS
		float ratSpawnRate = (float)Integer.parseInt(ratSpawnRateRatsField.getText())/Integer.parseInt(ratSpawnRateSecsField.getText());
		levelToAdd = new MPPLevel(levelNameField.getText(), Integer.parseInt(numMinesField.getText()), 
				ratSpawnRate, ratSpawnLocation, customLittleGameBoard);
		
		String fileName = "./setup/" + levelToAdd.getLevelName().replaceAll("\\s", "") + ".txt";
		
		LittleTile visibleLittleTile;
		
		try
		{
			Writer writer = new FileWriter(fileName);
			PrintWriter out = new PrintWriter(writer);
			
			// WRITE TO TEXT FILE REPRESENTING LEVEL THE LEVEL'S NAME
			out.println("" + levelToAdd.getLevelName());
			// AND NUMBER OF MINES IT HAS
			out.println("" + levelToAdd.getNumMines());
			// AND ITS RAT SPAWN RATE
			out.println("" + levelToAdd.getRatSpawnRate());
			
			// WRITE TO FILE NUMBER OF VISIBLE GAME BOARD TILES LEVEL HAS TO USE LATER FOR READING 
			// TILES BACK IN
			out.println("" + visibleLittleTiles.size());
			
			// WRITE TO FILE COORDINATES OF LEVEL'S VISIBLE GAME BOARD TILES
			for (int i = 0; i < visibleLittleTiles.size(); i++)
			{
				visibleLittleTile = visibleLittleTiles.elementAt(i);
				out.println(visibleLittleTile.getX() + FILE_DELIMITER + visibleLittleTile.getY());
			}
			
			// WRITE TO FILE COORDINATES OF LEVEL'S RAT SPAWN LOCATION
			out.println(levelToAdd.getRatSpawnLocation().getX() + FILE_DELIMITER 
							+ levelToAdd.getRatSpawnLocation().getY());
			
			// CLEAR AND CLOSE PRINTWRITER
			out.flush();
			out.close();
			
			levelFileNames.add(fileName);
			updateLevelFileNamesFile();
		}
		catch(Exception e)
        {
			JOptionPane.showMessageDialog(levelEditorWindow, "Error saving custom level");
			System.exit(0);
        }
		
		// CLEAR visibleLittleTiles VECTOR SO THAT IT CAN BE USED FOR NEXT LEVEL
		visibleLittleTiles.removeAllElements();
	}
	
	public void deleteCustomLevel()
	{
		String fileName = "./setup/" + levels.elementAt(levelIndex).getLevelName().replaceAll("\\s", "") + ".txt";
		File file = new File(fileName);
		String tempFileName = "./setup/temp.txt";
		
		try
		{
			file.delete();
			
			Reader reader = new FileReader(LEVEL_FILENAMES_FILE);
			BufferedReader in = new BufferedReader(reader);
			Writer writer = new FileWriter(tempFileName);
			PrintWriter out = new PrintWriter(writer);
				
			String line = in.readLine();
			
			while (line != null)
			{
				if (!line.equals(fileName))
				{
					out.println(line);
				}
				
				line = in.readLine();
			}
			
			out.flush();
			out.close();
			in.close();
			
			File levelFileNamesFile = new File(LEVEL_FILENAMES_FILE);
			// File can't exist if it's going to be overwritten
			levelFileNamesFile.delete();
			File tempFile = new File(tempFileName);
			tempFile.renameTo(levelFileNamesFile);
			
			levels.removeElementAt(levelIndex);
			levelFileNames.remove(fileName);
			
			// go back to showing previous level now that this level
			// is gone
			levelIndex--;
			changePanel();
		}
		catch(Exception e)
        {
			JOptionPane.showMessageDialog(levelEditorWindow, "Error deleting custom level");
			System.exit(0);
        }
	}
	
	// ADDS NAME OF TEXT FILE REPRESENTING NEW LEVEL TO TEXT FILE CONTAINING LIST OF LEVEL FILENAMES
	public void updateLevelFileNamesFile(/*String initFileName*/)
	{
		/*Vector<String> namesInFile = new Vector<String>();
		
		// READS IN EXISTING FILENAMES IN LIST TO REWRITE THEM TO FILE BEFORE WRITING NEW FILENAME TO
		// IT
		try
		{
			Reader reader = new FileReader(LEVEL_FILENAMES_FILE);
			BufferedReader in = new BufferedReader(reader);
			
			String line = in.readLine();
			
			if (!(line == null) && !(line.equals("")))
			{
				namesInFile.add(line);
				
				boolean hasLine = true;
				
				while (hasLine)
				{
					line = in.readLine();
					
					if (!(line == null))
						namesInFile.add(line);
					else
						hasLine = false;
				}
			}
			
			in.close();
				
			Writer writer = new FileWriter(LEVEL_FILENAMES_FILE);
			PrintWriter out = new PrintWriter(writer);
			
			for (int i = 0; i < namesInFile.size(); i++)
			{
				out.println(namesInFile.elementAt(i));
			}
			
			out.println(initFileName);
			out.flush();
			out.close();
		}*/
		try
		{
			Writer writer = new FileWriter(LEVEL_FILENAMES_FILE);
			PrintWriter out = new PrintWriter(writer);
			
			for (int i = 0; i < levelFileNames.size(); i++)
			{
				out.println(levelFileNames.elementAt(i));
			}
			
			/*out.println(initFileName);*/
			out.flush();
			out.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(levelEditorWindow, "Error updating list of level filenames");
		}

	}
	
	public void clearBoardForRatSpawnLocationSelection()
	{
		for (int row = 0; row < MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_ROWS; row++) 
		{
			for (int col = 0; col < MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_COLS; col++)
			{
				if (boardForRatSpawnLocationSelection[row][col].getState().equals(MinesweeperPlusPlusLevelEditor.RAT_SPAWN_STATE))
					boardForRatSpawnLocationSelection[row][col].setState(MinesweeperPlusPlusLevelEditor.INVISIBLE_STATE);
			}
		}
	}
	
	public void setRatSpawnLocationSelectionPanel(int initRow, int initCol)
	{
		float xCoord = boardForRatSpawnLocationSelection[initRow][initCol].getX();
		float yCoord = boardForRatSpawnLocationSelection[initRow][initCol].getY();
		boardForRatSpawnLocationSelection[initRow][initCol] = new LittleTile(littleTileSpriteType, 
				xCoord, yCoord, (float)0, (float)0, RAT_SPAWN_STATE);
		
		ratSpawnLocationSelectionPanel.setLittleGameBoard(boardForRatSpawnLocationSelection);
		ratSpawnLocationSelectionPanel.repaint();
	}
	
	// RUN LEVEL EDITOR BY OPENING LEVEL EDITOR WINDOW AND RENDERING LITTLE GAME
	// BOARD VISUALS REPRESENTING LEVEL OPTIONS
	public void startLevelEditor()
	{
		littleGameBoardPanel.repaint();
		levelNamePanel.repaint();
		levelEditorWindow.setVisible(true);
	}
	
	public void initInGameWindow()
	{
		game = new MinesweeperPlusPlusGame(this);
		game.startGame();
		levelEditorWindow.setVisible(false);
	}
}