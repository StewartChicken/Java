import java.util.Random; // Imports

// This class carries out all the logic when it comes to breeding two parents together
public class Breeder {

	// Declares two parent objects
	private Parent father;
	private Parent mother;
	
	// probability a mutation is displayed based on fertility level
	private int displayedFertilityLevel; 
	
	// probability a mutation is carried based on fertility level
	private int carriedFertilityLevel; 
	
	// Final offspring strings
	private String offSpring = "";
	
	// Number of mutated color genes
	private int numColorMutationGenes; 
	
	// Number of muted physical genes
	public int numPhysicalMutationGenes;
	
	// These are the different genes available for the color/physical characteristics
	private String[] colorMutationTypes = {"a", "p", "m", "t", "l", "N"}; // "N" added to prevent an IllegalArgumentException should the user enter "NN"
	private String[] physicalMutationTypes = {"c", "f", "h", "l", "e", "i", "r", "t", "b", "w", "s", "y", "a", "z", "q", "X"}; // "X" added to prevent exception
	
	// Constructor
	Breeder(){
		
		// Instantiates parent objects
		father = new Parent();  
		mother = new Parent(); 
		
	}
	
	// Calculates the physical mutation of offspring
	public String calculateRandomPhysicalMutation() {
		
		// Creates the sorted/fully filled physical mutation strings for the father/mother
		// The strings are fully filled, even if the user input is not full length, to make it easier
		// For the program to calculate the genes, so that it doesn't have to deal with dynamic
		// Gene string lengths
		String fatherSortedString = sortPhysicalMutationInput(fillPhysicalMutationStringInput(father.getPhysicalMutation()));
		String motherSortedString = sortPhysicalMutationInput(fillPhysicalMutationStringInput(mother.getPhysicalMutation()));
		
		// Creates the offspring's physical mutation given the father's/mother's strings, and the number of mutations
		String childPhysicalMutation = calculateMutationString(fatherSortedString, motherSortedString, 15);
		
		String randomMutationString = "";
		
		// If a mutation is carried by the offspring
		if(detectCarriedMutation(carriedFertilityLevel)) {
			Random random = new Random();
			
			// Chooses a random mutation of the 15 possibilities
			int x = random.nextInt(15);
			
			// If offspring has a homozygous recessive allele pair and that specific allele is selected to carry a mutation, the child dies
			for(int i = 0; i < 15; i ++) {
				if(i == x) {
					if(isHomozygousRecessive(childPhysicalMutation.substring(i * 4, (i * 4) + 2))) { 
						randomMutationString += childPhysicalMutation.substring(0, i * 4) + "death" + childPhysicalMutation.substring((i * 4) + 2);
						break;
					}else if(isHeterozygous(childPhysicalMutation.substring(i * 4, (i * 4) + 2))) { 
						randomMutationString += childPhysicalMutation.substring(0, i * 4) + getPhysicalMutationType(i) + getPhysicalMutationType(i) + childPhysicalMutation.substring((i * 4) + 2);
						break;
					}else {
						randomMutationString += childPhysicalMutation.substring(0, i * 4) + "X" + getPhysicalMutationType(i) + childPhysicalMutation.substring((i * 4) + 2);
						break;
					}
				}
			}
		}
		
		if(detectDisplayedMutation(displayedFertilityLevel)) {
			Random random = new Random();
			int x = random.nextInt(15);
			
			// If offspring has a homozygous recessive allele pair, or that allele pair is heterozygous, and that specific allele 
			// is selected to display a mutation, the offspring dies
			for(int i = 0; i < 15; i ++) {
				if(i == x) {
					if(isHomozygousRecessive(childPhysicalMutation.substring(i * 4, (i * 4) + 2))) { 
						randomMutationString += childPhysicalMutation.substring(0, i * 4) + "death" + childPhysicalMutation.substring((i * 4) + 2);				
						break;
					}else if(isHeterozygous(childPhysicalMutation.substring(i * 4, (i * 4) + 2))) { 
						randomMutationString += childPhysicalMutation.substring(0, i * 4) + "death" + childPhysicalMutation.substring((i * 4) + 2);						
						break;
					}else {
						randomMutationString += childPhysicalMutation.substring(0, i * 4) + getPhysicalMutationType(i) + getPhysicalMutationType(i) + getColorMutationType(i) + childPhysicalMutation.substring((i * 4) + 2);						
						break;
					}
				}
			}
		}
	
		// Removes any alleles that are homozygous dominant (not mutations) so that the gene string can be formatted/worked with more easily
		if(randomMutationString.length() == 0) {
			return removeNonMutations(childPhysicalMutation, childPhysicalMutation.length());
		}
		
		return removeNonMutations(randomMutationString, randomMutationString.length());
	}
	
