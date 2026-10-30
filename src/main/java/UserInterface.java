import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    static Controller controller = new Controller();

    static void StartProgram() {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while(running) {
            System.out.println("Velkommen til filmsamling!");

            System.out.print("Vælg en af følgende muligheder:");
            System.out.println("1: Opret en film");
            System.out.println("2: Vis liste over alle film");
            System.out.println("3: Søg efter specifik film");
            System.out.println("4: Rediger liste over film");
            System.out.println("5: Slet en film");
            System.out.println("0: Sluk program");

            System.out.print("Indtast dit valg:");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> createMovie(scanner);
                case "2" -> showAllMovies(scanner);
                case "3" -> searchMovies(scanner);
                case "4" -> editMovie(scanner);
                case "5" -> deleteMovie(scanner);
                case "0" -> running = false;
                default -> System.out.println("Ukendt valg: " + choice);
            }
        }

    }

    private static void createMovie(Scanner scanner) {
        // First step: ask user for all variable values
        System.out.println("Indtast navn: ");
        String movieName = scanner.nextLine();

        System.out.println("Indtast instruktør: ");
        String director = scanner.nextLine();

        System.out.println("Indtast årstal: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Indtast længde i minutter: ");
        int length = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Er filmen i farver? (true/false): ");
        boolean color = scanner.nextBoolean();
        scanner.nextLine();

        System.out.println("Indtast genre: ");
        String genre = scanner.nextLine();

        // Next step: send the values to the Controller
        controller.addMovie(movieName, director, year, color, length, genre );
        System.out.println("Du har tilføjet: " + movieName + " Til databasen");
    }


    private static void showAllMovies(Scanner scanner) {
        ArrayList<Movie> movies = controller.getMovies();

        int pageSize = 9;
        int totalPages = (int) Math.ceil((double) movies.size() / pageSize);

        if (movies.isEmpty()) {
            System.out.println("Der er ingen film i samlingen.");
            return;
        }

        int currentPage = 1;

        while (true) {
            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, movies.size());

            System.out.println("Liste af alle film:");
            System.out.println("Side " + currentPage + " ud af " + totalPages);

            for (int i = startIndex; i < endIndex; i++) {
                System.out.println((i + 1) + ": " + movies.get(i).getTitle());
            }

            System.out.println("Indtast sidetal (0 for at gå tilbage):");
            int nextPage = scanner.nextInt();
            scanner.nextLine();
            if (nextPage == 0) {
                return;
            }

            if (nextPage < 1 || nextPage > totalPages) {
                System.out.println("Ugyldigt sidetal!");
            } else {
                currentPage = nextPage;
            }
        }
    }
    private static void searchMovies(Scanner scanner) {
        System.out.println("Hvilken film leder du efter?: ");
        String search = scanner.nextLine();
        //TILFØJ CONTAINS() FOR AT SØGE EFTER HARRY ISTEDET FOR HARRY POTTER
        ArrayList<Movie> movies = controller.getMovies();
        boolean found = false;
        for (Movie movie : movies) {
            if (search.equalsIgnoreCase(movie.getTitle())) {
                System.out.println(movie.getTitle());
                found = true;
                break;
            }
        }
        if (found == false) {
            System.out.println("Filmen blev ikke fundet!");
        }
    }
    private static void editMovie(Scanner scanner) {
        System.out.println("Hvilken film vil du redigere?: ");
        String search = scanner.nextLine();

        ArrayList<Movie> movies = controller.getMovies();
        boolean found = false;
        for (Movie movie : movies) {
            if (search.equalsIgnoreCase(movie.getTitle())) {
                System.out.println(movie.getTitle());
                found = true;
                System.out.println("1: Titel");
                System.out.println("2: Instruktør");
                System.out.println("3: Årstal");
                System.out.println("4: Farve");
                System.out.println("5: Længde");
                System.out.println("6: Genre");
                System.out.println("0: Gå tilbage");
                System.out.println("Hvilken information vil du redigere?: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1" -> {
                        System.out.println("Indtast en ny titel: ");
                        String newTitle = scanner.nextLine();
                        movie.setTitle(newTitle);
                        System.out.println("Titlen er blevet ændret");
                    }
                    case "2" -> {
                        System.out.println("Indtast en ny instruktør: ");
                        String newInstruktor = scanner.nextLine();
                        movie.setDirector(newInstruktor);
                        System.out.println("Instruktøren er blevet ændret!");
                    }
                    case "3" -> {
                        System.out.println("Indtast nyt årstal: ");
                        int newYear = scanner.nextInt();
                        movie.setYearCreated(newYear);
                        scanner.nextLine();
                        System.out.println("Årstallet er blevet ændret!");
                    }
                    case "4" -> {
                        System.out.println("Er filmen i farver? (True eller false): ");
                        boolean newColor = scanner.nextBoolean();
                        movie.setInColor(newColor);
                        scanner.nextLine();
                        System.out.println("Farven er blevet ændret!");
                    }
                    case "5" -> {
                        System.out.println("Indtast nyt længde i minutter: ");
                        int newLength = scanner.nextInt();
                        movie.setLengthInMinutes(newLength);
                        scanner.nextLine();
                        System.out.println("Længden er blevet ændret!");
                    }
                    case "6" -> {
                        System.out.println("Indtast en ny genre: ");
                        String newGenre = scanner.nextLine();
                        movie.setGenre(newGenre);
                        System.out.println("Genren er blevet ændret!");
                    }
                    case "0" -> {
                        System.out.println("Går tilbage...");
                    }
                    default -> System.out.println("Ugyldigt valg!");
                }
                break;
            }
        }
        if (found == false) {
            System.out.println("Filmen blev ikke fundet!");
        }
    }
    private static void deleteMovie(Scanner scanner) {
        System.out.println("Hvilken film vil du gerne slette?: ");
        String delete = scanner.nextLine();
        //TILFØJ CONTAINS() FOR AT SØGE EFTER HARRY ISTEDET FOR HARRY POTTER
        ArrayList<Movie> movies = controller.getMovies();
        boolean found = false;
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);

            if (delete.equalsIgnoreCase(movie.getTitle())) {
                movies.remove(i);
                System.out.println("Filmen er blevet slettet");
                found = true;
                break;
            }
        }
        if (found == false) {
            System.out.println("Filmen blev ikke fundet!");
        }
    }

}
