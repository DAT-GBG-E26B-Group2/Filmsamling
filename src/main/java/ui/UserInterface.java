package ui;
import java.io.FileNotFoundException;
import domainmodel.Controller;
import domainmodel.Movie;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class UserInterface {
    static Controller controller = new Controller();

    public static void StartProgram() {
        System.out.println("Velkommen til filmsamling!");
        Scanner scanner = new Scanner(System.in);
        try {
            int loadedMovies = controller.loadMovies();
            System.out.println("Indlæste " + loadedMovies + " film.");
        } catch (FileNotFoundException e) {
            System.out.println("Ingen eksisterende filmsamling fundet. Starter en ny.");
        }

        boolean running = true;
        while(running) {
            System.out.println("Vælg en af følgende muligheder:");
            System.out.println("1: Opret en film");
            System.out.println("2: Vis liste over alle film");
            System.out.println("3: Søg efter specifik film");
            System.out.println("4: Rediger film");
            System.out.println("5: Slet en film");
            System.out.println("6: Markér en film som set");
            System.out.println("7: Vis film, der ikke er set i X dage");
            System.out.println("8: Vis film sorteret efter...");
            System.out.println("0: Sluk program");

            System.out.print("Indtast dit valg:");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> createMovie(scanner);
                case "2" -> showAllMovies(scanner);
                case "3" -> searchMovies(scanner);
                case "4" -> editMovie(scanner);
                case "5" -> deleteMovie(scanner);
                case "6" -> markMovieAsWatched(scanner);
                case "7" -> showMoviesNotWatchedForDays(scanner);
                case "8" -> sortMovies(scanner);
                case "0" -> running = false;
                default -> System.out.println("Ukendt valg: " + choice);
            }
        }
        try {
            boolean saved = controller.saveMoviesIfChanged();

            if (saved) {
                System.out.println("Filmsamlingen er blevet gemt.");
            } else {
                System.out.println("Ingen ændringer at gemme.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Kunne ikke gemme filmsamlingen.");
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

        // Next step: send the values to the domainmodel.Controller
        controller.addMovie(movieName, director, year, color, length, genre );
        System.out.println("Du har tilføjet: " + movieName + " Til databasen");
    }

    private static void sortMovies(Scanner scanner) {
        System.out.println("Hvordan vil du sortere filmene?");
        System.out.println("1: Titel");
        System.out.println("2: Instruktør");
        System.out.println("3: Årstal");
        System.out.println("4: Længde");
        System.out.println("5: Genre");

        System.out.print("Indtast dit valg: ");
        String choice = scanner.nextLine().trim();

        switch (choice){
            case "1" -> controller.sortMovies("title");
            case "2" -> controller.sortMovies("director");
            case "3" -> controller.sortMovies("year");
            case "4" -> controller.sortMovies("length");
            case "5" -> controller.sortMovies("genre");
            default -> {
                System.out.println("Ugyldigt valg. Prøv igen.");
                return;
            }
        }
        displayMovies(scanner);
    }
    private static void showAllMovies(Scanner scanner) {
        controller.sortMoviesByTitle();
        displayMovies(scanner);
    }
    private static void displayMovies(Scanner scanner) {
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
                Movie movie = movies.get(i);

                System.out.println((i + 1) + ": " + movie.getTitle());
                System.out.println("   Instruktør: " + movie.getDirector());
                System.out.println("   Årstal: " + movie.getYearCreated());
                System.out.println("   Farve: " + (movie.isInColor() ? "Ja" : "Nej"));
                System.out.println("   Længde: " + movie.getLengthInMinutes() + " minutter");
                System.out.println("   Genre: " + movie.getGenre());
                System.out.println();
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
        ArrayList<Movie> results = controller.searchMovies(search);
        boolean found = false;
        for (Movie movie : results) {
            System.out.println(movie.getTitle());
            found = true;
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
                        controller.editMovie(movie,newTitle, movie.getDirector(), movie.getYearCreated(), movie.isInColor(), movie.getLengthInMinutes(), movie.getGenre());
                        System.out.println("Titlen er blevet ændret");
                    }
                    case "2" -> {
                        System.out.println("Indtast en ny instruktør: ");
                        String newInstruktor = scanner.nextLine();
                        controller.editMovie(movie,movie.getTitle(), newInstruktor, movie.getYearCreated(), movie.isInColor(), movie.getLengthInMinutes(), movie.getGenre());
                        System.out.println("Instruktøren er blevet ændret!");
                    }
                    case "3" -> {
                        System.out.println("Indtast nyt årstal: ");
                        int newYear = scanner.nextInt();
                        controller.editMovie(movie,movie.getTitle(), movie.getDirector(), newYear, movie.isInColor(), movie.getLengthInMinutes(), movie.getGenre());
                        scanner.nextLine();
                        System.out.println("Årstallet er blevet ændret!");
                    }
                    case "4" -> {
                        System.out.println("Er filmen i farver? (True eller false): ");
                        boolean newColor = scanner.nextBoolean();
                        controller.editMovie(movie, movie.getTitle(), movie.getDirector(), movie.getYearCreated(), newColor, movie.getLengthInMinutes(), movie.getGenre());
                        scanner.nextLine();
                        System.out.println("Farven er blevet ændret!");
                    }
                    case "5" -> {
                        System.out.println("Indtast nyt længde i minutter: ");
                        int newLength = scanner.nextInt();
                        controller.editMovie(movie, movie.getTitle(), movie.getDirector(), movie.getYearCreated(), movie.isInColor(), newLength, movie.getGenre());
                        scanner.nextLine();
                        System.out.println("Længden er blevet ændret!");
                    }
                    case "6" -> {
                        System.out.println("Indtast en ny genre: ");
                        String newGenre = scanner.nextLine();
                        controller.editMovie(movie, movie.getTitle(), movie.getDirector(), movie.getYearCreated(), movie.isInColor(), movie.getLengthInMinutes(), newGenre);
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

        ArrayList<Movie> results = controller.searchMovies(delete);

        if (!results.isEmpty()) {
            Movie movie = results.get(0);
            boolean deleted = controller.deleteMovie(movie);

            if (deleted) {
                System.out.println("Filmen er blevet slettet");
            }
        } else {
            System.out.println("Filmen blev ikke fundet!");
        }
    }

    private static void markMovieAsWatched(Scanner scanner) {
        System.out.println("Indtast titlen på filmen, du har set:");
        String title = scanner.nextLine().trim();

        ArrayList<Movie> results = controller.searchMovies(title);

        Movie selectedMovie = null;

        for (Movie movie : results) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                selectedMovie = movie;
                break;
            }
        }

        if (selectedMovie == null) {
            System.out.println("Filmen blev ikke fundet!");
            return;
        }

        System.out.println("Hvornår så du filmen?");
        System.out.println("1: I dag");
        System.out.println("2: En tidligere dato");
        System.out.print("Indtast dit valg: ");

        String choice = scanner.nextLine().trim();
        LocalDate date;

        switch (choice) {
            case "1" -> date = LocalDate.now();

            case "2" -> {
                System.out.println("Indtast dato (ÅÅÅÅ-MM-DD):");
                String dateInput = scanner.nextLine().trim();

                try {
                    date = LocalDate.parse(dateInput);
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Ugyldig dato. Brug formatet ÅÅÅÅ-MM-DD.");
                    return;
                }
            }

            default -> {
                System.out.println("Ugyldigt valg.");
                return;
            }
        }

        try {
            controller.markAsWatched(selectedMovie, date);

            System.out.println("Filmen '" + selectedMovie.getTitle() + "' er markeret som set den " + date);

        } catch (IllegalArgumentException e) {
            System.out.println("Ugyldig dato: " + e.getMessage());
        }
    }

    private static void showMoviesNotWatchedForDays(Scanner scanner) {
        System.out.println("Hvor mange dage må der være gået?");
        String input = scanner.nextLine().trim();

        int days;

        try {
            days = Integer.parseInt(input);

            if (days < 0) {
                System.out.println("Antal dage må ikke være negativt.");
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println("Du skal indtaste et gyldigt heltal.");
            return;
        }

        ArrayList<Movie> movies = controller.getMoviesNotWatchedForDays(days);

        if (movies.isEmpty()) {
            System.out.println("Ingen film fundet.");
            return;
        }

        System.out.println("Film, der ikke er set i mindst " + days + " dage:");

        for (Movie movie : movies) {
            System.out.println("- " + movie.getTitle());

            if (movie.hasBeenWatched()) {
                System.out.println("  Sidst set: " + movie.getLastWatched());
            } else {
                System.out.println("  Aldrig set");
            }
        }
    }

}