	// Same as calculateRandomPhysicalMutation method, except there are only five mutations to choose from, not fifteen 
	public String calculateRandomColorMutation() {
		
		String fatherSortedString = sortColorMutationInput(fillColorMutationStringInput(father.getColorMutation()));
		String motherSortedString = sortColorMutationInput(fillColorMutationStringInput(mother.getColorMutation()));
		
		String childColorMutation = calculateMutationString(fatherSortedString, motherSortedString, 5);
		
		String randomMutationString = "";
		
		if(detectCarriedMutation(carriedFertilityLevel)) {
			Random random = new Random();
			int x = random.nextInt(5);
			
			for(int i = 0; i < 5; i ++) {
				if(i == x) {
					if(isHomozygousRecessive(childColorMutation.substring(i * 4, (i * 4) + 2))) { 
						randomMutationString += childColorMutation.substring(0, i * 4) + "death" + childColorMutation.substring((i * 4) + 2);
						break;
					}else if(isHeterozygous(childColorMutation.substring(i * 4, (i * 4) + 2))) { 
						randomMutationString += childColorMutation.substring(0, i * 4) + getColorMutationType(i) + getColorMutationType(i) + childColorMutation.substring((i * 4) + 2);
						break;
					}else {
						randomMutationString += childColorMutation.substring(0, i * 4) + "N" + getColorMutationType(i) + childColorMutation.substring((i * 4) + 2);
						break;
					}
				}
			}
		}
		
		if(detectDisplayedMutation(displayedFertilityLevel)) {
			Random random = new Random();
			int x = random.nextInt(5);
			
			for(int i = 0; i < 5; i ++) {
				if(i == x) {
					if(isHomozygousRecessive(childColorMutation.substring(i * 4, (i * 4) + 2))) { 
						randomMutationString += childColorMutation.substring(0, i * 4) + "death" + childColorMutation.substring((i * 4) + 2);				
						break;
					}else if(isHeterozygous(childColorMutation.substring(i * 4, (i * 4) + 2))) { 
						randomMutationString += childColorMutation.substring(0, i * 4) + "death" + childColorMutation.substring((i * 4) + 2);						
						break;
					}else {
						randomMutationString += childColorMutation.substring(0, i * 4) + getColorMutationType(i) + getColorMutationType(i) + getColorMutationType(i) + childColorMutation.substring((i * 4) + 2);						
						break;
					}
				}
			}
		}
		
		if(randomMutationString.length() == 0) {
			return removeNonMutations(childColorMutation, childColorMutation.length());
		}
		return removeNonMutations(randomMutationString, randomMutationString.length());
		
	}
	
	// Randomly returns a gender to the offspring
	public String calculateGender() { 
		Random random = new Random();
		int x = random.nextInt(2);
		
		switch(x) {
		case 0:
			return "male";
		case 1:
			return "female";
		default:
			return "Something went wrong";
		}
			
	}
	
	// Returns the number of genes/alleles within a gene string (based on the specified format, where each allele is separated
	// by a '//'
	public int getNumGenes(String a) {
		int numMutations = 1;
		
		for(int i = 0; i < a.length() - 1; i ++){  // Determines the number of mutated genes carried by the parents
			if(a.substring(i, i + 2).equals("//")){
				numMutations ++;
			}
		}
		
		return numMutations;
		
	}
	
