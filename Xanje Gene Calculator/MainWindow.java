import java.awt.Color; // Imports
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;


/* This is the class that controls the main window of the program
*It extends the JFrame class because the window in it of itself is a JFrame
*Implements an Action Listener to detect user interaction
*/
public class MainWindow extends JFrame implements ActionListener{

	// Creates a 'Breeder' object for the breeding logic
	private Breeder breed;
	
	//Window dimension
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int windowWidth = screenSize.width / 2;
	private int windowHeight = screenSize.height / 2;
	
	// Creates an object that manages the color scheme
	private ColorScheme colors;
	
	// Initializes the various colors that are used in the program
	private Color backgroundColor;
	private Color textColor;
	private Color buttonColor;
	private Color entryTextColor;
	private Color underlineColor;
	private Color menuBarColor;
	private Color errorCodeColor;
	private Color offSpringStringColor;
	
	// Establishes the physical heights of each user input box within the window
	public final int lineArtHeight = windowHeight / 5;
	public final int markingsHeight = windowHeight * 7 / 25;
	public final int colorMutationHeight = windowHeight * 9  / 25;
	public final int physicalMutationHeight = windowHeight * 11 / 25;
	public final int fertilityHeight = windowHeight * 13 / 25;
	
	// Dimensions for each user input box (text field)
	public final int entryBoxHeight = 15;
	public final int entryBoxWidth = 270;
	
	// Creates 'UserEntry' objects for each required text field. Each text field
	// Will take in a string of genes which will later be used in the calculations for the
	// Genetic code of offspring
	private UserEntry motherLineArt;
	private UserEntry motherMarkings;
	private UserEntry motherColorMutation;
	private UserEntry motherPhysicalMutation;
	private UserEntry fertility;
	
	private UserEntry fatherLineArt;
	private UserEntry fatherMarkings;
	private UserEntry fatherColorMutation;
	private UserEntry fatherPhysicalMutation;
	
	// Creates custom Label objects
	private Label title;
	private Label mother; 
	private Label father;
	
	private Label lineArt;
	private Label markings; 
	private Label colorMutation;
	private Label physicalMutation;
	private Label fertilityLabel; 
	
	private Label offSpringLabel;
	
	// Declares custom underline objects
	private UnderLine motherLineArtUnderline;
	private UnderLine motherMarkingsUnderline;
	private UnderLine motherColorMutationUnderline;
	private UnderLine motherPhysicalMutationUnderline;
	private UnderLine fertilityUnderLine;
	
	private UnderLine fatherLineArtUnderline;
	private UnderLine fatherMarkingsUnderline;
	private UnderLine fatherColorMutationUnderline;
	private UnderLine fatherPhysicalMutationUnderline;
	
	// Declares 'calculate' button (JButton object)
	private JButton calculate;
	
	// Creates the menuBar (Solely used for color selection)
	private JMenuBar menuBar;
	
	// colorChanger JMenu object which is added to the menu bar
	private JMenu colorChanger;
	
	// All the menu items within the JMenu object 'colorChanger'
	private JMenuItem backgroundColorSelector;
	private JMenuItem textColorSelector;
	private JMenuItem entryTextColorSelector;
	private JMenuItem underlineColorSelector;
	private JMenuItem errorCodeColorSelector;
	private JMenuItem buttonColorSelector;
	private JMenuItem menuBarColorSelector;
	private JMenuItem offSpringStringColorSelector;
	private JMenuItem save;
	private JMenuItem resetColorScheme;
	
	// Strings containing the offspring's genetic code
	private String offSpringLineArt;
	private String offSpringMarkings;
	private String offSpringColorMutation;
	private String offSpringPhysicalMutation;
	
	// Final genetic code of the offspring which will be returned to the user
	private String offSpringFinalString;
	
	// Hash map that keeps track of each attributes status and whether or not it has been input by the user
	private HashMap<String, Boolean> entriesEntered; 

