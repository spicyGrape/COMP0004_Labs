package Lab_1;

import EasyIO.Input;

// Written by Shuaiting Li, January 2025
// Solution to lab sheet 1 Q1.9.
// A program to determine whether a long int is a palindrome
public class IntPalindrome {
    public boolean is_palindrome(long n) {
        long n_copy = n;
        long reversed = 0;
        while (n != 0) {
            reversed = reversed * 10 + n % 10;
            n /= 10;
        }
        return reversed == n_copy;
    }

    public static void main(String[] args) {
        IntPalindrome ip = new IntPalindrome();
        System.out.print("Please enter a long int: ");
        System.out.println(ip.is_palindrome(new Input().nextLong()));
    }
}