	// This name is a bit misleading, it doesn't calculate the genetic mutations (like the calculateRandomColorMutation method)
	// rather, it returns the raw offspring gene string when two parents are bred with each other. The random genetic
	// mutations are later calculated separately within the calculateRandomColorMutation and calculateRandomPhysicalMutation methods
	public  String calculateMutationString(String a, String b, int geneStringLength) { 
		
		String mutation = "";
		
		// Nested for-loop calculates the Punnett squares for each input gene string
		for(int j = 0; j < geneStringLength; j ++) { 
			String first = a.substring(j * 4, (j * 4) + 2);
			String second = b.substring(j * 4, (j * 4)  + 2);
			
			mutation += calculatePSquare(first, second) + "//";
		}
		
		return mutation;

	}
	
	// Calculates the punnett square between two genes
	public String calculatePSquare(String a, String b) { 
		
		String combo1 = a.substring(0, 1) + b.substring(0, 1);
		String combo2 = a.substring(0, 1) + b.substring(1);
		String combo3 =  a.substring(1) + b.substring(0, 1);
		String combo4 =  a.substring(1) + b.substring(1);
		
		Random random = new Random();
		int x = random.nextInt(4);
		
		switch(x) {
		case 0:
			return correctGene(combo1);
		case 1:
			return correctGene(combo2);
		case 2:
			return correctGene(combo3);
		case 3:
			return correctGene(combo4);
		default:
			return "Something went wrong";
		}
	}

	// Calculates offspring fertility (assuming it's female)
	// Fertility is calculated randomly
	public String calculateFertility() {
		Random random = new Random();
		String[] fertilities = {"VHF", "HF", "AF", "LF", "VLF"};
		
		int x = random.nextInt(5);
		return fertilities[x];
	}
	
	// Based on the 'fertility' characteristic of the mother, this method determines whether or not the 
	// offspring carries a mutation. The more fertile the mother is, the lower the probability
	public boolean detectCarriedMutation(int fertility) { 
		Random random = new Random();
		int x = random.nextInt(fertility) + 1;
	
		return (x == 1);
	}
	
	// Same as the 'detectCarriedMutation', but for *displayed* mutations (as opposed to just carried).
	// The probability that a mutation is displayed is generally lower than the mutation being carried
	public boolean detectDisplayedMutation(int fertility) { 
		Random random = new Random();
		int x = random.nextInt(fertility) + 1;
		
		return (x == 1);
		
	}
	
