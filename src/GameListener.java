package voxspell;

/**
 * Interface for listener for GameEvent. Adapters implement this interface to must
 * implement the given four methods 
 * @author CJ Bang
 *
 */
public interface GameListener {
	/*
	 * signature methods that need implementing by the class that implements 
	 * this interface
	 */ 
	void updateProgressBar(GameEvent event);
	String getWord();
	void setWord(GameEvent event, String word);
	int getLength();
}
