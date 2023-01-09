//Imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame; 
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
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
	
	private JPanel modeButtonPanel;
	private int modePanelWidth;
	private int modePanelHeight;
	
	private OptionButton NotesButton;
	private OptionButton ChordsButton;
	private OptionButton ProgressionsButton;
	
	//CurrentNotes | AllNotes | CurrentChords | NaturalNotes | AccidentalNotes | MMChords | MajorChords | MinorChords"

	private Timer timer;
	
	//Initial BPM
	private int BPMValue = 40;
	

	//This array will contain the current mode's notes or chords (will be changed based on the user's input)
	private String[] currentMode;
	
	//Tracks current index to avoid repeated notes
	private int currentIndex;
	
	private String knownChords;
	private String knownNotes;
	private String knownKeys;
	private String knownProgressions;
	
	//Strings which will be displayed on GUI
	private String[] DisplayedChords;
	private String[] DisplayedNotes;
	private String[] DisplayedProgressions; //This contains chord progressions and known key signatures
	
	private String[] KeySignatures;
	
	public Screen() throws IOException {
		
		//Screen size variables
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.width;
		screenHeight = screenSize.height;
		
		//Color instantiation 
		backgroundColor = new Color(15, 15, 18);
		noteColor = new Color(200, 200, 200);
		textColor = new Color(100, 100, 100);
		
		//Font instantiation
		noteFont = new Font("sans-serif", Font.PLAIN, 100);
		textFont = new Font("sans-serif", Font.PLAIN, 20);
		
		//Label instantiation
		note = new JLabel();
		
		//TextField instantiation
		BPM = new JTextField();
		
		modeButtonPanel = new JPanel();
		modePanelWidth = 1400;
		modePanelHeight = 50;
		modeButtonPanel.setBounds(screenWidth / 2 - modePanelWidth / 2, screenHeight * 2 / 3, modePanelWidth, modePanelHeight);
		modeButtonPanel.setLayout(new FlowLayout());
		modeButtonPanel.setBackground(backgroundColor);
		
		NotesButton = new OptionButton("Notes");
		NotesButton.addActionListener(this);
		ChordsButton = new OptionButton("Chords");
		ChordsButton.addActionListener(this);
		ProgressionsButton= new OptionButton("Progressions");
		ProgressionsButton.addActionListener(this);
		
		modeButtonPanel.add(NotesButton);
		modeButtonPanel.add(ChordsButton);
		modeButtonPanel.add(ProgressionsButton);
	
		//Default mode
		ChordsButton.setBoldFont();
		
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
		
		BPM.setBounds(screenWidth / 2 - 50 / 2, screenHeight / 2 + 50, 50, 50);
		BPM.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		BPM.setBackground(backgroundColor);
		BPM.setForeground(textColor);
		BPM.setText("BPM");
		BPM.setFont(textFont);
		BPM.setHorizontalAlignment(JTextField.CENTER);
		BPM.addActionListener(this);
		
		//modes.setFont(textFont);
		//modes.setBackground(backgroundColor);
		//modes.setForeground(textColor);
		
		//Dimension modeDimension = modes.getPreferredSize();
		
		//modes.setBounds(screenWidth / 2 - modeDimension.width / 2, screenHeight * 2 / 3, modeDimension.width + 10, modeDimension.height);

		this.add(note);
		this.add(BPM);
		//this.add(modes);
		
		this.add(modeButtonPanel);
		
		//Strings of knonw data - read in from txt files
		knownChords = getKnownData("C:\\Users\\stewa\\Desktop\\Guitar Trainer\\KnownChords.txt");
		knownNotes = getKnownData("C:\\Users\\stewa\\Desktop\\Guitar Trainer\\KnownNotes.txt");
		knownKeys = getKnownData("C:\\Users\\stewa\\Desktop\\Guitar Trainer\\KnownKeys.txt");
		knownProgressions = getKnownData("C:\\Users\\stewa\\Desktop\\Guitar Trainer\\ChordProgressions.txt");
		
		int numChords = 0;
		int numNotes = 0;
		int numKeys = 0;
		int numProgressions = 0;
		
		for(int i = 0; i < knownChords.length(); i ++) {
			if(knownChords.charAt(i) == ',') {
				numChords ++;
			}
			
		}
		
		for(int i = 0; i < knownNotes.length(); i ++) {
			if(knownNotes.charAt(i) == ',') {
				numNotes ++;
			}
			
		}
		
		for(int i = 0; i < knownKeys.length(); i ++) {
			if(knownKeys.charAt(i) == ',') {
				numKeys ++;
			}
			
		}
		
		for(int i = 0; i < knownProgressions.length(); i ++) {
			if(knownProgressions.charAt(i) == ',') {
				numProgressions ++;
			}
			
		}
		
		DisplayedChords = new String[numChords];
		
		int startIndex = 0;
		int endIndex = 1;
		
		for(int i = 0; i < numChords; i ++) {
			boolean found = false;
			while(!found) {
				if(knownChords.charAt(endIndex) == ',') {
					DisplayedChords[i] = new String(knownChords.substring(startIndex, endIndex));
					startIndex = endIndex + 1; // Moves start index to next note beginning (skips over comma)
					endIndex += 2; // Move end index to character immediately proceeding start index 
					found = true;
				}else {
					endIndex ++;
				}
			}
		}
		
	
		DisplayedNotes = new String[numNotes * 6];
		
		startIndex = 0;
		endIndex = 1;
		
		for(int i = 0; i < numNotes; i ++) {
			boolean found = false;
			while(!found) {
				if(knownNotes.charAt(endIndex) == ',') {
					for(int j = 0; j < 6; j ++) {
						DisplayedNotes[i * 6 + j] = new String(knownNotes.substring(startIndex, endIndex) + (j + 1));
					}
					
					startIndex = endIndex + 1; // Moves start index to next note beginning (skips over comma)
					endIndex += 2; // Move end index to character immediately proceeding start index 
					found = true;
				}else {
					endIndex ++;
				}
			}
			
		}
		
		KeySignatures = new String[numKeys];
		
		startIndex = 0;
		endIndex = 1;
		
		for(int i = 0; i < numKeys; i ++) {
			boolean found = false;
			while(!found) {
				if(knownKeys.charAt(endIndex) == ',') {
					KeySignatures[i] = new String(knownKeys.substring(startIndex, endIndex));
					startIndex = endIndex + 1; // Moves start index to next note beginning (skips over comma)
					endIndex += 2; // Move end index to character immediately proceeding start index 
					found = true;
				}else {
					endIndex ++;
				}
			}
		}
		
		
		DisplayedProgressions = new String[numProgressions * numKeys];
		
		startIndex = 0; // for progressions
		endIndex = 1;
		
		for(int i = 0; i < numProgressions; i ++) {
			boolean found = false;
			while(!found) {
				if(knownProgressions.charAt(endIndex) == ',') {
					for(int j = 0; j < numKeys; j ++) {
						DisplayedProgressions[i * numKeys + j] = new String(KeySignatures[j] + ": " + knownProgressions.substring(startIndex, endIndex));
					}
					
					startIndex = endIndex + 1; // Moves start index to next note beginning (skips over comma)
					endIndex += 2; // Move end index to character immediately proceeding start index 
					found = true;
				}else {
					endIndex ++;
				}
			}
					
		}
		
		
		currentMode = DisplayedChords;
		
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
		
		if(e.getSource() == NotesButton) {
			
			currentMode = DisplayedNotes;
			NotesButton.setBoldFont();
			ChordsButton.setNeutralFont();
			ProgressionsButton.setNeutralFont();
			
		}
		
		if(e.getSource() == ChordsButton) {
				
			currentMode = DisplayedChords;
			ChordsButton.setBoldFont();	
			NotesButton.setNeutralFont();
			ProgressionsButton.setNeutralFont();
		}
		
		if(e.getSource() == ProgressionsButton) {
			
			currentMode = DisplayedProgressions;
			ProgressionsButton.setBoldFont();	
			NotesButton.setNeutralFont();
			ChordsButton.setNeutralFont();
		}
		
		//Mode Changer interface
			
			timer.cancel();
			timer = new Timer();
			timer.schedule(new event(currentMode), 0, 60000 / BPMValue);
		
	}
		
	
	
	//Returns a chord or note from an array
	public String getMusicalEntity(String[] array, int index) { 
		return array[index];
	}
	
	//Reads the text files containing the notes/chords and returns the raw string data of the known notes or chords
	public String getKnownData(String path) throws IOException {
		String data = new String(Files.readAllBytes(Paths.get(path)));
	    return data;
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
