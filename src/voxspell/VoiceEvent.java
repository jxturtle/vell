package voxspell;


public class VoiceEvent {
	public enum VoiceType {Default, BritishEnglish, Spanish, NewZealand};
	private static VoiceType _type;
	private String _voiceType;
	private VoiceEvent(VoiceType type) {
		_type = type;
	}
	public static VoiceEvent makeDefaultVoice() {
		return new VoiceEvent(VoiceType.Default);
	}
	public static VoiceEvent makeBritishEnglishVoice() {
		return new VoiceEvent(VoiceType.BritishEnglish);
	}
	
	public static VoiceEvent makeSpanishVoice() {
		return new VoiceEvent(VoiceType.Spanish);
	}
	
	public static VoiceEvent makeNewZealandVoice() {
		return new VoiceEvent(VoiceType.NewZealand);
	}
	
	public static String getVoiceType() {
		String _voiceType = null;
		switch (_type) {
		case Default:
			_voiceType = "voice_kal_diphone";
			break;
		case NewZealand:
			_voiceType = "voice_akl_nz_jdt_diphone";
			break;
		default:
			break;
		}
		return _voiceType;
	}

}
