package voxspell.voxspellModel;

import java.util.ArrayList;

import voxspell.voxspellGraphicAssets.GameGUI;
/**
 * Adapter class that adapts GameLogic to the StatsModel, GlobalStatsModel, 
 * GameGUI target interface. An instance of GameAdapter allows to show the
 * user's progress on StatsModel and GlobalStatsModel, to detect the change
 * in voice setting during the game, and to achieve an appropriate GameGUI. 
 * @author CJ Bang
 *
 */
public class GameAdapter implements GameListener {
	private StatsModel _statsView;
	private int _length, _incorrectCount, _totalWords;
	private String _word;
	private ArrayList<GameAdapter> _listeners = new ArrayList<GameAdapter>();
	private GameGUI _game;
	/* 
	 * Constructor for Adapter object. First two parameters are two models to show 
	 * updates, and the rest are necessary to reset the adapter object.
	 */
	public GameAdapter(StatsModel view, GameGUI game, int length, int incorrectCount, String word) {
		_statsView = view;
		_length = length;
		_incorrectCount = incorrectCount;
		_word = word;
		_totalWords = 9; // _totalWords to be tested is set at 9 as default but it
						 // increases to 10 if user gets one word wrong during the game session
		_game = game;
	}
	/*
	 * sets the field _word to the current word that is being tested in game session
	 * for the 'listen the word again' function. 
	 */
	public void setWord(GameEvent event, String word) {
		switch (event.eventType()) {
		case wordSpoken:
			_word = word;
			break;
		default:
			break;	
		}
	}
	/*
	 * returns String _word. Used in GameGUI for listenAgain button
	 */
	public String getWord() {
		return _word;
	}
	/*
	 * updates models according to the type of GameEvent. If eventType is wordCorrect
	 * or wordIncorrect, it updates StatsModel to update the progressbar, and if the
	 * type is gameFinished, it notifies GameGUI object.
	 */
	@Override
	public void update(GameEvent event) {
		switch (event.eventType()) {
		case wordCorrect:
			_length++;
			_statsView.compute(_length, _incorrectCount);
			break;
		case wordIncorrect:
			_incorrectCount++;
			_statsView.compute(_length, _incorrectCount);			
			break;
		case gameFinished:
			_game.setGameFinished();
			break;
		default:
			break;
		}
	}
	/*
	 * resets the number of correct words, incorrect words, total words to be tested
	 */
	public void setNumber(int wordsCorrect, int wordsIncorrect, int totalWords) {
		_length = wordsCorrect;
		_incorrectCount = wordsIncorrect;
		_totalWords = totalWords;
	}
	/*
	 * returns the length of the progress bar for the current game session. 
	 * Used in gameLogic to check if the user has got 9 words correct in a row
	 * before the 10th word is tested
	 */
	@Override
	public int getLength() {
		return _length;	
	}
}
