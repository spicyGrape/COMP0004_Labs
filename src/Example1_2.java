public class Example1_2 {
    private String getFileName() {
        String fileName = "";
        Input in = new Input();
        while (true) {
            System.out.print("Enter a file name: ");
            if (in.hasNextLine()) {
                fileName = in.nextLine();
                break;
            } else {
                System.out.println("You haven't enter a file name.");
            }
        }
        return fileName;
    }

    private void displayFileContent(String fileName) {
        FileInput fileIn = new FileInput(fileName);
        while (fileIn.hasNextLine()) {
            String s = fileIn.nextLine();
            System.out.println(s);
        }
        fileIn.close();
    }

    public void showFile() {
        String fileName = getFileName();
        displayFileContent(fileName);
    }

    public static void main(String[] args) {
        new Example1_2().showFile();
    }
}
