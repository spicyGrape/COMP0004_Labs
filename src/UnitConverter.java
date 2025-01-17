// Written by Shuaiting Li, January 2025
// Solution to lab sheet 1 Q1.8
// An interactive program to convert units.
public class UnitConverter {
    private int showMenu() {
        System.out.println("1. Convert from millimetres to feet.");
        System.out.println("2. Convert from metres to inches.");
        System.out.println("3. Convert kilometres to yards.");
        System.out.println("4. Quit.");
        Input in = new Input();
        do {
            int choice = in.nextInt();
            if (choice > 0 && choice < 5) {
                return choice;
            } else {
                System.out.println("Invalid choice! Please choose again!");
            }
        } while (true);
    }

    private double getUserInput() {
        return new Input().nextDouble();
    }

    private void mTOInches() {
        System.out.print("Converting metres to inches. Please enter an length in metres: ");
        double m = this.getUserInput();
        double inches = m * 0.00328084;
        System.out.println("That's: " + inches + " inches.");

    }

    private void mmToFeet() {
        System.out.print("Converting millimetre to feet. Please enter an length in millimetre: ");
        double mm = this.getUserInput();
        double feet = mm * 0.00328084;
        System.out.println("That's: " + feet + " feet.");

    }

    private void kmTOYards() {
        System.out.print("Converting kilometres to yards. Please enter an length in kilometres: ");
        double km = this.getUserInput();
        double yards = km * 0.00328084;
        System.out.println("That's: " + yards + " yards.");

    }

    public void run() {
        while (true) {
            int userChoice = this.showMenu();
            if (userChoice == 4) {
                break;
            } else {
                switch (userChoice) {
                    case 1:
                        this.mmToFeet();
                        break;
                    case 2:
                        this.mTOInches();
                        break;
                    case 3:
                        this.kmTOYards();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new UnitConverter().run();
    }
}

