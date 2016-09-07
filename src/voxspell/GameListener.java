package voxspell;

public interface GameListener {
	void updateProgressBar(GameEvent event);
	int getLength();
}