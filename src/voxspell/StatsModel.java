package voxspell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatsModel extends JPanel {

	private static final int BAR_HEIGHT = 23;
	private int _level, _wordsCorrect, _wordsIncorrect, _totalWords;
//	private JLabel _levelLabel, _scoreLabel;
	
	public StatsModel(int level) {
		_level = level;
		_totalWords = 9;
	}
//	private void updateNumbers(int wordsCorrect, int wordsTotal) {
//		_scoreLabel.setText(Integer.toString(_wordsCorrect)+" / " +Integer.toString(_wordsTotal));
//	}
	public void compute(int wordsCorrect, int wordsIncorrect) {
		_wordsCorrect = wordsCorrect;
		_wordsIncorrect = wordsIncorrect;
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
		if (_wordsIncorrect >= 2) {
			g.setColor(Color.RED);
			_totalWords = 10;
		} else if (_wordsIncorrect == 1) {
			g.setColor(Color.YELLOW);
			_totalWords = 10;
		} else {
			g.setColor(Color.GREEN);
		}
		g.fillRect(0, 5, _wordsCorrect*30, BAR_HEIGHT);
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setColor(Color.BLACK);		
		g2.setFont(new Font("Verdana", Font.BOLD, 18));
//		g2.drawString("100%", 270, 17);
		g2.drawString(Integer.toString(100*_wordsCorrect/_totalWords)+"%", 280, 15);
	}
	
}