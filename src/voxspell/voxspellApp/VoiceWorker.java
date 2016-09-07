package voxspell.voxspellApp;

import javax.swing.SwingWorker;

public class VoiceWorker extends SwingWorker<Void, Void>{
	private int _sleep;
	private String _command;
	
	public VoiceWorker(int sleep, String command) {
		_sleep = sleep;
		_command = command;
	}

	@Override
	protected Void doInBackground() throws Exception {
		Thread.sleep(_sleep);
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", _command);
		Process process = pb.start();
		process.waitFor();
		process.destroy();
		return null;
	}	
}