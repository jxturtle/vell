
package voxspell.voxspellApp;

import javax.swing.SwingWorker;

import voxspell.VoiceEvent;

import java.io.IOException;
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
		_voiceType = VoiceEvent.getVoiceType();
//		Runtime rt = Runtime.getRuntime();
//		try {
//			process = rt.exec("festival -b");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		output = process.getOutputStream();

	}
	@Override
	protected Void doInBackground() throws Exception {
		Thread.sleep(_sleep);
		Runtime rt = Runtime.getRuntime();
//		try {
			process = rt.exec("festival -b '(" +_voiceType+")' '(SayText \"" + _command + "\")'");
			System.out.println("festival -b '(" +_voiceType+")' '(SayText \"" + _command + "\")'");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		output = process.getOutputStream();
//		output.write(("(_voiceType)n").getBytes());
//		output.flush();
//		output.write(("(SayText \"" + _command + "\" )n").getBytes());
		output.flush();
		process.waitFor();
		process.destroy();
//		process.destroyForcibly();
		return null;
	}	
}