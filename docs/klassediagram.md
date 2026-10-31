
# Klassediagram – Filmsamling

Dette klassediagram viser klasserne i vores Filmsamling-projekt,
deres attributter, metoder og relationer.

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
        +Controller()
        +addMovie(...) void
        +getMovies() ArrayList~Movie~
        +searchMovies(String searchText) ArrayList~Movie~
        +editMovie(...) boolean
        +deleteMovie(Movie movie) boolean
        +markAsWatched(Movie movie, LocalDate date) void
        +getMoviesNotWatchedForDays(int days) ArrayList~Movie~
    }

    class MovieCollection {
        -ArrayList~Movie~ movies
        +addMovie(...) void
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

    Main ..> UserInterface : starter
    UserInterface --> Controller : bruger
    Controller *-- MovieCollection : ejer
    MovieCollection "1" o-- "0..*" Movie : indeholder
```

## Forklaring

- **Main:** Starter programmet.
- **UserInterface:** Viser menuen og modtager brugerens input.
- **Controller:** Forbinder brugerfladen med filmsamlingen.
- **MovieCollection:** Gemmer film og håndterer søgning, redigering og sletning.
- **Movie:** Indeholder oplysninger om en film og datoen, hvor den sidst blev set.

## UML-symboler

- `+` betyder public.
- `-` betyder private.
- `~` betyder package-private.
- `-->` viser en relation mellem klasser.
- `*--` viser komposition.
- `o--` viser aggregation.
