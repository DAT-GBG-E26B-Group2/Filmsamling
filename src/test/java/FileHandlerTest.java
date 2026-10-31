import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    private static final String TEST_FILE = "test-movies.csv";

    @AfterEach
    void deleteTestFile() {
        new File(TEST_FILE).delete();
    }

    //Check that movies are being saved to csv file and loaded from csv file
    @Test
    void savedMoviesCanBeLoadedAgain() throws FileNotFoundException {

        // Arrange
        FileHandler fileHandler = new FileHandler(TEST_FILE);
        ArrayList<Movie> movies = new ArrayList<>();

        Movie firstMovie = new Movie("Adams æbler", "Anders Thomas Jensen", 2005, true, 94, "Komedie"
        );

        firstMovie.markAsWatched(LocalDate.of(2025, 10, 30));

        Movie secondMovie = new Movie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller"
        );

        movies.add(firstMovie);
        movies.add(secondMovie);

        // Act
        fileHandler.saveMovies(movies);
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        // Assert
        assertEquals(2, loadedMovies.size());

        Movie loadedFirst = loadedMovies.get(0);

        assertEquals("Adams æbler", loadedFirst.getTitle());
        assertEquals("Anders Thomas Jensen", loadedFirst.getDirector());
        assertEquals(2005, loadedFirst.getYearCreated());
        assertTrue(loadedFirst.isInColor());
        assertEquals(94, loadedFirst.getLengthInMinutes());
        assertEquals("Komedie", loadedFirst.getGenre());
        assertEquals(LocalDate.of(2025, 10, 30),
                loadedFirst.getLastWatched());

        Movie loadedSecond = loadedMovies.get(1);

        assertEquals("Jaws", loadedSecond.getTitle());
        assertEquals("Steven Spielberg", loadedSecond.getDirector());
        assertEquals(1975, loadedSecond.getYearCreated());
        assertTrue(loadedSecond.isInColor());
        assertEquals(124, loadedSecond.getLengthInMinutes());
        assertEquals("Thriller", loadedSecond.getGenre());
        assertFalse(loadedSecond.hasBeenWatched());
    }

    //Check that file with 0 movies still contains the header and is loading (should retunr empty list)
    @Test
    void emptyCollectionCanBeSavedAndLoaded() throws FileNotFoundException {

        // Arrange
        FileHandler fileHandler = new FileHandler(TEST_FILE);
        ArrayList<Movie> movies = new ArrayList<>();

        // Act
        fileHandler.saveMovies(movies);
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        // Assert
        assertTrue(loadedMovies.isEmpty());
        assertEquals(0, fileHandler.getSkippedLineCount());
    }

    //Check that if there is no csv file then user gets FileNotFoundException
    @Test
    void missingFileThrowsFileNotFoundException() {

        // Arrange
        FileHandler fileHandler = new FileHandler(TEST_FILE);

        // Act & Assert
        assertThrows(FileNotFoundException.class, () -> {
            fileHandler.loadMovies();
        });
    }

    //Check that corrupted CSV lines are skipped but good lines are returned
    @Test
    void corruptedLinesAreSkipped() throws FileNotFoundException {

        // Arrange
        PrintStream output = new PrintStream(new File(TEST_FILE));

        output.println("title;director;yearCreated;inColor;lengthInMinutes;genre;lastWatched");

        // Valid movie
        output.println("Jaws;Steven Spielberg;1975;true;124;Thriller;");

        // Invalid year
        output.println("Bad Movie;Someone;hello;true;90;Drama;");

        // Missing fields
        output.println("Broken Movie;Unknown;2000");

        output.close();

        FileHandler fileHandler = new FileHandler(TEST_FILE);

        // Act
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        // Assert
        assertEquals(1, loadedMovies.size());
        assertEquals("Jaws", loadedMovies.get(0).getTitle());
        assertEquals(2, fileHandler.getSkippedLineCount());
    }
}
