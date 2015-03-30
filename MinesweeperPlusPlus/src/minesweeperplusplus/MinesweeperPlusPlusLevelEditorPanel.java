package minesweeperplusplus;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import mini_game.Sprite;
import mini_game.SpriteType;

public class MinesweeperPlusPlusLevelEditorPanel extends JPanel
{
	private MinesweeperPlusPlusLevelEditor editor;
	private int littleTileWidth;
	private int littleTileHeight;
	private SpriteType littleTileSpriteType;
	private Sprite[][] littleGameBoard;
	private Sprite littleTileSprite;
	private Image littleTileImage;
	
	// INITILIAZE PANEL ON WHICH WILL BE RENDERED LITTLE GAME BOARD
	// VISUALS REPRESENTING LEVEL OPTIONS
	public MinesweeperPlusPlusLevelEditorPanel(MinesweeperPlusPlusLevelEditor initEditor, 
			Sprite[][] initLittleGameBoard)
	{
		editor = initEditor;
		littleTileWidth = editor.getLittleTileWidth();
		littleTileHeight = editor.getLittleTileHeight();
		littleGameBoard = initLittleGameBoard;
	}
	
	public void setLittleGameBoard(Sprite[][] initLittleGameBoard)
	{
		littleGameBoard = initLittleGameBoard;
	}
	
	// RETRIEVE EACH TILE SPRITE IN 2D ARRAY MAKING UP GAME BOARD
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		littleTileSpriteType = editor.getLittleTileSpriteType();
		
		for (int row = 0; row < MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_ROWS; row++) 
		{
			for (int col = 0; col < MinesweeperPlusPlusLevelEditor.MAX_GAME_BOARD_COLS; col++) 
			{
				littleTileSprite = littleGameBoard[row][col];
				renderSprite(g, littleTileSprite);
			}
		}
		
	}
	
	// RENDER EACH TILE SPRITE EXTRACTED FROM 2D ARRAY MAKING UP GAME BOARD
	public void renderSprite(Graphics g, Sprite initltSprite)
	{
		Sprite ltSprite = initltSprite;
		littleTileImage = littleTileSpriteType.getStateImage(ltSprite.getState());
		g.drawImage(littleTileImage, (int)ltSprite.getX(), (int)ltSprite.getY(), 
				littleTileWidth, littleTileHeight, null);
	}
}
