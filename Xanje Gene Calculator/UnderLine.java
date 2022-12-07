import java.awt.Color; // Imports
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

// This class extends the JPanel class so that the underlines of the text fields may be drawn and added separately. 
// Each underline is contained within it's own panel object
public class UnderLine extends JPanel{ 

	private ColorScheme colors; 
	
	private Color lineColor; 
	
	// Constructor that throws the necessary exceptions should the file reader fail
	UnderLine(int xCoor, int yCoor, int width, int height) throws IOException, ClassNotFoundException{
		
		colors = null;
		
		FileInputStream fileIn = new FileInputStream("ColorScheme.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		colors = (ColorScheme) in.readObject();
		in.close();
		fileIn.close();
		
		lineColor = colors.getUnderLineColor();
		this.setBounds(xCoor - width / 2, yCoor, width, height);
	
	}
	
	@Override
	public void paint(Graphics g) {
		
		// Creates a Graphics2D object named g2 that is a Graphics2D-casted version of the Graphics "g" argument
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(lineColor);
		g2.drawLine(0, 0, 100, 0);
		
	}
	
	public void setLineColor(Color l) {
		lineColor = l;
		
	}
}
