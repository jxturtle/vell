package voxspell.voxspellGraphicAssets;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InvalidObjectException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import voxspell.voxspellApp.TrickTreat;

public class LevelPanel extends JPanel implements ActionListener {
	private JButton[] _basicLevels,_advancedLevels;
	private JFrame _frame;
	private JPanel _advancedPanel;
	private JPanel _basicPanel;
	private JLabel _help;
	private boolean _isCustomFile;
	private TrickTreat _tricksAndTreats;
	private File _inputFile;
	private boolean fileChanged;
	
	public LevelPanel(JFrame frame, TrickTreat tricksAndTreats, File inputFile, boolean isCustomFile) {
		_frame = frame;
		_inputFile = inputFile;
		_isCustomFile = isCustomFile;
		_tricksAndTreats = tricksAndTreats;
		buildGUI();
		for(int i = 0; i < 6; i++) {
			_basicLevels[i].addActionListener(this);
		}
		for (int i = 0; i < 5; i++) {
			_advancedLevels[i].addActionListener(this);
		}
	}
	
	private void buildGUI() {
		_basicLevels = new JButton[6];
		_advancedLevels = new JButton[5];

		setOpaque(false);
		setBounds(114, 323, 686, 224);
		setBorder(null);
		setLayout(null);
		_advancedPanel = new JPanel();
		_advancedPanel.setBounds(54, 130, 470, 55);
		add(_advancedPanel);
		_advancedPanel.setLayout(null);
		_advancedPanel.setOpaque(false);
		_advancedPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Advanced Levels", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));
		
		_advancedLevels[0] = new JButton("Level 7");
		_advancedLevels[0].setBounds(10, 21, 82, 23);
		_advancedPanel.add(_advancedLevels[0]);
		
		_advancedLevels[1] = new JButton("Level 8");
		_advancedLevels[1].setBounds(102, 21, 82, 23);
		_advancedPanel.add(_advancedLevels[1]);
		
		_advancedLevels[2] = new JButton("Level 9");
		_advancedLevels[2].setBounds(194, 21, 82, 23);
		_advancedPanel.add(_advancedLevels[2]);
		
		_advancedLevels[3] = new JButton("Level 10");
		_advancedLevels[3].setBounds(286, 21, 82, 23);
		_advancedPanel.add(_advancedLevels[3]);
		
		_advancedLevels[4] = new JButton("Level 11");
		_advancedLevels[4].setBounds(378, 21, 82, 23);
		_advancedPanel.add(_advancedLevels[4]);
		
		JLabel label_2 = new JLabel("Please Select a Game Level");
		label_2.setBounds(127, 16, 373, 48);
		add(label_2);
		label_2.setForeground(new Color(255, 215, 0));
		label_2.setFont(new Font("IrisUPC", Font.PLAIN, 40));
		
		_basicPanel = new JPanel();
		_basicPanel.setBounds(6, 64, 565, 55);
		add(_basicPanel);
		_basicPanel.setLayout(null);
		_basicPanel.setOpaque(false);
		_basicPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Basic Levels", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));
		
		_basicLevels[0] = new JButton("Level 1");
		_basicLevels[0].setBounds(10, 21, 82, 23);
		_basicPanel.add(_basicLevels[0]);
		
		
		_basicLevels[1] = new JButton("Level 2");
		_basicLevels[1].setBounds(102, 21, 82, 23);
		_basicPanel.add(_basicLevels[1]);
		
		_basicLevels[2] = new JButton("Level 3");
		_basicLevels[2].setBounds(194, 21, 82, 23);
		_basicPanel.add(_basicLevels[2]);
		
		_basicLevels[3] = new JButton("Level 4");
		_basicLevels[3].setBounds(286, 21, 82, 23);
		_basicPanel.add(_basicLevels[3]);
		
		_basicLevels[4] = new JButton("Level 5");
		_basicLevels[4].setBounds(378, 21, 82, 23);
		_basicPanel.add(_basicLevels[4]);
		
		_basicLevels[5] = new JButton("Level 6");
		_basicLevels[5].setBounds(470, 21, 82, 23);
		_basicPanel.add(_basicLevels[5]);
		
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		_help = new JLabel("?");
		_help.setBounds(623, 169, 53, 55);
		add(_help);
		_help.setToolTipText("<html>\r\nThese are the levels of the Voxspell spelling game.<br>\r\nPlaying the spelling game on the 'Advanced' spelling<br>\r\ndifficulty allow you to unlock different 'Treat' rewards.<br>\r\nGames played on 'Basic' levels will count towards<br>\r\ngaining 'Trick' points.");
		_help.setForeground(Color.WHITE);
		_help.setFont(new Font("Tahoma", Font.BOLD, 40));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(_isCustomFile + " fromHere");
		String levelChosen = ((JButton)e.getSource()).getText();
		for (int i = 0; i < 11; i++) {
			if (levelChosen.equals("Level "+Integer.toString(i+1))) {
				if (_isCustomFile) {
					System.out.println("CustomFile");
					//GameGUI game;
					try {
						GameGUI game = new GameGUI(i+1, _frame,_tricksAndTreats, _inputFile);
						_frame.getContentPane().removeAll();
						_frame.getContentPane().add(game);
						_frame.revalidate();
						_frame.repaint();									
					} catch (InvalidObjectException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					System.out.println("NonCustomFile");
					GameGUI game = new GameGUI(i+1, _frame,_tricksAndTreats);
					_frame.getContentPane().removeAll();
					_frame.getContentPane().add(game);
					_frame.revalidate();
					_frame.repaint();										
				}					

			}
		}

	}

}


