package domainmodel;

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

    //Check that movie title that is empty will throw IllegalArgumentException
    @Test
    void emptyTitleIsRejected() {
        // Arrange + Act + Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Movie("", "Steven Spielberg", 1975, true, 124, "Thriller")
        );
    }

    //Check that year 1887 is rejected
    @Test
    void yearBefore1888IsRejected() {

        // Arrange
        int invalidYear = 1887;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Movie("Jaws", "Steven Spielberg", invalidYear, true, 124, "Thriller")
        );
    }

    //Check that year 1888 must be accpeted
    @Test
    void year1888IsAccepted() {

        // Arrange
        int validYear = 1888;

        // Act
        Movie movie = new Movie("Roundhay Garden Scene", "Louis Le Prince", validYear, false, 1, "Historical");

        // Assert
        assertEquals(1888, movie.getYearCreated());
    }

    //Check that  movie year that is 2 years in future is rejected (only +1 year can be accepted)
    @Test
    void yearTwoYearsInFutureIsRejected() {

        // Arrange
        int futureYear = LocalDate.now().getYear() + 2;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Movie("Future domainmodel.Movie", "Director", futureYear, true, 120, "Sci-fi")
        );
    }

    //Check that a movie with invalid length is rejected
    @Test
    void zeroLengthIsRejected() {

        // Arrange
        int invalidLength = 0;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Movie("Jaws", "Steven Spielberg", 1975, true, invalidLength, "Thriller")
        );
    }

    //Check that a length of a 1 minute is accepted
    @Test
    void oneMinuteLengthIsAccepted() {

        // Arrange
        int validLength = 1;

        // Act
        Movie movie = new Movie(
                "Short Film", "Director", 2020, true, validLength, "Drama"
        );

        // Assert
        assertEquals(1, movie.getLengthInMinutes());
    }

    //Check that invalid length is rejected and the original movie length is still there
    @Test
    void invalidLengthDoesNotChangeOriginalValue() {

        // Arrange
        Movie movie = new Movie(
                "Jaws", "Steven Spielberg", 1975, true, 124, "Thriller"
        );

        // Act
        assertThrows(IllegalArgumentException.class, () ->
                movie.setLengthInMinutes(0)
        );

        // Assert
        assertEquals(124, movie.getLengthInMinutes());
    }


}
