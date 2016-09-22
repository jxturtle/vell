package voxspell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GlobalStatsModel extends JPanel {
	private static final int BAR_WIDTH = 22;
	private static final int BAR_CEILING = 350;
	private static final int HORIZONTAL_GAP = 10;
	private static final int VERTICAL_GAP = 30;
	private int[] _percentage;
	public GlobalStatsModel(int[] percentage) {
		setPreferredSize(new Dimension(400,480));
		_percentage = percentage;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = 40;
		int y = 355;
		g.drawString("%", 10, 30);
		g.drawLine(40,50,40,BAR_CEILING);
		for (int scale = 0; scale < 101; scale+=10) {
			g.drawString(Integer.toString(scale), 10, y);
			y -= VERTICAL_GAP;
		}
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