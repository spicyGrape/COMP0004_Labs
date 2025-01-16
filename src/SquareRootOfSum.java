// Written by Shuaiting Li, January 2025
// Solution to lab sheet 1 Q1.5
// To take two doubles and return the square root of the sum of them.
public class SquareRootOfSum {
    private double getDouble() {
        System.out.print("Please enter a number: ");
        return new Input().nextDouble();
    }

    private double squareRootOfSum(double a, double b) {
        return Math.sqrt(a + b);
    }

    public static void main(String[] args) {
        SquareRootOfSum sq = new SquareRootOfSum();
        double ans = sq.squareRootOfSum(sq.getDouble(), sq.getDouble());
        System.out.println("The square root of the numbers are: " + ans);
    }
}
