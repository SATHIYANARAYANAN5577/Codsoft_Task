import java.util.InputMismatchException;
import java.util.Scanner;

public class CourseRegistrationSystem {

    private static CourseDatabase courseDb = new CourseDatabase();
    private static StudentDatabase studentDb = new StudentDatabase();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        courseDb.loadSampleData();
        System.out.println("=====================================================");
        System.out.println("      WELCOME TO THE COURSE REGISTRATION SYSTEM");
        System.out.println("=====================================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> courseDb.listAvailableCourses();
                case 2 -> registerStudent();
                case 3 -> enrollInCourse();
                case 4 -> dropCourse();
                case 5 -> viewStudentCourses();
                case 6 -> studentDb.listAllStudents();
                case 0 -> {
                    running = false;
                    System.out.println("Thank you for using the Course Registration System. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please select a valid option.\n");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("****************** MAIN MENU ************************");
        System.out.println("1. View Available Courses");
        System.out.println("2. Register New Student");
        System.out.println("3. Enroll in a Course");
        System.out.println("4. Drop a Course");
        System.out.println("5. View My Registered Courses");
        System.out.println("6. View All Students");
        System.out.println("0. Exit");
        System.out.println("*******************************************************");
    }

   
    private static void registerStudent() {
        System.out.println("\n--- Register New Student ---");
        String id = readNonEmptyString("Enter Student ID: ");

        if (studentDb.studentExists(id)) {
            System.out.println("A student with ID '" + id + "' already exists.\n");
            return;
        }

        String name = readNonEmptyString("Enter Student Name: ");
        studentDb.addStudent(new Student(id, name));
        System.out.println("Student '" + name + "' registered successfully with ID: " + id + "\n");
    }

  
    private static void enrollInCourse() {
        System.out.println("\n**** Enroll in a Course ****");
        Student student = findStudentOrPrompt();
        if (student == null) return;

        courseDb.listAvailableCourses();
        String courseCode = readNonEmptyString("Enter Course Code to enroll in: ").toUpperCase();

        Course course = courseDb.getCourse(courseCode);
        if (course == null) {
            System.out.println("No course found with code '" + courseCode + "'.\n");
            return;
        }

        if (course.isStudentRegistered(student.getStudentId())) {
            System.out.println(student.getName() + " is already enrolled in " + courseCode + ".\n");
            return;
        }

        if (course.isFull()) {
            System.out.println("Sorry, course '" + courseCode + "' is already full.\n");
            return;
        }

        course.addStudent(student.getStudentId());
        student.addCourse(courseCode);
        System.out.println(student.getName() + " successfully enrolled in " + course.getTitle()
                + " (" + courseCode + "). Remaining slots: " + course.getAvailableSlots() + "\n");
    }

   
    private static void dropCourse() {
        System.out.println("\n**** Drop a Course ****");
        Student student = findStudentOrPrompt();
        if (student == null) return;

        if (student.getRegisteredCourseCodes().isEmpty()) {
            System.out.println(student.getName() + " is not registered for any courses.\n");
            return;
        }

        System.out.println(student.getName() + "'s registered courses: "
                + String.join(", ", student.getRegisteredCourseCodes()));
        String courseCode = readNonEmptyString("Enter Course Code to drop: ").toUpperCase();

        if (!student.isRegisteredFor(courseCode)) {
            System.out.println(student.getName() + " is not registered for '" + courseCode + "'.\n");
            return;
        }

        Course course = courseDb.getCourse(courseCode);
        if (course != null) {
            course.removeStudent(student.getStudentId());
        }
        student.removeCourse(courseCode);
        System.out.println(student.getName() + " successfully dropped course '" + courseCode + "'.\n");
    }

   
    private static void viewStudentCourses() {
        System.out.println("\n**** View Registered Courses ****");
        Student student = findStudentOrPrompt();
        if (student == null) return;

        if (student.getRegisteredCourseCodes().isEmpty()) {
            System.out.println(student.getName() + " has not registered for any courses.\n");
            return;
        }

        System.out.println("\nCourses registered by " + student.getName() + ":");
        for (String code : student.getRegisteredCourseCodes()) {
            Course c = courseDb.getCourse(code);
            if (c != null) {
                System.out.println("**************************************");
                System.out.print(c.getFullDetails());
            }
        }
        System.out.println();
    }

  
    private static Student findStudentOrPrompt() {
        String id = readNonEmptyString("Enter your Student ID: ");
        Student student = studentDb.getStudent(id);
        if (student == null) {
            System.out.println("No student found with ID '" + id + "'. Please register first (Menu option 2).\n");
            return null;
        }
        return student;
    }

    
    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }
}