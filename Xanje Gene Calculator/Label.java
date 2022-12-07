import java.awt.Color; // Imports
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JLabel;

// Custom child class of JLabel that allows for easier adjustments when creating labels
public class Label extends JLabel{

	private Dimension labelSize; // The dimension of the current label
	private ColorScheme colors;  // ColorScheme declaration for access to universal color scheme class
	
	private Color textColor; // Color of text for this label
	
	// This constructor throws these two exceptions if the file reading aspect fails
	Label(String text, int xCoor, int yCoor, Font font) throws ClassNotFoundException, IOException{
		
		// Initializes colors object to "null" so that it can be instantiated via the file reader
		colors = null;
				
		// File reading system that reads the byte code of a saved color scheme
		FileInputStream fileIn = new FileInputStream("ColorScheme.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		colors = (ColorScheme) in.readObject();
		in.close();
		fileIn.close();
		
		textColor = colors.getTextColor();
		
		this.setText(text);
		this.setForeground(textColor);
		this.setFont(font);
		labelSize = this.getPreferredSize();
		this.setBounds(xCoor - labelSize.width / 2 , yCoor, labelSize.width + 50, labelSize.height + 50);
	}
	
}
