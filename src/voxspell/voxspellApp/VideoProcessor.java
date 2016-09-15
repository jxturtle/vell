package voxspell.voxspellApp;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class VideoProcessor {
	private final EmbeddedMediaPlayerComponent _mediaPlayerComponent;
	private JFrame _frame;
	private JPanel _panel;
	private VideoProcessor(String[] args) {
		_frame = new JFrame("Reward Video");
		_panel = new JPanel();
//		_panel.setLayout(new );
		_mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		final EmbeddedMediaPlayer video = _mediaPlayerComponent.getMediaPlayer();
		_frame.setContentPane(_mediaPlayerComponent);
//		_frame.setLocation(100, 100);
	    _frame.setSize(800, 600);
	    _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    _frame.setVisible(true);
	    
        String filename = "big_buck_bunny_1_minute.avi";
        video.playMedia(filename);
	}
    public static void main(final String[] args) {
        
        NativeLibrary.addSearchPath(
            RuntimeUtil.getLibVlcLibraryName(), "/Applications/vlc-2.0.0/VLC.app/Contents/MacOS/lib"
        );
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VideoProcessor(args);
            }
        });
    }
}