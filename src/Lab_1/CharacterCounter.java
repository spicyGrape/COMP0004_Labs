package Lab_1;

import EasyIO.FileInput;
import EasyIO.Input;

import java.util.HashMap;

// Written by Shuaiting Li, January 2025
// Solution to Lab Sheet 1 Q1.11
// A program to read characters from a file and count the frequency of the characters.
public class CharacterCounter {
    private HashMap<Character, Integer> counter = new HashMap<>();

    private String getUserInput() {
        Input in = new Input();
        return in.nextLine();
    }

    public void start() {
        getFileContent(getUserInput());
        printStatistics();
    }

    private void getFileContent(String fileName) {
        FileInput in = new FileInput(fileName);

        // iterate though all lines
        while (in.hasNextLine()) {
            String line = in.nextLine();
            int length = line.length();

            // iterate though all characters in the line
            for (int i = 0; i < length; i++) {
                char c = line.charAt(i);

                // if the character has been recorded
                if (this.counter.containsKey(c)) {
                    this.counter.put(c, this.counter.get(c) + 1);
                } else {
                    this.counter.put(c, 1);
                }
            }
        }
        in.close();
    }

    private void printStatistics() {
        for (char c : this.counter.keySet()) {
            System.out.printf("The frequency of character '%c' is: %d.\n", c, this.counter.get(c));
        }
    }

    public static void main(String[] args) {
        new CharacterCounter().start();
    }
}
