package voxspell;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Class that draws a progress bar for each level of game session. The colour
 * of the bar also reflects how well the user is playing the game session.
 * An extra representation of accuracy rate is also present on the right.
 * @author CJ Bang
 */
public class StatsModel extends JPanel {
	private static final int BAR_HEIGHT = 20;
	private int _level, _wordsCorrect, _wordsIncorrect, _totalWords;
	private Color _color;
	/* 
	 * Each statsModel represents a progress bar with progress % that user makes
	 * so takes level as parameter (from 1 to 11). TotalWords will be either 9 
	 * or 10 depending on the user's progress.
	 */
	public StatsModel(int level, int totalWords) {
		_level = level;
		_totalWords = totalWords;
	}
	/* 
	 * updates the StatsModel when GameEvent is fired. StatsModelAdapter 
	 * calls this method when the GameEvent is fired. Then the StatsModel
	 * repaints instantly
	 */
	public void compute(int wordsCorrect, int wordsIncorrect) {
		_wordsCorrect = wordsCorrect;
		_wordsIncorrect = wordsIncorrect;
		repaint();
	}
	/* 
	 * resets int parameters. Required for the case where StatsModel for the same
	 * level is called twice. (When the user repeats the level once complete)
	 */
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
		// if review mode, total words can be any value between 1 to 10 unlike
		// the game mode, therefore word number needs to be shown on the panel. 
		// Also the percentage is shown in the same manner as in game mode.
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
			// if game mode, there are three aspects to be updated 
			//  1) the length of the progress bar: whenever user gets the word correct, 
			//     bar length increases. The maximum length is 9.
			//  2) the color of the progress bar: if user gets one word wrong, the color 
			//     is changed to yellow to give the user a warning. And to red if user 
			//     gets two words wrong - the color persists through the session.
			//  3) the accuracy rate in percentage. percentage out of 9 words and
			//     once user gets one word wrong, % out of 10 words is displayed. 
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
