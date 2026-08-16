import java.util.Scanner;

public class GradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("********************************************");
        System.out.println("        STUDENT GRADE CALCULATOR");
        System.out.println("********************************************");

        int numberOfSubjects = readPositiveInt(scanner, "Enter the number of subjects: ");

        double[] marks = new double[numberOfSubjects];
        double totalMarks = 0;

        for (int i = 0; i < numberOfSubjects; i++) {
            double mark = readMark(scanner, "Enter marks obtained in subject " + (i + 1) + " (out of 100): ");
            marks[i] = mark;
            totalMarks += mark;
        }

        double maxPossibleMarks = numberOfSubjects * 100.0;
        double averagePercentage = totalMarks / numberOfSubjects;
        String grade = calculateGrade(averagePercentage);

        System.out.println("\n----------------- RESULTS ----------------");
        System.out.printf("Total Marks       : %.2f / %.0f%n", totalMarks, maxPossibleMarks);
        System.out.printf("Average Percentage: %.2f%%%n", averagePercentage);
        System.out.println("Grade             : " + grade);
        System.out.println("********************************************");

        scanner.close();
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a number greater than 0.");
            } else {
                System.out.println("Invalid input. Please enter a whole number.");
                scanner.next();
            }
        }
    }

    private static double readMark(Scanner scanner, String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                if (value >= 0 && value <= 100) {
                    return value;
                }
                System.out.println("Marks must be between 0 and 100.");
            } else {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.next();
            }
        }
    }

    
    private static String calculateGrade(double percentage) {
        if (percentage >= 90) {
            return "A+ (Outstanding)";
        } else if (percentage >= 80) {
            return "A (Excellent)";
        } else if (percentage >= 70) {
            return "B (Very Good)";
        } else if (percentage >= 60) {
            return "C (Good)";
        } else if (percentage >= 50) {
            return "D (Average)";
        } else if (percentage >= 40) {
            return "E (Pass)";
        } else {
            return "F (Fail)";
        }
    }
}