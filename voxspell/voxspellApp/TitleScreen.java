package voxspell.voxspellApp;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TitleScreen extends JPanel {
	private JPanel _butPanel, _picPanel, _viewPanel;
	private JButton _level1, _level2, _level3, _level4, _level5;
	private JLabel _picLabel, _label;
	private ImageIcon _image;
	protected static JFrame frame;
	public TitleScreen() {
		buildGUI();
	}
	
	private void buildGUI() {
		_picPanel = new JPanel();
		_picPanel.setPreferredSize(new Dimension(600, 250));
		_viewPanel = new JPanel();
		_viewPanel.setPreferredSize(new Dimension(600, 150));
		_butPanel = new JPanel();
		_butPanel.setPreferredSize(new Dimension(600, 50));
		_image = new ImageIcon("main.jpg");
		_picLabel = new JLabel(_image);
		_picPanel.add(_picLabel);

		_label = new JLabel("<html>Welcome to VOXSPELL<br>Please select the spelling level</html>", SwingConstants.CENTER);
		_viewPanel.add(_label);

		_level1 = new JButton("Level 1");
		_level2 = new JButton("Level 2");
		_level3 = new JButton("Level 3");
		_level4 = new JButton("Level 4");
		_level5 = new JButton("Level 5");
		_butPanel.setLayout(new GridLayout(1,5));
		_butPanel.add(_level1);
		_butPanel.add(_level2);
		_butPanel.add(_level3);
		_butPanel.add(_level4);
		_butPanel.add(_level5);
				
		setLayout(new BorderLayout());
		add(_picPanel, BorderLayout.NORTH);
		add(_viewPanel, BorderLayout.CENTER);
		add(_butPanel, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		frame = new JFrame();
		frame.add(new TitleScreen());
		frame.setSize(600, 450);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("VOXSPELL");
	}
}