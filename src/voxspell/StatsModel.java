package voxspell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatsModel extends JPanel {

	private static final int BAR_HEIGHT = 20;
	private int _level, _wordsCorrect, _color;
	
	public StatsModel(int level) {
		_level = level;
	}
	public void compute(int wordsCorrect, int color) {
		_wordsCorrect = wordsCorrect;
		_color = color;
		repaint();
	}
	
	public void setCorrect(int wordsCorrect) {
		_wordsCorrect = wordsCorrect;
	}
	
	public int getCorrect() {
		return _wordsCorrect;
	}
	/**
	 * Refreshes the appearance of this JComponent. This method performs the
	 * actual drawing of the bar chart.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (_color >= 2) {
			g.setColor(Color.RED);
		} else if (_color == 1) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.GREEN);
		}

//		if(_wordsCorrect == 0) {
//			return;
//		} else {
			g.fillRect(0, 5, _wordsCorrect*30, BAR_HEIGHT);
//		}
	}
}