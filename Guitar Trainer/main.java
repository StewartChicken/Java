import java.io.IOException;

/*
 * Guitar Trainer
 * Evan Poon
 * December 10, 2022
 * 
 * This project was written with the hopes of helping me learn my way around the guitar
 * There will be three main practice features:
 * Chord Practice
 * Note Practice
 * Chord Progression/key practice
 * 
 * The Chord Practice feature will give me a random chord that I must play within
 * a given amount of time (Depends on the input BPM). The selection of chords will be input by the user
 * within the "KnownChords.txt" text file located within the app's directory.
 * 
 * The Note Practice Feature will give me a random note on a random string and
 * I will need to find its correct location on the fret board. The list of notes that the program
 * is allowed to choose from is also located within the app's directory and is labeled "KnownNotes.txt"
 * 
 * The Chord Progression/key practice feature will give me a random chord progression (predetermined by the
 * "ChordProgressions.txt" file in the app's directory) and a key to play it in. The program will randomly choose
 * a key from the list of "KnownKeys.txt" document in the app's directory.
 * 
 * I will also include a feature that allows me to change the BPM of each practice session
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Screen program = new Screen();
        
    }

}

