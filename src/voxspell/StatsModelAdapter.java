package voxspell;

import java.util.ArrayList;

public class StatsModelAdapter implements GameListener {
	private StatsModel _adaptee;
	private int _length, _colorCode;
	private ArrayList<StatsModelAdapter> _listeners = new ArrayList<StatsModelAdapter>();
	public StatsModelAdapter(StatsModel view, int length, int colorCode) {
		_adaptee = view;
		_length = length;
		_colorCode = colorCode;
	}
	
	@Override
	public void updateProgressBar(GameEvent event) {
		switch (event.eventType()) {
		case correct:
			_length++;
			_adaptee.compute(_length, _colorCode);
			break;
		case incorrect:
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