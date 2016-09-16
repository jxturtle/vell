package voxspell;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ReviewStatsModel extends StatsModel {

	public ReviewStatsModel() {
		super();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
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
