import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class MovieTest {

    //Check that constructor stores all 6 attributes
    @Test
    void constructorSetsAllFields() {
        // Arrange + Act
        Movie movie = new Movie("Titanic", "James Cameron", 1997, true, 194, "Romance");

        // Assert
        assertEquals("Titanic", movie.getTitle());
        assertEquals("James Cameron", movie.getDirector());
        assertEquals(1997, movie.getYearCreated());
        assertTrue(movie.isInColor());
        assertEquals(194, movie.getLengthInMinutes());
        assertEquals("Romance", movie.getGenre());
    }

    //Check that a setter method changes objects attribute
    @Test
    void setTitleChangesTitle() {
        //Arrange
        Movie movie = new Movie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller");

        //Act
        movie.setTitle("Dødens gab");

        //Assert
        assertEquals("Dødens gab", movie.getTitle());
    }

    //Check that just created movie has not been watched (return lastWatched date as null)
    @Test
    void newMovieHasNotBeenWatched() {
        // Arrange
        Movie movie = new Movie(
                "Jaws",
                "Steven Spielberg",
                1975,
                true,
                124,
                "Thriller"
        );

        // Act
        boolean watched = movie.hasBeenWatched();

        // Assert
        assertFalse(watched);
        assertNull(movie.getLastWatched());
    }

    //Check that wachedDate saves the date user watched movie
    @Test
    void markAsWatchedSavesDate() {
        // Arrange
        Movie movie = new Movie(
                "Jaws",
                "Steven Spielberg",
                1975,
                true,
                124,
                "Thriller"
        );

        LocalDate watchedDate = LocalDate.of(2025, 10, 26);

        // Act
        movie.markAsWatched(watchedDate);

        // Assert
        assertTrue(movie.hasBeenWatched());
        assertEquals(watchedDate, movie.getLastWatched());
    }

    //Check that it calculates the correct number of days since user watched a movie
    @Test
    void daysSinceWatchedCountsAcrossMonths() {
        // Arrange
        Movie movie = new Movie(
                "Jaws",
                "Steven Spielberg",
                1975,
                true,
                124,
                "Thriller"
        );

        movie.markAsWatched(LocalDate.of(2025, 9, 30));

        // Act
        long days = movie.getDaysSinceWatched(
                LocalDate.of(2025, 10, 26)
        );

        // Assert
        assertEquals(26, days);
    }
}
