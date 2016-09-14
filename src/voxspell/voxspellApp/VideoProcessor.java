package voxspell.voxspellApp;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class VideoProcessor {
	private final EmbeddedMediaPlayerComponent _mediaPlayerComponent;
	private JFrame _frame;
	private JPanel _panel;
	private VideoProcessor(String[] args) {
		_mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		final EmbeddedMediaPlayer _video = _mediaPlayerComponent.getMediaPlayer();
		_panel = new JPanel(new BorderLayout());
		_panel.add(_mediaPlayerComponent,)
	}
}
