package voxspell.voxspellApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class VideoProcessor {
	private String _fileName = "big_buck_bunny_1_minute.avi";
	private EmbeddedMediaPlayerComponent _mediaPlayerComponent;
	private EmbeddedMediaPlayer _video;
    private JFrame _videoFrame; 
	private JPanel _mainPanel, _buttonPanel, _leftPanel, _rightPanel;
    private JButton _mute, _play, _stop, _extra;
    private ImageIcon _muteImage, _unmuteImage, _playImage, _stopImage, _pauseImage;
    public VideoProcessor() {
    	createAndShowVideo();
    	setUpListeners();
    }
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
					_video.playMedia("bird_hey.avi");
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
}