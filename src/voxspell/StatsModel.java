package voxspell;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class StatsModel extends JPanel {

	private static final int BAR_HEIGHT = 20;
	private int _level, _wordsCorrect, _wordsIncorrect, _totalWords;
	private Color _color;

	public StatsModel(int level, int totalWords) {
		_level = level;
		_totalWords = totalWords;
	}
	public void compute(int wordsCorrect, int wordsIncorrect) {
		_wordsCorrect = wordsCorrect;
		_wordsIncorrect = wordsIncorrect;
		repaint();
	}
	public void setNumber(int wordsCorrect, int wordsIncorrect, int totalWords) {
		_wordsCorrect = wordsCorrect;
		_wordsIncorrect = wordsIncorrect;
		_totalWords = totalWords;
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
		if (_level == 0) {
			if (_totalWords > 10) {
				_totalWords = 10;
			}
			int wordNumber = _wordsCorrect + _wordsIncorrect + 1;
			if (wordNumber > _totalWords) {
				wordNumber = _totalWords;
			}
			g.setFont(new Font("Verdana", Font.BOLD, 20));
			g.drawString("Word " + Integer.toString(wordNumber) + " of " + Integer.toString(_totalWords), 140,100);
			Graphics2D g2 = (Graphics2D)g.create();
			g2.setFont(new Font("Verdana", Font.BOLD, 50));
			g2.drawString(Integer.toString(100*_wordsCorrect/_totalWords)+"%", 170,250);
		} else {
			if (_wordsIncorrect >= 2) {
				_color = Color.RED;
				_totalWords = 10;
			} else if (_wordsIncorrect == 1) {
				_color = Color.YELLOW;
				_totalWords = 10;
			} else {
				_color = Color.GREEN;
			}
			g.setColor(_color);
			g.fillRect(0, 5, _wordsCorrect*30, BAR_HEIGHT);
			Graphics2D g2 = (Graphics2D)g.create();
			g2.setColor(Color.BLACK);		
			g2.setFont(new Font("Verdana", Font.BOLD, 18));
			g2.drawString(Integer.toString(100*_wordsCorrect/_totalWords)+"%", 280, 17);
		}
	}
}