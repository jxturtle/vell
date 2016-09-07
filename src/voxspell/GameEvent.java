package voxspell;

public class GameEvent {
	public enum EventType {correct, incorrect};
	private EventType _type;
	private GameEvent(EventType type) {
		_type = type;
	}
	public static GameEvent makeCorrectEvent() {
		return new GameEvent(EventType.correct);
	}
	public static GameEvent makeIncorrectEvent() {
		return new GameEvent(EventType.incorrect);
	}
	public EventType eventType() {
		return _type;
	}
}
