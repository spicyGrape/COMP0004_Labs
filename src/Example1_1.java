class Example1_1 {
    public int sumOfDigits(int n) {
        int sum = 0;
        n = Math.abs(n);
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    public void inputAndProcess() {
        Input in = new Input();
        int n;
        while (true) {
            System.out.print("Enter an integer: ");
            if (in.hasNextInt()) {
                n = in.nextInt();
                break;
            } else {
                in.nextLine();
                System.out.println("You did not enter an integer. Please try again.");
            }
        }
        System.out.print("The sum of the digits of: " + n);
        System.out.println(" is: " + sumOfDigits(n));
    }

    public void guiInputAndProcess() {
        GUIInput input = new GUIInput();
        int n = input.readInt("Enter an integer: ");
        System.out.print("The sum of the digits of: " + n);
        System.out.println("is: " + sumOfDigits(n) + "\n\nPress Enter to continue...");
    }

    // The main method should do no more than create the object
// and call a method to do the work.
    public static void main(String[] args) {
        new Example1_1().inputAndProcess();
    }
}