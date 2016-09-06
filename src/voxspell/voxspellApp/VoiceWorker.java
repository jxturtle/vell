package voxspell.voxspellApp;

import javax.swing.SwingWorker;

public class VoiceWorker extends SwingWorker<Void, Void>{
	private String _command;
	
	public VoiceWorker(String command) {
		_command = command;
	}

	@Override
	protected Void doInBackground() throws Exception {
		//System.out.println("Doing in background...");
		System.out.println(_command);
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", _command);
		Process process = pb.start();
		process.waitFor();
		process.destroy();
		return null;
	}
	
	
}
