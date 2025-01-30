package Lab_1;

import EasyIO.Input;

// Written by Shuaiting Li, January 2025
// Solution to lab sheet 1 Q1.7
// To translate number into various bases.
public class BaseTranslator {
    public String toBase(int n, int base) {
        return Integer.toString(n, base);
    }

    public static void main(String[] args) {
        Input in = new Input();
        System.out.println(new BaseTranslator().toBase(in.nextInt(), in.nextInt()));
    }
}
