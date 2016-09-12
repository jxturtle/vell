package voxspell.voxspellApp;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class VoiceWorker extends SwingWorker<Void, Void>{
	private int _sleep;
	private String _command;
	private JTextField _inputField;
	
	public VoiceWorker(int sleep, String command, JTextField inputField) {
		_sleep = sleep;
		_command = command;
		_inputField = inputField;
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
//	protected void done() {
//		_inputField.setEnabled(true);
//	}
}