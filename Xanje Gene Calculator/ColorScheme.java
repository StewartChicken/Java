import java.awt.Color; // Imports
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// Color scheme class for a universal color scheme of the code
// This class is frequently serialized when the user saves the present color scheme of the GUI
public class ColorScheme implements Serializable{
	
	// Attribute colors
	private Color backgroundColor;
	private Color textColor;
	private Color underLineColor;
	private Color buttonColor;
	private Color errorColor;
	private Color entryColor;
	private Color menuBarColor;
	private Color offSpringStringColor;
	
	// Default constructor
	ColorScheme(){
	
		backgroundColor = new Color(40, 40, 43);
		textColor = new Color(150, 150, 150);
		underLineColor = new Color(100, 100, 100);
		buttonColor = new Color(60, 60, 63);
		errorColor = new Color(200, 0, 0);
		entryColor = new Color(180, 180, 180);
		menuBarColor = new Color(60, 60, 63);
		offSpringStringColor = new Color(150, 150, 150);

	}
	
	// Encapsulation methods
	
	public void setBackgroundColor(Color b) {
		backgroundColor = b;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setTextColor(Color t) {
		textColor = t;
	}
	
	public Color getTextColor() {
		return textColor;
	}
	
	public void setUnderLineColor(Color u) {
		underLineColor = u;
	}
	
	public Color getUnderLineColor() {
		return underLineColor;
	}
	
	public void setButtonColor(Color b) {
		buttonColor = b;
	}
	
	public Color getButtonColor() {
		return buttonColor;
	}
	
	public void setErrorColor(Color e) {
		errorColor = e;
	}
	
	public Color getErrorColor() {
		return errorColor;
	}
	
	public void setEntryColor(Color e) {
		entryColor = e;
	}
	
	public Color getEntryColor() {
		return entryColor;
	}
	
	public void setMenuBarColor(Color m) {
		menuBarColor = m;
	}
	
	public Color getMenuBarColor() {
		return menuBarColor;
	}
	
	public void setOffSpringStringColor(Color o){
		offSpringStringColor = o;
	}
	
	public Color getOffSpringStringColor() {
		return offSpringStringColor;
	}
	
	// Method for reseting colors to their default values
	public void reset() {
		backgroundColor = new Color(40, 40, 43);
		textColor = new Color(150, 150, 150);
		underLineColor = new Color(100, 100, 100);
		buttonColor = new Color(60, 60, 63);
		errorColor = new Color(200, 0, 0);
		entryColor = new Color(180, 180, 180);
		menuBarColor = new Color(60, 60, 63);
		offSpringStringColor = new Color(150, 150, 150);
	}
	
	// Method that saves the present state of this class to the desired location for later acces
	public void serialize() throws IOException {
		FileOutputStream fileOut = new FileOutputStream("ColorScheme.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();
		
		System.out.println("Saved");
		
	}
}
