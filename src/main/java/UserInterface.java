import java.sql.SQLOutput;
import java.util.Scanner;

public class UserInterface {
    static Controller controller = new Controller();

    static void StartProgram() {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        System.out.println("Velkommen til filmsamling!");


        while(running) {
            System.out.println("Indtaste din valg:");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> createMovie();
                case "2" -> showAllMovies();
                case "3" -> searchMovies();
                case "4" -> editMovie();
                case "5" -> deleteMovie();
                case "0" -> running = false;
                default -> System.out.println("Ukendt valg: " + choice);
            }
        }

    }

    private static void createMovie() {
    }

    private static void showAllMovies() {
    }

    private static void searchMovies() {
    }

    private static void editMovie() {
    }

    private static void deleteMovie() {
    }

}
