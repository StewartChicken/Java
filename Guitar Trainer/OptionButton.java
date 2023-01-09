import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

//Custom button class for various tempo/mode options
public class OptionButton extends JButton{
	
	private Font neutralFont;
	private Font boldFont;
	
	private Color backgroundColor;
	private Color neutralTextColor;
	private Color boldTextColor;
	
	private boolean active = false;
	
	public OptionButton(String label) {
		
		neutralFont = new Font("sans-serif", Font.PLAIN, 20);
		boldFont = new Font("sans-serif", Font.BOLD, 20);
		
		backgroundColor = new Color(15, 15, 18);
		neutralTextColor = new Color(100, 100, 100);
		boldTextColor = new Color(130, 130, 130);
		
		this.setText(label);
		this.setFont(neutralFont);
		this.setBackground(backgroundColor);
		this.setForeground(neutralTextColor);
		this.setFocusable(true);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		UIManager.put( "Button.select", backgroundColor);
		
	}
	
	public void setBoldFont() {
		this.setFont(boldFont);
		this.setForeground(boldTextColor);
	}
	
	public void setNeutralFont() {
		this.setFont(neutralFont);
		this.setForeground(neutralTextColor);
	}
	
	public void activate() {
		active = true;
	}
	
	public void deActivate() {
		active = false;
	}
	
	public boolean isActive() {
		return active;
	}

}
