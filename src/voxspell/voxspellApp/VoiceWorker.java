
package voxspell.voxspellApp;

import javax.swing.SwingWorker;

import voxspell.VoiceEvent;

import java.io.OutputStream;

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
		Thread.sleep(_sleep);
		Runtime rt = Runtime.getRuntime();
		process = rt.exec("festival --pipe");
		output = process.getOutputStream();
		
		output.write(("(" + _voiceType + ")n").getBytes());
		//output.flush();
		output.write(("(SayText \"" + _command + "\" )n").getBytes());
		output.write(("(exit)n").getBytes());
		output.flush();
		//process.destroy();
		//System.out.println(_command);
		//output.flush();
		//process.destroyForcibly();
		//process.waitFor();
		//System.out.println("SayText Hello -o | festival --tts");
		//rt.exec("SayText Hello -o");
		//rt.exec("SayText" + _command + ""
//		ProcessBuilder pb = new ProcessBuilder("bash", "-c", _command);
//		Process process = pb.start();
//		process.waitFor();
//		process.destroy();
		return null;
	}	
	
	
}
