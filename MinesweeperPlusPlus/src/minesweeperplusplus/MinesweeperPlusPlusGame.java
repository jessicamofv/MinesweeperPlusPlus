package minesweeperplusplus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import minesweeperplusplus.events.InGameLogOutHandler;
import minesweeperplusplus.events.NewGameHandler;
import minesweeperplusplus.events.QuitGameHandler;
import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;

public class MinesweeperPlusPlusGame extends MiniGame
{
	public static final int GAME_WIDTH = 1233;
	public static final int GAME_HEIGHT = 700;
	public static final float BOUNDARY_TOP = 0.1f;
	public static final float BOUNDARY_BOTTOM = 0.05f;
	public static final float BOUNDARY_LEFT = 0.01f;
	public static final float BOUNDARY_RIGHT = 0.01f;
	
	public static final int FRAME_RATE = 30;
	public static final String APP_TITLE = "MinesweeperPlusPlus";
	
	public static final Color COLOR_KEY = new Color(255, 255, 255);
	
	public static final String TILE_TYPE = "TILE_TYPE";
	public static final String CHEESE_TYPE = "CHEESE_TYPE";
	public static String VISIBLE_STATE = "VISIBLE_STATE";
	public static String INVISIBLE_STATE = "INVISIBLE_STATE";
	public static String INVISIBLE_OUTLINED_STATE = "INVISIBLE_OUTLINED_STATE";
	public static String RAT_SPAWN_STATE = "RAT_SPAWN_STATE";
	
	public static final String MINE_TYPE = "MINE_TYPE";
	public static final String INTACT_STATE = "INTACT_STATE";
	public static final String EXPLODING_STATE = "EXPLODING_STATE";
	public static final String INITIAL_MINE_EXPLODED_STATE = "INITIAL_MINE_EXPLODED_STATE";
	public static final String EXPLODED_STATE = "EXPLODED_STATE";
	
	public static final String RAT_TYPE = "RAT_TYPE";
	public static final String RAT_LEFT_STATE = "RAT_LEFT_STATE";
	public static final String RAT_RIGHT_STATE = "RAT_RIGHT_STATE";
	public static final String EXPLODING_RAT_LEFT_STATE = "EXPLODING_RAT_LEFT_STATE";
	public static final String EXPLODING_RAT_RIGHT_STATE = "EXPLODING_RAT_RIGHT_STATE";
	public static final String EXPLODED_RAT_STATE = "EXPLODED_RAT_STATE";
	
	public static final String BACKGROUND_TYPE = "BACKGROUND_TYPE";
	public static final String NORTH_TOOLBAR_TYPE = "SOUTH_TOOLBAR_TYPE";
	public static final String DEFAULT_STATE = "DEFAULT_STATE";
	
	public static final String NEW_GAME_TYPE = "NEW_GAME_TYPE";
	public static final String QUIT_GAME_TYPE = "QUIT_GAME_TYPE";
	public static final String LOG_OUT_TYPE = "LOG_OUT_TYPE";
	
	public static final String TIMER_CLOCK_TYPE = "TIMER_CLOCK_TYPE";
	public static final String TIMER_DISPLAY_TYPE = "TIMER_DISPLAY_TYPE";
	public static final String MINE_COUNTER_TYPE = "MINE_COUNTER_TYPE";
	public static final String COUNTER_DISPLAY_TYPE = "COUNTER_DISPLAY_TYPE";
	
	public static final int BUTTON_WIDTH = 125;
	
	private MinesweeperPlusPlusLevelEditor editor;
	private String currentUser;
	
	public MinesweeperPlusPlusGame(MinesweeperPlusPlusLevelEditor initEditor)
	{
		// CALL MiniGame CONSTRUCTOR
		super(APP_TITLE, FRAME_RATE);
		initSpriteTypes();
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		window.setLocation((int)(screen.getWidth() - window.getWidth())/21, (int)(screen.getHeight() - window.getHeight())/45);
		
		editor = initEditor;
		currentUser = editor.getCurrentUser();
		initLogOutFunctionality();
		reset();
	}
	
