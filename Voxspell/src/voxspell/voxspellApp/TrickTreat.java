package voxspell.voxspellApp;

public class TrickTreat {
	private int _tricks, _treats;
	
	
	private static TrickTreat _trickAndTreat = new TrickTreat(0,0);
	private TrickTreat(int tricks, int treats) {
		_tricks = tricks;
		_treats = treats;
	}
	
	public static TrickTreat getInstance() {
		return _trickAndTreat;
	}
	public int getTreats() {
		return _treats;
	}
	
	public int getTricks() {
		return _tricks;
	}
	
	public void addTricks(int tricks) {
		_tricks += tricks;
	}
	
	public void addTreats(int treats) {
		_treats += treats;
	}
	
	public void subtractTreats(int treats) {
		_treats -= treats;
	}
	
	public void subtractTricks(int tricks) {
		_tricks -= tricks;
	}
}
