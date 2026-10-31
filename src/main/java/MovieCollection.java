import java.util.ArrayList;
import java.time.LocalDate;

public class MovieCollection {
    private ArrayList<Movie> movies;

    public MovieCollection() {
        movies = new ArrayList<>();
    }

    //Opret og tilføj ny film til samling
    public void addMovie(String title, String director, int yearCreated, boolean inColor,
                         int lengthInMinutes, String genre) {
        Movie movie = new Movie(title, director, yearCreated, inColor, lengthInMinutes, genre);
        movies.add(movie);
    }

    //Returnere alle film
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    //Returnere hvor mange film man har i samling
    public int getNumberOfMovies() {
        return movies.size();
    }

    // Finder alle film, hvor søgeteksten indgår i titlen – uanset store og små bogstaver
    public ArrayList<Movie> searchMovies(String searchText) {
        ArrayList<Movie> matches = new ArrayList<>();
        String text = searchText.toLowerCase();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(text)) {
                matches.add(movie);
            }
        }
        return matches;
    }

    //Redigere en film hvis den er i samling samt opdatere kun de felter som er angivet
    public boolean editMovie(Movie movie, String title, String director, int yearCreated, boolean inColor, int lengthInMinutes, String genre) {

        // Check om film er i collectino
        if (!movies.contains(movie)) {
            return false;
        }

        // Check alle ny values før man redigere film
        new Movie(title, director, yearCreated, inColor, lengthInMinutes, genre);

        // Opdatere film
        movie.setTitle(title);
        movie.setDirector(director);
        movie.setYearCreated(yearCreated);
        movie.setInColor(inColor);
        movie.setLengthInMinutes(lengthInMinutes);
        movie.setGenre(genre);

        return true;
    }

    public boolean deleteMovie(Movie movie) {
        return movies.remove(movie);
    }

    //Finder og returnere alle film som er slet ikke blevet set eller som er set for lang tid siden (siden date)
    public ArrayList<Movie> getMoviesNotWatchedSince(LocalDate date) {

        ArrayList<Movie> result = new ArrayList<>();

        for (Movie movie : movies) {

            if (!movie.hasBeenWatched() || movie.getLastWatched().isBefore(date)) {
                result.add(movie);
            }
        }

        return result;
    }
}
