import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String question;
    String[] options;
    int correctAnswer;

    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

public class QuizApplication {

    static boolean answered = false;
    static int userAnswer = -1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Question[] quiz = {
            new Question(
                    "1. Method to start a java program?",
                    new String[]{"1. Start()", "2. main()", "3. int()", "4. run()"},
                    2
            ),
            new Question(
                    "2. Which language is used for Android development?",
                    new String[]{"1. Java", "2. Python", "3. C", "4. PHP"},
                    1
            ),
            new Question(
                    "3. Which keyword is used to inherit a class in Java?",
                    new String[]{"1. implement", "2. extends", "3. inherits", "4. super"},
                    2
            ),
            new Question(
                    "4. Size of int in Java?",
                    new String[]{"1. 2bytes", "2. 4bytes", "3. 8bytes", "4. 16bytes"},
                    2
            ),
             new Question(
                    "5. Default access modifer in java ?",
                    new String[]{"1. Public ", "2. Private ", "3. Protected", "4. Package"},
                    4
            )
        };

        int score = 0;
        String[] summary = new String[quiz.length];

        System.out.println("----- ONLINE QUIZ APPLICATION -----");

        for (int i = 0; i < quiz.length; i++) {

            answered = false;
            userAnswer = -1;

            System.out.println("\n" + quiz[i].question);
            for (String option : quiz[i].options) {
                System.out.println(option);
            }

            System.out.println("You have 10 seconds to answer.");

            Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                public void run() {
                    if (!answered) {
                        System.out.println("\nTime is up!");
                        answered = true;
                    }
                }
            }, 10000);

            long startTime = System.currentTimeMillis();

            while (!answered) {
                if (sc.hasNextInt()) {
                    userAnswer = sc.nextInt();
                    answered = true;
                }

                if (System.currentTimeMillis() - startTime >= 10000) {
                    answered = true;
                }
            }

            timer.cancel();

            if (userAnswer == quiz[i].correctAnswer) {
                System.out.println("Correct!");
                score++;
                summary[i] = "Question " + (i + 1) + ": Correct";
            } else if (userAnswer == -1) {
                System.out.println("No answer submitted.");
                summary[i] = "Question " + (i + 1) + ": No Answer";
            } else {
                System.out.println("Wrong!");
                summary[i] = "Question " + (i + 1) + ": Incorrect";
            }
        }

        System.out.println("\n----- RESULT -----");
        System.out.println("Total Questions : " + quiz.length);
        System.out.println("Correct Answers : " + score);
        System.out.println("Wrong Answers   : " + (quiz.length - score));
        System.out.println("Final Score     : " + score + "/" + quiz.length);

        System.out.println("\nSummary:");
        for (String s : summary) {
            System.out.println(s);
        }

        sc.close();


    }
}