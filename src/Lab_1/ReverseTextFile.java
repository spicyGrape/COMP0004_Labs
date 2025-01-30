package Lab_1;

import EasyIO.Input;
import EasyIO.FileInput;

import java.util.ArrayList;

// Written by Shuaiting Li, January 2025
// Solution to lab sheet 1 Q1.12
// A program that take a text file and make a reversed copy.
public class ReverseTextFile {
    private String getUserInput() {
        System.out.println("Please enter the name of the target file: ");
        return new Input().nextLine();
    }

    private ArrayList<String> getFileContent(final String fileName) {
        var lines = new ArrayList<String>();
        var in = new FileInput(fileName);
        while (in.hasNextLine()) {
            lines.add(in.nextLine());
        }
        in.close();
        return lines;
    }

    private String reverseString(String line) {
        String reversedLine = "";
        for (int i = line.length() - 1; i >= 0; i--) {
            reversedLine = reversedLine + line.charAt(i);
        }
        return reversedLine;
    }

    private ArrayList<String> reverseFileContent(final ArrayList<String> lines) {
        var reversedLines = new ArrayList<String>();
        for (int i = lines.size() - 1; i >= 0; i--) {
            reversedLines.add(reverseString(lines.get(i)));
        }
        return reversedLines;
    }

    private void writeReversedContent(String fileName, ArrayList<String> reversedLines) {
        var out = new FileOutput(fileName);
        for (var reversedLine : reversedLines) {
            out.writeString(reversedLine);
            out.writeEndOfLine();
        }
        out.writeEndOfLine();
        out.close();
    }

    public void start() {
        var inName = getUserInput();
        var content = getFileContent(inName);
        var reversed = reverseFileContent(content);
        writeReversedContent("reversedFile.txt", reversed);
    }

    public static void main(String[] args) {
        new ReverseTextFile().start();
    }
}
