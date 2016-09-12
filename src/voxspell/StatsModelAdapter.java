package voxspell;

import java.util.ArrayList;

public class StatsModelAdapter implements GameListener {
	private StatsModel _adaptee;
	private int _length, _colorCode;
	private String _word;
	private ArrayList<StatsModelAdapter> _listeners = new ArrayList<StatsModelAdapter>();
	public StatsModelAdapter(StatsModel view, int length, int colorCode, String word) {
		_adaptee = view;
		_length = length;
		_colorCode = colorCode;
		_word = word;
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
	public void updateProgressBar(GameEvent event) {
		switch (event.eventType()) {
		case wordCorrect:
			_length++;
			_adaptee.compute(_length, _colorCode);
			break;
		case wordIncorrect:
			_colorCode++;
			_adaptee.compute(_length, _colorCode);			
			break;
		default:
			break;
		}
	}
	
	public void setLength(int correct) {
		_length = correct;
	}
	@Override
	public int getLength() {
		return _length;	
	}
}