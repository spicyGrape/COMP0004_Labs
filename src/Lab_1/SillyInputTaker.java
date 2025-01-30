package Lab_1;

import EasyIO.Input;


// Written by Shuaiting Li, January 2025
// Solution to LabSheet 1, Q1.1.
// A program that inputs a sequence of Strings until the word stop is entered.
public class SillyInputTaker {
    //Take the input and check whether the input is "stop"
    public void takeInput() {
        Input in = new Input();
        do {
            System.out.println("Please enter a String: ");
            String line = in.nextLine();
            if (line.compareTo("stop") == 0) {
                break;
            }
        } while (true);
    }

    public static void main(String[] args) {
        new SillyInputTaker().takeInput();
    }
}
