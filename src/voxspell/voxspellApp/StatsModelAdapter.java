package voxspell;

public class StatsModelAdapter implements GameListener {
	private StatsModel _adaptee;
	private int _length = 0;
	public StatsModelAdapter(StatsModel view) {
		_adaptee = view;
	}
	
	@Override
	public void updateProgressBar() {
		_length++;
		_adaptee.compute(_length);
	}
}
