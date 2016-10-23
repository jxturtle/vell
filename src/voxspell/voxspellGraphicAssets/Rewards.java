package voxspell.voxspellGraphicAssets;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import voxspell.voxspellApp.TrickTreat;

public class Rewards extends JPanel {
	private JPanel panel_1;
	private JComboBox comboBox;
	private JButton _watchVideo;
	private JButton _getJoke;
	private TrickTreat _tricksAndTreats;
	private JLabel _tricksLabel,_treatsLabel;
	public Rewards(JLabel tricksLabel, JLabel treatsLabel, TrickTreat tricksAndTreats) {
		
		_tricksLabel = tricksLabel;
		_treatsLabel = treatsLabel;
		_tricksAndTreats = tricksAndTreats;
		
		buildGUI();
		setUpActionListeners();
	}
	
	private void setUpActionListeners() {
		//Source of jokes = http://www.jokes4us.com/miscellaneousjokes/cleanjokes.html.
		_getJoke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (_tricksAndTreats.getTricks() >= 5) {
					_tricksAndTreats.subtractTricks(5);
					String stringTricks = Integer.toString(_tricksAndTreats.getTricks());
					_tricksLabel.setText(stringTricks);
					_tricksLabel.revalidate();
					File file = new File("jokes.txt");
					try {
						Scanner fin = new Scanner(file);
						ArrayList<String> jokes = new ArrayList<>();
						while(fin.hasNext()) {
							jokes.add(fin.nextLine());
						}
						int randomInteger = (int)(Math.random() * jokes.size());
						String randomJoke = jokes.get(randomInteger);
						JOptionPane joke = new JOptionPane();
						joke.showMessageDialog(new JFrame(), randomJoke);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane notEnoughTricks = new JOptionPane();
					notEnoughTricks.showMessageDialog(new JFrame(), "<html>You don't have enough tricks.<br>Play basic levels to earn tricks.</html>");
				}				
			}
		});
		
		_watchVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (comboBox.getSelectedItem().toString()) {
				case "Big Buck Bunny (-10 Treats)":
					if (_tricksAndTreats.getTreats() >= 10) {
						_tricksAndTreats.subtractTricks(10);
					} else {
						JOptionPane notEnoughTricks = new JOptionPane();
						notEnoughTricks.showMessageDialog(new JFrame(), "<html>You don't have enough treats.<br>Play advanced levels to earn treats.</html>");
					}
					break;
				case "Heya (-30 Treats)":
					if (_tricksAndTreats.getTreats() >= 30) {
						_tricksAndTreats.subtractTricks(30);
					} else {
						JOptionPane notEnoughTricks = new JOptionPane();
						notEnoughTricks.showMessageDialog(new JFrame(), "<html>You don't have enough treats.<br>Play advanced levels to earn treats.</html>");
					}										
					break;
				}
			}
		});
		
	}
	private void buildGUI() {
		setOpaque(false);
		setBounds(128, 211, 543, 101);
		setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBounds(273, 0, 300, 101);
		add(panel_1);
		panel_1.setOpaque(false);
		panel_1.setBorder(new TitledBorder(null, "Spend Treats", TitledBorder.CENTER, TitledBorder.TOP, null, Color.WHITE));
		panel_1.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Big Buck Bunny (-10 Treats)", "Heya (-30 Treats)"}));
		comboBox.setBounds(10, 33, 173, 40);
		panel_1.add(comboBox);
		
		_watchVideo = new JButton("Watch");
		_watchVideo.setBounds(193, 33, 85, 40);
		panel_1.add(_watchVideo);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 270, 101);
		add(panel);
		panel.setOpaque(false);
		panel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		panel.setBorder(new TitledBorder(null, "Spend Tricks", TitledBorder.CENTER, TitledBorder.TOP, null, Color.WHITE));
		panel.setLayout(null);
		
		_getJoke = new JButton("Get Joke (-5 Tricks)");
		_getJoke.setBounds(54, 31, 158, 41);
		panel.add(_getJoke);
	}




}
