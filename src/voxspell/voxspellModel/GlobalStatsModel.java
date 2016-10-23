package voxspell.voxspellModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Class that draws a bar chart for statistics of the VOXSPELL application.
 * Shows the statistics saved in the hidden files as long as the statistics get
 * cleared by the user. int[] with percentage (from 0% to 100%) for each level 
 * is passed in as a parameter (from the class StatisticsManager).
 * @author CJ Bang
 *
 */
public class GlobalStatsModel extends JPanel {
	private static final int BAR_WIDTH = 22;
	private static final int BAR_CEILING = 350;
	private static final int HORIZONTAL_GAP = 10;
	private static final int VERTICAL_GAP = 30;
	private int[] _percentage;
	/*
	 * Constructor for GLobalStatsModel object that takes int[] as 
	 * a parameter.
	 */
	public GlobalStatsModel(int[] percentage) {
		setPreferredSize(new Dimension(400,480));
		_percentage = percentage;
	}
	/*
	 * Refreshes the appearance of this JPanel. This method performs the
	 * actual drawing of the bar chart.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = 40;
		int y = 355;
		//draws a y-axis with 10% increments 
		g.drawString("%", 10, 30);
		g.drawLine(40,50,40,BAR_CEILING);
		for (int scale = 0; scale < 101; scale+=10) {
			g.drawString(Integer.toString(scale), 10, y);
			y -= VERTICAL_GAP;
		}
		//draws the actual bar for each level and draws labels for x axis
		g.drawLine(x, BAR_CEILING, 382, BAR_CEILING);
		for (int i = 0; i < _percentage.length; i++) {
			g.setColor(new Color(0,0,150));
			g.fillRect(x, BAR_CEILING - _percentage[i]*3, BAR_WIDTH, _percentage[i]*3);
			g.setColor(Color.black);
			g.drawString("Lv"+Integer.toString(i+1), x, 370);
			x += BAR_WIDTH + HORIZONTAL_GAP;
		}		
	}
}