	public MinesweeperPlusPlusLevelEditor getEditor()
	{
		return editor;
	}
	
	public JFrame getWindow()
	{
		return window;
	}
	
	public String getCurrentUser() { return currentUser; }
	
	public void initData()
	{
		data = new MinesweeperPlusPlusGameDataModel(this);
		data.setGameDimensions(GAME_WIDTH, GAME_HEIGHT);
		
		boundaryLeft 	= BOUNDARY_LEFT	* GAME_WIDTH;
		boundaryRight 	= GAME_WIDTH 	- (GAME_WIDTH * BOUNDARY_RIGHT);
		boundaryTop 	= BOUNDARY_TOP 	* GAME_HEIGHT;
		boundaryBottom 	= GAME_HEIGHT 	- (GAME_HEIGHT * BOUNDARY_BOTTOM);
	}
	
	private void initSpriteTypes()
	{
		Image img;
		SpriteType sT;
		
		sT = new SpriteType(TILE_TYPE);
		data.addSpriteType(sT);
		img = loadImage("./setup/images/Tile.png");
		sT.addState(VISIBLE_STATE, img);
		img = loadImage("./setup/images/InvisibleTile.png");
		sT.addState(INVISIBLE_STATE, img);
		img = loadImage("./setup/images/InvisibleOutlinedTile.png");
		sT.addState(INVISIBLE_OUTLINED_STATE, img);
		img = loadImage("./setup/images/LittleRatSpawnLocation.png");
		sT.addState(RAT_SPAWN_STATE, img);
		
		sT = new SpriteType(CHEESE_TYPE);
		data.addSpriteType(sT);
		img = loadImageWithColorKey("./setup/images/Cheese.png", COLOR_KEY);
		sT.addState(VISIBLE_STATE, img);
		sT.addState(INVISIBLE_STATE, img);
		
		sT = new SpriteType(MINE_TYPE);
		data.addSpriteType(sT);
		img = loadImage("./setup/images/IntactMine.png");
		sT.addState(INTACT_STATE, img);
		sT.addState(INVISIBLE_STATE, img);
		img = loadImage("./setup/images/ExplodingMine.png");
		sT.addState(EXPLODING_STATE, img);
		img = loadImage("./setup/images/ExplodedInitialMine.png");
		sT.addState(INITIAL_MINE_EXPLODED_STATE, img);
		img = loadImage("./setup/images/ExplodedMine.png");
		sT.addState(EXPLODED_STATE, img);
		
		sT = new SpriteType(RAT_TYPE);
		data.addSpriteType(sT);
		img = loadImageWithColorKey("./setup/images/RatFacingLeft.png", COLOR_KEY);
		sT.addState(RAT_LEFT_STATE, img);
		img = loadImageWithColorKey("./setup/images/RatFacingRight.png", COLOR_KEY);
		sT.addState(RAT_RIGHT_STATE, img);
		img = loadImageWithColorKey("./setup/images/ExplodingRatFacingLeft.png", COLOR_KEY);
		sT.addState(EXPLODING_RAT_LEFT_STATE, img);
		img = loadImageWithColorKey("./setup/images/ExplodingRatFacingRight.png", COLOR_KEY);
		sT.addState(EXPLODING_RAT_RIGHT_STATE, img);
		img = loadImageWithColorKey("./setup/images/ExplodedRat.png", COLOR_KEY);
		sT.addState(EXPLODED_RAT_STATE, img);
	}
	
