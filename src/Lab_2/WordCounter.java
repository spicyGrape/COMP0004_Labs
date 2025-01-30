// Written by Shuaiting Li, 30 January 2025
// Solution to lab sheet 2 Q1
// A program that read in a text file and output the number of characters, words and lines it
// contains. Spaces, tabs, newlines and similar characters should all be counted as characters.
// Words should contain only a-z and A-Z. Hyphens, quotes, digits and any other characters are not
// part of a word. This means, for example, that words hyphenated like ‘on-time’ are treated as two
// words.
package Lab_2;

import EasyIO.FileInput;

import java.util.ArrayList;

public class WordCounter {
    private int lineCount;
    private int charCount;
    private int wordCount;
    private ArrayList<String> fileContent;

    public WordCounter(String fileName) {
        fileContent = new ArrayList<>();
        getFileContent(fileName);
    }

    public void printStats() {
        System.out.println("Lines: " + lineCount);
        System.out.println("Characters: " + charCount);
        System.out.println("Words: " + wordCount);
    }

    private void getFileContent(String fileName) {
        FileInput in = new FileInput(fileName);
        while (in.hasNextLine()) {
            fileContent.add(in.nextLine());
        }
        in.close();
    }

    private int countCharInLine(String line) {
        return line.length();
    }

    private boolean isEnglishCharacter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private void countLine() {
        this.lineCount = this.fileContent.size();
    }

    private void countCharAndWord() {
        for (String line : fileContent) {
            this.charCount += countCharInLine(line);
            this.wordCount += countWordInLine(line);
        }
    }


    private int countWordInLine(String line) {
        int wordInLine = 0;
        boolean inWord = false;
        for (char character : line.toCharArray()) {
            if (inWord && !isEnglishCharacter(character)) {
                wordInLine += 1;
                inWord = false;
            }
            if (!inWord && isEnglishCharacter(character)) {
                inWord = true;
            }
        }
        if (inWord) {
            wordInLine += 1;
            inWord = false;
        }
        return wordInLine;
    }

    public void count() {
        countCharAndWord();
        countLine();
    }

    public static void main(String[] args) {
        var countFile = new WordCounter("./.gitignore");
        countFile.count();
        countFile.printStats();
    }
}
