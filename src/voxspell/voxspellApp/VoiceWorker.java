package voxspell.voxspellApp;

import javax.swing.SwingWorker;

import java.io.OutputStream;

public class VoiceWorker extends SwingWorker<Void, Void>{
	
	private int _sleep;
	private String _command;
	Process process;
	OutputStream output;
	private String _voice;
	
	public VoiceWorker(int sleep, String command) {
		_sleep = sleep;
		_command = command;
	}
	//output.write("(name_of_voice)n".getBytes());
	@Override
	protected Void doInBackground() throws Exception {
		Thread.sleep(_sleep);
		Runtime rt = Runtime.getRuntime();
		process = rt.exec("festival --pipe");
		output = process.getOutputStream();
		output.write(("(SayText \"" + _command + "\" )n").getBytes());
		output.flush();
		process.waitFor();
		//System.out.println("SayText Hello -o | festival --tts");
		//rt.exec("SayText Hello -o");
		//rt.exec("SayText" + _command + ""
//		ProcessBuilder pb = new ProcessBuilder("bash", "-c", _command);
//		Process process = pb.start();
//		process.waitFor();
//		process.destroy();
		return null;
	}	
	
	public void setVoice(String voice) {
		_voice = voice;
	}
}