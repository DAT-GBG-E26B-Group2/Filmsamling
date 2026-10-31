package datasource;

import domainmodel.Movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private static final String HEADER =
            "title;director;yearCreated;inColor;lengthInMinutes;genre;lastWatched";

    private String fileName;
    private int skippedLineCount;

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    public void saveMovies(ArrayList<Movie> movies) throws FileNotFoundException {

        PrintStream output = new PrintStream(new File(fileName));

        output.println(HEADER);

        for (Movie movie : movies) {
            output.println(toCsvLine(movie));
        }

        output.close();
    }

    private String toCsvLine(Movie movie) {

        String lastWatched = "";

        if (movie.hasBeenWatched()) {
            lastWatched = movie.getLastWatched().toString();
        }

        return movie.getTitle() + ";"
                + movie.getDirector() + ";"
                + movie.getYearCreated() + ";"
                + movie.isInColor() + ";"
                + movie.getLengthInMinutes() + ";"
                + movie.getGenre() + ";"
                + lastWatched;
    }

    public int getSkippedLineCount() {
        return skippedLineCount;
    }

    private Movie parseMovie(String line) {

        String[] fields = line.split(";", -1);

        if (fields.length != 7) {
            return null;
        }

        try {
            String title = fields[0];
            String director = fields[1];
            int yearCreated = Integer.parseInt(fields[2]);

            // Check that the color value is valid
            if (!fields[3].equalsIgnoreCase("true") && !fields[3].equalsIgnoreCase("false")) {
                return null;
            }
            boolean inColor = Boolean.parseBoolean(fields[3]);
            int lengthInMinutes = Integer.parseInt(fields[4]);
            String genre = fields[5];

            Movie movie = new Movie(
                    title,
                    director,
                    yearCreated,
                    inColor,
                    lengthInMinutes,
                    genre
            );

            if (!fields[6].isEmpty()) {
                movie.markAsWatched(LocalDate.parse(fields[6]));
            }

            return movie;

        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public ArrayList<Movie> loadMovies() throws FileNotFoundException {

        ArrayList<Movie> loadedMovies = new ArrayList<>();

        skippedLineCount = 0;

        try (Scanner fileScanner = new Scanner(new File(fileName))) {

            // Skip the header
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
            }

            // Read the remaining lines
            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                Movie movie = parseMovie(line);

                if (movie != null) {
                    loadedMovies.add(movie);
                } else {
                    skippedLineCount++;
                }
            }
        }

        return loadedMovies;
    }
}