	//MainWindow constructor which throws the ClassNotFoundException and IOException (for serialization of color scheme)
	MainWindow() throws ClassNotFoundException, IOException{
	
		// Instantiates a Breeder object which controls the logic of the genetic calculations
		breed = new Breeder();
		
		// Creates a the HashMap which keeps track of each attributes input status
		entriesEntered = new HashMap<String, Boolean>();
		
		// Fills entriesEntered HashMap
		fillEntriesEntered();
		
		// Imports serialized color scheme
		colors = null;
		
		FileInputStream fileIn = new FileInputStream("ColorScheme.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		colors = (ColorScheme) in.readObject();
		in.close();
		fileIn.close();
		
		// Sets each color based on the Serialized/imported ColorScheme class
		backgroundColor = colors.getBackgroundColor();
		textColor = colors.getTextColor();
		buttonColor = colors.getButtonColor();
		entryTextColor = colors.getEntryColor();
		underlineColor = colors.getUnderLineColor();
		menuBarColor = colors.getMenuBarColor();
		errorCodeColor = colors.getErrorColor();
		offSpringStringColor = colors.getOffSpringStringColor();
		
		// Sets basic parameters of the window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(windowWidth, windowHeight);
		this.getContentPane().setBackground(backgroundColor);
		this.setTitle("Xanje Breeder");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		// Creates each text label for the window
	 	title = new Label("Gene Calculator", windowWidth / 2, windowHeight / 100, new Font("MV Boli", Font.BOLD, 18));
		mother = new Label("Mother's Characteristics", windowWidth / 5, windowHeight / 12, new Font("Dialog", Font.ITALIC, 14));
		father = new Label("Father's Characteristics", (windowWidth * 4) / 5, windowHeight / 12, new Font("Dialog", Font.ITALIC, 14));
		
		lineArt = new Label("Line Art", windowWidth / 2, lineArtHeight, new Font("Dialog", Font.ITALIC, 14));
		markings = new Label("Markings", windowWidth / 2, markingsHeight, new Font("Dialog", Font.ITALIC, 14));
		colorMutation = new Label("Color Mutation", windowWidth / 2, colorMutationHeight, new Font("Dialog", Font.ITALIC, 14));
		physicalMutation = new Label("Physical Mutation", windowWidth / 2, physicalMutationHeight, new Font("Dialog", Font.ITALIC, 14));
		fertilityLabel = new Label("Fertility", windowWidth / 2, fertilityHeight, new Font("Dialog", Font.ITALIC, 14));
		
		offSpringLabel = new Label(offSpringFinalString, windowWidth / 2, windowHeight * 7 / 8, new Font("Dialog", Font.ITALIC, 14));
		
		// Creates/modifies the 'calculate' button which initializes the genetic calculation process
		calculate = new JButton("Calculate!");
		calculate.setBounds(windowWidth / 2 - 110 * calculate.getPreferredSize().width / 200, windowHeight * 35 / 50, 100, 30);
		calculate.setFont(new Font("MV Boli", Font.BOLD, 14));
		calculate.setFocusable(false);
		calculate.setBackground(buttonColor);
		calculate.setFocusPainted(false);
		calculate.setForeground(textColor);
		calculate.addActionListener(this);
		UIManager.put( "Button.select", textColor);
		
		// Instantiates each text field object for the window
		motherLineArt = new UserEntry(windowWidth / 5, lineArtHeight + entryBoxHeight + 10, entryBoxWidth, entryBoxHeight);
		motherLineArt.getField().addActionListener(this);
		motherMarkings = new UserEntry(windowWidth / 5, markingsHeight + entryBoxHeight + 10, entryBoxWidth, entryBoxHeight);
		motherMarkings.getField().addActionListener(this);
		motherColorMutation = new UserEntry(windowWidth / 5, colorMutationHeight + entryBoxHeight + 10, entryBoxWidth, entryBoxHeight);
		motherColorMutation.getField().addActionListener(this);
		motherPhysicalMutation = new UserEntry(windowWidth / 5, physicalMutationHeight + entryBoxHeight + 10, entryBoxWidth, entryBoxHeight);
		motherPhysicalMutation.getField().addActionListener(this);
		fertility = new UserEntry(windowWidth / 5, fertilityHeight + entryBoxHeight + 10, entryBoxWidth, entryBoxHeight);
		fertility.getField().addActionListener(this);
		
		fatherLineArt = new UserEntry(windowWidth * 4 / 5, lineArtHeight + entryBoxHeight + 10, entryBoxWidth, entryBoxHeight);
		fatherLineArt.getField().addActionListener(this);
		fatherMarkings = new UserEntry(windowWidth * 4 / 5, markingsHeight + entryBoxHeight + 10, entryBoxWidth, entryBoxHeight);
		fatherMarkings.getField().addActionListener(this);
		fatherColorMutation = new UserEntry(windowWidth * 4 / 5, colorMutationHeight + entryBoxHeight + 10, entryBoxWidth, entryBoxHeight);
		fatherColorMutation.getField().addActionListener(this);
		fatherPhysicalMutation = new UserEntry(windowWidth * 4 / 5, physicalMutationHeight + entryBoxHeight + 10, entryBoxWidth, entryBoxHeight);
		fatherPhysicalMutation.getField().addActionListener(this);
		
		// Instantiates each custom underline object (these underlines show the user where the text fields are)
		motherLineArtUnderline = new UnderLine(windowWidth / 5, lineArtHeight + entryBoxHeight * 2 + 10, 100, 10);
		motherMarkingsUnderline = new UnderLine(windowWidth / 5, markingsHeight + entryBoxHeight * 2 + 10, 100, 5);
		motherColorMutationUnderline = new UnderLine(windowWidth / 5, colorMutationHeight + entryBoxHeight * 2 + 10, 100, 5);
		motherPhysicalMutationUnderline = new UnderLine(windowWidth / 5, physicalMutationHeight + entryBoxHeight * 2 + 10, 100, 5);
		fertilityUnderLine = new UnderLine(windowWidth / 5, fertilityHeight + entryBoxHeight * 2 + 10, 100, 5); 
		
		fatherLineArtUnderline = new UnderLine(windowWidth * 4 / 5, lineArtHeight + entryBoxHeight * 2 + 10, 100, 10);
		fatherMarkingsUnderline = new UnderLine(windowWidth * 4 / 5, markingsHeight + entryBoxHeight * 2 + 10, 100, 5);
		fatherColorMutationUnderline = new UnderLine(windowWidth * 4 / 5, colorMutationHeight + entryBoxHeight * 2 + 10, 100, 5);
		fatherPhysicalMutationUnderline = new UnderLine(windowWidth * 4 / 5, physicalMutationHeight + entryBoxHeight * 2 + 10, 100, 5);
		
		// Instantiates the menu bar and adjusts its background color and removes its border
		menuBar = new JMenuBar();
		menuBar.setBackground(menuBarColor);
		menuBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		// JMenu item 'colorChanger' to-be-added to the menu bar
		colorChanger = new JMenu("Change The Color Scheme!!!");
		colorChanger.setForeground(textColor);
		
		// Each option to-be-added to the 'colorChanger' menu
		backgroundColorSelector = new JMenuItem("Background Color");
		backgroundColorSelector.addActionListener(this);
		textColorSelector = new JMenuItem("Text Color");
		textColorSelector.addActionListener(this);
		entryTextColorSelector = new JMenuItem("Entry Text Color");
		entryTextColorSelector.addActionListener(this);
		underlineColorSelector = new JMenuItem("Underline Color");
		underlineColorSelector.addActionListener(this);
		errorCodeColorSelector = new JMenuItem("Error Code Color");
		errorCodeColorSelector.addActionListener(this);
		buttonColorSelector = new JMenuItem("Button Color");
		buttonColorSelector.addActionListener(this);
		menuBarColorSelector = new JMenuItem("Menu Bar Color");
		menuBarColorSelector.addActionListener(this);
		offSpringStringColorSelector = new JMenuItem("OffSpring Color");
		offSpringStringColorSelector.addActionListener(this);
		save = new JMenuItem("Save");
		save.addActionListener(this);
		//resetColorScheme = new JMenuItem("Reset dem colors babe ;)");
		resetColorScheme = new JMenuItem("Reset Colors");
		resetColorScheme.addActionListener(this);
		
		// Adds each menu item to the menu 'colorChanger'
		colorChanger.add(backgroundColorSelector);
		colorChanger.add(textColorSelector);
		colorChanger.add(entryTextColorSelector);
		colorChanger.add(underlineColorSelector);
		colorChanger.add(buttonColorSelector);
		colorChanger.add(menuBarColorSelector);
		colorChanger.add(errorCodeColorSelector);
		colorChanger.add(offSpringStringColorSelector);
		colorChanger.add(save);
		colorChanger.add(resetColorScheme);
		
		// Adds the 'colorChanger' menu to the menu bar
		menuBar.add(colorChanger);
		
		// Adds every label, underline, text field, and button to the window (JFrame)
		this.add(motherLineArtUnderline);
		this.add(motherMarkingsUnderline);
	 	this.add(motherColorMutationUnderline);
	 	this.add(motherPhysicalMutationUnderline);
	 	this.add(fertilityUnderLine);
	 	this.add(fatherLineArtUnderline);
		this.add(fatherMarkingsUnderline);
	 	this.add(fatherColorMutationUnderline);
	 	this.add(fatherPhysicalMutationUnderline);
	 	
		this.add(title);
		this.add(father);
		this.add(mother);
		this.add(lineArt);
		this.add(markings);
		this.add(colorMutation);
		this.add(physicalMutation);
		this.add(fertilityLabel);
		this.add(offSpringLabel);
		
		this.add(motherLineArt.getField());
		this.add(motherMarkings.getField());
		this.add(motherColorMutation.getField());
		this.add(motherPhysicalMutation.getField());
		this.add(fatherLineArt.getField());
		this.add(fatherMarkings.getField());
		this.add(fatherColorMutation.getField());
		this.add(fatherPhysicalMutation.getField());
		this.add(fertility.getField());
		
		this.add(calculate);
		
		// Adds the menu bar
		this.setJMenuBar(menuBar);
		
		// Makes the window visible
		this.setVisible(true);
		
	}
	
	
	// Detects when each component (text field, menu bar, or 'calculate' button) is interacted with
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Detects whenever any text field is interacted with. Ensures that each entry has the correct number of genes
		// (using the numGenes method) and is in the correct format for the genetic interpreter.
		
		// If the source is the motherLineArt text field, the program ensures that the number of genes entered is one (since only one 
		// gene can be entered for this genetic characteristic)
		if(e.getSource() == motherLineArt.getField() && numGenes(motherLineArt.getField().getText()) == 1) {
			
			// Removes any text the output gene string may have had previously
			offSpringLabel.setText("");
			
			// Resets color of output gene string label 
			offSpringLabel.setForeground(offSpringStringColor);
			
			// Tries to set the mother line art as the text field's input within the 'Breeder' object 'breed'
			// Throws errors if the input string is incorrectly formatted
			try {
				breed.setMotherLineArt(motherLineArt.getField().getText());
				
				// If no error is thrown, sets the input status for this characteristic in the HashMap 'entriesEntered'
				entriesEntered.replace("MotherLineArtEntered", true);
				
			}catch(IllegalArgumentException exception) {
				
				// Informs the user of the error, should an exception be thrown
				offSpringLabel.setText(exception.getMessage());
				offSpringLabel.setForeground(errorCodeColor);
				offSpringLabel.setBounds(windowWidth / 2 - offSpringLabel.getPreferredSize().width / 2, windowHeight * 13 / 16, 
						offSpringLabel.getPreferredSize().width, offSpringLabel.getPreferredSize().height);
			}
			
		}
		
		if(e.getSource() == fatherLineArt.getField() && numGenes(fatherLineArt.getField().getText()) == 1) {
		
			offSpringLabel.setText("");
			offSpringLabel.setForeground(offSpringStringColor);
			
			try {
				breed.setFatherLineArt(fatherLineArt.getField().getText());
				entriesEntered.replace("FatherLineArtEntered", true);
				
			}catch(IllegalArgumentException exception) {
				offSpringLabel.setText(exception.getMessage());
				offSpringLabel.setForeground(errorCodeColor);
				offSpringLabel.setBounds(windowWidth / 2 - offSpringLabel.getPreferredSize().width / 2, windowHeight * 13 / 16, 
						offSpringLabel.getPreferredSize().width, offSpringLabel.getPreferredSize().height);
			}
			
		}
		
		if(e.getSource() == motherMarkings.getField() && numGenes(motherMarkings.getField().getText()) == 1) {
			
			offSpringLabel.setText("");
			offSpringLabel.setForeground(offSpringStringColor);
			try {
				breed.setMotherMarkings(motherMarkings.getField().getText());
				entriesEntered.replace("MotherMarkingsEntered", true);
				
			}catch(IllegalArgumentException exception) {
				offSpringLabel.setText(exception.getMessage());
				offSpringLabel.setForeground(errorCodeColor);
				offSpringLabel.setBounds(windowWidth / 2 - offSpringLabel.getPreferredSize().width / 2, windowHeight * 13 / 16, 
						offSpringLabel.getPreferredSize().width, offSpringLabel.getPreferredSize().height);
			}
			
		}
		if(e.getSource() == fatherMarkings.getField() && numGenes(fatherMarkings.getField().getText()) == 1) {
			
			offSpringLabel.setText("");
			offSpringLabel.setForeground(offSpringStringColor);
			try {
				breed.setFatherMarkings(fatherMarkings.getField().getText());
				entriesEntered.replace("FatherMarkingsEntered", true);
				
			}catch(IllegalArgumentException exception) {
				offSpringLabel.setText(exception.getMessage());
				offSpringLabel.setForeground(errorCodeColor);
				offSpringLabel.setBounds(windowWidth / 2 - offSpringLabel.getPreferredSize().width / 2, windowHeight * 13 / 16, 
						offSpringLabel.getPreferredSize().width, offSpringLabel.getPreferredSize().height);
			}
			
		}
		if(e.getSource() == motherColorMutation.getField() && numGenes(motherColorMutation.getField().getText()) >= 1
				&& numGenes(motherColorMutation.getField().getText()) <= 5) {
				
			offSpringLabel.setText("");
			offSpringLabel.setForeground(offSpringStringColor);
			try {
				breed.setMotherColorMutation(motherColorMutation.getField().getText());
				entriesEntered.replace("MotherColorMutationEntered", true);
				
			}catch(IllegalArgumentException exception) {
				offSpringLabel.setText(exception.getMessage());
				offSpringLabel.setForeground(errorCodeColor);
				offSpringLabel.setBounds(windowWidth / 2 - offSpringLabel.getPreferredSize().width / 2, windowHeight * 13 / 16, 
						offSpringLabel.getPreferredSize().width, offSpringLabel.getPreferredSize().height);
				System.out.println("error");
			}
			
		}
		if(e.getSource() == fatherColorMutation.getField() && numGenes(fatherColorMutation.getField().getText()) >= 1
				&& numGenes(fatherColorMutation.getField().getText()) <= 5) {
			
			offSpringLabel.setText("");
			offSpringLabel.setForeground(offSpringStringColor);
			try {
				breed.setMotherColorMutation(fatherColorMutation.getField().getText());
				entriesEntered.replace("FatherColorMutationEntered", true);
				
			}catch(IllegalArgumentException exception) {
				offSpringLabel.setText(exception.getMessage());
				offSpringLabel.setForeground(errorCodeColor);
				offSpringLabel.setBounds(windowWidth / 2 - offSpringLabel.getPreferredSize().width / 2, windowHeight * 13 / 16, 
						offSpringLabel.getPreferredSize().width, offSpringLabel.getPreferredSize().height);
			}
			
		}
		
		if(e.getSource() == motherPhysicalMutation.getField() && numGenes(motherPhysicalMutation.getField().getText()) >= 1
				&& numGenes(motherPhysicalMutation.getField().getText()) <= 15) {
			
			offSpringLabel.setText("");
			offSpringLabel.setForeground(offSpringStringColor);
			try {
				breed.setMotherPhysicalMutation(motherPhysicalMutation.getField().getText());
				entriesEntered.replace("MotherPhysicalMutationEntered", true);
				
			}catch(IllegalArgumentException exception) {
				offSpringLabel.setText(exception.getMessage());
				offSpringLabel.setForeground(errorCodeColor);
				offSpringLabel.setBounds(windowWidth / 2 - offSpringLabel.getPreferredSize().width / 2, windowHeight * 13 / 16, 
						offSpringLabel.getPreferredSize().width, offSpringLabel.getPreferredSize().height);
			}
		
			
		}
		if(e.getSource() == fatherPhysicalMutation.getField() && numGenes(fatherPhysicalMutation.getField().getText()) >= 1
				&& numGenes(fatherPhysicalMutation.getField().getText()) <= 15) {
			
			offSpringLabel.setText("");
			offSpringLabel.setForeground(offSpringStringColor);
			try {
				breed.setMotherPhysicalMutation(fatherPhysicalMutation.getField().getText());
				entriesEntered.replace("FatherPhysicalMutationEntered", true);
				
			}catch(IllegalArgumentException exception) {
				offSpringLabel.setText(exception.getMessage());
				offSpringLabel.setForeground(errorCodeColor);
				offSpringLabel.setBounds(windowWidth / 2 - offSpringLabel.getPreferredSize().width / 2, windowHeight * 13 / 16, 
						offSpringLabel.getPreferredSize().width, offSpringLabel.getPreferredSize().height);
			}
			
		}
		
		if(e.getSource() == fertility.getField() && fertility.getField().getText().length() >= 2 && 
				fertility.getField().getText().length() <= 3) {
			
			offSpringLabel.setText("");
			offSpringLabel.setForeground(offSpringStringColor);
			
			try {
				breed.setFertilityMother(fertility.getField().getText());
				entriesEntered.replace("Fertility", true);
				
			}catch(IllegalArgumentException exception) {
				offSpringLabel.setText(exception.getMessage());
				offSpringLabel.setForeground(errorCodeColor);
				offSpringLabel.setBounds(windowWidth / 2 - offSpringLabel.getPreferredSize().width / 2, windowHeight * 13 / 16, 
						offSpringLabel.getPreferredSize().width, offSpringLabel.getPreferredSize().height);
			}
			
		}
		
		// If the 'calculate' button is interacted with
		if(e.getSource() == calculate) {
			
			// Creates the initial output string using the calculateGender method (returns a random gender)
			offSpringFinalString = breed.calculateGender() + " ";
			
			// Determines which characteristics were entered into their respective text fields, and performs the proper calculations 
			// for the output genetic string
			if(canCalculate(entriesEntered.get("FatherLineArtEntered"), entriesEntered.get("MotherLineArtEntered"))) {
				offSpringLineArt = breed.calculatePSquare(breed.getFatherLineArt(), breed.getMotherLineArt());
				offSpringFinalString = offSpringFinalString.concat(offSpringLineArt + " ");
			}
			
			if(canCalculate(entriesEntered.get("FatherMarkingsEntered"), entriesEntered.get("MotherMarkingsEntered"))) {
				offSpringMarkings = breed.calculatePSquare(breed.getFatherMarkings(), breed.getMotherMarkings());
				offSpringFinalString = offSpringFinalString.concat(offSpringMarkings + " ");
			}
			
			// Calculates the color mutation for the offspring
			if(canCalculate(entriesEntered.get("FatherColorMutationEntered"), entriesEntered.get("MotherColorMutationEntered"))) {
				
				breed.setMotherColorMutation(motherColorMutation.getField().getText());
				breed.setFatherColorMutation(fatherColorMutation.getField().getText());
				
				offSpringColorMutation = breed.calculateRandomColorMutation();
				offSpringFinalString = offSpringFinalString.concat(offSpringColorMutation + " ");
			
			}
			
			
			// Calculates the physical mutation for the offspring
			if(canCalculate(entriesEntered.get("FatherPhysicalMutationEntered"), entriesEntered.get("MotherPhysicalMutationEntered"))) {
				
				breed.setMotherPhysicalMutation(motherPhysicalMutation.getField().getText());
				breed.setFatherPhysicalMutation(fatherPhysicalMutation.getField().getText());
		
				offSpringPhysicalMutation = breed.calculateRandomPhysicalMutation();
				offSpringFinalString = offSpringFinalString.concat(offSpringPhysicalMutation + " ");
				
			}
			
			// If the offspring is a female, add a 'fertility' characteristic to the end of the gene string
			offSpringLabel.setText(offSpringFinalString);
			if(!offSpringLabel.getText().substring(0, 4).equals("male")) {
				offSpringLabel.setText(offSpringFinalString.concat(breed.calculateFertility()));
			}
			
			// Sets size of the output offspring label to the preferred size (size the text takes up)
			offSpringLabel.setBounds(windowWidth / 2 - offSpringLabel.getPreferredSize().width / 2, windowHeight * 13 / 16, 
					offSpringLabel.getPreferredSize().width, offSpringLabel.getPreferredSize().height);
			
		}
		
		// The following if statements detect if any aspect of the color changer menu is interacted with
		
		if(e.getSource() == textColorSelector) {
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = JColorChooser.showDialog(null, "Pick a Text Color!", textColor);
			colors.setTextColor(color);
			
			textColor = colors.getTextColor();
			
			title.setForeground(textColor);
			mother.setForeground(textColor);
			father.setForeground(textColor);
			
			lineArt.setForeground(textColor);
			markings.setForeground(textColor);
			colorMutation.setForeground(textColor);
			physicalMutation.setForeground(textColor);
			fertilityLabel.setForeground(textColor);
			
			offSpringLabel.setForeground(textColor);
			
			colorChanger.setForeground(textColor);
			
			calculate.setForeground(textColor);
			
			UIManager.put( "Button.select", textColor);
		
		}
		
		if(e.getSource() == backgroundColorSelector) {
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = JColorChooser.showDialog(null, "Pick a Background Color!", backgroundColor);
			colors.setBackgroundColor(color);
			
			backgroundColor = colors.getBackgroundColor();
			this.getContentPane().setBackground(backgroundColor);
			
			fatherLineArt.setBackgroundColor(backgroundColor);
			fatherMarkings.setBackgroundColor(backgroundColor);
			fatherColorMutation.setBackgroundColor(backgroundColor);
			fatherPhysicalMutation.setBackgroundColor(backgroundColor);
			motherLineArt.setBackgroundColor(backgroundColor);
			motherMarkings.setBackgroundColor(backgroundColor);
			motherColorMutation.setBackgroundColor(backgroundColor);
			motherPhysicalMutation.setBackgroundColor(backgroundColor);
			fertility.setBackgroundColor(backgroundColor);
		}
		
		if(e.getSource() == entryTextColorSelector) {
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = JColorChooser.showDialog(null, "Pick an Entry Text Color!", entryTextColor);
			colors.setEntryColor(color);
			
			entryTextColor = colors.getEntryColor();
			
			fatherLineArt.setForegroundColor(entryTextColor);
			fatherMarkings.setForegroundColor(entryTextColor);
			fatherColorMutation.setForegroundColor(entryTextColor);
			fatherPhysicalMutation.setForegroundColor(entryTextColor);
			motherLineArt.setForegroundColor(entryTextColor);
			motherMarkings.setForegroundColor(entryTextColor);
			motherColorMutation.setForegroundColor(entryTextColor);
			motherPhysicalMutation.setForegroundColor(entryTextColor);
			fertility.setForegroundColor(entryTextColor);
			
		}
		
		if(e.getSource() == underlineColorSelector) {
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = JColorChooser.showDialog(null, "Pick an Underline Color!", underlineColor);
			colors.setUnderLineColor(color);
			
			underlineColor = colors.getUnderLineColor();
			
			motherLineArtUnderline.setLineColor(underlineColor);
			motherMarkingsUnderline.setLineColor(underlineColor);
			motherColorMutationUnderline.setLineColor(underlineColor);
			motherPhysicalMutationUnderline.setLineColor(underlineColor);
			fertilityUnderLine.setLineColor(underlineColor);
			fatherLineArtUnderline.setLineColor(underlineColor);
			fatherMarkingsUnderline.setLineColor(underlineColor);
			fatherColorMutationUnderline.setLineColor(underlineColor);
			fatherPhysicalMutationUnderline.setLineColor(underlineColor);
			repaint();
		}
		
		if(e.getSource() == buttonColorSelector) {
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = JColorChooser.showDialog(null, "Pick a Button Color!", textColor);
			colors.setButtonColor(color);
			
			buttonColor = colors.getButtonColor();
			
			calculate.setBackground(buttonColor);
			
		}
		
		if(e.getSource() == menuBarColorSelector) {
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = JColorChooser.showDialog(null, "Pick a Menu Bar Color!", textColor);
			colors.setMenuBarColor(color);
			
			menuBarColor = colors.getMenuBarColor();
			
			menuBar.setBackground(menuBarColor);
			
		}
		
		if(e.getSource() == errorCodeColorSelector) {
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = JColorChooser.showDialog(null, "Pick an Error Code Color!", textColor);
			colors.setErrorColor(color);
			
			errorCodeColor = colors.getErrorColor();
			
		}
		
		if(e.getSource() == offSpringStringColorSelector) {
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = JColorChooser.showDialog(null, "Pick an Offspring Gene Color!", offSpringStringColor);
			colors.setOffSpringStringColor(color);
			
			offSpringStringColor = colors.getOffSpringStringColor();
			
			offSpringLabel.setForeground(offSpringStringColor);
			
		}
		
		// Serializes the currently selected color scheme
		if(e.getSource() == save) {
			try {
				colors.serialize();
			} catch (IOException exception) {
				System.out.println("Error while saving");
			}
		}
		
		// resets the color scheme to default
		if(e.getSource() == resetColorScheme) {
			colors.reset();
			
			backgroundColor = colors.getBackgroundColor();
			textColor = colors.getTextColor();
			underlineColor = colors.getUnderLineColor();
			buttonColor = colors.getButtonColor();
			errorCodeColor = colors.getErrorColor();
			entryTextColor = colors.getEntryColor();
			menuBarColor = colors.getMenuBarColor();
			offSpringStringColor = colors.getOffSpringStringColor();
			
			title.setForeground(textColor);
			mother.setForeground(textColor);
			father.setForeground(textColor);
			
			lineArt.setForeground(textColor);
			markings.setForeground(textColor);
			colorMutation.setForeground(textColor);
			physicalMutation.setForeground(textColor);
			fertilityLabel.setForeground(textColor);
			
			offSpringLabel.setForeground(textColor);
			
			colorChanger.setForeground(textColor);
			
			calculate.setForeground(textColor);
			
			UIManager.put( "Button.select", textColor);
			
			this.getContentPane().setBackground(backgroundColor);
			
			fatherLineArt.setBackgroundColor(backgroundColor);
			fatherMarkings.setBackgroundColor(backgroundColor);
			fatherColorMutation.setBackgroundColor(backgroundColor);
			fatherPhysicalMutation.setBackgroundColor(backgroundColor);
			motherLineArt.setBackgroundColor(backgroundColor);
			motherMarkings.setBackgroundColor(backgroundColor);
			motherColorMutation.setBackgroundColor(backgroundColor);
			motherPhysicalMutation.setBackgroundColor(backgroundColor);
			fertility.setBackgroundColor(backgroundColor);
			
			fatherLineArt.setForegroundColor(entryTextColor);
			fatherMarkings.setForegroundColor(entryTextColor);
			fatherColorMutation.setForegroundColor(entryTextColor);
			fatherPhysicalMutation.setForegroundColor(entryTextColor);
			motherLineArt.setForegroundColor(entryTextColor);
			motherMarkings.setForegroundColor(entryTextColor);
			motherColorMutation.setForegroundColor(entryTextColor);
			motherPhysicalMutation.setForegroundColor(entryTextColor);
			fertility.setForegroundColor(entryTextColor);

			motherLineArtUnderline.setLineColor(underlineColor);
			motherMarkingsUnderline.setLineColor(underlineColor);
			motherColorMutationUnderline.setLineColor(underlineColor);
			motherPhysicalMutationUnderline.setLineColor(underlineColor);
			fertilityUnderLine.setLineColor(underlineColor);
			fatherLineArtUnderline.setLineColor(underlineColor);
			fatherMarkingsUnderline.setLineColor(underlineColor);
			fatherColorMutationUnderline.setLineColor(underlineColor);
			fatherPhysicalMutationUnderline.setLineColor(underlineColor);
			repaint();
			
			calculate.setBackground(buttonColor);
			
			menuBar.setBackground(menuBarColor);
			
			errorCodeColor = colors.getErrorColor();
		}
	}
	
	// Calculates the number of genes contained within an input string (in the format NN//Nm//Np etc. 
	public int numGenes(String a) {
		int numMutations = 1;
		
		for(int i = 0; i < a.length() - 1; i ++){  // Determines the number of mutated genes carried by the parents
			if(a.substring(i, i + 2).equals("//")){
				numMutations ++;
			}
		}
		
		return numMutations;
		
	}
	
	// Returns true if both the input arguments are true (can only calculate if both are true)
	public boolean canCalculate(boolean a, boolean b) {
		if(a && b) {
			return true;
		}
		return false;
	}
	
	
	public void fillEntriesEntered() { 
		entriesEntered.put("MotherLineArtEntered", false);
		entriesEntered.put("FatherLineArtEntered", false);
		entriesEntered.put("MotherMarkingsEntered", false);
		entriesEntered.put("FatherMarkingsEntered", false);
		entriesEntered.put("MotherColorMutationEntered", false);
		entriesEntered.put("FatherColorMutationEntered", false);
		entriesEntered.put("MotherPhysicalMutationEntered", false);
		entriesEntered.put("FatherPhysicalMutationEntered", false);
		entriesEntered.put("Fertility", false);

	}
}
