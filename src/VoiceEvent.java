package voxspell;

/**
 * This class requires getVoiceType called to be useful. 
 * VoiceEvent handles the JComboBox ActionListener that 
 * feeds this class the type of voice that was selected by the
 * user. By default, the VoiceEvent created is "Default" 
 * festival voice(kal_diphone). Additional voices may be added by 
 * adding the name of the voice as a String parameter into the
 * case statements, switched on by the name of the voice in the
 * JComboBox.
 * @author Andon Xia
 */
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
	
	/*
	* Currently only the New Zealand and default voices are supported.
	* This method returns the name of the String that is to be input in
	* the festival environment to change the voice, along with +('voice_...')
	*/
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