	public void initGUIControls()
	{
		canvas = new MinesweeperPlusPlusGamePanel(this, (MinesweeperPlusPlusGameDataModel)data);
				
		Image img;
		float x, y;
		SpriteType sT;
		Sprite s;
		
		sT = new SpriteType(BACKGROUND_TYPE);
		img = loadImage("./setup/images/Background.jpg");
		sT.addState(DEFAULT_STATE, img);
		x = 0;
		y = 0;
		s = new Sprite(sT, x, y, (float)0, (float)0, DEFAULT_STATE);
		guiDecor.put(BACKGROUND_TYPE, s);
		
		sT = new SpriteType(NORTH_TOOLBAR_TYPE);
		img = loadImage("./setup/images/NorthToolbar.png");
		sT.addState(DEFAULT_STATE, img);
		x = 0;
		y = data.getGameHeight() - img.getHeight(null);
		s = new Sprite(sT, x, y, (float)0, (float)0, DEFAULT_STATE);
		guiDecor.put(NORTH_TOOLBAR_TYPE, s);
		
		sT = new SpriteType(MINE_COUNTER_TYPE);
		img = loadImage("./setup/images/MineCounter.png");
		sT.addState(DEFAULT_STATE, img);
		x = GAME_WIDTH - 50;
		y = 0;
		s = new Sprite(sT, x, y, (float)0, (float)0, DEFAULT_STATE);
		guiDecor.put(MINE_COUNTER_TYPE, s);
		
		sT = new SpriteType(COUNTER_DISPLAY_TYPE);
		img = loadImage("./setup/images/DisplayScreen.png");
		sT.addState(DEFAULT_STATE, img);
		x = GAME_WIDTH - 112;
		y = 1;
		s = new Sprite(sT, x, y, (float)0, (float)0, DEFAULT_STATE);
		guiDecor.put(COUNTER_DISPLAY_TYPE, s);
		
		sT = new SpriteType(TIMER_DISPLAY_TYPE);
		sT.addState(DEFAULT_STATE, img);
		x = GAME_WIDTH - 212;
		y = 1;
		s = new Sprite(sT, x, y, (float)0, (float)0, DEFAULT_STATE);
		guiDecor.put(TIMER_DISPLAY_TYPE,  s);
		
		sT = new SpriteType(TIMER_CLOCK_TYPE);
		img = loadImage("./setup/images/TimerClock.png");
		sT.addState(DEFAULT_STATE, img);
		x = GAME_WIDTH - 249;
		y = 0;
		s = new Sprite(sT, x, y, (float)0, (float)0, DEFAULT_STATE);
		guiDecor.put(TIMER_CLOCK_TYPE, s);
		
		sT = new SpriteType(NEW_GAME_TYPE);
		img = loadImage("./setup/images/NewGameButton.png");
		sT.addState(DEFAULT_STATE, img);
		x = 0;
		y = 0;
		s = new Sprite(sT, x, y, (float)0, (float)0, DEFAULT_STATE);
		NewGameHandler ngh = new NewGameHandler(this, data);
		s.setActionListener(ngh);
		guiButtons.put(NEW_GAME_TYPE, s);
		
		sT = new SpriteType(QUIT_GAME_TYPE);
		img = loadImage("./setup/images/QuitGameButton.png");
		sT.addState(DEFAULT_STATE, img);
		x = BUTTON_WIDTH;
		y = 0;
		s = new Sprite(sT, x, y, (float)0, (float)0, DEFAULT_STATE);
		QuitGameHandler qgh = new QuitGameHandler(this);
		s.setActionListener(qgh);
		guiButtons.put(QUIT_GAME_TYPE, s);
		
		sT = new SpriteType(LOG_OUT_TYPE);
		img = loadImage("./setup/images/LogOutButton.png");
		sT.addState(DEFAULT_STATE, img);
		x = 2 * BUTTON_WIDTH;
		y = 0;
		s = new Sprite(sT, x, y, (float)0, (float)0, DEFAULT_STATE);
		guiButtons.put(LOG_OUT_TYPE, s);
	}
	
	public void initGUIHandlers()
	{
		/*InGameWindowHandler inGameWH = new InGameWindowHandler(this);
		window.addWindowListener(inGameWH);*/
	}
	
	public void initLogOutFunctionality()
	{
		Sprite logOutSprite = guiButtons.get(LOG_OUT_TYPE);
		InGameLogOutHandler igloh = new InGameLogOutHandler(this, editor, editor.getApplication());
		logOutSprite.setActionListener(igloh);
	}
	
	public void updateGUI()
	{
		;
	}
	
	public void reset()
	{
		data.reset(this);
	}
	
	public void closeInGameWindow()
	{
		editor.startLevelEditor();
		window.setVisible(false);
	}
}
