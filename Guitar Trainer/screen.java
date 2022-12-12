//Imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame; 
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.UIManager;

//This class will encompass the main screen of the Guitar Practice application
public class Screen extends JFrame implements ActionListener{

	//Colors
	private Color backgroundColor; 
	private Color noteColor;
	private Color textColor;
	
	//Fonts
	private Font noteFont;
	private Font textFont;
	
	private JLabel note; //Note
	private JLabel modes; //Mode options
	
	//Screen dimensions for centering elements
	private Dimension screenSize;
	private int screenWidth;
	private int screenHeight;
	
	//Inputs to change BPM and mode
	private JTextField BPM;
	private JTextField mode;

	private Timer timer;
	
	//Initial BPM
	private int BPMValue = 40;
	

	//This array will contain the current mode's notes or chords (will be changed based on the user's input)
	private String[] currentMode;
	
	//Tracks current index to avoid repeated notes
	private int currentIndex;
	
	
	//Arrays of notes/chords
	
	//This array contains every note as well as a string number
	//NoteFinder mode
	private String[] AllNotes = {"A 1", "A# 1", "B 1", "C 1", "C# 1", "D 1", "D# 1", "E 1", "F 1", "F# 1", "G 1", "G# 1",
								 "A 2", "A# 2", "B 2", "C 2", "C# 2", "D 2", "D# 2", "E 2", "F 2", "F# 2", "G 2", "G# 2",
								 "A 3", "A# 3", "B 3", "C 3", "C# 3", "D 3", "D# 3", "E 3", "F 3", "F# 3", "G 3", "G# 3",
								 "A 4", "A# 4", "B 4", "C 4", "C# 4", "D 4", "D# 4", "E 4", "F 4", "F# 4", "G 4", "G# 4",
								 "A 5", "A# 5", "B 5", "C 5", "C# 5", "D 5", "D# 5", "E 5", "F 5", "F# 5", "G 5", "G# 5",
								 "A 6", "A# 6", "B 6", "C 6", "C# 6", "D 6", "D# 6", "E 6", "F 6", "F# 6", "G 6", "G# 6"}; 
	//NaturalNotes mode
	private String[] NaturalNotes = {"A 1", "B 1", "C 1", "D 1", "E 1", "F 1", "G 1",
									 "A 2", "B 2", "C 2", "D 2", "E 2", "F 2", "G 2",
									 "A 3", "B 3", "C 3", "D 3", "E 3", "F 3", "G 3",
									 "A 4", "B 4", "C 4", "D 4", "E 4", "F 4", "G 4",
									 "A 5", "B 5", "C 5", "D 5", "E 5", "F 5", "G 5",
									 "A 6", "B 6", "C 6", "D 6", "E 6", "F 6", "G 6"};
	//AccidentalNotes mode
	private String[] AccidentalNotes = {"A# 1", "C# 1", "D# 1", "F# 1", "G# 1", "Bb 1", "Db 1", "Eb 1", "Gb 1", "Ab 1",
									    "A# 2", "C# 2", "D# 2", "F# 2", "G# 2", "Bb 2", "Db 2", "Eb 2", "Gb 2", "Ab 2",
									    "A# 3", "C# 3", "D# 3", "F# 3", "G# 3", "Bb 3", "Db 3", "Eb 3", "Gb 3", "Ab 3",
									    "A# 4", "C# 4", "D# 4", "F# 4", "G# 4", "Bb 4", "Db 4", "Eb 4", "Gb 4", "Ab 4",
									    "A# 5", "C# 5", "D# 5", "F# 5", "G# 5", "Bb 5", "Db 5", "Eb 5", "Gb 5", "Ab 5",
									    "A# 6", "C# 6", "D# 6", "F# 6", "G# 6", "Bb 6", "Db 6", "Eb 6", "Gb 6", "Ab 6"};
	
	//Chords
	
	//Major/Minor Chords - MMChords mode
	private String[] MajorMinorChords = {"A", "B", "C", "D", "E", "F", "G", "Am", "Bm", "Cm", "Dm", "Em", "Fm", "Gm"};
	//MajorChords mode
	private String[] MajorChords = {"A", "B", "C", "D", "E", "F", "G"};
	//MinorChords mode
	private String[] MinorChords = {"Am", "Bm", "Cm", "Dm", "Em", "Fm", "Gm"};
	
	//Chords I currently know
	private String[] CurrentChords = {"A","C", "D", "E", "F", "G", "Am", "Bm", "Dm", "Em", "B7"}; //CurrentChords
	
