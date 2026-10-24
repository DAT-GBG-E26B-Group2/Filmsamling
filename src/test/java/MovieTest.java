import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
}
