package Lab_1;

import java.util.Random;

// Written by Shuaiting Li, January 2025
// Solution to LabSheet 1 Q1.4
// a program to generate 10000 random doubles between -0.9999999 and +0.9999999 and print out the largest, smallest and the average values.
public class MaxMinInRandom {
    public void doMaxMinAverage() {
        double max = -1;
        double min = 1;
        double sum = 0;
        Random ran = new Random();
        for (int i = 0; i < 10000; i++) {
            double num = ran.nextDouble();
            if (ran.nextBoolean()) {
                num = -num;
            }
            if (num > max) {
                max = num;
            } else if (num < min) {
                min = num;
            }
            sum += num;
        }
        System.out.print("Max is: " + max);
        System.out.print("Min is: " + min);
        System.out.print("Average is: " + sum / 10000);
    }

    public static void main(String[] args) {
        new MaxMinInRandom().doMaxMinAverage();
    }
}
