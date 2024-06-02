import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Driver {
    public static void main(String[] args) {
        File file = new File("polynomial.txt");

        try {
            Scanner scanner = new Scanner(file);
            String polynomialString = scanner.nextLine().trim();
            scanner.close();

            String[] terms = polynomialString.split("(?=[+-])");

            for (String term : terms) {
                term = term.trim();

                if (term.contains("x")) {
                    String[] parts = term.split("x\\^?");
                    double coefficient = parts[0].isEmpty() || parts[0].equals("+") ? 1 :
                                         parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]);
                    int exponent = parts.length == 1 ? 1 : Integer.parseInt(parts[1]);

                    System.out.println("Coefficient: " + coefficient);
                    System.out.println("Exponent: " + exponent);
                } else {
                    double coefficient = Double.parseDouble(term);
                    int exponent = 0;  // Exponent is 0 for constant term

                    System.out.println("Coefficient: " + coefficient);
                    System.out.println("Exponent: " + exponent);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}