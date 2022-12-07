import java.awt.Color; // Imports
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.*;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.Border;

/* Class that creates text fields in which the user may enter values
/ A custom class was used (as opposed to a direct instantiation of JTextField objects)
   to allow for more customizability and for the utilization of preset values*/
public class UserEntry{
	
	private JTextField text = new JTextField(); // Creates a JTextField object
	
	// Declares a color schemes object so that all the colors may be controlled by the "ColorScheme" class
	private ColorScheme colors; 
	
	private Color entryTextColor; // Two color variables for the text field text and background
	private Color backgroundColor;
	
	//Constructor
	UserEntry(int xCoor, int yCoor, int width, int height) throws ClassNotFoundException, IOException{ // Throws these exceptions for the file reading system
		
		colors = null;
		
		// Creates a "ColorScheme" object by reading the 'save' file of the ColorScheme class in order to determine which colors will be used
		
		FileInputStream fileIn = new FileInputStream("ColorScheme.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		colors = (ColorScheme) in.readObject();
		in.close();
		fileIn.close();
		
		entryTextColor = colors.getEntryColor();
		backgroundColor = colors.getBackgroundColor();
		
		text.setBounds(xCoor - width / 2, yCoor, width, height);
		text.setBackground(backgroundColor);
		text.setForeground(entryTextColor);
		text.setBorder(null);
		text.setHorizontalAlignment(JTextField.CENTER); // Aligns the cursor to the center of each text field
		
	}
	
	// Encapsulation methods to alter the values
	
	public JTextField getField() {
		return text;
	}
	
	public void setBackgroundColor(Color b) {
		text.setBackground(b);
	}
	
	public void setForegroundColor(Color b) {
		text.setForeground(b);
	}

}
