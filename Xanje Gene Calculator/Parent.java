
public class Parent {  // Parent class for each to-be-bread parents' attributes
	
	private String gender; // Parent attributes
	private String lineArt;
	private String markings;
	private String colorMutation;
	private String physicalMutation;
	private String fertility;  // Only mother has fertility
	
	Parent(){ // Default constructor
		
		gender = "";
		lineArt = "";
		markings = "";
		colorMutation = "";
		physicalMutation = "";
		fertility = "";
	}
	
	
	public void setGender(String s) {   // Getter/setter methods
		gender = s;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setLineArt(String s) {
		lineArt = s;
	}
	
	public String getLineArt() {
		return lineArt;
	}
	
	public void setMarkings(String s) {
		markings = s;
	}
	
	public String getMarkings() {
		return markings;
	}
	
	public void setColorMutation(String s) {
		colorMutation = s;
	}
	
	public String getColorMutation() {
		return colorMutation;
	}
	
	public void setPhysicalMutation(String s) {
		physicalMutation = s;
	}
	
	public String getPhysicalMutation() {
		return physicalMutation;
	}
	
	public void setFertility(String s) {
		fertility = s;
	}
	
	public String getFertility() {
		return fertility;
	}
	
}
