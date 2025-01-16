// Written by Shuaiting Li, January 2025
// Solution to lab sheet 1 Q1.5
// To take two doubles and return the square root of the sum of them.
public class SquareRootOfSum {

    private double a;
    private double b;

    private void getAB() {
        this.a = this.getDouble();
        this.b = this.getDouble();
    }

    private double getDouble() {
        System.out.print("Please enter a number: ");
        return new Input().nextDouble();
    }

    private double squareRootOfSum(double a, double b) {
        return Math.sqrt(a + b);
    }

    public static void main(String[] args) {
        SquareRootOfSum sq = new SquareRootOfSum();
        sq.getAB();
        double ans = sq.squareRootOfSum(sq.a, sq.b);
        System.out.println("The square root of the numbers are: " + ans);
    }
}
