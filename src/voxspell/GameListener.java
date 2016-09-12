package voxspell;

public interface GameListener {
	void updateProgressBar(GameEvent event);
	String getWord();
	void setWord(GameEvent event, String word);
	int getLength();
}