package voxspell.voxspellApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * Class to create a video player for the reward video. When the object is instantiated,
 * a video player with embedded video pops up over the existing VOXSPELL program.  
 * @author CJ Bang, Andon Xia
 *
 */
public class VideoProcessor {
	/*
	 * specifies the video file that was provided by clients
	 */
	private String _fileName = "big_buck_bunny_1_minute.avi";
	/*
	 * required components for setting up a video player including all the GUI components
	 */
	private EmbeddedMediaPlayerComponent _mediaPlayerComponent;
	private EmbeddedMediaPlayer _video;
    private JFrame _videoFrame; 
	private JPanel _mainPanel, _buttonPanel, _leftPanel, _rightPanel;
    private JButton _mute, _play, _stop, _extra;
    private ImageIcon _muteImage, _unmuteImage, _playImage, _stopImage, _pauseImage;
    /* 
     * constructor to create a video player with embedded video.
     */
    public VideoProcessor() {
    	createAndShowVideo();
    	setUpListeners();
    }
    /* 
     * private method to create and show a JFrame of video player with embedded video
     */
    private void createAndShowVideo() {
        _videoFrame = new JFrame("Video Reward");
        _videoFrame.setSize(800, 600);
        _videoFrame.setVisible(true);
		_videoFrame.setLocationRelativeTo(null);
        _mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        _video = _mediaPlayerComponent.getMediaPlayer();
        buildGUI();
        _videoFrame.setContentPane(_mainPanel);
        _video.playMedia(_fileName);
    }
    private void buildGUI() {
        _mainPanel = new JPanel(new BorderLayout());
        _buttonPanel = new JPanel();
        _buttonPanel.setLayout(new BorderLayout());
        _buttonPanel.setPreferredSize(new Dimension(800,60));
        _mainPanel.add(_mediaPlayerComponent, BorderLayout.CENTER);
        _mainPanel.add(_buttonPanel, BorderLayout.SOUTH);
    	_muteImage = new ImageIcon("mute.png");
    	_playImage = new ImageIcon("play.png");
    	_stopImage = new ImageIcon("stop.png");
    	_pauseImage = new ImageIcon("pause.png");
    	_unmuteImage = new ImageIcon("unmute.png");

		_play = new JButton(_pauseImage);
		_play.setPreferredSize(new Dimension(50,50));
        _stop = new JButton(_stopImage);
		_stop.setPreferredSize(new Dimension(50,50));
        _mute = new JButton(_muteImage);
		_mute.setPreferredSize(new Dimension(50,50));
        _extra = new JButton("Watch the special clip");
        _extra.setPreferredSize(new Dimension(225, 50));

        _leftPanel = new JPanel();
        _leftPanel.add(_play);
        _leftPanel.add(_stop);
        _leftPanel.add(_mute);

        _rightPanel = new JPanel();
        _rightPanel.add(_extra);
        _buttonPanel.add(_leftPanel, BorderLayout.WEST);
        _buttonPanel.add(_rightPanel, BorderLayout.EAST);
    }
    private void setUpListeners() {
        _videoFrame.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		_video.stop();
        		_videoFrame.dispose();
        	}
        });
        _video.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			public void playing(MediaPlayer arg0) {
				_play.setIcon(_pauseImage);
			}
        	public void finished(MediaPlayer arg0) {
				_play.setIcon(_playImage);
			}
        	public void stopped(MediaPlayer arg0) {
        		_play.setIcon(_playImage);
        	}
        	public void paused(MediaPlayer arg0) {
        		_play.setIcon(_playImage);
        	}
        });
        _play.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (_play.getIcon().equals(_pauseImage)) {
        			_video.pause();
        		} else {
        			_video.play();
        		}
        	}
        });
        _stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_video.stop();
			}
		});
        _extra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (_extra.getText().equals("Watch the special clip")) {
					_extra.setText("Watch the original video");
					VideoWorker videoworker = new VideoWorker();
					videoworker.execute();
				} else {
					_extra.setText("Watch the special clip");
					_video.playMedia(_fileName);
				}
			}
		});
        _mute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (_mute.getIcon().equals(_muteImage)) {
					_mute.setIcon(_unmuteImage);
				} else {
					_mute.setIcon(_muteImage);
				}
				_video.mute();
			}
		}); 	
    }
    protected class VideoWorker extends SwingWorker<Void, Void> {
    	public static final String command = "ffmpeg -i big_buck_bunny_1_minute.avi -acodec copy -ss 00:00:16 -t "
    			+ "00:00:07 bird_cut.avi"+"\nffmpeg -i bird_cut.avi -c copy -an muted_bird_cut.avi"+"\nffmpeg -i "
    			+ "final_hey.mp3 -i muted_bird_cut.avi -acodec copy -vcodec copy muxed.avi";//+"\nrm -f bird_cut.avi"
    			//+"\nrm -f muted_bird_cut.avi";
    	protected Void doInBackground() throws Exception {
    		ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
    		Process process = pb.start();
    		process.waitFor();
    		_video.playMedia("muxed.avi");
			return null;
    	}   	
    	protected void done(){
    		File file = new File("muxed.avi");
    		file.delete();
    	}
    }
}