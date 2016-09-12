package voxspell;

public class GameEvent {
	public enum EventType {wordSpoken, wordCorrect, wordIncorrect};
	private EventType _type;
	private GameEvent(EventType type) {
		_type = type;
	}
	public static GameEvent makeFestivalEvent() {
		return new GameEvent(EventType.wordSpoken);
	}
	public static GameEvent makeCorrectEvent() {
		return new GameEvent(EventType.wordCorrect);
	}
	public static GameEvent makeIncorrectEvent() {
		return new GameEvent(EventType.wordIncorrect);
	}
	public EventType eventType() {
		return _type;
	}
}
