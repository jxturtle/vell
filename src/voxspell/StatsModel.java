package voxspell;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatsModel extends JPanel {

	private static final int BAR_HEIGHT = 20;
	private int _level;
	private int _wordsCorrect;
	
	public StatsModel(int level) {
		_level = level;
		
	}
	public void compute(int wordsCorrect) {
		_wordsCorrect = wordsCorrect;
		repaint();
	}
	
	/**
	 * Refreshes the appearance of this JComponent. This method performs the
	 * actual drawing of the bar chart.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(_wordsCorrect == 0) {
			return;
		} else if(_wordsCorrect <= 3) {
			g.setColor(Color.RED);
		} else if (_wordsCorrect <= 6) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.GREEN); 
		}
		g.fillRect(0, 5, _wordsCorrect*30, BAR_HEIGHT);
		
//		int x = MARGIN;
//		for (int i = 0; i < 11; i++) {
//			g.setColor(Color.GREEN);
//			g.fillRect(0, x, _wordsCorrect*10, BAR_HEIGHT);
//			x += BAR_HEIGHT + VERTICAL_GAP;
//		}
	}

}
