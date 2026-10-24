import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MovieCollectionTest {

    private MovieCollection collection;

    //SETUP - create a collection with 3 movies so all tests have this collection (runs before each test)
    @BeforeEach
    void setUp() {
        collection = new MovieCollection();
        collection.addMovie("The Godfather", "Francis Ford Coppola", 1972, true, 175, "Crime");
        collection.addMovie("Casablanca", "Michael Curtiz", 1942, false, 102, "Drama");
        collection.addMovie("Godzilla", "Ishirō Honda", 1954, false, 96, "Sci-fi");
    }

    //Check if new collection has 0 movies
    @Test
    void newCollectionIsEmpty() {
        //Arrange
        MovieCollection emptyCollection = new MovieCollection();

        //Act
        int numberOfMovies = emptyCollection.getNumberOfMovies();

        //Assert
        assertEquals(0, numberOfMovies);
    }

    //Check if collection contains exactly 1 movie after creation and that its the correct one
    @Test
    void addOneMovie() {
        //Arrange
        MovieCollection emptyCollection = new MovieCollection();

        //Act
        emptyCollection.addMovie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller");

        //Assert
        assertEquals(1, emptyCollection.getNumberOfMovies());

        Movie addedMovie = emptyCollection.getMovies().getFirst();

        assertEquals("Jaws", addedMovie.getTitle());
        assertEquals("Steven Spielberg", addedMovie.getDirector());
        assertEquals(1975, addedMovie.getYearCreated());
        assertTrue(addedMovie.isInColor());
        assertEquals(124, addedMovie.getLengthInMinutes());
        assertEquals("Thriller", addedMovie.getGenre());
    }

    //Check for when added several movies that the number of movies in collection is correct
    @Test
    void addSeveralMovies() {
        //Arrange
        MovieCollection emptyCollection = new MovieCollection();

        //Act
        emptyCollection.addMovie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller");
        emptyCollection.addMovie("Titanic", "James Cameron", 1997, true, 194, "Romance");
        emptyCollection.addMovie("Avatar", "James Cameron", 2009, true, 162, "Sci-fi");

        //Assert
        assertEquals(3, emptyCollection.getNumberOfMovies());
    }

    //Check that search without a match return empty list and not null
    @Test
    void searchWithoutMatch() {
        //Act
        ArrayList<Movie> result = collection.searchMovies("Jaws");

        //Assert
        assertEquals(0, result.size());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    //Check that search with 1 match is correct
    @Test
    void searchWithMatch() {
        //Act
        ArrayList<Movie> result = collection.searchMovies("Casablanca");

        //Assert
        assertEquals(1, result.size());
        assertEquals("Casablanca", result.getFirst().getTitle());
    }

    //Check that search with more that 1 match is correct
    @Test
    void searchWithSeveralMatches() {
        //Act
        ArrayList<Movie> result = collection.searchMovies("god");

        //Assert
        assertEquals(2, result.size());
    }

    //Check that search is case-insensitive
    @Test
    void searchCaseInsensitive() {
        //Act
        ArrayList<Movie> result = collection.searchMovies("CASA");

        //Assert
        assertEquals(1, result.size());
        assertEquals("Casablanca", result.getFirst().getTitle());
    }

    //Check that edit updates movies info and returns tru
    @Test
    void editMovieUpdate() {
        //Arrange
        Movie movie = collection.searchMovies("Casablanca").getFirst();

        //Act
        boolean edited = collection.editMovie(movie, "Casablanca Remastered", "New Director", 2026, true, 120, "Romance");

        //Assert
        assertTrue(edited);

        assertEquals("Casablanca Remastered", movie.getTitle());
        assertEquals("New Director", movie.getDirector());
        assertEquals(2026, movie.getYearCreated());
        assertTrue(movie.isInColor());
        assertEquals(120, movie.getLengthInMinutes());
        assertEquals("Romance", movie.getGenre());

        assertEquals(3, collection.getNumberOfMovies());
    }

    //Check that movie that is not in collection return false and is not updated
    @Test
    void editMovieNotInCollection() {
        // Arrange
        Movie movie = new Movie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller");

        // Act
        boolean edited = collection.editMovie(movie, "Jaws 2", "New Director", 2026, false, 150, "Horror");

        // Assert
        assertFalse(edited);

        assertEquals("Jaws", movie.getTitle());
        assertEquals("Steven Spielberg", movie.getDirector());
        assertEquals(1975, movie.getYearCreated());
        assertTrue(movie.isInColor());
        assertEquals(124, movie.getLengthInMinutes());
        assertEquals("Thriller", movie.getGenre());

        assertEquals(3, collection.getNumberOfMovies());
    }

    //Check that deleting movie removes it and returns true
    @Test
    void deleteMovieRemovesIt() {
        // Arrange
        Movie casablanca = collection.searchMovies("Casablanca").getFirst();

        // Act
        boolean deleted = collection.deleteMovie(casablanca);

        // Assert
        assertTrue(deleted);
        assertEquals(2, collection.getNumberOfMovies());
        assertTrue(collection.searchMovies("Casablanca").isEmpty());
    }

    //Check that removing movie that is not in collection return false and collection remains the same
    @Test
    void deleteMovieNotInCollection () {
        // Arrange
        Movie movie = new Movie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller");

        // Act
        boolean deleted = collection.deleteMovie(movie);

        // Assert
        assertFalse(deleted);
        assertEquals(3, collection.getNumberOfMovies());
    }
}