	public Screen() {
		
		//Color instantiation 
		backgroundColor = new Color(15, 15, 18);
		noteColor = new Color(200, 200, 200);
		textColor = new Color(100, 100, 100);
		
		//Font instantiation
		noteFont = new Font("sans-serif", Font.PLAIN, 100);
		textFont = new Font("sans-serif", Font.PLAIN, 20);
		
		//Label instantiation
		note = new JLabel();
		modes = new JLabel("AllNotes | CurrentChords | NaturalNotes | AccidentalNotes | MMChords | MajorChords | MinorChords");
		
		//TextField instantiation
		BPM = new JTextField();
		mode = new JTextField();
		
		//Screen size variables
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.width;
		screenHeight = screenSize.height;
		
		//Default mode
		currentMode = AllNotes;
		
		//Firts index check
		currentIndex = 0;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setSize(1280, 720);
		this.setUndecorated(true);
		this.getContentPane().setBackground(backgroundColor);
		this.setTitle("Guitar Trainer");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		note.setText("A");
		note.setForeground(Color.white);
		note.setFont(noteFont);
		Dimension noteSize = note.getPreferredSize();
		note.setBounds(screenWidth / 2 - noteSize.width / 2, (screenHeight / 3) - noteSize.height / 2, noteSize.width, noteSize.height);
		
		BPM.setBounds(screenWidth / 2 - 50 / 2, screenHeight / 2 - 50 / 2, 50, 50);
		BPM.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		BPM.setBackground(backgroundColor);
		BPM.setForeground(textColor);
		BPM.setText("BPM");
		BPM.setFont(textFont);
		BPM.setHorizontalAlignment(JTextField.CENTER);
		BPM.addActionListener(this);
		
		mode.setBounds(screenWidth / 2 - 200 / 2, screenHeight / 2 + 50, 200, 50);
		mode.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		mode.setBackground(backgroundColor);
		mode.setForeground(textColor);
		mode.setText("Mode");
		mode.setFont(textFont);
		mode.setHorizontalAlignment(JTextField.CENTER);
		mode.addActionListener(this);
		
		modes.setFont(textFont);
		modes.setBackground(backgroundColor);
		modes.setForeground(textColor);
		
		Dimension modeDimension = modes.getPreferredSize();
		
		modes.setBounds(screenWidth / 2 - modeDimension.width / 2, screenHeight * 2 / 3, modeDimension.width + 10, modeDimension.height);

		this.add(note);
		this.add(BPM);
		this.add(mode);
		this.add(modes);
		this.setVisible(true);
		
		//Timer/timer task responsible for changing the note/chord
		timer = new Timer();
		timer.schedule(new event(currentMode), 0, 60000 / BPMValue);
	}
	
	//Input for BPM/mode text fields
	@Override
	public void actionPerformed(ActionEvent e) {
		//Changes BPM
		if(e.getSource() == BPM) {
			BPMValue = Integer.parseInt(BPM.getText());
			timer.cancel();
			timer = new Timer();
			timer.schedule(new event(currentMode), 0, 60000 / BPMValue);
		}
		
		//Changes mode
		if(e.getSource() == mode) {
			if(mode.getText().equals("AllNotes")) {
				currentMode = AllNotes;
			}else if(mode.getText().equals("CurrentChords")) {
				currentMode = CurrentChords;
			}else if(mode.getText().equals("NaturalNotes")){
				currentMode = NaturalNotes;
			}else if(mode.getText().equals("AccidentalNotes")) {
				currentMode = AccidentalNotes;
			}else if(mode.getText().equals("MMChords")) {
				currentMode = MajorMinorChords;
			}else if(mode.getText().equals("MajorChords")) {
				currentMode = MajorChords;
			}else if(mode.getText().equals("MinorChords")) {
				currentMode = MinorChords;
			}else {
				currentMode = AllNotes;
			}
			
			timer.cancel();
			timer = new Timer();
			timer.schedule(new event(currentMode), 0, 60000 / BPMValue);
		}
		
	}
	
	//Returns a chord or note from an array
	public String getMusicalEntity(String[] array, int index) { 
		return array[index];
	}
	
	//Private TimerTask class that will be responsible for changing the note/chord periodically
	private class event extends TimerTask{
		
		private int len;
		private String[] arr;
		
		public event(String[] arrName) {
			arr = arrName;
			len = arrName.length;
		}
		
		@Override
		public void run() {
			Random rand = new Random();
			int index = rand.nextInt(len);
			
			while(index == currentIndex) { //Prevents repeated notes/chords
				index = rand.nextInt(len);
			}
			
			note.setText(getMusicalEntity(arr, index));
			
			Dimension noteSize = note.getPreferredSize();

			note.setBounds(screenWidth / 2 - noteSize.width / 2, screenHeight / 3 - noteSize.height / 2, noteSize.width, noteSize.height);
			//System.out.println(getMusicalEntity(arr, index));
			
			currentIndex = index;
		}
	}

}
