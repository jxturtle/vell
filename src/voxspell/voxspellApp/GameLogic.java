package voxspell.voxspellApp;

import java.util.List;

import voxspell.GameListener;

public class GameLogic {
	private List<GameListener> _listeners;

	public void addGameListener(GameListener listener) {
		_listeners.add(listener);
	}
	public void wordIsCorrect() {
		for (GameListener listener : _listeners) {
			listener.updateProgressBar();
		}
	}	
}
