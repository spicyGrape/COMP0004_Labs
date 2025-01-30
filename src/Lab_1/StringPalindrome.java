package Lab_1;

import EasyIO.Input;

// Written by Shuaiting Li, January 2025
// Solution to Lab Sheet 1 Q1.10.
// A program to remove all whitespaces and punctuations in a string, then decided whether it is a palindrome.
public class StringPalindrome {
    public boolean is_palindrome(String s) {
        s = s.replaceAll("[^a-zA-Z0-9]", "");
        s = s.toLowerCase();
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public static void main(String[] args) {
        StringPalindrome sp = new StringPalindrome();
        System.out.print("Please enter a String: ");
        System.out.println(sp.is_palindrome(new Input().nextLine()));
    }
}
