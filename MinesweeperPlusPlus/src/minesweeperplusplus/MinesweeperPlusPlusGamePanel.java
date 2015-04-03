package minesweeperplusplus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JPanel;

import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;

import static minesweeperplusplus.MinesweeperPlusPlusGame.*;

public class MinesweeperPlusPlusGamePanel extends JPanel
{
	private MiniGame game;
	
	private MinesweeperPlusPlusGameDataModel data;
	
	public MinesweeperPlusPlusGamePanel(MiniGame initGame, MinesweeperPlusPlusGameDataModel initData)
	{
		game = initGame;
		data = initData;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		renderBackground(g);
		
		renderGUIControls(g);
		
		renderCells(g);
		
		renderMines(g);
		
		renderNumBorderingMines(g);
		
		renderGameBoard(g);
		
		renderCheese(g);
		
		renderRats(g);
	}
	
	public void renderBackground(Graphics g)
	{
		Sprite bg = game.getGUIDecor().get(BACKGROUND_TYPE);
		renderSprite(g, bg);
	}
	
	public void renderGUIControls(Graphics g)
	{
		Collection<Sprite> decorSprites = game.getGUIDecor().values();
		for (Sprite s : decorSprites)
		{
			if (!s.getSpriteType().getSpriteTypeID().equals(BACKGROUND_TYPE))
				renderSprite(g, s);
		}
		
		Collection<Sprite> buttonSprites = game.getGUIButtons().values();
		for (Sprite s : buttonSprites)
			renderSprite(g, s);
	}
	
	public void renderCells(Graphics g)
	{
		Tile[][] cellSprites = data.getCells();
		
		for (int row = 0; row < data.getMaxRows(); row++) 
		{
			for (int col = 0; col < data.getMaxCols(); col++) 
			{
				renderSprite(g, cellSprites[row][col]);
			}
		}
	}
	
	public void renderMines(Graphics g)
	{
		Tile[][] tileSprites = data.getGameBoard();
		
		for (int row = 0; row < data.getMaxRows(); row++) 
		{
			for (int col = 0; col < data.getMaxCols(); col++) 
			{
				renderSprite(g, tileSprites[row][col].getMine());
			}
		}
	}
	
	public void renderNumBorderingMines(Graphics g)
	{
		Vector<Tile> inPlayTiles = data.getMineCandidates();
		int x;
		int y;
		
		for (int i = 0; i < inPlayTiles.size(); i++)
		{
			if (inPlayTiles.elementAt(i).getMine().getState().equals(INVISIBLE_STATE) && !(inPlayTiles.elementAt(i).getNumBorderingMines().equals("0")))
			{
				g.setColor(Color.BLUE);
				g.setFont(new Font(null, Font.BOLD, 16));
				x = (int)((2 * inPlayTiles.elementAt(i).getX() + data.getTileWidth())/2) - 5;
				y = (int)((2 * inPlayTiles.elementAt(i).getY() + data.getTileHeight())/2) + 6;
				g.drawString(inPlayTiles.elementAt(i).getNumBorderingMines(), 
						x, y);
			}
		}
	}
	
	public void renderGameBoard(Graphics g)
	{
		Tile[][] tileSprites = data.getGameBoard();
		
		for (int row = 0; row < data.getMaxRows(); row++) 
		{
			for (int col = 0; col < data.getMaxCols(); col++) 
			{
				if (tileSprites[row][col].getMine().getState().equals(INVISIBLE_STATE) ||
						tileSprites[row][col].getMine().getState().equals(INTACT_STATE))
							renderSprite(g, tileSprites[row][col]);
			}
		}
	}
	
	public void renderCheese(Graphics g)
	{
		Tile[][] tileSprites = data.getGameBoard();
		
		for (int row = 0; row < data.getMaxRows(); row++) 
		{
			for (int col = 0; col < data.getMaxCols(); col++) 
			{
				renderSprite(g, tileSprites[row][col].getCheese());
			}
		}
	}
	
	public void renderRats(Graphics g)
	{
		Vector<Rat> rats = data.getRats();
		for (int i = 0; i < rats.size(); i++)
		{
			renderSprite(g, rats.get(i));
		}
	}
	
	public void renderSprite(Graphics g, Sprite s)
	{
		if (!s.getState().equals(INVISIBLE_STATE))
		{
			SpriteType sT = s.getSpriteType();
			Image img = sT.getStateImage(s.getState());
			g.drawImage(img, (int)s.getX(), (int)s.getY(), sT.getWidth(), sT.getHeight(), null);
		}
	}
}
