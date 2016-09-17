
package voxspell.voxspellApp;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class VideoProcessor {
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public VideoProcessor() {
        final JFrame frame = new JFrame("Video Reward");

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

        final EmbeddedMediaPlayer video = mediaPlayerComponent.getMediaPlayer();
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(mediaPlayerComponent, BorderLayout.CENTER);
        
        frame.setContentPane(panel);

        JButton btnMute = new JButton("Shh....");
        panel.add(btnMute, BorderLayout.NORTH);
        btnMute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				video.mute();
			}
		});
        
        frame.setLocation(100, 100);
        frame.setSize(1050, 600);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
		frame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			frame.dispose();
		}
	});

        String filename = "bird_hey.avi";
        video.playMedia(filename);
    }
}




//package voxspell.voxspellApp;
//
//import uk.co.caprica.vlcj.binding.LibVlc;
//import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
//import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
//import uk.co.caprica.vlcj.runtime.RuntimeUtil;
//
//import java.awt.BorderLayout;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.SwingUtilities;
//
//import com.sun.jna.Native;
//import com.sun.jna.NativeLibrary;
//
//public class VideoProcessor {
//	private final EmbeddedMediaPlayerComponent _mediaPlayerComponent;
//	private JButton _start, _back;
//	private JFrame _frame;
//	private JPanel _panel;
//	private JPanel _videoPanel;
//	public VideoProcessor(JButton start, JButton back) {
//		_start = start;
//		_back = back;
//		final JFrame _frame = new JFrame("Video Reward");
//		_panel = new JPanel();
//		_mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
//		
//		JPanel panel = new JPanel(new BorderLayout());
//		panel.add(_mediaPlayerComponent, BorderLayout.CENTER);
//		_frame.setContentPane(panel);
//		final EmbeddedMediaPlayer video = _mediaPlayerComponent.getMediaPlayer();
//		
//		_frame.add(_panel);
//		_frame.setContentPane(_mediaPlayerComponent);
////		_frame.setLocation(100, 100);
//	    _frame.setSize(800, 600);
//	    //_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    _frame.setVisible(true);
//		_frame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				_frame.dispose();
//				int test = JOptionPane.showConfirmDialog(null, "Would you like to move on to the next level?", "Level finished", JOptionPane.YES_NO_OPTION);
//				switch(test) {
//				case JOptionPane.YES_OPTION:
//					_start.setText("Begin the next level");
//					_start.setVisible(true);
//					_back.setVisible(true);
//					break;
//				case JOptionPane.NO_OPTION:
//					_start.setText("Repeat the same level");
//					_start.setVisible(true);
//					_back.setVisible(true);
//					break;
//				default:
//					break;
//				}
//			}
//		});
//        String filename = "bird_hey.avi";
//        video.playMedia(filename);
//	}
//    public void showVideo() {
//        
//        NativeLibrary.addSearchPath(
//            RuntimeUtil.getLibVlcLibraryName(), "/Applications/vlc-2.0.0/VLC.app/Contents/MacOS/lib"
//        );
//        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
//        
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new VideoProcessor();
//            }
//        });
//    }
//}