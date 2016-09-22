
package voxspell.voxspellApp;

import javax.swing.SwingWorker;

import voxspell.VoiceEvent;

import java.io.OutputStream;

/**
 * This class is responsible for entering commands to be spoken
 * into the festival environment without "locking up" Java GUI.
 * If no voice is selected in the main frame's JComboBox, then the
 * Default voice option ("voice_kal_diphone") is selected.
 * @author Andon Xia
 */
 
public class VoiceWorker extends SwingWorker<Void, Void>{
	
	private int _sleep;
	private String _command;
	Process process;
	OutputStream output;
	private String _voiceType;
	
	public VoiceWorker(int sleep, String command) {
		_sleep = sleep;
		_command = command;
	}
	//output.write("(name_of_voice)n".getBytes());
	@Override
	protected Void doInBackground() throws Exception {
		_voiceType = VoiceEvent.getVoiceType();
//		Thread.sleep(_sleep);
		Runtime rt = Runtime.getRuntime();
		process = rt.exec("festival --pipe");
		output = process.getOutputStream();
		
		output.write(("(" + _voiceType + ")n").getBytes());
		//output.flush();
		if (_command.length() > 10) {
			String[] cmdArray = _command.split("\\:");
			for (String s: cmdArray) {
				output.write(("(SayText \"" + s + "\" )n").getBytes());
				Thread.sleep(_sleep);
			}
		} else {
			output.write(("(SayText \"" + _command + "\" )n").getBytes());
		}

		output.write(("(exit)n").getBytes());
		output.flush();
		return null;
	}	
	
	
}
