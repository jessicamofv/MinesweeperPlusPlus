package minesweeperplusplus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class MinesweeperPlusPlusLevelNamePanel extends JPanel
{
	private MinesweeperPlusPlusLevelEditor editor;
	private String levelName;
	
	public MinesweeperPlusPlusLevelNamePanel(MinesweeperPlusPlusLevelEditor initEditor, 
			String initLevelName)
	{
		editor = initEditor;
		levelName = initLevelName;
	}
	
	public void setLevelName(String initLevelName)
	{
		levelName = initLevelName;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font(null, Font.BOLD, 16));
		g.drawString(levelName, 5, 5);
	}
}
