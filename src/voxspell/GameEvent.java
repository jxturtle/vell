package voxspell;

/**
 * Class to describe a change to the game session. An instance of GameEvent is sent
 * to the StatsModelAdapter that converts the event to something that StatsModel or VoiceWorker
 * can interpret.
 * @author CJ Bang
 *
 */
public class GameEvent {
	/* 
	 * Enum to specify three different types of GameEvents. 
	 */
	public enum EventType {wordSpoken, wordCorrect, wordIncorrect};
	
	private EventType _type;

	/* 
	 * Hidden constructor used by the static factory methods. 
	 */
	private GameEvent(EventType type) {
		_type = type;
	}
	/* 
	 * Creates a festivalEvent. 
	 */
	public static GameEvent makeFestivalEvent() {
		return new GameEvent(EventType.wordSpoken);
	}
	/*
	 * Creates a wordCorrectEvent
	 */
	public static GameEvent makeCorrectEvent() {
		return new GameEvent(EventType.wordCorrect);
	}
	/*
	 * Creates a wordIncorrectEvent
	 */
	public static GameEvent makeIncorrectEvent() {
		return new GameEvent(EventType.wordIncorrect);
	}
	/*
	 * returns the type of the event, one of wordSpoken, wordCorrect and wordIncorrect.
	 */
	public EventType eventType() {
		return _type;
	}
}
