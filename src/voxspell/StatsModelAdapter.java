package voxspell;

public class StatsModelAdapter implements GameListener {
	private StatsModel _adaptee;
	private int _length;
	public StatsModelAdapter(StatsModel view, int length) {
		_adaptee = view;
		_length = length;
	}
	
	@Override
	public void updateProgressBar() {
		//System.out.println(_length + " " + "lengthFromAdapter");
		_length++;
		_adaptee.compute(_length);
	}
	
	public void setLength(int correct) {
		_length = correct;
	}
	
	@Override
	public int getLength() {
		return _length;
		
	}
}