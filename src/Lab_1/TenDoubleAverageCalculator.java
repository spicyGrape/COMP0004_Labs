package Lab_1;

import EasyIO.Input;

// Written by Shuaiting Li, January 2025
// Solution to LabSheet 1 Q1.2
// A program that takes 10 doubles and return the average of them.
public class TenDoubleAverageCalculator {
    private double[] takeTenDoubles() {
        Input in = new Input();
        double[] numbers = new double[10];
        for (int i = 0; i < 10; i++) {
            System.out.print("Please enter the " + (i + 1) + "th number: ");
            numbers[i] = in.nextDouble();
        }
        return numbers;
    }

    private double calculateAverage(double[] numbers) {
        double sum = 0;
        for (double number : numbers) {
            sum += number;
        }
        return sum / numbers.length;
    }

    public void calTenDoubleAverage() {
        System.out.println("The average of the ten entered doubles is: " + this.calculateAverage(this.takeTenDoubles()));
    }

    public static void main(String[] args) {
        TenDoubleAverageCalculator cal = new TenDoubleAverageCalculator();
        cal.calTenDoubleAverage();
    }
}