	public boolean isHomozygousDominant(String g) { // Returns if gene is Homozygous Dominant
		
		if(Character.isUpperCase(g.charAt(0)) && Character.isUpperCase(g.charAt(1))) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isHomozygousRecessive(String g) { // Returns if gene is Homozygous Recessive
		if(Character.isLowerCase(g.charAt(0)) && Character.isLowerCase(g.charAt(1))) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isHeterozygous(String g) { // Returns if gene is Heterozygous
		if(Character.isUpperCase(g.charAt(0)) && Character.isLowerCase(g.charAt(1))) {
			return true;
		}else {
			return false;
		}
	}
	
	// Some encapsulation methods
	public String getOffSpringString() { // Returns offspring string
		return offSpring;
	}
		
	public String getColorMutationType(int index) { // Returns the type of color mutation at the index
		return colorMutationTypes[index];
	}
	
	public String getPhysicalMutationType(int index) {
		return physicalMutationTypes[index];
	}
	
	public void setDisplayedFertilityLevel(String f) { // Assigns probability of displaying a gene based on fertility
		
		switch(f) {
		case "VLF": 
			displayedFertilityLevel = 10;
			break;
		case "LF": 
			displayedFertilityLevel = 35;
			break;
		case "AF": 
			displayedFertilityLevel = 75;
			break;
		case "HF": 
			displayedFertilityLevel = 125;
			break;
		case "VHF": 
			displayedFertilityLevel = 200;
			break;
		default:
			displayedFertilityLevel = 0;
			break;
		}
	}
	
	public void setCarriedFertilityLevel(String f) { // Assigns probability of carrying a gene based on fertility
		switch(f) {
		case "VLF": 
			carriedFertilityLevel = 5;
			break;
		case "LF": 
			carriedFertilityLevel = 17;
			break;
		case "AF": 
			carriedFertilityLevel = 30;
			break;
		case "HF": 
			carriedFertilityLevel = 60;
			break;
		case "VHF": 
			carriedFertilityLevel = 125;
			break;
		default:
			carriedFertilityLevel = 0;
			break;
		}
	}
	
	// Puts alleles into the correct format. If a gene is arrange 'aB', the allele is rearranged into 'Ba', 
	// since dominant alleles are supposed to be displayed before recessive alleles
	public String correctGene(String g) {
		
		String gene = g;
		
		if(Character.isLowerCase(g.charAt(0)) && Character.isUpperCase(g.charAt(1))) {
			gene = g.substring(1) + g.substring(0, 1);
		}
		
		return gene;
	}
	
	// Encapsulation methods with built in exception throwing, should an input string be of the wrong format
	public void setFatherLineArt(String s) {
		detectIllegalArgument(s);
		father.setLineArt(s);
	}
	
	public String getFatherLineArt() {
		return father.getLineArt();
	}
	
	public void setFatherMarkings(String s) {
		detectIllegalArgument(s);
		father.setMarkings(s);
	}
	
	public String getFatherMarkings() {
		return father.getMarkings();
	}
	
	public void setFatherColorMutation(String s) {
		detectIllegalMutationArgument(s, colorMutationTypes, 'N');
		father.setColorMutation(s);
	}
	
	public String getFatherColorMutation() {
		return father.getColorMutation();
	}
	
	public void setFatherPhysicalMutation(String s) {
		detectIllegalMutationArgument(s, physicalMutationTypes, 'X');
		father.setPhysicalMutation(s);
	}
	
	public String getFatherPhysicalMutation() {
		return father.getPhysicalMutation();
	}
	
	public void setMotherLineArt(String s) {
		detectIllegalArgument(s);
		mother.setLineArt(s);

	}
	
	public String getMotherLineArt() {
		return mother.getLineArt();
	}
	public void setMotherMarkings(String s) {
		detectIllegalArgument(s);
		mother.setMarkings(s);
	}
	
	public String getMotherMarkings() {
		return mother.getMarkings();
	}
	
	public void setMotherColorMutation(String s) {
		detectIllegalMutationArgument(s, colorMutationTypes, 'N');
		mother.setColorMutation(s);
	}
	
	public String getMotherColorMutation() {
		return mother.getColorMutation();
	}
	
	public void setMotherPhysicalMutation(String s) {
		detectIllegalMutationArgument(s, physicalMutationTypes, 'X');
		mother.setPhysicalMutation(s);
	}
	
	public String getMotherPhysicalMutation() {
		return mother.getPhysicalMutation();
	}
	
	public void setFertilityMother(String s) {
		detectIllegalFertilityArgument(s);
		mother.setFertility(s);
		setDisplayedFertilityLevel(s);
		setCarriedFertilityLevel(s);
		
	}
	
	public String getFertility() {
		return mother.getFertility();
	}
	
	// Puts the user input physical mutation string into a specified format for ease of manipulation
	public String sortPhysicalMutationInput(String input) { 
		
		String[] sortedStringArray = new String[15];
		
		for(int i = 0; i < 15; i ++) {
			sortedStringArray[i] = "XX";
		}
		
		String sortedString = "";
		
		for(int i = 0; i < 15; i ++) {
			for(int j = 0; j < 15; j ++) {
				if(input.substring((4 * j) + 1, (4 * j) + 2).equals(getPhysicalMutationType(i))) {
					sortedStringArray[i] = input.substring(4 * j, (4 * j) + 2);
					break;
				}
			}
		}
		
		for(String i: sortedStringArray) {
			sortedString += i + "//";
		}
		return sortedString.substring(0, sortedString.length() - 2);
	}
	
	// Fills each physical mutation input with ordinary, non-mutated genes until the gene string contains 15 genes.
	// This makes it easier to manipulate/perform calculations with the gene string later in the code
	public String fillPhysicalMutationStringInput(String input) {
		String temp = input;
		
		if(getNumGenes(temp) == 15) {
			return temp;
		}
		return fillPhysicalMutationStringInput(temp + "//XX");
	}
	
	// Sorts color mutation string inputs, same as with the physical mutation string inputs
	public String sortColorMutationInput(String input) { 
		
		String[] sortedStringArray = {"NN", "NN", "NN", "NN", "NN"};
		String sortedString = "";
		
		for(int i = 0; i < 5; i ++) {
			for(int j = 0; j < 5; j ++) {
				if(input.substring((4 * j) + 1, (4 * j) + 2).equals(getColorMutationType(i))) {
					sortedStringArray[i] = input.substring(4 * j, (4 * j) + 2);
					break;
				}
			}
		}
		
		for(String i: sortedStringArray) {
			sortedString += i + "//";
		}
		return sortedString.substring(0, sortedString.length() - 2);
	}
	
	// Fills color mutation string input, same as with physical mutation string input
	public String fillColorMutationStringInput(String input) {
		String temp = input;
		
		if(getNumGenes(temp) == 5) {
			return temp;
		}
		return fillColorMutationStringInput(temp + "//NN");
	}
	
	// Removes any non-mutated, ordinary genes from an input gene string in order to determine which 
	// genes are mutated. The gene strings will then be fed through the fill/sort methods to standardize them
	public String removeNonMutations(String fullString, int geneLength) { 
		String returnString = "";
		 
		for(int i = 0; i < geneLength; i += 2) {
			if(fullString.substring(i).length() >= 5) {
				if(fullString.substring(i, i + 5).equals("death")) {
					returnString += "death" + "//";
					i += 5;
					continue;
				}
			}
			if(!isHomozygousDominant(fullString.substring(i, i + 2))) {
				returnString += fullString.substring(i, i + 2) + "//";
				i += 2;
			}else {
				i += 2;
			}
		}
		if(returnString.length() > 0) {
			return returnString.substring(0, returnString.length() - 2);
		}
		
		return returnString;
	}
	
	// Checks for illegal arguments within the NON-mutated gene strings
	public void detectIllegalArgument(String input) throws IllegalArgumentException{
		char[] characterArray = input.toCharArray();
		
		for(char c: characterArray) {
			if(!Character.isLetter(c)) {
				throw new IllegalArgumentException("Must be a string of letters!!!");
			}
		}
		
	}
	
	// Checks for illegal arguments within the MUTATED gene strings
	public void detectIllegalMutationArgument(String input, String[] cases, char baseGene) throws IllegalArgumentException{
		char[] characterArray = input.toCharArray();
		boolean temp = false;
		
		if(input.length() == 0) {
			throw new IllegalArgumentException("Empty Field");
		}
		
		for(char c: characterArray) {
			if(!Character.isLetter(c) && c != '/') {
				throw new IllegalArgumentException("Must be a proper gene string!!!");
			}
		}
		
		if(input.substring(input.length() - 1).equals("/")) {
			throw new IllegalArgumentException("Remove the forwardslash at the end please");
		}
		
		for(int i = 0; i < input.length(); i ++) {
			if(input.charAt(i) == '/') {
				if(input.charAt(i + 1) != '/' && input.charAt(i - 1) != '/') {
					throw new IllegalArgumentException("Must be proper gene String!!!");
				}
			}
		}
		
		for(String s: cases) {
			
			for(int i = 0; i < input.length(); i ++) {
				if(input.substring(i, i + 1).equals(s)) {
					temp = true;
				}
			}
		}
		if(!temp) {
			throw new IllegalArgumentException("This mutation letter is not part of the gene list");
		}
		
		for(char c: characterArray) {
			if(Character.isUpperCase(c)) {
				if(c != baseGene) {
					throw new IllegalArgumentException("Unknown base gene");
				}
			}
		}
	}
	
	// Detects illegal fertility text field inputs
	public void detectIllegalFertilityArgument(String input) {
		
		String[] fertilityLevels = {"VLF", "LF", "AF", "HF", "VHF"};
		boolean contains = false;
		for(int i = 0; i < 5; i ++) {
			if(input.equals(fertilityLevels[i])) {
				contains = true;
			}
		}
		
		if(!contains) {
			throw new IllegalArgumentException("Not a proper fertility type");
		}
	}
	
}
