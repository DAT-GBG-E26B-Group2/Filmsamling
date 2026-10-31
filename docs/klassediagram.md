# Klassediagram – Filmsamling

Dette klassediagram viser klasserne i vores Filmsamling-projekt, deres attributter, metoder og relationer. Diagrammet er opdateret med filhåndtering fra Del 7 (US13 og US14).

```mermaid
classDiagram
    direction TB

    class Main {
        +main(String[] args) void
    }

    class UserInterface {
        ~Controller controller
        ~StartProgram() void
        -createMovie(Scanner scanner) void
        -showAllMovies(Scanner scanner) void
        -searchMovies(Scanner scanner) void
        -editMovie(Scanner scanner) void
        -deleteMovie(Scanner scanner) void
    }

    class Controller {
        -MovieCollection movieCollection
        -FileHandler fileHandler
        -boolean changed
        +Controller()
        +addMovie(...) void
        +getMovies() ArrayList~Movie~
        +searchMovies(String searchText) ArrayList~Movie~
        +editMovie(...) boolean
        +deleteMovie(Movie movie) boolean
        +markAsWatched(Movie movie, LocalDate date) void
        +getMoviesNotWatchedForDays(int days) ArrayList~Movie~
        +loadMovies() int
        +saveMoviesIfChanged() boolean
        +getSkippedLineCount() int
    }

    class MovieCollection {
        -ArrayList~Movie~ movies
        +addMovie(...) void
        +addMovie(Movie movie) void
        +getMovies() ArrayList~Movie~
        +getNumberOfMovies() int
        +searchMovies(String searchText) ArrayList~Movie~
        +editMovie(...) boolean
        +deleteMovie(Movie movie) boolean
        +getMoviesNotWatchedSince(LocalDate date) ArrayList~Movie~
    }

    class Movie {
        -String title
        -String director
        -int yearCreated
        -boolean inColor
        -int lengthInMinutes
        -String genre
        -LocalDate lastWatched
        -int FIRST_MOVIE_YEAR
        +Movie(...)
        +getTitle() String
        +setTitle(String title) void
        +getDirector() String
        +setDirector(String director) void
        +getYearCreated() int
        +setYearCreated(int year) void
        +isInColor() boolean
        +setInColor(boolean color) void
        +getLengthInMinutes() int
        +setLengthInMinutes(int length) void
        +getGenre() String
        +setGenre(String genre) void
        +getLastWatched() LocalDate
        +hasBeenWatched() boolean
        +markAsWatched(LocalDate date) void
        +getDaysSinceWatched(LocalDate today) long
    }

    class FileHandler {
        -String fileName
        -int skippedLineCount
        -String HEADER
        +FileHandler(String fileName)
        +saveMovies(ArrayList~Movie~ movies) void
        +loadMovies() ArrayList~Movie~
        +getSkippedLineCount() int
        -toCsvLine(Movie movie) String
        -parseMovie(String line) Movie
    }

    Main ..> UserInterface : starter
    UserInterface --> Controller : bruger
    Controller *-- MovieCollection : ejer
    Controller *-- FileHandler : ejer
    MovieCollection "1" o-- "0..*" Movie : indeholder
    FileHandler ..> Movie : gemmer og indlaeser
```

## Forklaring

- **Main:** Starter programmet.
- **UserInterface:** Viser menuen, modtager brugerens input og skal indlæse og gemme filmsamlingen ved opstart og afslutning.
- **Controller:** Forbinder brugerfladen med filmsamlingen og filhåndteringen. Holder styr på ændringer med `changed`.
- **MovieCollection:** Gemmer film og håndterer søgning, redigering og sletning. Kan også tilføje et eksisterende `Movie`-objekt.
- **Movie:** Indeholder oplysninger om en film, validerer værdier og holder styr på datoen, hvor filmen sidst blev set.
- **FileHandler:** Gemmer film i CSV-format, indlæser dem igen og tæller ugyldige linjer.

## UML-symboler

- `+` betyder public.
- `-` betyder private.
- `~` betyder package-private.
- `-->` viser en relation mellem klasser.
- `..>` viser en afhængighed.
- `*--` viser komposition.
- `o--` viser aggregation.
