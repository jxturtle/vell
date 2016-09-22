package voxspell;

import java.util.ArrayList;

import javax.swing.JFrame;

import voxspell.voxspellApp.GameGUI;

public class GameAdapter implements GameListener {
	private StatsModel _adaptee;
	private int _length, _incorrectCount, _totalWords;
	private String _word;
	private ArrayList<GameAdapter> _listeners = new ArrayList<GameAdapter>();
	private GameGUI _game;
	public GameAdapter(StatsModel view, GameGUI game, int length, int incorrectCount, String word) {
		_adaptee = view;
		_length = length;
		_incorrectCount = incorrectCount;
		_word = word;
		_totalWords = 9;
		_game = game;
	}
	public void setWord(GameEvent event, String word) {
		switch (event.eventType()) {
		case wordSpoken:
			_word = word;
			break;
		default:
			break;	
		}
	}
	public String getWord() {
		return _word;
	}
	@Override
	public void update(GameEvent event) {
		switch (event.eventType()) {
		case wordCorrect:
			_length++;
			_adaptee.compute(_length, _incorrectCount);
			break;
		case wordIncorrect:
			_incorrectCount++;
			_adaptee.compute(_length, _incorrectCount);			
			break;
		case gameFinished:
			_game.setGameFinished();
			break;
		default:
			break;
		}
	}
	public void setNumber(int wordsCorrect, int wordsIncorrect, int totalWords) {
		_length = wordsCorrect;
		_incorrectCount = wordsIncorrect;
		_totalWords = totalWords;
	}
	@Override
	public int getLength() {
		return _length;	
	}
}